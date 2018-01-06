package application;
	
import java.io.IOException;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

public class Main extends Application {
	
	private Stage primaryStage; 
	private AnchorPane rootLayout;
	
	@Override	
	public void start(Stage primaryStage) {
		try {
			//set and define title	
			this.primaryStage = primaryStage;
			this.primaryStage.setTitle("appReport");
			
			loadRootLayout();
			
		} catch(Exception e) {
			e.printStackTrace();
		}
	}
	
	private void loadRootLayout() {
		try {
			//load fxml file
			FXMLLoader loader = new FXMLLoader();
			loader.setLocation(getClass().getResource("Main.fxml"));
			rootLayout = (AnchorPane) loader.load();
			
			//show scene
			Scene scene = new Scene(rootLayout);
			primaryStage.setScene(scene);
			primaryStage.show();			
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public static void main(String[] args) {
		launch(args);
	}
}
