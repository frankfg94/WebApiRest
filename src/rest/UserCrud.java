package rest;

import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;
import java.sql.SQLException;
import java.util.List;

import javax.annotation.security.RolesAllowed;
import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;

import dao.CommentDao;
import dao.MediaDao;
import dao.UserDao;
import model.Comment;
import model.Media;
import model.User;
import utils.Utilities;

@Path("users")
public class UserCrud implements CrudBase<User> {

      @Override
	  @GET
	  @Produces({MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML})
	  public List<User> getAll() throws SQLException
	  {
		 return new UserDao().getAll();
	  }
	  
      @Override
      @RolesAllowed("USER")
	  @DELETE
	  @Path("/{id}")
	  public void delete(@PathParam("id") int id) throws SQLException
	  {
		 new UserDao().delete(id);
	  }
      
	  @Override	    
	  @PUT
	  @Consumes({MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML})
	  public Response update(User u)
	  {
		  try{
			  new UserDao().update(u);			  
		  }
		  catch(SQLException ex)
		  {
			  return Response.status(500).entity("SQL exception " + ex.getMessage()).build();
		  }
        String result = "User has been updated :  \n"+ u;
		return Response.status(201).entity(result).build();
	  }
	   	  
	  @Override
	  @GET
	  @Produces({MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML})
	  @Path("/{id}")
	  public User find(@PathParam("id") int id) throws SQLException
	  {
		return new UserDao().get(id);
	  }
	  
	  @Override
	  @POST
	  @Consumes({MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML})
	  public Response add(User user){
		    new UserDao().save(user);
	        String result = "Data sent successfully  :  \n"+ user;
	        return Response.status(201).entity(result).build();
	  }
	  
	  //////////// End of implementation of the CrudBase methods///////////
	  
	  	/**
		 * Getting all the comments for this user
		 */
	  	@Path("/{id}/comments")
	  	@GET
	  	@Produces({MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML})
	  	public List<Comment> getAllComments(@PathParam("id") int id) throws SQLException {
			return	new UserDao().getAllComments(id);
		}
	  	
	  	/**
	  	 * Getting a specific comment amongst all the comments
	  	 */
	  	@Path("/{id}/comments/{idCom}")
	  	@GET
	  	@Produces({MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML})
		public Comment getCommentFromAllComments(@PathParam("id") int id, @PathParam("idCom") int idCom) throws SQLException {
			return	new UserDao().getAllComments(id).get(idCom-1);
		}
	  	
	  	/**
	  	 * Deleting a specific comment amongst all the comments of the user
	  	 */
	  	@Path("/{id}/comments/{idCom}")
	  	@RolesAllowed("USER")
	  	@DELETE
		public Response deleteCommentOfUser(@PathParam("id") int id, @PathParam("idCom") int idCom) throws SQLException {
	  		new CommentDao().delete(new UserDao().getAllComments(id).get(idCom-1));
	  		String result = "Comment has been deleted successfully for user "+id+" \n";
			return Response.status(Status.ACCEPTED).entity(result).build();
		}
	  	
	  	/**
		 * Getting all the media for this user
		 */
	  	@Path("/{id}/media")
	  	@GET
	  	@Produces({MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML})
		public List<Media> getAllMediaFromOneUser(@PathParam("id") int id) throws SQLException {
			return	new UserDao().getAllMedia(id);
		}
	  	
	  	/**
	  	 * Getting a specific Media amongst all the Media
	  	 */
	  	@Path("/{id}/media/{idMed}")
	  	@GET
	  	@Produces({MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML})
		public Media getMediaFromAllMedia(@PathParam("id") int id, @PathParam("idMed") int idMed) throws SQLException {
			return new UserDao().getAllMedia(id).get(idMed-1);
		}
	  	
	  	/**
	  	 * Deleting a specific Media amongst all the Media of the user
	  	 */
	  	@Path("/{id}/media/{idMed}")
	  	@RolesAllowed("USER")
	  	@DELETE
		public Response deleteMediaOfUser(@PathParam("id") int id, @PathParam("idMed") int idMed) throws SQLException {
	  		new MediaDao().delete(new UserDao().getAllMedia(id).get(idMed-1));
	  		String result = "Media has been deleted successfully for user "+id+" \n";
			return Response.status(Status.ACCEPTED).entity(result).build();
		}
	  	
	  	/**
	  	 * Hash all the passwords of the users in the database
	  	 * @throws InvalidKeySpecException 
	  	 * @throws NoSuchAlgorithmException 
	  	 */
	  	@Path("/hashAll")
	  	@RolesAllowed("ADMIN")
	  	@GET
		public Response hashPasswords() throws SQLException, NoSuchAlgorithmException, InvalidKeySpecException {
	  		Utilities.HashDbPasswords();
	  		String result = "All the users now have hashed passwords\n";
			return Response.status(Status.ACCEPTED).entity(result).build();
		}
}
