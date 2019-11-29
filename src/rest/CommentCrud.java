package rest;

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
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import dao.CommentDao;
import dao.UserDao;
import model.Comment;
import model.User;

@Path("comments")
public class CommentCrud implements CrudBase<Comment>{

	  @Override
	  @GET
	  @Produces({MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML})
	  public List<Comment> getAll() throws SQLException
	  {
		 return new CommentDao().getAll();
	  }
	  
	  @Override
	  @DELETE
	  @RolesAllowed("ADMIN")
	  @Path("/{id}")
	  public void delete(@PathParam("id") int id)
	  {
		 new CommentDao().delete(id);
	  }
	  

	  @Override
	  @PUT
	  @Consumes({MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML})
	  public Response update(Comment c)
	  {
		  try{
			  new CommentDao().update(c);			  
		  }
		  catch(SQLException ex)
		  {
			  return Response.status(500).entity("SQL exception " + ex.getMessage()).build();
		  }
        String result = "User has been updated :  \n"+ c;
		return Response.status(201).entity(result).build();
	  }
	  
	  @Override
	  @GET
	  @Produces({MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML})
	  @Path("/{id}")
	  public Comment find(@PathParam("id") int id) throws SQLException
	  {
		return new CommentDao().get(id);
	  }
	  
	  @Override
	  @POST
	  @Consumes({MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML})
	  public Response add(Comment com){
		  try{
			  new CommentDao().save(com);			  
		  }
		  catch(SQLException ex)
		  {
			  return Response.status(500).entity("SQL exception " + ex.getMessage()).build();
		  } 
		  String result = "Data sent successfully  :  \n"+ com;
		  return Response.status(201).entity(result).build();
	  }
	  
	  //////////// End of implementation of the CrudBase methods///////////
	  	  
	  /**
	   * Returns the user associated to the comment id
	   * @param id
	   * @return
	   * @throws SQLException
	   */
	  @GET
	  @Path("/{id}/user")
	  @Produces({MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML})
	  public User getUserOfComment(@PathParam("id") int id) throws SQLException
	  {
		  return new CommentDao().getAuthor(id);
	  }
	  	 

		
	  	
}

