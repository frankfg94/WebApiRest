package utils;

public class Constants {

	// Table fields
	// User table
	public static final String USER_TABLE_NAME = "user";
	public static final String USER_TABLE_NAME_F = "name";
	public static final String USER_TABLE_CITY_F = "city";
	public static final String USER_TABLE_ID_F = "user_id";

	// Comment table
	public static final String COMMENT_TABLE_NAME = "comment";
	public static final String COMMENT_TABLE_ID_F = "comment_id";
	public static final String COMMENT_TABLE_CONTENT_F = "content";
	public static final String COMMENT_TABLE_USER_ID_F = "user_id";
	public static final String COMMENT_TABLE_MEDIA_ID_F = "media_id";
	public static final String COMMENT_TABLE_RATE_F = "rate";

	// Media table
	public static final String MEDIA_TABLE_NAME = "media";
	public static final String MEDIA_TABLE_ID_F = "media_id";
	public static final String MEDIA_TABLE_TITLE_F = "title";
	public static final String MEDIA_TABLE_YEAR_F = "year";
	public static final String MEDIA_TABLE_DESCRIPTION_F = "description";
	public static final String MEDIA_TABLE_CREATOR_F = "creator";
	public static final String MEDIA_TABLE_USER_ID_F = "user_id";
	public static final String MEDIA_TABLE_CAT_ID_F = "cat_id";
	
	
	// Category table
			public static final String CAT_TABLE_NAME = "category";
			public static final String CAT_TABLE_ID_F = "cat_id";
			public static final String CAT_TABLE_NAME_F = "cat_name";
			
	// Category table
    public static final String QUERY_CAT_GET_ALL = "SELECT * from "+CAT_TABLE_NAME;
    public static final String QUERY_CAT_GET = String.format("SELECT * FROM %s WHERE %s = ? LIMIT 1",CAT_TABLE_NAME,CAT_TABLE_ID_F);
    public static final String QUERY_CAT_UPDATE = String.format("UPDATE %s  SET %s = ? WHERE %s = ?",CAT_TABLE_NAME,CAT_TABLE_NAME_F, CAT_TABLE_ID_F);
    public static final String QUERY_CAT_INSERT = String.format("INSERT INTO %s(%s) values(?)",CAT_TABLE_NAME,CAT_TABLE_NAME_F);
	// Queries

	// Common
	public static final String QUERY_DELETE = "DELETE FROM $tName WHERE $tId = ?";

	// User table
	public static final String QUERY_USER_GET_ALL = "SELECT * from " + USER_TABLE_NAME;
	public static final String QUERY_USER_GET = String.format("SELECT * FROM %s WHERE %s = ? LIMIT 1", USER_TABLE_NAME,
			USER_TABLE_ID_F);
	public static final String QUERY_USER_UPDATE = String.format("UPDATE %s  SET %s = ?, %s = ? WHERE %s = ?",
			USER_TABLE_NAME, USER_TABLE_NAME_F, USER_TABLE_CITY_F, USER_TABLE_ID_F);
	public static final String QUERY_USER_INSERT = String.format("INSERT INTO %s(%s,%s) values(?,?)", USER_TABLE_NAME,
			USER_TABLE_NAME_F, USER_TABLE_CITY_F);

	// Comment table
	public static final String QUERY_COMMENT_GET = String.format("SELECT * FROM %s WHERE %s = ? LIMIT 1",
			COMMENT_TABLE_NAME, COMMENT_TABLE_ID_F);
	public static final String QUERY_COMMENT_GET_ALL = "SELECT * from " + COMMENT_TABLE_NAME;
	public static final String QUERY_COMMENT_UPDATE = String.format(
			"UPDATE %s  SET " + "%s = ?, %s = ?, %s = ?,%s = ?  WHERE %s = ?", COMMENT_TABLE_NAME,
			COMMENT_TABLE_CONTENT_F, COMMENT_TABLE_RATE_F, COMMENT_TABLE_MEDIA_ID_F, COMMENT_TABLE_USER_ID_F,
			COMMENT_TABLE_ID_F);

	public static final String QUERY_COMMENT_INSERT = String.format("INSERT INTO %s(%s,%s,%s,%s) values(?,?,?,?)",
			COMMENT_TABLE_NAME, COMMENT_TABLE_CONTENT_F, COMMENT_TABLE_RATE_F, COMMENT_TABLE_MEDIA_ID_F,
			COMMENT_TABLE_USER_ID_F);
	
	
	// Media table
		public static final String QUERY_MEDIA_GET = String.format("SELECT * FROM %s WHERE %s = ? LIMIT 1",
				MEDIA_TABLE_NAME, MEDIA_TABLE_ID_F);
		public static final String QUERY_MEDIA_GET_ALL = "SELECT * from " + MEDIA_TABLE_NAME;
		public static final String QUERY_MEDIA_UPDATE = String.format(
				"UPDATE %s  SET " + "%s = ?, %s = ?, %s = ?,%s = ?,%s = ? ,%s = ? WHERE %s = ?", MEDIA_TABLE_NAME,
				MEDIA_TABLE_TITLE_F, MEDIA_TABLE_YEAR_F, MEDIA_TABLE_DESCRIPTION_F, MEDIA_TABLE_CREATOR_F, MEDIA_TABLE_USER_ID_F, MEDIA_TABLE_CAT_ID_F, MEDIA_TABLE_ID_F);

		public static final String QUERY_MEDIA_INSERT = String.format("INSERT INTO %s(%s,%s,%s,%s,%s,%s) values(?,?,?,?,?,?)",
				MEDIA_TABLE_NAME,MEDIA_TABLE_TITLE_F, MEDIA_TABLE_YEAR_F, MEDIA_TABLE_DESCRIPTION_F, MEDIA_TABLE_CREATOR_F, MEDIA_TABLE_USER_ID_F, MEDIA_TABLE_CAT_ID_F);
		public static final String QUERY_MEDIA_SEARCH_BY_KEYWORD = String.format("SELECT * FROM %s WHERE %s LIKE ? OR %s LIKE ? OR %s LIKE ? OR %s LIKE ?", MEDIA_TABLE_NAME, MEDIA_TABLE_CREATOR_F, MEDIA_TABLE_DESCRIPTION_F, MEDIA_TABLE_TITLE_F, MEDIA_TABLE_YEAR_F );

	// Database & Driver Information
	public static final String DB_NAME = "restweb";
	public static final String MYSQL_DRIVER_CLASS_NAME = "com.mysql.cj.jdbc.Driver";
	public static final String LOCALHOST_MYSQL_CONN_STRING = "jdbc:mysql://localhost:3306/" + DB_NAME + "?"
			+ "zeroDateTimeBehavior=convertToNull" + "&useJDBCCompliantTimezoneShift=true"
			+ "&useLegacyDatetimeCode=false" + "&serverTimezone=UTC";

	public static final String DB_PASSWORD = "";
	public static final String DB_USERNAME = "root";

}
