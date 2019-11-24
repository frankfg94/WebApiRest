package model;

public class Media{
	private String title;
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
}