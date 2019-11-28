package security;

import java.io.IOException;

import javax.annotation.security.DenyAll;
import javax.annotation.security.PermitAll;
import javax.annotation.security.RolesAllowed;
import javax.ws.rs.container.ContainerRequestContext;
import javax.ws.rs.container.ContainerRequestFilter;
import javax.ws.rs.container.ResourceInfo;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MultivaluedMap;
import javax.ws.rs.core.Response;
import javax.ws.rs.ext.Provider;

import com.sun.jersey.core.util.Base64;

import utils.Constants;

import java.lang.reflect.Method;
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
	
	/**
	 * The method will catch all incoming requests
	 */
	@Override
	public void filter(ContainerRequestContext requestContext) throws IOException {
		
		Method resMethod = resourceInfo.getResourceMethod();
		if(!resMethod.isAnnotationPresent(PermitAll.class))
		{
			if(resMethod.isAnnotationPresent(DenyAll.class))
			{
				// With DenyAll we refuse every access to the method by sending an error message 
				requestContext.abortWith(Response.status(Response.Status.FORBIDDEN)
						.entity(Constants.DENY_ALL_REQUEST_MESSAGE).build());
				return;
			}
		}
		
		// Getting all the headers of the entering request
		final MultivaluedMap<String, String> headers = requestContext.getHeaders();
		
		final List<String> authorization = headers.get(AUTHORIZATION_PROPERTY);
		
		if(authorization == null || authorization.isEmpty())
		{
			requestContext.abortWith(Response.status(Response.Status.FORBIDDEN)
					.entity(Constants.NO_AUTH_HEADER_MESSAGE).build());
			return;
		}
		
		// Get the 64 BITS encoded data containing the username and the passsword
		final String encodedData = authorization.get(0).replaceFirst(AUTHENTICATION_SCHEME + " ", "");
		
		// Decode data to get a string of username and password	
		String usernameAndPassword = new String(Base64.decode(encodedData.getBytes()));
		
		// Split username and password tokens with the ":"
		final StringTokenizer tokenizer = new StringTokenizer(usernameAndPassword,":");
		final String username = tokenizer.nextToken();
		final String password = tokenizer.nextToken();
		
		// Verifying username and password
		System.out.println(username);
		System.out.println(password);
		
		if(resMethod.isAnnotationPresent(RolesAllowed.class))
		{
			RolesAllowed rolesAnn = resMethod.getAnnotation(RolesAllowed.class);
			Set<String> roles = new HashSet<String>(Arrays.asList(rolesAnn.value()));
			
			if(!isUserAdmin(username, password, roles))
			{
				 requestContext.abortWith(Response.status(Response.Status.UNAUTHORIZED)
	                        .entity(Constants.INVALID_CREDENTIALS).build());
	                    return;
			}
		}
	}

	private boolean isUserAdmin(String username, String password, Set<String> roles) {
		boolean requestGranted = false;
		if(username.equals("ADMIN") &&  password.equals("ADMIN"))
		{
			String userRole = "ADMIN";
			if(roles.contains(userRole))
			{
				requestGranted = true;
			}
		}
		return requestGranted;
	}
	
	

}
