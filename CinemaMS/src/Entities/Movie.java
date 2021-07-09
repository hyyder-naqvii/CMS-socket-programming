package Entities;

import java.io.Serializable;
import java.util.UUID;

public class Movie implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	/**
	 * 
	 */

	public String ID;
	public String name;
	public String genre;
	public String country;
	
	public Movie(String name, String genre, String country, String language, String year) {
		super();
		this.ID = UUID.randomUUID().toString();
		this.name = name;
		this.genre = genre;
		this.country = country;
		this.language = language;
		this.year = year;
		System.out.println("New Movie Object Created");
	}



	public String getID() {
		return ID;
	}
	
	public String getCountry() {
		return country;
	}



	public void setCountry(String country) {
		this.country = country;
	}



	public String language;
	public String year;
	public String getName() {
		return name;
	}



	public void setName(String name) {
		this.name = name;
	}



	public String getGenre() {
		return genre;
	}



	public void setGenre(String genre) {
		this.genre = genre;
	}



	public String getLanguage() {
		return language;
	}



	public void setLanguage(String language) {
		this.language = language;
	}



	public String getYear() {
		return year;
	}



	public void setYear(String year) {
		this.year = year;
	}



	

	public boolean equals(String key) {
		
		if(this.name.contains(key.trim())) {
			return true;
		}
		else {
			return false;
		}
	}
	
	
	public String toString() {
		return  this.ID + "|" + this.name + "|" + this.genre + "|" + this.language+ "|" + this.country + "|" + this.year +  "\n";
	}
	
	public Movie (String movieString) {
		
		String[] movieData = movieString.split("\\|");
		this.ID = movieData[0];
		this.name = movieData[1];
		this.genre = movieData[2];
		this.language = movieData[3];
		this.country = movieData[4];
		this.year = movieData[5];
				
				
	}
	
	//Serialization
	
	
	
	
	
	
	
	
	
}
