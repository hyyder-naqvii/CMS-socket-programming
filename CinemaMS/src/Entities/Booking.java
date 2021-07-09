package Entities;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.UUID;

public class Booking implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	public String ID;
	public String firstName,lastName;
	public String movieName;
	public String showTime; //e.g 10PM
	public String[] seats = null;
	public String seatsString = "";
	public Booking(String firstName, String lastName, String movieName, String showTime, int seats) {
		super();
		this.ID = UUID.randomUUID().toString();
		this.firstName = firstName;
		this.lastName = lastName;
		this.movieName = movieName;
		this.showTime = showTime;
		this.seats = new String[seats];
		for (int i = 0; i < this.seats.length; i++) {
			this.seats[i] = "X";
		}
		this.seatsString = getSeatsString();
	}

	public String getID() {
		return this.ID;
	}
	
	public String getFirstName() {
		return firstName;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	public String getLastName() {
		return lastName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	public String getMovieName() {
		return movieName;
	}

	public void setMovieName(String movieName) {
		this.movieName = movieName;
	}

	public String getShowTime() {
		return showTime;
	}

	public void setShowTime(String showTime) {
		this.showTime = showTime;
	}

	public String[] getSeats() {
		return seats;
	}

	public void setSeats(String[] seats) {
		this.seats = seats;
	}

	public String getSeatsString() {
		String seatsString = "";
		for (int i = 0; i < this.seats.length; i++) {
			if(i !=this.seats.length-1) {
				seatsString += seats[i]+":";
			}
			else {
				seatsString += seats[i];
			}
		}
		return seatsString;
	}
	public boolean equals(String key) {
		
		if(this.movieName.contains(key.trim())
				|| this.firstName.contains(key.trim())
				|| this.lastName.contains(key.trim())
				|| this.showTime.contains(key.trim())
				
				) {
			return true;
		}
		else {
			return false;
		}
	}
	
	public String toString() {
		return this.ID + "|" + this.firstName + "|" + this.lastName + "|" + this.movieName+ "|" + this.showTime + "|" + getSeatsString() +   "\n";
	}
	
	public Booking (String showString) {
		
		String[] bookingData = showString.split("\\|");
		this.ID = bookingData[0];
		this.firstName = bookingData[1];
		this.lastName = bookingData[2];
		this.movieName  = bookingData[3];
		this.showTime  = bookingData[4];
		this.seats = bookingData[5].split(":");
		this.seatsString = getSeatsString();
	}
	
	
	
	
	
}

