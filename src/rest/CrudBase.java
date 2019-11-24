package rest;

import java.sql.SQLException;
import java.util.List;

import javax.ws.rs.PathParam;
import javax.ws.rs.core.Response;


public interface  CrudBase<T> {


	  public  List<T> getAll() throws SQLException; 

	  public  void delete(@PathParam("id") int id) throws SQLException;
	  
	  public  Response update(T c);
	  
	  public  T find(@PathParam("id") int id) throws SQLException;
	  
	  public  Response add(T com);

}
