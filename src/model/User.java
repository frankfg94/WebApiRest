package model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;

public class User {
	
private int id;
private String name;
@JsonIgnore
private String password;
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

	@JsonIgnore
	public String getPassword() {
		return password;
	}
	
	@JsonProperty
	public void setPassword(String password) {
		this.password = password;
	}
	
	@Override
	public String toString()
	{
		return this.getId() +") City : "+ this.getCity() + " | name : "+ this.getName();
		
	}

}
