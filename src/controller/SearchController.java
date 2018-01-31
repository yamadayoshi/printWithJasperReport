package controller;

import java.net.URL;
import java.sql.Connection;
import java.sql.ResultSet;
import java.util.ResourceBundle;

import com.mysql.jdbc.Statement;

import application.ObjectList;
import application.ShowMessage;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;

public class SearchController implements Initializable {
	
	@FXML
	private Button btnOK;
	
	@FXML
	private Button btnCancel;
	
	@FXML
	private TextField edtSearch;
	
	@FXML
	private ListView<ObjectList> lstSearch;	
	
	private String command;
	
	private Connection connection;	
		
	private ObservableList<ObjectList> itens; 
	
	private ShowMessage message;
	
	private String id;
	
	private String content;	

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		message = new ShowMessage();
	}
	
	@FXML
	public void clickOK() {
		setId(lstSearch.getItems().get(lstSearch.getSelectionModel().getSelectedIndex()).getId());
		setContent(lstSearch.getItems().get(lstSearch.getSelectionModel().getSelectedIndex()).getContent());
		close();
	}
	
	//double click event
	@FXML
	public void doubleClickSelect() {
		lstSearch.setOnMouseClicked(new EventHandler<MouseEvent>() {	
			@Override
			public void handle(MouseEvent event) {
				if (event.getButton().equals(MouseButton.PRIMARY)) 
					if (event.getClickCount() == 2) 
						clickOK();				
			}
		});
	}
	
	public void completeSearch() {	
		try {			
			command = "select * from mybdname.clients where client_name_like '" + edtSearch.getText() + "%' order by CLIENTES_NOME_ABREVIADO limit 50";				
			Statement state = (Statement) connection.createStatement();
				
			ResultSet result = state.executeQuery(command);
				
			itens = FXCollections.observableArrayList();
	
			while(result.next()) {				
				itens.add(new ObjectList(result.getString(1), result.getString(12)));
			}			
				
			lstSearch.setItems(itens);		
		}catch (Exception e) {
			message.showMessageAlert("Erro", "Ocorreu um comportamento inesperado");
		}		
	}
	
	public void close() {
		Stage stage = (Stage) btnCancel.getScene().getWindow();		
		stage.close();
	}

	public void setConnection(Connection conn) {
		this.connection = conn;
	}
	
	@FXML
	 private void handleKeyPressed(KeyEvent key){
		if (key.getCode().toString().equals("ESCAPE")) {
			close();
		}	   
	 }
	
	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}
}
