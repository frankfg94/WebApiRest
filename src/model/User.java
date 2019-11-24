package model;

public class User {
	
private int id;
private String name;
private String city;
	
	public int getId() {
		return id;
	}
	public void setUser_id(int user_id) {
		this.id = user_id;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getCity() {
		return city;
	}
	public void setCity(String city) {
		this.city = city;
	}

	@Override
	public String toString()
	{
		return this.getId() +") City : "+ this.getCity() + " | name : "+ this.getName();
		
	}

}
