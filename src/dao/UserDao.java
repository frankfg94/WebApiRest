package dao;

import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

import model.Comment;
import model.Media;
import model.User;
import utils.Constants;
import utils.Utilities;

/**
 * Structure of a User table
 * city
 * name
 * user_id
 * @author franc
 *
 */
public class UserDao extends Dao<User> {

	private static final boolean INFOS = true;
	
	@Override
	public User get(long id) throws SQLException {
        	PreparedStatement stment = Dao.getConnection().prepareStatement(Constants.QUERY_USER_GET);
        	stment.setInt(1, (int) id);
        	stment.executeQuery();
			ResultSet rs = stment.getResultSet();
			if(rs.next())
			{
				User user = new User();
				user.setUser_id(rs.getInt(Constants.USER_TABLE_ID_F));
				user.setName(rs.getString(Constants.USER_TABLE_NAME_F));
				user.setCity(rs.getString(Constants.USER_TABLE_CITY_F));
				user.setPassword(rs.getString(Constants.USER_TABLE_PASSWORD_F));
				return user;
			}
		return null;
	}

	@Override
	public List<User> getAll() throws SQLException  {
		ResultSet rs;
		List<User>  users = null;
		
			
		rs = Dao.getConnection().createStatement().executeQuery(Constants.QUERY_USER_GET_ALL);
		users = new ArrayList<User>();
	           
		//if(INFOS)
			   //Logger.getLogger(UserDao.class.getName()).log(Level.INFO,"Result count : " + Utilities.getResultSetSize(rs));
		
		while (rs.next()) {
	               User u = new User();
	               u.setUser_id(rs.getInt(Constants.USER_TABLE_ID_F));
	               u.setCity(rs.getString(Constants.USER_TABLE_CITY_F));
	               u.setName(rs.getString(Constants.USER_TABLE_NAME_F));
				   u.setPassword(rs.getString(Constants.USER_TABLE_PASSWORD_F));

	               if(INFOS)
	            	   Logger.getLogger(UserDao.class.getName()).log(Level.INFO,u.toString());
	        users.add(u);    
			}
		return users;
	}

	@Override
	public void save(User t) throws NoSuchAlgorithmException, InvalidKeySpecException {
	    try {
			PreparedStatement stment = Dao.getConnection().prepareStatement(Constants.QUERY_USER_INSERT);
            stment.setString(1, t.getName());
            stment.setString(2, t.getCity());
            stment.setString(3, Utilities.generateStorngPasswordHash(t.getPassword()));
			stment.executeUpdate();
			
			if(INFOS)
         	   Logger.getLogger(UserDao.class.getName()).log(Level.INFO,"Insert of User is successful");

		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	@Override
	public void update(User t) throws SQLException {
        	PreparedStatement preparedStmt = Dao.getConnection().prepareStatement(Constants.QUERY_USER_UPDATE);
			preparedStmt.setString(1, t.getName());
			preparedStmt.setString(2, t.getCity());
			preparedStmt.setInt(3, t.getId());
			preparedStmt.execute();
			
			if(INFOS)
	         	   Logger.getLogger(UserDao.class.getName()).log(Level.INFO,"Update of User "+ t.getId() +" | "+ t.getName() +"  is successful");
	}
	
	@Override
	public void delete(User t) throws SQLException {
        delete(t.getId());
	}
	
	public void delete(int id) throws SQLException{
		
		/**
		 * 		
		// Deleting the childs
		String queryDelCom = String.format("Delete from Comment WHERE user_id = '%d'",id);
		String queryDelMed = String.format("Delete from Media WHERE user_id = '%d'",id);
		
    	PreparedStatement preparedStmtCom = Dao.getConnection().prepareStatement(queryDelCom);
    	PreparedStatement preparedStmtMed = Dao.getConnection().prepareStatement(queryDelMed);

    	preparedStmtCom.executeUpdate();
    	preparedStmtMed.executeUpdate();
    	
		 */

		// Deleting the parent
		super.delete(Constants.USER_TABLE_NAME, Constants.USER_TABLE_ID_F, id);
	}
	
	/**
	 * Getting all the comments for this user
	 */
	public List<Comment> getAllComments(int id) throws SQLException {
			List<Comment> coms = new CommentDao().getAll();
			List<Comment> comId = new ArrayList<Comment>();
			for(Comment c : coms)
			{
				if(c.getUser_id() == id)
					comId.add(c);
			}
			return comId;
	}

	/**
	 * Getting all the media for this user
	 * @throws SQLException 
	 */
	public List<Media> getAllMedia(int id) throws SQLException {
		List<Media> coms = new MediaDao().getAll();
		List<Media> comId = new ArrayList<Media>();
		for(Media c : coms)
		{
			if(c.getUser_id() == id)
				comId.add(c);
		}
		return comId;
	}
	

	
	
}
