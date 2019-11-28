package dao;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

import model.Category;
import model.Comment;
import model.Media;
import model.User;
import utils.Constants;

public class MediaDao extends Dao<Media> {

	private static final boolean INFOS = true;

	@Override
	public Media get(long id) throws SQLException {
		PreparedStatement stment = Dao.getConnection().prepareStatement(Constants.QUERY_MEDIA_GET);
		stment.setInt(1, (int) id);
		stment.executeQuery();
		ResultSet rs = stment.getResultSet();
		if (rs.next()) {
			Media media = new Media();
			media.setMedia_id(rs.getInt(Constants.MEDIA_TABLE_ID_F));
			media.setTitle(rs.getString(Constants.MEDIA_TABLE_TITLE_F));
			media.setYear(rs.getInt(Constants.MEDIA_TABLE_YEAR_F));
			media.setDescription(rs.getString(Constants.MEDIA_TABLE_DESCRIPTION_F));
			media.setCreator(rs.getString(Constants.MEDIA_TABLE_CREATOR_F));
			media.setUser_id(rs.getInt(Constants.MEDIA_TABLE_USER_ID_F));
			media.setCat_id(rs.getInt(Constants.MEDIA_TABLE_CAT_ID_F));
			return media;
		}
		return null;
	}

	@Override
	public List<Media> getAll() throws SQLException {
		ResultSet rs;
		List<Media> mediaList = null;

		rs = Dao.getConnection().createStatement().executeQuery(Constants.QUERY_MEDIA_GET_ALL);
		mediaList = new ArrayList<Media>();

		// if(INFOS)
		// Logger.getLogger(UserDao.class.getName()).log(Level.INFO,"Result
		// count : " + Utilities.getResultSetSize(rs));

		while (rs.next()) {
			Media media = new Media();
			media.setMedia_id(rs.getInt(Constants.MEDIA_TABLE_ID_F));
			media.setTitle(rs.getString(Constants.MEDIA_TABLE_TITLE_F));
			media.setYear(rs.getInt(Constants.MEDIA_TABLE_YEAR_F));
			media.setDescription(rs.getString(Constants.MEDIA_TABLE_DESCRIPTION_F));
			media.setCreator(rs.getString(Constants.MEDIA_TABLE_CREATOR_F));
			media.setUser_id(rs.getInt(Constants.MEDIA_TABLE_USER_ID_F));
			media.setCat_id(rs.getInt(Constants.MEDIA_TABLE_CAT_ID_F));

			if (INFOS)
				Logger.getLogger(MediaDao.class.getName()).log(Level.INFO, media.toString());
			mediaList.add(media);
		}
		return mediaList;
	}

	@Override
	public void save(Media t) throws SQLException {
		PreparedStatement stment = Dao.getConnection().prepareStatement(Constants.QUERY_MEDIA_INSERT);
		stment.setString(1, t.getTitle());
		stment.setInt(2, t.getYear());
		stment.setString(3, t.getDescription());
		stment.setString(4, t.getCreator());
		stment.setInt(5, t.getUser_id());
		stment.setInt(6, t.getCat_id());
		stment.executeUpdate();

		if (INFOS)
			Logger.getLogger(Media.class.getName()).log(Level.INFO, "Insert of Media is successful");

	}

	@Override
	public void update(Media t) throws SQLException {
		PreparedStatement stment = Dao.getConnection().prepareStatement(Constants.QUERY_MEDIA_UPDATE);
		stment.setString(1, t.getTitle());
		stment.setInt(2, t.getYear());
		stment.setString(3, t.getDescription());
		stment.setString(4, t.getCreator());
		stment.setInt(5, t.getUser_id());
		stment.setInt(6, t.getCat_id());
		stment.setInt(7, t.getMedia_id());
		
		if(INFOS)
      	   Logger.getLogger("Data : " + t.toString());
     
		stment.executeUpdate();
	}

	@Override
	public void delete(Media t){
		delete(t.getMedia_id());
	}

	public void delete(int id) {
		super.delete(Constants.MEDIA_TABLE_NAME, Constants.MEDIA_TABLE_ID_F, id);
	}
	
	  //////////// End of implementation of the CrudBase methods///////////

	public User getAuthor(int id) throws SQLException {
		int userId = this.get(id).getUser_id();
		return new UserDao().get(userId);
	}

	/**
	 * Obtaining the category related to the indicated media
	 */
	public Category getCategory(int mediaId) throws SQLException{
		int catId = this.get(mediaId).getCat_id();
		return new CategoryDao().get(catId);
	}
	
	public List<Media> getMediasSortedByKeyword(String sortType, String mode) throws SQLException {
		List<Media> sortedUsers = new ArrayList<>();
		String query = "SELECT * FROM media ORDER BY " + sortType + " " + mode ; // Ascending(asc) or descending (desc)
		ResultSet rs = Dao.getConnection().createStatement().executeQuery(query);
		while (rs.next()) {
			Media media = new Media();
			media.setMedia_id(rs.getInt(Constants.MEDIA_TABLE_ID_F));
			media.setTitle(rs.getString(Constants.MEDIA_TABLE_TITLE_F));
			media.setYear(rs.getInt(Constants.MEDIA_TABLE_YEAR_F));
			media.setDescription(rs.getString(Constants.MEDIA_TABLE_DESCRIPTION_F));
			media.setCreator(rs.getString(Constants.MEDIA_TABLE_CREATOR_F));
			media.setUser_id(rs.getInt(Constants.MEDIA_TABLE_USER_ID_F));
			media.setCat_id(rs.getInt(Constants.MEDIA_TABLE_CAT_ID_F));
		sortedUsers.add(media);
		}
		return sortedUsers;
	}
	

	public List<Media> getMediaSearchByKeyWord(String keyWord) throws SQLException {
		String k = "%"+keyWord+"%";
		PreparedStatement stment = Dao.getConnection().prepareStatement(Constants.QUERY_MEDIA_SEARCH_BY_KEYWORD);
		List<Media> listMedia = new ArrayList<>();
		stment.setString(1, k);
		stment.setString(2, k);
		stment.setString(3, k);
		stment.setString(4, k);
		stment.executeQuery();
		ResultSet rs = stment.getResultSet();
		while (rs.next()){
			Media media = new Media();
			media.setMedia_id(rs.getInt(Constants.MEDIA_TABLE_ID_F));
			media.setTitle(rs.getString(Constants.MEDIA_TABLE_TITLE_F));
			media.setYear(rs.getInt(Constants.MEDIA_TABLE_YEAR_F));
			media.setDescription(rs.getString(Constants.MEDIA_TABLE_DESCRIPTION_F));
			media.setCreator(rs.getString(Constants.MEDIA_TABLE_CREATOR_F));
			media.setUser_id(rs.getInt(Constants.MEDIA_TABLE_USER_ID_F));
			media.setCat_id(rs.getInt(Constants.MEDIA_TABLE_CAT_ID_F));
			listMedia.add(media);
		}
		return listMedia;
	}
	/**
	 * Getting all the comments for this media
	 */
	public List<Comment> getAllComments(int id) throws SQLException {
			List<Comment> coms = new CommentDao().getAll();
			List<Comment> comId = new ArrayList<Comment>();
			for(Comment c : coms)
			{
				if(c.getMedia_id() == id)
					comId.add(c);
			}
			return comId;
	}
	


}
