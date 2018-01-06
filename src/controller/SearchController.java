package controller;

import java.net.URL;
import java.sql.ResultSet;
import java.util.ResourceBundle;

import com.mysql.jdbc.Connection;
import com.mysql.jdbc.Statement;

import application.Query;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

public class SearchController implements Initializable {
	
	@FXML
	private Button btnOK;
	
	@FXML
	private Button btnCancel;
	
	@FXML
	private TextField edtSearch;
	
	@FXML
	private ListView<String> lstSearch;
	
	String command;
	
	Connection connect;	
	
	Query query;
	
	ObservableList<String> itens; 

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		 query = new Query();
		 
		 query.connect("jdbc:mysql://localhost", "3306", "root", "masterkey");
		 
		 connect = query.getConnect();		 
	}
	
	public void completeSearch() {	
		try {						
			command = "select * from iapp.clientes where CLIENTES_NOME_ABREVIADO like '" + edtSearch.getText() + "%' order by CLIENTES_NOME_ABREVIADO limit 50";
			
			Statement state = (Statement) connect.createStatement();
			
			ResultSet result = state.executeQuery(command);
			
			itens = FXCollections.observableArrayList();

			while(result.next()) {				
				itens.add(result.getString(12));
			}			
			
			lstSearch.setItems(itens);			
			
		}catch (Exception e) {
	        Alert alert = new Alert(Alert.AlertType.INFORMATION);
	        alert.setTitle("appReport");
	        alert.setHeaderText("Ocorreu um comportamento inesperado");
	        alert.setContentText(e.toString());
	        alert.showAndWait();
		}		
	}
	
	public void close() {
		Stage stage = (Stage) btnCancel.getScene().getWindow();		
		stage.close();
	}

}
