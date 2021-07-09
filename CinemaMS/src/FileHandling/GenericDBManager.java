package FileHandling;


import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Scanner;

import Entities.Booking;
import Entities.Movie;
import Entities.Show;

//import Entities.Movie;

public class GenericDBManager {
public static  void AddObject(Serializable object,String filename) throws Exception {

		try {
			//Create a new File stream
			File file = new File(filename);
			FileWriter writer = new FileWriter(file,true);
			writer.write(object.toString());
			writer.close();
			System.out.println("Object Successfully added");
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
        
	}

	public static  ArrayList<String> ViewObjects(String filename) throws Exception {

		ArrayList<String> objectsList = new ArrayList<String>();
		try {
			
			File file = new File(filename);
			Scanner reader = new Scanner(file);
			if(!file.exists()) {
				reader.close();
				return null;
			}
			else {
				while(reader.hasNextLine()) {
					objectsList.add(reader.nextLine());
				}
			}
			reader.close();
			
		} catch (FileNotFoundException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		} 
		
		return objectsList;
		
		
		
        
	}
	
	
	
	public static Serializable SearchObject(String key,String filename) throws Exception {
		try {
			File file = new File(filename);
			Scanner reader = new Scanner(file);
			if(!file.exists()) {
				reader.close();
				return null;
				
			}
			else {
				while(reader.hasNextLine()) {
					if(filename.contains("movies.txt")) {
						Movie movie = new Movie(reader.nextLine());
						if(movie.equals(key)) {
							reader.close();
							return movie;
						}
						
					}
					else if(filename.contains("shows.txt")) {
						Show show = new Show(reader.nextLine());
						if(show.equals(key)) {
							reader.close();
							return show;
						}
						
					}
					else if(filename.contains("bookings.txt")) {
						Booking booking = new Booking(reader.nextLine());
						if(booking.equals(key)) {
							reader.close();
							return booking;
						}
						
					}
					
				}
			}
			reader.close();
			//objectInputStream.close();
		} catch (FileNotFoundException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		} 
		
		return null;
	}
	
	
	public static ArrayList<String> SearchObjects(String key,String filename) throws Exception {
		ArrayList<String> objectsList = new ArrayList<String>();
		try {
			
			File file = new File(filename);
			Scanner reader = new Scanner(file);
			if(!file.exists()) {
				reader.close();
				return null;
			}
			else {
				while(reader.hasNextLine()) {
					
					String rawData = reader.nextLine();
					
					if(filename.contains("movies.txt")) {
						Movie movie = new Movie(rawData);
						if(movie.equals(key)) {
							objectsList.add(rawData);
						}
					}
					else if(filename.contains("shows.txt")) {
						Show show = new Show(rawData);
						if(show.equals(key)) {
							objectsList.add(rawData);
						}
					}
					else if(filename.contains("bookings.txt")) {
						Booking booking = new Booking(rawData);
						if(booking.equals(key)) {
							objectsList.add(rawData);
						}
					}
					
					
					
				}
			}
			reader.close();
			
			
			
		}  catch (FileNotFoundException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return objectsList;
	}
	
}
