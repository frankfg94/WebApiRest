package model;

import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
public class Category{
	private int id;
	private String cat_name;

	public String getCat_name(){
		return cat_name;
	}

	public void setCat_name(String cat_name){
		this.cat_name=cat_name;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}
}