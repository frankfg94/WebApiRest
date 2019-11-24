package model;

public class Comment{

	private int comment_id;
	private int user_id;
	private int media_id;
	private String content;
	private int rating;
	
	public int getComment_id() {
		return comment_id;
	}
	
	public void setComment_id(int comment_id) {
		this.comment_id = comment_id;
	}
	
	public String getContent() {
		return content;
	}
	
	public void setContent(String content) {
		this.content = content;
	}

	public int getRating(){
		return rating;
	}

	public void setRate(int rating){
		this.rating=rating;
	}

	public int getUser_id(){
		return user_id;
	}

	public void setUser_id(int user_id){
		this.user_id=user_id;
	}

	public int getMedia_id(){
		return media_id;
	}

	public void setMedia_id(int media_id){
		this.media_id=media_id;
	}
	
	@Override 
	public String toString() {
		return getComment_id() + " |" + getContent()  +"|" + getRating()+ "|"+ getMedia_id() + "|" + getUser_id();
	}
}
