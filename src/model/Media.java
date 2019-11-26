package model;

public class Media{
	private int media_id;
	private String title;
	private int year;
	private String description;
	private String creator;
	private int user_id;
	private int cat_id;

	public String getTitle(){
		return title;
	}

	public void setTitle(String title){
		this.title=title;
	}

	public String getCreator(){
		return creator;
	}

	public void setCreator(String creator){
		this.creator=creator;
	}

	public int getUser_id(){
		return user_id;
	}

	public void setUser_id(int user_id){
		this.user_id=user_id;
	}

	public int getCat_id(){
		return cat_id;
	}

	public void setCat_id(int cat_id){
		this.cat_id=cat_id;
	}

	public int getMedia_id() {
		return media_id;
	}

	public void setMedia_id(int media_id) {
		this.media_id = media_id;
	}

	public int getYear() {
		return year;
	}

	public void setYear(int year) {
		this.year = year;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}
}