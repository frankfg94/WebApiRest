package dao;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

import model.Category;
import utils.Constants;

public class CategoryDao extends Dao<Category>{

	@Override
	public Category get(long id) throws SQLException {
    	PreparedStatement stment = Dao.getConnection().prepareStatement(Constants.QUERY_CAT_GET);
    	stment.setInt(1, (int) id);
    	stment.executeQuery();
		ResultSet rs = stment.getResultSet();
		if(rs.next())
		{
			Category cat = new Category();
			cat.setId(rs.getInt(Constants.CAT_TABLE_ID_F));
			cat.setCat_name(rs.getString(Constants.CAT_TABLE_NAME_F));
			return cat;
		}
		return null;
	}

	@Override
	public List<Category> getAll() throws SQLException {
		ResultSet rs;
		List<Category>  cats = null;
			
		rs = Dao.getConnection().createStatement().executeQuery(Constants.QUERY_CAT_GET_ALL);
		cats = new ArrayList<Category>();

		while (rs.next()) {
			Category cat = new Category();
			cat.setId(rs.getInt(Constants.CAT_TABLE_ID_F));
			cat.setCat_name(rs.getString(Constants.CAT_TABLE_NAME_F));

			Logger.getLogger(CategoryDao.class.getName()).log(Level.INFO,cat.toString());
	        cats.add(cat);    
			}
		return cats;
	}

	@Override
	public void save(Category t) throws SQLException {
	    try {
			PreparedStatement stment = Dao.getConnection().prepareStatement(Constants.QUERY_CAT_INSERT);
            stment.setString(1, t.getCat_name());
			stment.executeUpdate();
			Logger.getLogger(UserDao.class.getName()).log(Level.INFO,"Insert of Category is successful");

		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	@Override
	public void update(Category t) throws SQLException {
		PreparedStatement preparedStmt = Dao.getConnection().prepareStatement(Constants.QUERY_CAT_UPDATE);
		preparedStmt.setString(1, t.getCat_name());
		preparedStmt.setInt(2, t.getId());
		preparedStmt.execute();
		Logger.getLogger(UserDao.class.getName()).log(Level.INFO,"Update of Category "+ t.getId() +" | "+ t.getCat_name() +"  is successful");
	}

	@Override
	public void delete(Category t) throws SQLException {
		delete(t.getId());
	}
	
	public void delete(int id){
		super.delete(Constants.CAT_TABLE_NAME, Constants.CAT_TABLE_ID_F, id);
	}

}
