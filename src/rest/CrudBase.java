package rest;

import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;
import java.sql.SQLException;
import java.util.List;
import javax.ws.rs.PathParam;
import javax.ws.rs.core.Response;

public interface  CrudBase<T> {

	/**
	 * Create Read Update Delete
	 * @return
	 * @throws SQLException
	 */

	  public  List<T> getAll() throws SQLException; 

	  public  void delete(@PathParam("id") int id) throws SQLException;
	  
	  public  Response update(T c);
	  
	  public  T find(@PathParam("id") int id) throws SQLException;
	  
	  public  Response add(T com) throws NoSuchAlgorithmException, InvalidKeySpecException;

}