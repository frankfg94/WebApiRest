package dao;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

import model.Comment;
import model.User;
import utils.Constants;

/**
 * Structure of a comment table
 * comment_id
 * content
 * media_id
 * rate
 * user_id
 * @author franc
 *
 */
public class CommentDao extends Dao<Comment> {

	private static final boolean INFOS = true;
	
	@Override
	public Comment get(long id) throws SQLException {
        	PreparedStatement stment = Dao.getConnection().prepareStatement(Constants.QUERY_COMMENT_GET);
        	stment.setInt(1, (int) id);
        	stment.executeQuery();
			ResultSet rs = stment.getResultSet();
			if(rs.next())
			{
				Comment comment = new Comment();
				comment.setComment_id(rs.getInt(Constants.COMMENT_TABLE_ID_F));
				comment.setContent(rs.getString(Constants.COMMENT_TABLE_CONTENT_F));
				comment.setUser_id(rs.getInt(Constants.COMMENT_TABLE_USER_ID_F));
				comment.setMedia_id(rs.getInt(Constants.COMMENT_TABLE_MEDIA_ID_F));
				comment.setRate(rs.getInt(Constants.COMMENT_TABLE_RATE_F));
				return comment;
			}
		return null;

	}

	@Override
	public List<Comment> getAll() throws SQLException  {
		ResultSet rs;
		List<Comment>  commentList = null;

		rs = Dao.getConnection().createStatement().executeQuery(Constants.QUERY_COMMENT_GET_ALL);
		commentList = new ArrayList<Comment>();
	           
		//if(INFOS)
			   //Logger.getLogger(UserDao.class.getName()).log(Level.INFO,"Result count : " + Utilities.getResultSetSize(rs));
		
		while (rs.next()) {
	               Comment comment = new Comment();
	               comment.setComment_id(rs.getInt(Constants.COMMENT_TABLE_ID_F));
				   comment.setContent(rs.getString(Constants.COMMENT_TABLE_CONTENT_F));
				   comment.setUser_id(rs.getInt(Constants.COMMENT_TABLE_USER_ID_F));
				   comment.setMedia_id(rs.getInt(Constants.COMMENT_TABLE_MEDIA_ID_F));
				   comment.setRate(rs.getInt(Constants.COMMENT_TABLE_RATE_F));
	               
	               if(INFOS)
	            	   Logger.getLogger(CommentDao.class.getName()).log(Level.INFO,comment.toString());
	        commentList.add(comment);    
			}
		return commentList;
	}

	@Override
	public void save(Comment t) throws SQLException {
			PreparedStatement stment = Dao.getConnection().prepareStatement(Constants.QUERY_COMMENT_INSERT);
            stment.setString(1, t.getContent());
            stment.setInt(2, t.getRating());
            stment.setInt(3, t.getMedia_id());
            stment.setInt(4, t.getUser_id());
			stment.executeUpdate();
			
			if(INFOS)
         	   Logger.getLogger(Comment.class.getName()).log(Level.INFO,"Insert of Comment is successful");
	}

	@Override
	public void update(Comment t) throws SQLException {
        	PreparedStatement stment = Dao.getConnection().prepareStatement(Constants.QUERY_COMMENT_UPDATE);
            stment.setString(1, t.getContent());
            stment.setInt(2, t.getRating());
            stment.setInt(3, t.getMedia_id());
            stment.setInt(4, t.getUser_id());
            stment.setInt(5, t.getComment_id());
            
            if(INFOS)
	         	   Logger.getLogger("Data : " + t.toString());
            
			stment.execute();
			
			if(INFOS)
	         	   Logger.getLogger(CommentDao.class.getName()).log(Level.INFO,"Update of Comment "+ t.getUser_id() +" | "+ t.getUser_id() +"  is successful");
	}

	@Override
	public void delete(Comment t) {
        delete(t.getComment_id());
	}
	
	
	public void delete(int id){
	        super.delete(Constants.COMMENT_TABLE_NAME,Constants.COMMENT_TABLE_ID_F,id);
	}

	/**
	 * Getting the author of a specific comment by indicating the id of the latter
	 */
	public User getAuthor(int commentId) throws SQLException {
		int userId = this.get(commentId).getUser_id();
		return new UserDao().get(userId);
	}
	
}
