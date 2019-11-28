package rest;

import java.sql.SQLException;
import java.util.List;

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

import dao.CommentDao;
import dao.UserDao;
import model.Comment;
import model.Media;
import model.User;

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
	  @DELETE
	  @Path("/{id}")
	  public void delete(@PathParam("id") int id)
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
		 * Getting all the media for this user
		 */
	  	@Path("/{id}/media")
	  	@GET
	  	@Produces({MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML})
		public List<Media> getAllMediaFromOneUser(@PathParam("id") int id) throws SQLException {
			return	new UserDao().getAllMedia(id);
		}
}
