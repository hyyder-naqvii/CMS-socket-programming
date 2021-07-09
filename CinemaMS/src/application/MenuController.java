package application;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

import CinemaMS.Client;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

public class MenuController implements Initializable {
	@FXML
	public Button closeButton;
	@FXML
	public Button moviesBTN;
	@FXML
	public Button showsButton;
	@FXML
	private AnchorPane menu;
	//Called when exit button is pressed
	public void OnExit(ActionEvent e) throws IOException {
		Client.closeConnection();
		//Server.DisconnectClient();
		Stage stage = (Stage) closeButton.getScene().getWindow();
	    stage.close();	
	}
	//Called when movies button is pressed
	public void OnMovies(ActionEvent e) throws IOException {
		Client.clientOut.writeObject("1");
		AnchorPane parent = FXMLLoader.load(getClass().getResource("movies.fxml"));
		menu.getChildren().setAll(parent);

	}
	//Called when shows button is pressed
	public void OnShows(ActionEvent e) throws IOException {
		Client.clientOut.writeObject("2");
		AnchorPane parent = FXMLLoader.load(getClass().getResource("shows.fxml"));
		menu.getChildren().setAll(parent);

		
	}
	//Called when bookings button is pressed
	public void OnBookings(ActionEvent e) throws IOException {
		Client.clientOut.writeObject("3");
		AnchorPane parent = FXMLLoader.load(getClass().getResource("bookings.fxml"));
		menu.getChildren().setAll(parent);

	}
	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		// TODO Auto-generated method stub
		
	}
	
}
