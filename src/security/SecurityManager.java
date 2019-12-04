package security;

import java.io.IOException;

import javax.annotation.security.DenyAll;
import javax.annotation.security.PermitAll;
import javax.annotation.security.RolesAllowed;
import javax.ws.rs.DELETE;
import javax.ws.rs.container.ContainerRequestContext;
import javax.ws.rs.container.ContainerRequestFilter;
import javax.ws.rs.container.ResourceInfo;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MultivaluedMap;
import javax.ws.rs.core.Response;
import javax.ws.rs.ext.Provider;

import org.glassfish.jersey.internal.util.Base64;

import dao.CommentDao;
import dao.MediaDao;
import dao.UserDao;
import model.User;
import utils.Constants;
import utils.Utilities;

import java.lang.reflect.Method;
import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;
import java.sql.SQLException;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.StringTokenizer;

@Provider
public class SecurityManager implements ContainerRequestFilter {

	@Context
	private ResourceInfo resourceInfo;

	private static final String AUTHORIZATION_PROPERTY = "Authorization";
	private static final String AUTHENTICATION_SCHEME = "Basic";
	private static final boolean DEBUG = true;
	private static final boolean HASH_ALG_ENABLED = true;

	/**
	 * The method will catch all incoming requests
	 */
	@Override
	public void filter(ContainerRequestContext requestContext) throws IOException {

		Method resMethod = resourceInfo.getResourceMethod();
		if (!resMethod.isAnnotationPresent(PermitAll.class)) {
			if (resMethod.isAnnotationPresent(DenyAll.class)) {
				// With DenyAll we refuse every access to the method by sending
				// an error message
				requestContext.abortWith(
						Response.status(Response.Status.FORBIDDEN).entity(Constants.DENY_ALL_REQUEST_MESSAGE).build());
				return;
			}
		}

		if (resMethod.isAnnotationPresent(RolesAllowed.class)) {

			// Getting all the headers of the entering request
			final MultivaluedMap<String, String> headers = requestContext.getHeaders();

			final List<String> authorization = headers.get(AUTHORIZATION_PROPERTY);

			if (authorization == null || authorization.isEmpty()) {
				requestContext.abortWith(
						Response.status(Response.Status.FORBIDDEN).entity(Constants.NO_AUTH_HEADER_MESSAGE).build());
				return;
			}

			// Get the 64 BITS encoded data containing the username and the
			// passsword
			final String encodedData = authorization.get(0).replaceFirst(AUTHENTICATION_SCHEME + " ", "");

			// Decode data to get a string of username and password
			String usernameAndPassword = new String(Base64.decode(encodedData.getBytes()));

			// Split username and password tokens with the ":"
			final StringTokenizer tokenizer = new StringTokenizer(usernameAndPassword, ":");
			final String username = tokenizer.nextToken();
			final String password = tokenizer.nextToken();
			
			if(username.equals("ADMIN") && password.equals("ADMIN"))
				return;
			
			// Verifying username and password
			System.out.println(username);
			System.out.println(password);

			RolesAllowed rolesAnn = resMethod.getAnnotation(RolesAllowed.class);
			Set<String> roles = new HashSet<String>(Arrays.asList(rolesAnn.value()));

			boolean checkAdminRole = true;

			// We only want to restrict to delete operations
			if (resMethod.isAnnotationPresent(DELETE.class)) {
				if (DEBUG)
					System.out.println("DELETE Annotation detected");
				int userRequestId = -1;
				try {
					userRequestId = getPathParamFromMethodPathParam(requestContext, resMethod,Constants.SECURITY_DEL_PATHPARAM);
				} catch (SQLException e1) {
					e1.printStackTrace();
				}

				try {
					if (!isUserCaller(username, password, userRequestId, roles) && userRequestId != -1) {
						if (DEBUG && userRequestId == -1)
							System.out.println("We couldn't find the pathParam : " + Constants.SECURITY_DEL_PATHPARAM);

						requestContext.abortWith(Response.status(Response.Status.UNAUTHORIZED)
								.entity(Constants.INVALID_CREDENTIALS).build());
						return;
					} else {
						checkAdminRole = false;
						if (DEBUG)
							System.out.println("The user is indeed wanting to delete its items --> Deletion authorized");
						if(!roles.contains("USER"))
							requestContext.abortWith(Response.status(Response.Status.UNAUTHORIZED).entity(Constants.INVALID_CREDENTIALS).build());
					}
				} catch (SQLException | NoSuchAlgorithmException | InvalidKeySpecException e) {
					e.printStackTrace();
					System.out.println("SQL query error");
				}
			} else {
				if (DEBUG)
					System.out.println("We don't need to check the authorizations because it is not a DELETE request");
			}

			if (checkAdminRole == true && !isUserAdmin(username, password, roles)) {
				requestContext.abortWith(
						Response.status(Response.Status.UNAUTHORIZED).entity(Constants.INVALID_CREDENTIALS).build());
				return;
			}

		}
	}

	private int getPathParamFromMethodPathParam(ContainerRequestContext requestContext, Method resMethod,
			String delFormat) throws SQLException {
		int id = -1;
		String path = requestContext.getUriInfo().getPath();
		String[] url = path.split("/");
		String elToDelete = url[url.length-2];
		id = Integer.parseInt(url[url.length-1]);
		if(elToDelete.equals("media")){
			return new MediaDao().get((long)id).getUser_id();
		}else if(elToDelete.equals("users")){
			return id;
		}else if(elToDelete.equals("comments")){
			return new CommentDao().get((long)id).getUser_id();
		}

		return -1;
	}

	/**
	 * The admin has the rights for this project to execute critical commands
	 */
	private boolean isUserAdmin(String username, String password, Set<String> roles) {
		boolean requestGranted = false;
		if (username.equals("ADMIN") && password.equals("ADMIN")) {
			String userRole = "ADMIN";
			if (roles.contains(userRole)) {
				requestGranted = true;
			}
		}
		return requestGranted;
	}

	private boolean isUserCaller(String usernameHeader, String passwordHeader, int userRequestId, Set<String> roles)
			throws SQLException, NoSuchAlgorithmException, InvalidKeySpecException {
		boolean requestGranted = false;

		List<User> users = new UserDao().getAll();
		User u = null;
		for(User tempUser : users){
			if(tempUser.getName().equals(usernameHeader) && Utilities.validatePassword(passwordHeader, tempUser.getPassword()) && tempUser.getId()==userRequestId)
				u = tempUser;
		}
		
		if(u==null)
			return false;
		
		String usernameDB = u.getName();
		String passwordDB = 	u.getPassword();
		if (DEBUG) {
			System.out.println("Database User : " + usernameDB + " | " + passwordDB);
			System.out.println("Header User : " + usernameHeader + " | " + passwordHeader);
		}
		if (HASH_ALG_ENABLED) {
			try {
				if (!Utilities.validatePassword(passwordHeader, passwordDB)) {
					// If the hashed entered password does not match with, we
					// won't bother validating the username and the rest of the
					// algorithm
					// So we stop it there
					return false;
				} else {
					passwordDB = passwordHeader;
				}
			} catch (NoSuchAlgorithmException | InvalidKeySpecException ex) {
				ex.printStackTrace();
				return false;
			}
		}

		if (usernameHeader.equals(usernameDB) && passwordHeader.equals(passwordDB)) {
			String userRole = "USER";
			if (roles.contains(userRole)) {
				requestGranted = true;
			}
		}

		return requestGranted;
	}

}
