package application;
	
import CinemaMS.Server;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;

import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.Text;


public class App extends Application {

	double x,y = 0;
	
	
	
	public static void Launch(String[] args) {
		launch(args);
	}
	
	
	@Override
	public void start(Stage stage) throws Exception {
		//Set stage resolution
		
		stage.setResizable(false);
		
		//Create root node
		Parent root = FXMLLoader.load(getClass().getResource("menu.fxml"));
		
		//Make window draggable
		root.setOnMousePressed(mouseEvent ->{
			x = mouseEvent.getSceneX();
			y = mouseEvent.getSceneY();
		});
		
		root.setOnMouseDragged(mouseEvent->{
			stage.setX(mouseEvent.getScreenX()-x);
			stage.setY(mouseEvent.getScreenY()-y);
		});
		
		
		
		//Create a new scene
		Scene scene = new Scene(root,Color.DARKSLATEBLUE);
		scene.getStylesheets().add(getClass().getResource("application.css").toExternalForm());
		//Create an icon for the stage
		Image icon = new Image("cinemaIcon.png");
		
		//Set the icon
		stage.getIcons().add(icon);
		stage.initStyle(StageStyle.UNDECORATED);
		
		//setTitle(root, scene, "Cinema Management System");
		//set the scene
		stage.setTitle("Cinema Management System");
		stage.setScene(scene);
		stage.show();
		
	}
	
	private void setTitle(Parent parent, Scene scene, String title) {
		Text text = new Text(title);
		text.setFill(Color.WHITE);
		text.setFont(Font.font("Verdana",50));
		text.setX(100);
		text.setY(100);
		//parent.getChildren().add(text);
	}
	
}
