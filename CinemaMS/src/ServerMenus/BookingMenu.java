package ServerMenus;

import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;

import Entities.Booking;
import Entities.Movie;
import Entities.Show;
import FileHandling.GenericDBManager;


public class BookingMenu {
public  static void ShowMenu(ObjectInputStream serverIn,ObjectOutputStream serverOut) throws Exception {
		
		System.out.println("Client Requested to Perform Show Operations....");
		while(true) {
			String choiceStr = (String)serverIn.readObject();

			int choiceSelected = Integer.parseInt(choiceStr.trim());
			switch(choiceSelected) {
			case 1:
				AddBooking(serverIn, serverOut);
				break;
			case 2:
				ViewBookings(serverOut);
				break;
			case 3:
				SearchBookings(serverIn, serverOut);
				break;
				//serverObjectOut.close();
			case 4:
				return;
		}
		}
		
	}
	

	private static void AddBooking(ObjectInputStream serverIn,ObjectOutputStream serverOut) throws Exception {
		//--------------- UNCOMMENT THIS PART FOR CLI IMPLEMENTATION -------------
//		System.out.println("Client Requested to Add Booking....");
//		ArrayList<String> availableShows  = GenericDBManager.ViewObjects("shows.txt");
//		serverOut.writeObject(availableShows);
//		Booking booking = (Booking)serverIn.readObject();
//
//		boolean showFound = false;
//		for(String s : availableShows) {
//			Show show = new Show(s);
//			if(show.getMovieName().trim().equals(booking.getMovieName().trim())
//					&&
//					show.getShowTime().trim().equals(booking.getShowTime().trim())
//					) {
//				booking.setSeats(show.bookSeats(booking.getSeats().length).split(":"));
//				GenericDBManager.AddObject(booking,"bookings.txt");
//				serverOut.writeObject("Ticket Booked Succesfully!");
//				showFound = true;
//				break;
//			}
//			else {
//				System.out.println("No Show Found!");
//			}
//		}
//		if(!showFound) {
//			serverOut.writeObject("Cannot book ticket!");
//		}
//		
//		
//		serverOut.flush();
		
		
		//--------------- xxxxxxxxXXXXXXxxxxxxXXXXXXxxxxxxxxx -------------
		
		//--------------- COMMENT THIS PART TO REMOVE GUI IMPLEMENTATION -------------
		ArrayList<String> availableShows  = GenericDBManager.ViewObjects("shows.txt");
		System.out.println("Client Requested to Add Booking....");
		Booking booking = (Booking)serverIn.readObject();
		for(String s : availableShows) {
			Show show = new Show(s);
			if(show.getMovieName().trim().equals(booking.movieName.trim())
					&&
				show.getShowTime().trim().equals(booking.showTime.trim())
					) {
				booking.setSeats(show.bookSeats(booking.seats.length).split(":"));
			GenericDBManager.AddObject(booking,"bookings.txt");
			
			break;
			}
				System.out.println("No Show Found!");
		}

		//--------------- XXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXX -------------

	}

	private static void SearchBookings(ObjectInputStream serverIn,ObjectOutputStream serverOut) throws Exception {
		System.out.println("Client Requested to Search for a Booking....");
		String searchKey = (String)serverIn.readObject();
	
		ArrayList<String> bookingsList = GenericDBManager.SearchObjects(searchKey,"bookings.txt");
		System.out.println("Bookings"+bookingsList);
		serverOut.writeObject(bookingsList);
		
		serverOut.flush();
	
	
	}

	private static void ViewBookings(ObjectOutputStream serverOut) throws Exception
	{
		System.out.println("Client Requested to View Bookings....");
		ArrayList<String> bookingsList = GenericDBManager.ViewObjects("bookings.txt");
		System.out.println(bookingsList);
		serverOut.writeObject(bookingsList);
		serverOut.flush();
		
	}
}
