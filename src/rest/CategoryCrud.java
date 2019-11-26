package rest;

import java.sql.SQLException;
import java.util.List;

import javax.ws.rs.core.Response;

import dao.CategoryDao;

import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import model.Category;

@Path("category")
public class CategoryCrud implements CrudBase<Category>{

	@Override
	@GET
	@Produces({MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML})
	public List<Category> getAll() throws SQLException {
		return new CategoryDao().getAll();
	}

	@Override
	@DELETE
	@Path("/{id}")
	public void delete(int id) throws SQLException {
		new CategoryDao().delete(id);
	}

	@Override
	@PUT
	@Consumes({MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML})
	public Response update(Category c) {
		try{
			  new CategoryDao().update(c);			  
		  }
		  catch(SQLException ex)
		  {
			  return Response.status(500).entity("SQL exception " + ex.getMessage()).build();
		  }
		String result = "Category has been updated :  \n"+ c;
		return Response.status(201).entity(result).build();
	}

	@Override
	@GET
	@Produces({MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML})
	@Path("/{id}")
	public Category find(@PathParam("id")int id) throws SQLException {
		return new CategoryDao().get(id);
	}

	@Override
	@POST
	@Consumes({MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML})
	public Response add(Category com) {
		try{
			  new CategoryDao().save(com);			  
		  }
		  catch(SQLException ex)
		  {
			  return Response.status(500).entity("SQL exception " + ex.getMessage()).build();
		  } 
		  String result = "Data sent successfully  :  \n"+ com;
		  return Response.status(201).entity(result).build();
	}

}
