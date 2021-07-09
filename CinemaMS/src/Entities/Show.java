package Entities;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collections;
import java.util.UUID;

public class Show implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	public String ID;
	public String movieName;
	public String showTime; //e.g 10PM
	private ArrayList<Integer> seats = new ArrayList<Integer>();
	public String seatsString;
	

	public Show(String movieName, String showTime, int seatsLength) {
		super();
		this.ID = UUID.randomUUID().toString();
		this.movieName = movieName;
		this.showTime = showTime;
		for (int i = 0; i < seatsLength; i++) {
			seats.add(i+1);
		}
		this.seatsString = getSeatsString();
	}

	public Show(String movieName, String showTime, String seatsAvailableString) {
		super();
		this.ID = UUID.randomUUID().toString();
		this.movieName = movieName;
		this.showTime = showTime;
		this.seats = newSeats(seatsAvailableString);
		this.seatsString = seatsAvailableString;
	}


	public String getID() {
		return this.ID;
	}
	

	
	public ArrayList<Integer> newSeats(String seatsAvailableString){
		String[] seatsAvailable = seatsAvailableString.split(":");
	
		ArrayList<Integer> seats = new ArrayList<Integer>();
		for (int i = 0; i < seatsAvailable.length; i++) {
			seatsAvailable[i].trim();
			seats.add(Integer.parseInt(seatsAvailable[i].trim()));
		}
		return seats;
	}
	
	public String getSeatsString() {
		String seatsString = "";
		for (int i = 0; i < this.seats.size(); i++) {
			if(i != seats.size()-1) {
				seatsString += seats.get(i)+":";
			}
			else {
				seatsString += seats.get(i);
			}
		}
		return seatsString;
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



	public ArrayList<Integer> getSeats() {
		return seats;
	}

	
	


	public String bookSeats(int seatsToBook) {
		String bookedSeats = "";
		Collections.shuffle(this.seats);
		for (int i = 0; i < Math.min(this.seats.size(),seatsToBook); i++) {
			
			bookedSeats += this.seats.remove(i) + ":";
		}
		return bookedSeats;
	}



	public boolean equals(String key) {
		
			if(this.movieName.contains(key.trim())
					|| this.showTime.contains(key.trim())
					
					) {
				return true;
			}
			else {
				return false;
			}
		
		
		
	}
	
	public String toString() {
		return this.ID + "|" + this.movieName + "|" + this.showTime + "|" + getSeatsString() + "\n" ;
	}
	
	public Show (String showString) {
		
		String[] showData = showString.split("\\|");
		this.ID = showData[0];
		this.movieName = showData[1];
		this.showTime = showData[2];
		this.seats = newSeats(showData[3].trim());
		this.seatsString = showData[3].trim();
	}
	
	
	
	
	
}

