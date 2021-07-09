package ClientMenus;


import java.io.BufferedReader;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;

import CinemaMS.Client;
import Entities.Booking;
import Entities.Show;

//Class responsible for sending and recieveing Ticket booking related data

public class BookingMenuClient   {
	public static void ShowBookingsMenu(BufferedReader keyboardInput,ObjectInputStream clientIn,ObjectOutputStream clientOut) throws Exception{
		String choice = null;
		while(true) {
			if(choice == "4")break;
			System.out.println("Bookings");
			System.out.println("1) Book Ticket");
			System.out.println("2) View Booked Tickets");
			System.out.println("3) Search for Bookings");
			System.out.println("4) Return");
			System.out.println("Choose an Option");
			choice = keyboardInput.readLine();
//			//Send option to the client
			
			
		
			switch(choice) {
			case "1":
				clientOut.writeObject(choice);
//				//Create a new movie object and send it to server for processing
				clientOut.writeObject(GetBooking(keyboardInput,clientIn,clientOut));
				
				System.out.println((String)clientIn.readObject());
//				//clientObjectOut.close();
				break;
			case "2":
				clientOut.writeObject(choice);
				@SuppressWarnings("unchecked") ArrayList<String>  allBookings = (ArrayList<String>)clientIn.readObject();
				PrintBookingsTable(allBookings);
				break;
			case "3":
				clientOut.writeObject(choice);
				System.out.println("Enter Movie Name to Search for Booked Tickets!");
				String searchKey = keyboardInput.readLine();
				clientOut.writeObject(searchKey);
				
				@SuppressWarnings("unchecked") ArrayList<String> currentBookings = (ArrayList<String>)clientIn.readObject();
				
					PrintBookingsTable(currentBookings);
			
				break;
			case "4":
				clientOut.writeObject(choice);
				return;
			default:
				System.out.println("Please Select an Option between 1 and 4");
			}
			
		}
	}
	static  Booking GetBooking(BufferedReader keyboardInput,ObjectInputStream clientIn,ObjectOutputStream clientOut) throws IOException, ClassNotFoundException {
//		String firstName,lastName,movieName,showTime;
//		int seats;
//		@SuppressWarnings("unchecked")
//		ArrayList<String> availableShows = (ArrayList<String>)clientIn.readObject();
//		ShowMenuClient.PrintShowsTable(availableShows);
//		System.out.println("Book a ticket for Movie(M) on Time(T)");
//		System.out.println("Enter Movie Name (e.g Titanic)");
//		movieName = keyboardInput.readLine().trim();
//		System.out.println("Enter Movie Time (e.g 9 PM)");
//		showTime = keyboardInput.readLine().trim();
//		System.out.println("Enter First Name (e.g John)");
//		firstName = keyboardInput.readLine().trim();
//		System.out.println("Enter Last Name (e.g Doe)");
//		lastName = keyboardInput.readLine().trim();
//		System.out.println("Enter Total  Seats less than Available Seats (e.g 2)");
//		seats = Integer.parseInt(keyboardInput.readLine().trim());
//		
//		return new Booking(firstName, lastName, movieName, showTime, seats);
		return null;
	}

	static void PrintBookingsTable(ArrayList<String> bookings) {
		
//		System.out.printf("ID \t\t\t\t\t\t\t \t\t\t First Name \t\t\t Last Name \t\t\t  Movie \t\t\t Time  \t\t\t Booked Seats \n" );
//		if(bookings.isEmpty()) {
//			System.out.println("No Tickets booked yet");
//			return;
//		}
//		for(String rawData : bookings)
//		{
//			Booking booking = new Booking(rawData);
//			System.out.printf("%s \t\t\t\t\t\t %s \t\t\t\t %s  \t\t\t\t %s \t\t\t %s \t\t\t %s \n",
//					booking.getID(),
//					booking.getFirstName(),
//					booking.getLastName(),
//					booking.getMovieName(),
//					booking.getShowTime(),
//					booking.getSeatsString()
//					);
//			
//		}
	}
	
	
	
	
	public static ArrayList<String> SearchBookings(ObjectInputStream clientIn, String searchKey) throws IOException, ClassNotFoundException {
		System.out.println("Searching " + searchKey);
		Client.clientOut.writeObject("3");
		Client.clientOut.flush();
		Client.clientOut.writeObject(searchKey);
		Client.clientOut.flush();
		@SuppressWarnings("unchecked") ArrayList<String>  bookingListFromServer = (ArrayList<String>) clientIn.readObject();
		return bookingListFromServer;
	}
	public static void AddBooking(ObjectOutputStream clientOut, Booking booking) throws IOException {
		Client.clientOut.writeObject("1");
		Client.clientOut.flush();
		clientOut.writeObject(booking);
		clientOut.flush();
	}
	public static ArrayList<String> ViewBookings(ObjectInputStream clientIn) throws IOException, ClassNotFoundException {
		Client.clientOut.writeObject("2");
		Client.clientOut.flush();
		@SuppressWarnings("unchecked") ArrayList<String>  bookingListFromServer = (ArrayList<String>) clientIn.readObject();
		return bookingListFromServer;
	}
	
	
	
}
