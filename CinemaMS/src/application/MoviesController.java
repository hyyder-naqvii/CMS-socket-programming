package application;

import java.io.IOException;
import java.net.URL;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.ResourceBundle;


import CinemaMS.Client;
import CinemaMS.Server;
import ClientMenus.MovieMenuClient;
import Entities.Movie;
import ServerMenus.MovieMenu;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.AnchorPane;


public class MoviesController implements Initializable {

	DateTimeFormatter formatter = DateTimeFormatter.ofPattern("MMM dd, yyyy");
	public String[] genresString = {
			"Action",
			"Adventure",
			"Comedy",
			"Sci-Fi",
			"Romance",
			"Crime",
			"Family",
	};
	
	@FXML
	public AnchorPane movies;
	@FXML
	public AnchorPane movieContent;
	@FXML
	public AnchorPane viewMovies;
	@FXML
	public AnchorPane searchMovies;
	//Add Movie Inputs
	@FXML
	public TextField movieName;
	@FXML
	public TextField movieCountry;
	@FXML
	public TextField movieLanguage;
	
	@FXML
	public DatePicker movieDate;
	@FXML 
	public ChoiceBox<String> genres;

	@FXML
	public Label infoText;
	//Table View (Movies)
	@FXML
	public TableView<Movie> movieTable;
	@FXML
	 TableColumn<Movie,String> mID;
	@FXML
	 TableColumn<Movie,String> mName;
	@FXML
	 TableColumn<Movie,String> mGenre;
	@FXML
	 TableColumn<Movie,String> mCountry;
	@FXML
	 TableColumn<Movie,String> mLanguage;
	@FXML
	 TableColumn<Movie,String> mYear;
	//Table View (Search Movies)
	public TableView<Movie> movieSearchTable;
	@FXML
	 TableColumn<Movie,String> mIDS;
	@FXML
	 TableColumn<Movie,String> mNameS;
	@FXML
	 TableColumn<Movie,String> mGenreS;
	@FXML
	 TableColumn<Movie,String> mCountryS;
	@FXML
	 TableColumn<Movie,String> mLanguageS;
	@FXML
	 TableColumn<Movie,String> mYearS;
	@FXML
	TextField searchField;
	
	//Called when Exit button is pressed in the Movies menu
	public void OnExit(ActionEvent e) throws IOException {
		//Send an exit message to client so it can be forwarded to the server.
	  Client.clientOut.writeObject("4");
	  Client.clientOut.flush();
	  movieContent.setVisible(false);
	  searchMovies.setVisible(false);
	  viewMovies.setVisible(false);
	  AnchorPane parent = FXMLLoader.load(getClass().getResource("menu.fxml"));
	  movies.getChildren().setAll(parent);
	  
			
		
		
	}
	//Called when Add button is pressed in the Movies menu
@FXML
	public void OnAddMoviesMenu(ActionEvent e) throws IOException {
		  movieContent.setVisible(true);
		  viewMovies.setVisible(false);
		  searchMovies.setVisible(false);
		  infoText.setVisible(false);
		  movieName.setText("");
		  movieLanguage.setText("");
		  movieCountry.setText("");
		  genres.setValue("");
		  movieDate.setValue(null);
		}

//Called when Submit button is pressed in the Movies Add menu
@FXML
	public void OnAddMovie(ActionEvent e) throws Exception {
	boolean hasError = false;
	String errorString = "";
	infoText.setVisible(true);	
	//Get Inputs as strings here
		String name = movieName.getText();
		String language = movieLanguage.getText();
		String country = movieCountry.getText();
		LocalDate date  = movieDate.getValue();
		String year;
		if(date==null) {
			year = "";
		}
		else {
			year = formatter.format(date).trim();
		} 
		String genre = genres.getValue();
		//Create a new Movie Object
		if(name == "" || language == "" || country == "" || year == "" || genre == null || genre.isEmpty() ) {
			hasError = true;
			errorString = "Invalid Input!";	
		}
		if(!hasError) {
			Client.clientOut.writeObject("1");
			Client.clientOut.flush();
			Movie movie = new Movie(name, genre, country, language, year);
			MovieMenuClient.AddMovie(Client.clientOut, movie);
			infoText.setStyle("-fx-text-fill : green");
			infoText.setText("Added New Movie: " + name);
		}
		else {
			infoText.setText(errorString);
			infoText.setStyle("-fx-text-fill : red");
		}
		
	}

//Called when View button is pressed in the Movies menu
@FXML
public void OnViewMovie(ActionEvent e) throws Exception {
	movieContent.setVisible(false);
	viewMovies.setVisible(true);
	searchMovies.setVisible(false);
	movieTable.setItems(getMovies(MovieMenuClient.ViewMovies(Client.clientIn)));

}

//Called when Search button is pressed in the Movies menu
public void OnSearchMovieMenu(ActionEvent e) {
	movieContent.setVisible(false);
	viewMovies.setVisible(false);
	searchMovies.setVisible(true);
}

//Called when Search button is pressed in the Movies Search menu
public void OnSearchMovie() throws Exception {
	movieSearchTable.setItems(getMovies(MovieMenuClient.SearchMovies(Client.clientIn,searchField.getText().trim())));
	
}

private ObservableList<Movie> getMovies(ArrayList<String> movies){
	ObservableList<Movie> moviesOL = FXCollections.observableArrayList();
	for(String movie:movies) {
		moviesOL.add(new Movie(movie));
	}
	return moviesOL;
}

	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		movieContent.setVisible(false);
		viewMovies.setVisible(false);
		searchMovies.setVisible(false);
		 
		//Populate the Genres Choice box
		
		 for(String genre:genresString) {
			 genres.getItems().add(genre);
		 }
		 
		 //Set View Movies Table Cells
		 mID.setCellValueFactory(new PropertyValueFactory("ID"));
		 mName.setCellValueFactory(new PropertyValueFactory("name"));
		 mGenre.setCellValueFactory(new PropertyValueFactory("genre"));
		 mCountry.setCellValueFactory(new PropertyValueFactory("country"));
		 mYear.setCellValueFactory(new PropertyValueFactory("year"));
		 mLanguage.setCellValueFactory(new PropertyValueFactory("language"));
		 //Set Search Movies Table Cells
		 mIDS.setCellValueFactory(new PropertyValueFactory("ID"));
		 mNameS.setCellValueFactory(new PropertyValueFactory("name"));
		 mGenreS.setCellValueFactory(new PropertyValueFactory("genre"));
		 mCountryS.setCellValueFactory(new PropertyValueFactory("country"));
		 mYearS.setCellValueFactory(new PropertyValueFactory("year"));
		 mLanguageS.setCellValueFactory(new PropertyValueFactory("language"));
		
	}
	
}
