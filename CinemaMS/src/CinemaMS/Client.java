package CinemaMS;
import java.io.*;
import java.net.Socket;

import ClientMenus.BookingMenuClient;
import ClientMenus.MovieMenuClient;
import ClientMenus.ShowMenuClient;
import application.App;
import javafx.application.Application;
import javafx.stage.Stage;




public class Client {

	static Socket socket = null;
	 public static ObjectInputStream clientIn = null;
	 public static ObjectOutputStream clientOut = null;
	public static void main(String[] args) throws Exception {
		
		System.out.println("Client Started");
		try {
			//Create a new client and connect to server on a specific port
			socket = new Socket("localhost",8000);
			System.out.println("Client  Connected");
			clientOut = new ObjectOutputStream(socket.getOutputStream());
			clientIn = new ObjectInputStream(socket.getInputStream());
			//Display the application menu
			App.Launch(args);
			System.out.println("Client Closed");
			//CinemaMenu(socket);
			
			
		} catch (IOException e) {
			System.out.println(e.getMessage());
		}

	}
	
	
	public static void closeConnection() throws IOException {
		Client.clientOut.writeObject("4");
		Client.clientOut.flush();
		
		clientIn.close();
		
		clientOut.close();
		socket.close();
	}
	
static void CinemaMenu(Socket socket) throws Exception {
//		BufferedReader fromServer = new BufferedReader(new InputStreamReader(socket.getInputStream()));
//		System.out.println(fromServer.readLine());
		BufferedReader keyboardInput = new BufferedReader(new InputStreamReader(System.in));
		System.out.println((String)clientIn.readObject());
		String menuChoice = null;
		while(true) {
			if(menuChoice == "4")break;
			System.out.println("Main Menu");
			System.out.println("1) Movies");
			System.out.println("2) Shows");
			System.out.println("3) Bookings");
			System.out.println("4) Exit");
			System.out.println("Choose an Option");
			menuChoice = keyboardInput.readLine();
			//Send option to the client
			switch(menuChoice) {
			case "1":
				clientOut.writeObject(menuChoice);
				MovieMenuClient.ShowMoviesMenu(keyboardInput,clientIn,clientOut);
				break;
			case "2":
				clientOut.writeObject(menuChoice);
				ShowMenuClient.ShowShowsMenu(keyboardInput,clientIn,clientOut);
				//Shows
				break;
			case "3":
				clientOut.writeObject(menuChoice);
				BookingMenuClient.ShowBookingsMenu(keyboardInput, clientIn, clientOut);
				break;
			case "4":
				//Exit
				clientOut.writeObject(menuChoice);
				System.out.println((String)clientIn.readObject());
			
				
				clientIn.close();
				clientOut.close();
				socket.close();
				return;
			default:
				System.out.println("Please Select an Option between 1 and 4");
			}
		}

		
		
		
	}
	
	
	

	
	

}
