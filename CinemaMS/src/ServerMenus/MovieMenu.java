package ServerMenus;

import java.io.*;
import java.net.Socket;
import java.util.ArrayList;

import Entities.Movie;
import FileHandling.GenericDBManager;



public class MovieMenu {

	
	
	
public  static void ShowMenu(ObjectInputStream serverIn,ObjectOutputStream serverOut) throws Exception {
		
		System.out.println("Client Requested to Perform Movie Operations....");
		while(true) {
			String choiceStr = (String)serverIn.readObject();

			int choiceSelected = Integer.parseInt(choiceStr.trim());
			switch(choiceSelected) {
			case 1:
				AddMovie(serverIn, serverOut);
				break;
			case 2:
				ViewMovies(serverOut);
				break;
			case 3:
				SearchMovie(serverIn, serverOut);
				break;
				//serverObjectOut.close();
			case 4:
				return;
		}
		}
		
	}
	
	@SuppressWarnings("unused")
	public static void AddMovie(ObjectInputStream serverIn,ObjectOutputStream serverOut) throws Exception {
		System.out.println("Client Requested to Add Movie....");
		try {
			Movie movie = (Movie)serverIn.readObject();
			System.out.println(movie.getName());
			GenericDBManager.AddObject(movie,"movies.txt");
		}
		catch(Exception e) {
			
		}
		
		//serverOut.writeObject("Movie Added Succesfully");
		
		
		
		
		//serverOut.flush();
		
		
		
		
		
	}
	//------------ GUI CALLS THIS CODE --------------
//	public static void AddMovie(ObjectInputStream serverIn,Socket socket) throws Exception {
//		if(serverIn == null) {
//			serverIn = new ObjectInputStream(socket.getInputStream());
//		}
//		System.out.println("Client Requested to Add Movie....");
//		Movie movie = (Movie)serverIn.readObject();
//        System.out.println(movie.getName());
//        GenericDBManager.AddObject(movie,"movies.txt");
//	}

	@SuppressWarnings("unused")
	private static void SearchMovie(ObjectInputStream serverIn,ObjectOutputStream serverOut) throws Exception {
		System.out.println("Client Requested to Search for a Movie....");
		String searchKey = (String)serverIn.readObject();
	
		ArrayList<String> moviesList = GenericDBManager.SearchObjects(searchKey,"movies.txt");
		System.out.println(moviesList);
		serverOut.writeObject(moviesList);
		
		serverOut.flush();
	
	}
	@SuppressWarnings("unused")
	private static void ViewMovies(ObjectOutputStream serverOut) throws Exception
	{
		System.out.println("Client Requested to View Movies....");
		ArrayList<String> moviesList = GenericDBManager.ViewObjects("movies.txt");
		System.out.println(moviesList);
		serverOut.writeObject(moviesList);
		serverOut.flush();
		
	}
	
}
