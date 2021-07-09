package ClientMenus;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;

import CinemaMS.Client;
import Entities.Movie;


public class MovieMenuClient {
	public static void ShowMoviesMenu(BufferedReader keyboardInput,ObjectInputStream clientIn,ObjectOutputStream clientOut) throws Exception{
		String choice = null;
		while(true) {
			if(choice == "4")break;
			System.out.println("Movies");
			System.out.println("1) Add Movie");
			System.out.println("2) View Movies");
			System.out.println("3) Search Movie");
			System.out.println("4) Return");
			System.out.println("Choose an Option");
			choice = keyboardInput.readLine();
			//Send option to the client
			switch(choice) {
			case "1":
				clientOut.writeObject(choice);
				clientOut.flush();
				System.out.println("Trying to add MOvie");
				//Create a new movie object and send it to server for processing
				//Movie movie = GetMovieInput(keyboardInput);
				//System.out.println(movie.toString());
				//clientOut.writeObject(movie);
				
				System.out.println((String)clientIn.readObject());
				//clientObjectOut.close();
				break;
			case "2":
				clientOut.writeObject(choice);
				@SuppressWarnings("unchecked") ArrayList<String>  movieListFromServer = (ArrayList<String>) clientIn.readObject();
				System.out.println("Movie Length" + movieListFromServer.size());
				
				//PrintMoviesTable(movieListFromServer);
				break;
			case "3":
				clientOut.writeObject(choice);
				System.out.println("Enter Movie Name to Search!");
				String searchKey = keyboardInput.readLine();
				clientOut.writeObject(searchKey);
				
				Movie foundMovie = (Movie)clientIn.readObject();
				if(foundMovie == null) {
					System.out.println("No Movie Found!");
				}
				else {
					System.out.println("Found Movie with name : " + foundMovie.getName());
				}
				
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
	
//static  Movie GetMovieInput(BufferedReader keyboardInput) throws IOException {
//		String name,genre,country,language,year;
//		System.out.println("Enter Movie Details");
//		System.out.println("Enter Movie Name (e.g Titanic)");
//		name = keyboardInput.readLine().trim();
//		System.out.println("Enter Movie Genre (e.g Romance)");
//		genre = keyboardInput.readLine().trim();
//		System.out.println("Enter Movie Country (e.g United States)");
//		country = keyboardInput.readLine().trim();
//		System.out.println("Enter Movie Language (e.g English)");
//		language = keyboardInput.readLine().trim();
//		System.out.println("Enter Movie Release Year (e.g 1997)");
//		year = keyboardInput.readLine();
//		
//		return new Movie(name,genre,country,language,year);
//		return null;
		
//}
//	public static void PrintMoviesTable(ArrayList<String> movies) {
		
//		System.out.printf("ID \t\t\t\t\t\t\t NAME \t\t\t GENRE  \t\t\t LANGUAGE \t\t COUNTRY \t YEAR \n");
//		if(movies.isEmpty()) {
//			System.out.println("No Movies Found! Add some?");
//			return;
//		}
//		for(String rawData : movies)
//		{
//			Movie movie = new Movie(rawData);
//			System.out.printf("%s \t\t\t %s \t\t %s  \t\t\t %s \t\t %s \t\t %s \n",
//					movie.getID(),
//					movie.getName(),
//					movie.getGenre(),
//					movie.getLanguage(),
//					movie.getCountry(),
//					movie.getYear()
//					
//					);
//			
//		}
//	}
	
	//--------------- END OF CLI IMPLEMENTATION FUNCTIONS -------------
	
	//--------------- THESE ARE GUI IMPLEMENTATION FUNCTIONS -------------
	
	
	public static void AddMovie(ObjectOutputStream clientOut,Movie movie) throws IOException {
		clientOut.writeObject(movie);
		clientOut.flush();
	}
	
	public static ArrayList<String> ViewMovies(ObjectInputStream clientIn) throws ClassNotFoundException, IOException{
		Client.clientOut.writeObject("2");
		Client.clientOut.flush();
		@SuppressWarnings("unchecked") ArrayList<String>  movieListFromServer = (ArrayList<String>) clientIn.readObject();
		return movieListFromServer;
	}
	
	public static ArrayList<String> SearchMovies(ObjectInputStream clientIn,String seachKey) throws ClassNotFoundException, IOException{
		Client.clientOut.writeObject("3");
		Client.clientOut.flush();
		Client.clientOut.writeObject(seachKey);
		Client.clientOut.flush();
		@SuppressWarnings("unchecked") ArrayList<String>  movieListFromServer = (ArrayList<String>) clientIn.readObject();
		return movieListFromServer;
	}
	
	//--------------- END OF GUI IMPLEMENTATION FUNCTIONS -------------

}
