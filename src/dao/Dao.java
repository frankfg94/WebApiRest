package dao;

import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.List;
import utils.Constants;

public abstract class Dao<T> {
	
	/* The connection we use to query our database*/
	private static Connection c;
	
	/* The methods that will use our connection */
	
	 	abstract T get(long id) throws SQLException;
	    abstract List<T> getAll() throws SQLException;
	    abstract void save(T t) throws SQLException, NoSuchAlgorithmException, InvalidKeySpecException;	     
	    abstract void update(T t)  throws SQLException;
	    abstract void delete(T t)throws SQLException;
	    
	    /**
	     * To obtain the MySql database connection 
	     */
	    public static Connection getConnection() throws SQLException
	    {
	    	if( c == null)
	    	{
	    		try {
					Class.forName(Constants.MYSQL_DRIVER_CLASS_NAME);
				} catch (ClassNotFoundException e) {
					e.printStackTrace();
				}	    		
	    		c = DriverManager.getConnection(Constants.LOCALHOST_MYSQL_CONN_STRING, // URL
	    				Constants.DB_USERNAME, // User
	    				Constants.DB_PASSWORD); // Password
	    	}
	    	return c;
	    }

	    /**
	     * The delete query is reusable across all the tables
	     * @param tableName the name of the table in our database
	     * @param TableIdF the name of the id of an element for the table
	     * @param toDelId the id of the table row to delete
	     */
		public void delete(String tableName, String tableIdF, int toDelId) {
			try {
	        	PreparedStatement preparedStmt = Dao.getConnection().prepareStatement(
	        			Constants.QUERY_DELETE
	        			.replace("$tName",tableName)
	        			.replace("$tId", tableIdF));
				preparedStmt.setInt(1, toDelId);
				preparedStmt.executeUpdate();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
}
