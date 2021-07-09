package CinemaMS;

import java.io.*;

import java.net.ServerSocket;
import java.net.Socket;

import ServerMenus.BookingMenu;
import ServerMenus.MovieMenu;
//import socket_programming.ServerMenus.ShowsMenu;
import ServerMenus.ShowsMenu;

public class Server {
	 ServerSocket serverSocket = null;
	 static Socket socket = null;
	
	 //Stream to recieve data from the clients
	 static ObjectInputStream serverIn = null;
	 //Stream to send data to the clients
	 static ObjectOutputStream serverOut = null;
	
	 
	
	public static void main(String[] args) throws Exception {
		//Create a new server instance
		new Server();
	}
	
	Server() throws Exception{
		
		
		try {
			//Open server socket for communication
			serverSocket = new ServerSocket(8000);
			//While the Server socket is open continue listening for incoming client connections
			while(!serverSocket.isClosed()) {
				
				System.out.println("Waiting for Clients");
				socket = serverSocket.accept();
				System.out.println("Connection Established");
				//Initialize In and Out Streams
				//Note: Streams are initialized only once. else it will give a stream header error
				serverOut = new ObjectOutputStream(socket.getOutputStream());
				serverIn = new ObjectInputStream(socket.getInputStream());
				//Send welcome message to clients
				//serverOut.writeObject("Welcome to Cinema Management System");
				
			
				while(!(socket.isClosed())) {
					System.out.println("Inside Main menu");
					String menuChoice = (String)serverIn.readObject();
					int menuChoiceSelected = Integer.parseInt(menuChoice.trim());
					switch(menuChoiceSelected) {
					case 1:
						//Handle Movie Related Operations (CSV)
						MovieMenu.ShowMenu(serverIn, serverOut);
						break;
					case 2:
						//Shows
						ShowsMenu.ShowMenu(serverIn, serverOut);
						break;
					case 3:
						BookingMenu.ShowMenu(serverIn, serverOut);
						break;
					case 4:
						DisconnectClient();
						break;
					}		
				}
				
			}

		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	private static void DisconnectClient() throws IOException {
		System.out.println("Client Disconnected. Socket Closing...");
		serverOut.writeObject("Thanks for visiting!");
		
		serverIn.close();
		serverOut.close();
        socket.close();
	}
	
	
}
