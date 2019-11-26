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
import dao.MediaDao;
import model.Media;
import model.User;

@Path("media")
public class MediaCrud implements CrudBase<Media> {

	@Override
	@GET
	@Produces({ MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML })
	public List<Media> getAll() throws SQLException {
		return new MediaDao().getAll();
	}

	@Override
	@DELETE
	@Path("/{id}")
	public void delete(@PathParam("id") int id) {
		new MediaDao().delete(id);
	}
	
	///Pas mieux de mettre le path a {id} pour ne pas avoir a le remettre en param ensuite ???
	@Override
	@PUT
	@Consumes({ MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML })
	public Response update(Media m) {
		try {
			new MediaDao().update(m);
		} catch (SQLException ex) {
			return Response.status(500).entity("SQL exception " + ex.getMessage()).build();
		}
		String result = "Media has been updated :  \n" + m;
		return Response.status(201).entity(result).build();
	}

	@Override
	@GET
	@Produces({ MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML })
	@Path("/{id}")
	public Media find(@PathParam("id") int id) throws SQLException {
		return new MediaDao().get(id);
	}

	@Override
	@POST
	@Consumes({ MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML })
	public Response add(Media com) {
		try{
			  new MediaDao().save(com);			  
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
	   * Returns the user associated to the media id
	   * @param id
	   * @return
	   * @throws SQLException
	   */
	  @GET
	  @Path("/{id}/user")
	  @Produces({MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML})
	  public User getUserOfMedia(@PathParam("id") int id) throws SQLException
	  {
		  return new MediaDao().getAuthor(id);
	  }

}
