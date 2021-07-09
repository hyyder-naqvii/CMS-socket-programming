package application;

import java.io.IOException;
import java.net.URL;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.ResourceBundle;
import java.util.regex.Pattern;

import CinemaMS.Client;
import CinemaMS.Server;
import ClientMenus.BookingMenuClient;
import ClientMenus.MovieMenuClient;
import ClientMenus.ShowMenuClient;
import Entities.Booking;
import Entities.Movie;
import Entities.Show;
import FileHandling.GenericDBManager;
import ServerMenus.MovieMenu;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;

import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;

import javafx.scene.layout.AnchorPane;


public class BookingsController implements Initializable {

	
	@FXML
	public AnchorPane bookings;
	@FXML
	public AnchorPane addBooking;
	@FXML
	public AnchorPane viewBooking;
	@FXML
	public AnchorPane searchBooking;
	
	//Add Movie Inputs
	

	@FXML
	public TextField fName;
	@FXML
	public TextField lName;
	@FXML
	public TextField nSeats;

	@FXML 
	public ChoiceBox<String> availableShows;

	
//	@FXML
//	public Button showAddButton;
	
	@FXML
	public Label infoTextB;
	
	
	
	//Table View (Movies)
	@FXML
	public TableView<Booking> bookingTable;
	@FXML
	 TableColumn<Booking,String> bID;
	@FXML
	 TableColumn<Booking,String> bFirstName;
	@FXML
	 TableColumn<Booking,String> bLastName;
	@FXML
	 TableColumn<Booking,String> bMovie;
	@FXML
	 TableColumn<Booking,String> bTime;
	@FXML
	 TableColumn<Booking,String> bSeats;
	
	//Table View (Search Movies)
	public TableView<Booking> bookingSearchTable;
	@FXML
	 TableColumn<Booking,String> bIDS;
	@FXML
	 TableColumn<Booking,String> bFirstNameS;
	@FXML
	 TableColumn<Booking,String> bLastNameS;
	@FXML
	 TableColumn<Booking,String> bMovieS;
	@FXML
	 TableColumn<Booking,String> bTimeS;
	@FXML
	 TableColumn<Booking,String> bSeatsS;
	
	@FXML
	TextField searchFieldB;
	
	
	public void OnExit(ActionEvent e) throws IOException {
		//Send an exit message to client so it can be forwarded to the server.
	  Client.clientOut.writeObject("4");
	  Client.clientOut.flush();
	  addBooking.setVisible(false);
	  viewBooking.setVisible(false);
	  searchBooking.setVisible(false);
	  AnchorPane parent = FXMLLoader.load(getClass().getResource("menu.fxml"));
	  bookings.getChildren().setAll(parent);
	  
			
		
		
	}
@FXML
	public void OnAddBookingMenu(ActionEvent e) throws IOException {
//	//Send an add movie message to client so it can be forwarded to the server.

		  addBooking.setVisible(true);
		  viewBooking.setVisible(false);
		  searchBooking.setVisible(false);
		  infoTextB.setVisible(false);
		  fName.setText("");
		  fName.setText("");
		  nSeats.setText("");
		 
		  availableShows.setValue("");

		  
		  
		}
//
//@FXML
	public void OnAddBooking(ActionEvent e) throws Exception {
	boolean hasError = false;
	String errorString = "";
//	
	infoTextB.setVisible(true);	
//	//Get Inputs as strings here
		String firstName = fName.getText();
		String lastName = lName.getText();
		String seats = nSeats.getText();
		
		String movieName = "";
		String showTime = "";
		
		//Validate First Name
		if(firstName == null || firstName.isEmpty()) {
			hasError = true;
			errorString += "First Name cannot be empty \n";
		}
		//Validate Last Name
				if(lastName == null || lastName.isEmpty()) {
					hasError = true;
					errorString += "Last Name cannot be empty \n";
				}
		
		
				
		//Validate Show 
		if(availableShows.getValue()==null || availableShows.getValue().isEmpty()) {
			hasError = true;
			errorString += "Show  cannot be empty \n";	
		}
		else {
			String[] splittedShowData = availableShows.getValue().split(":");
			movieName = splittedShowData[0];
			showTime = splittedShowData[1];
			
		}
	
		
		
		//Validate no of seats
		int noSeats = 0;
		try {
			noSeats = Integer.parseInt(seats);
		}
		catch(Exception x) {
			errorString += "No of seats should be a number \n";
			hasError = true;
		}
		
		
		if(!hasError) {
			System.out.println("No Error");
			
			Booking booking = new Booking(firstName, lastName, movieName, showTime, noSeats);
			BookingMenuClient.AddBooking(Client.clientOut, booking);
			infoTextB.setStyle("-fx-text-fill : green");
			infoTextB.setText("Added New Booking: " + booking.toString());
		}
		else {
			infoTextB.setText(errorString);
			infoTextB.setStyle("-fx-text-fill : red");
		}
		
		//MovieMenu.AddMovie(Server.serverIn,Server.socket);
		
	}
	

@FXML
public void OnViewBookings(ActionEvent e) throws Exception {
	addBooking.setVisible(false);
	viewBooking.setVisible(true);
	searchBooking.setVisible(false);
	
	bookingTable.setItems(getBookings(BookingMenuClient.ViewBookings(Client.clientIn)));


	
}

public void OnSearchBookingMenu(ActionEvent e) {
	addBooking.setVisible(false);
	viewBooking.setVisible(false);
	searchBooking.setVisible(true);
}

public void OnSearchBookings() throws Exception {
	
	
	
	bookingSearchTable.setItems(getBookings(BookingMenuClient.SearchBookings(Client.clientIn,searchFieldB.getText().trim())));


	
}

private ObservableList<Booking> getBookings(ArrayList<String> bookings){
	ObservableList<Booking> bookingsOL = FXCollections.observableArrayList();
	for(String booking:bookings) {
		bookingsOL.add(new Booking(booking));
	}
	return bookingsOL;
}


	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		addBooking.setVisible(false);
		viewBooking.setVisible(false);
		searchBooking.setVisible(false);
		infoTextB.setVisible(false);
		
		//Get all movies from the DB
		ArrayList<String> showsAvailable;
		try {
			showsAvailable = GenericDBManager.ViewObjects("shows.txt");
			for(String show:showsAvailable) {
				Show s = new Show(show);
				 availableShows.getItems().add(s.movieName + ":" + s.showTime);
				 
			 }
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		 
		 //Set View Bookings Table Cells
		 bID.setCellValueFactory(new PropertyValueFactory("ID"));
		 bFirstName.setCellValueFactory(new PropertyValueFactory("firstName"));
		 bLastName.setCellValueFactory(new PropertyValueFactory("lastName"));
		 bMovie.setCellValueFactory(new PropertyValueFactory("movieName"));
		 bTime.setCellValueFactory(new PropertyValueFactory("showTime"));
		 bSeats.setCellValueFactory(new PropertyValueFactory("seatsString"));
		
		 //Set Search Bookings Table Cells
		 bIDS.setCellValueFactory(new PropertyValueFactory("ID"));
		 bFirstNameS.setCellValueFactory(new PropertyValueFactory("firstName"));
		 bLastNameS.setCellValueFactory(new PropertyValueFactory("lastName"));
		 bMovieS.setCellValueFactory(new PropertyValueFactory("movieName"));
		 bTimeS.setCellValueFactory(new PropertyValueFactory("showTime"));
		 bSeatsS.setCellValueFactory(new PropertyValueFactory("seatsString"));
		
	}
	
}
