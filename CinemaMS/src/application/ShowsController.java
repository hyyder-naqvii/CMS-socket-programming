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
import ClientMenus.MovieMenuClient;
import ClientMenus.ShowMenuClient;
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


public class ShowsController implements Initializable {

	
	@FXML
	public AnchorPane shows;
	@FXML
	public AnchorPane addShow;
	@FXML
	public AnchorPane viewShow;
	@FXML
	public AnchorPane searchShow;
	
	//Add Movie Inputs
	

	@FXML
	public TextField time;
	@FXML
	public TextField seats;

	@FXML 
	public ChoiceBox<String> movieName;
	
	@FXML
	public Button showAddButton;
	
	@FXML
	public Label infoTextS;
	
	
	
	//Table View (Movies)
	@FXML
	public TableView<Show> showTable;
	@FXML
	 TableColumn<Show,String> sID;
	@FXML
	 TableColumn<Show,String> sMovie;
	@FXML
	 TableColumn<Show,String> sTime;
	@FXML
	 TableColumn<Show,String> sSeats;

	
	//Table View (Search Movies)
	public TableView<Show> showSearchTable;
	@FXML
	 TableColumn<Show,String> sIDS;
	@FXML
	 TableColumn<Show,String> sMovieS;
	@FXML
	 TableColumn<Show,String> sTimeS;
	@FXML
	 TableColumn<Show,String> sSeatsS;
	
	@FXML
	TextField searchFieldS;
	
	
	public void OnExit(ActionEvent e) throws IOException {
		//Send an exit message to client so it can be forwarded to the server.
	  Client.clientOut.writeObject("4");
	  Client.clientOut.flush();
	  addShow.setVisible(false);
	  viewShow.setVisible(false);
	  searchShow.setVisible(false);
	  AnchorPane parent = FXMLLoader.load(getClass().getResource("menu.fxml"));
	  shows.getChildren().setAll(parent);
	  
			
		
		
	}
@FXML
	public void OnAddShowsMenu(ActionEvent e) throws IOException {
//	//Send an add movie message to client so it can be forwarded to the server.

		  addShow.setVisible(true);
		  viewShow.setVisible(false);
		  searchShow.setVisible(false);
		  infoTextS.setVisible(false);
		  time.setText("");
		  seats.setText("");
		 
		  movieName.setValue("");

		  
		  
		}
//
//@FXML
	public void OnAddShow(ActionEvent e) throws Exception {
	boolean hasError = false;
	String errorString = "";
//	
	infoTextS.setVisible(true);	
//	//Get Inputs as strings here
		String movie = movieName.getValue();
		String _time = time.getText();
		String _seats = seats.getText();
		
		
		//Create a new Movie Object
		//Validate time
		String pattern = "([0-9]|0[0-9]|1[0-9]|2[0-3])\\s*([AaPp][Mm])";
		if(!Pattern.matches(pattern, _time)) {
			hasError = true;
			errorString += "Time should be in the format HH:AM/PM \n";
		}
		
		//Validate Movie
		if(movie == null || movie.isEmpty()) {
			hasError = true;
			errorString += "Movie cannot be empty \n";
		}
		
		
		//Validate no of seats
		int noSeats = 0;
		try {
			noSeats = Integer.parseInt(_seats);
		}
		catch(Exception x) {
			errorString += "No of seats should be a number \n";
			hasError = true;
		}
		
		
		if(!hasError) {
			System.out.println("No Error");
			
			Show show = new Show(movie, _time, noSeats);
			ShowMenuClient.AddShow(Client.clientOut, show);
			infoTextS.setStyle("-fx-text-fill : green");
			infoTextS.setText("Added New Show: " + show.toString());
		}
		else {
			infoTextS.setText(errorString);
			infoTextS.setStyle("-fx-text-fill : red");
		}
		
		//MovieMenu.AddMovie(Server.serverIn,Server.socket);
		
	}
	

@FXML
public void OnViewShow(ActionEvent e) throws Exception {
	addShow.setVisible(false);
	viewShow.setVisible(true);
	searchShow.setVisible(false);
	
	showTable.setItems(getShows(ShowMenuClient.ViewShows(Client.clientIn)));


	
}

public void OnSearchShowMenu(ActionEvent e) {
	addShow.setVisible(false);
	viewShow.setVisible(false);
	searchShow.setVisible(true);
}

public void OnSearchShow() throws Exception {
	
	
	
	showSearchTable.setItems(getShows(ShowMenuClient.SearchShows(Client.clientIn,searchFieldS.getText().trim())));


	
}

private ObservableList<Show> getShows(ArrayList<String> shows){
	ObservableList<Show> showsOL = FXCollections.observableArrayList();
	for(String show:shows) {
		showsOL.add(new Show(show));
	}
	return showsOL;
}


	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		addShow.setVisible(false);
		viewShow.setVisible(false);
		searchShow.setVisible(false);
		infoTextS.setVisible(false);
		
		//Get all movies from the DB
		ArrayList<String> moviesAvailable;
		try {
			moviesAvailable = GenericDBManager.ViewObjects("movies.txt");
			for(String movie:moviesAvailable) {
				 movieName.getItems().add(new Movie(movie).name);
			 }
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		 
		 //Set View Shows Table Cells
		 sID.setCellValueFactory(new PropertyValueFactory("ID"));
		 sMovie.setCellValueFactory(new PropertyValueFactory("movieName"));
		 sTime.setCellValueFactory(new PropertyValueFactory("showTime"));
		 sSeats.setCellValueFactory(new PropertyValueFactory("seatsString"));
		
		 //Set Search Shows Table Cells
		 sIDS.setCellValueFactory(new PropertyValueFactory("ID"));
		 sMovieS.setCellValueFactory(new PropertyValueFactory("movieName"));
		 sTimeS.setCellValueFactory(new PropertyValueFactory("showTime"));
		 sSeatsS.setCellValueFactory(new PropertyValueFactory("seatsString"));
		
	}
	
}
