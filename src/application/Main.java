package application;
	
import java.io.IOException;
import java.sql.Connection;

import controller.MainController;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

public class Main extends Application {
	
	private Stage primaryStage; 	
	private AnchorPane rootLayout;
	
	private static MainController main;

	private static Connection connection;
	
	private static String reportId;
	
	public static void main(String[] args) {
		
		//start connection with DB
		Query query = new Query();		
		connection = query.connect("jdbc:mysql://"+args[0], args[1], args[2], args[3]);	
		
		//reportID
		reportId = args[4];
		
		launch(args);
	}
	
	@Override	
	public void start(Stage primaryStage) {
		try {
			//set and define title	
			this.primaryStage = primaryStage;
			this.primaryStage.setTitle("reportWithJasper");
						
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
			primaryStage.setResizable(false);
			primaryStage.show();		
			
			//pass parameter
			main = loader.getController();
			main.setConnection(connection);
			main.setReportId(reportId);			
			
		} catch (IOException e) {			
			e.printStackTrace();
		}
	}
}
