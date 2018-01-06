package controller;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

import application.Query;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.DatePicker;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

public class MainController implements Initializable{
	
	@FXML
	private Button btnPrint;
	
	@FXML
	private Button btnCancel;	
	
	@FXML
	private DatePicker datePickerInicio1, datePickerFim1, datePickerInicio2, datePickerFim2;
	
	@FXML
	private Pane paneDate1, paneDate2, paneDate3;	
	
	@FXML
	private ImageView searchIcon;
	
	private AnchorPane rootLayout;
	
	private Stage searchStage;
	
	public Query query; 
	
	@Override
	public void initialize(URL location, ResourceBundle resources) {
		//set images in imageView
		Image icon = new Image("images/search.png");
		searchIcon.setImage(icon);
		
		//Start connection with DB
		query = new Query();
		
		query.connect("jdbc:mysql://localhost", "3306", "root", "masterkey");
	}
	
	public void search() {
		try {
			//load fxml file
			FXMLLoader loader = new FXMLLoader(getClass().getResource("Search.fxml"));			
			rootLayout = (AnchorPane) loader.load();
			
			//show scene
			Scene scene = new Scene(rootLayout);	
			searchStage = new Stage();
			searchStage.setScene(scene);
			searchStage.show();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			
	        Alert alert = new Alert(Alert.AlertType.INFORMATION);
	        alert.setTitle("appReport");
	        alert.setHeaderText("Erro");
	        alert.setContentText("Ocorreu um erro: " + e.getMessage());
	        alert.showAndWait();
		}
	}
	
	public void print() {
		paneDate2.setVisible(false);
		paneDate3.setLayoutX(paneDate2.getLayoutX());
		paneDate3.setLayoutY(paneDate2.getLayoutY());
		
//        Alert alert = new Alert(Alert.AlertType.INFORMATION);
//        alert.setTitle("Message Here...");
//        alert.setHeaderText("Look, an Information Dialog");
//        alert.setContentText("I have a great message for you!");
//        alert.showAndWait();
	}
	
	public void close() {
		Platform.exit();
	}
}
