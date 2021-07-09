package ServerMenus;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;

import Entities.Movie;
import Entities.Show;
import FileHandling.GenericDBManager;


public class ShowsMenu {
public  static void ShowMenu(ObjectInputStream serverIn,ObjectOutputStream serverOut) throws Exception {
		
		System.out.println("Client Requested to Perform Show Operations....");
		while(true) {
			String choiceStr = (String)serverIn.readObject();

			int choiceSelected = Integer.parseInt(choiceStr.trim());
			switch(choiceSelected) {
			case 1:
				AddShow(serverIn, serverOut);
				break;
			case 2:
				ViewShows(serverOut);
				break;
			case 3:
				SearchShows(serverIn, serverOut);
				break;
				//serverObjectOut.close();
			case 4:
				return;
		}
		}
		
	}
	
	@SuppressWarnings("unused")
	private static void AddShow(ObjectInputStream serverIn,ObjectOutputStream serverOut) throws Exception {
//		System.out.println("Client Requested to Add Show....");
//		ArrayList<String> availableMovies  = GenericDBManager.ViewObjects("movies.txt");
//		System.out.println(availableMovies.size());
//		serverOut.writeObject(availableMovies);
//		Show show = (Show)serverIn.readObject();
//		System.out.println(show.getMovieName());
//		boolean showFound = false;
//		for(String s : availableMovies) {
//			Movie movie = new Movie(s);
//			System.out.println("From: " + show.getMovieName() + "To : " + movie.getName());
//			if(movie.getName().trim().equals(show.getMovieName().trim())) {
//				
//				GenericDBManager.AddObject(show,"shows.txt");
//				serverOut.writeObject("Show Added Succesfully!");
//				showFound = true;
//				break;
//			}
//			else {
//				System.out.println("No Match FOund!");
//			}
//		}
//		if(!showFound) {
//			serverOut.writeObject("No Movie Found for Show!");
//		}
//		
		
		//serverOut.flush();
		//---- GUI RELATED CODE---
	System.out.println("Client Requested to Add Show....");
	Show show = (Show)serverIn.readObject();
	GenericDBManager.AddObject(show,"shows.txt");
	System.out.println("Successfully Added Show" + show.movieName);
		
		
		
	}

	@SuppressWarnings("unused")
	private static void SearchShows(ObjectInputStream serverIn,ObjectOutputStream serverOut) throws Exception {
		System.out.println("Client Requested to Search for a Show....");
		String searchKey = (String)serverIn.readObject();
	
		ArrayList<String> showsList = GenericDBManager.SearchObjects(searchKey,"shows.txt");
		System.out.println("Shows"+showsList);
		serverOut.writeObject(showsList);
		
		serverOut.flush();
	
	
	}
	@SuppressWarnings("unused")
	private static void ViewShows(ObjectOutputStream serverOut) throws Exception
	{
		System.out.println("Client Requested to View Shows....");
		ArrayList<String> showsList = GenericDBManager.ViewObjects("shows.txt");
		System.out.println(showsList);
		serverOut.writeObject(showsList);
		serverOut.flush();
		
	}
}
