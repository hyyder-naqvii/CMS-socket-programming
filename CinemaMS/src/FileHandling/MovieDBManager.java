package FileHandling;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Scanner;

import Entities.Movie;

public class MovieDBManager {
public static  void AddMovie(Movie movie) throws Exception {
		
		
		System.out.println("Add Movie Called");
		try {
			//Create a new File stream
			File file = new File("movies.txt");
			FileWriter writer = new FileWriter(file,true);
			writer.write(movie.toString());
			writer.close();
			System.out.println("Movie Successfully added");
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
        
	}

	public static  ArrayList<String> ViewMovies() throws Exception {

		ArrayList<String> moviesList = new ArrayList<String>();
		try {
			
			File file = new File("movies.txt");
			Scanner reader = new Scanner(file);
			if(!file.exists()) {
				return null;
			}
			else {
				while(reader.hasNextLine()) {
					moviesList.add(reader.nextLine());
				}
			}
			
		} catch (FileNotFoundException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return moviesList;
		
		
		
        
	}
	
	
	
	@SuppressWarnings("unchecked")
	public static Movie SearchMovie(String key) throws Exception {
		try {
			File file = new File("movies.txt");
			Scanner reader = new Scanner(file);
			if(!file.exists()) {
				return null;
			}
			else {
				while(reader.hasNextLine()) {
					Movie movie = new Movie(reader.nextLine());
					if(movie.equals(key)) {
						return movie;
					}
				}
			}
			//objectInputStream.close();
		} catch (FileNotFoundException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return null;
	}
	
}
