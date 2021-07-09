package ClientMenus;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;

import CinemaMS.Client;
import Entities.Show;

public class ShowMenuClient {
	public static void ShowShowsMenu(BufferedReader keyboardInput,ObjectInputStream clientIn,ObjectOutputStream clientOut) throws Exception{
		String choice = null;
		while(true) {
			if(choice == "4")break;
			System.out.println("Shows");
			System.out.println("1) Add Show");
			System.out.println("2) View All Shows");
			System.out.println("3) Search for Shows");
			System.out.println("4) Return");
			System.out.println("Choose an Option");
			choice = keyboardInput.readLine();
//			//Send option to the client
			switch(choice) {
			case "1":
				clientOut.writeObject(choice);
//				//Create a new movie object and send it to server for processing
				//clientOut.writeObject(GetShowInput(keyboardInput,clientIn,clientOut));
				
				System.out.println((String)clientIn.readObject());
//				//clientObjectOut.close();
				break;
			case "2":
				clientOut.writeObject(choice);
				@SuppressWarnings("unchecked") ArrayList<String>  showListFromServer = (ArrayList<String>)clientIn.readObject();
				//PrintShowsTable(showListFromServer);
				break;
			case "3":
				clientOut.writeObject(choice);
				System.out.println("Enter Movie Name to Search for Available Shows!");
				String searchKey = keyboardInput.readLine();
				clientOut.writeObject(searchKey);
				
				@SuppressWarnings("unchecked") ArrayList<String> availableShows = (ArrayList<String>)clientIn.readObject();
				
					//PrintShowsTable(availableShows);
			
				break;
			case "4":
				clientOut.writeObject(choice);
				return;
			default:
				System.out.println("Please Select an Option between 1 and 4");
			}
			
		}
	}
	
	//--------------- THESE ARE CLI IMPLEMENTATION FUNCTIONS -------------
	
//	static  Show GetShowInput(BufferedReader keyboardInput,ObjectInputStream clientIn,ObjectOutputStream clientOut) throws IOException, ClassNotFoundException {
//		String movieName,showTime;
//		int seats;
//		@SuppressWarnings("unchecked")
//		ArrayList<String> availableMovies = (ArrayList<String>)clientIn.readObject();
//		MovieMenuClient.PrintMoviesTable(availableMovies);
//		
//		System.out.println("Enter Movie Name (e.g Titanic)");
//		movieName = keyboardInput.readLine().trim();
//		System.out.println("Enter Movie Time (e.g 9 PM)");
//		showTime = keyboardInput.readLine().trim();
//		System.out.println("Enter Total  Seats (e.g 30)");
//		seats = Integer.parseInt(keyboardInput.readLine().trim());
//		
//		return new Show(movieName,showTime,seats);
//		return null;
		
//	}

//	public static void PrintShowsTable(ArrayList<String> shows) {
		
//		System.out.printf("ID \t\t\t\t\t\t\t Movie \t\t\t Time  \t\t\t Available Seats\n" );
//		if(shows.isEmpty()) {
//			System.out.println("No Shows found for the corresponding Movie!");
//			return;
//		}
//		for(String rawData : shows)
//		{
//			Show show = new Show(rawData);
//			System.out.printf("%s \t\t\t %s \t\t %s  \t\t %s \n",
//					show.getID(),
//					show.getMovieName(),
//					show.getShowTime(),
//					show.getSeatsString()
//					);
//			
//		}
//	}
	
	
	//--------------- END OF CLI IMPLEMENTATION FUNCTIONS -------------
	
	
	//--------------- THESE ARE GUI IMPLEMENTATION FUNCTIONS -------------

	
	public static ArrayList<String> SearchShows(ObjectInputStream clientIn, String searchKey) throws IOException, ClassNotFoundException {
		System.out.println("Searching " + searchKey);
		Client.clientOut.writeObject("3");
		Client.clientOut.flush();
		Client.clientOut.writeObject(searchKey);
		Client.clientOut.flush();
		@SuppressWarnings("unchecked") ArrayList<String>  showListFromServer = (ArrayList<String>) clientIn.readObject();
		return showListFromServer;
	}
	public static void AddShow(ObjectOutputStream clientOut, Show show) throws IOException {
		Client.clientOut.writeObject("1");
		Client.clientOut.flush();
		clientOut.writeObject(show);
		clientOut.flush();
	}
	public static ArrayList<String> ViewShows(ObjectInputStream clientIn) throws IOException, ClassNotFoundException {
		Client.clientOut.writeObject("2");
		Client.clientOut.flush();
		@SuppressWarnings("unchecked") ArrayList<String>  showListFromServer = (ArrayList<String>) clientIn.readObject();
		return showListFromServer;
	}
	
	//--------------- END OF GUI IMPLEMENTATION FUNCTIONS -------------
}
