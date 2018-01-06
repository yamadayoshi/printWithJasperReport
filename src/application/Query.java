package application;

import java.sql.DriverManager;

import com.mysql.jdbc.Connection;

import javafx.scene.control.Alert;

public class Query {
	
	Connection connect = null;

	public Connection connect(String host, String port, String user, String passwd) {
		try {
			Class.forName("com.mysql.jdbc.Driver");
			
			connect = (Connection) DriverManager.getConnection(host+":"+port, user, passwd);
		}
		catch (Exception e) {
			e.printStackTrace();
	        Alert alert = new Alert(Alert.AlertType.INFORMATION, e.getMessage()+"\nFavor verificar usuário e senha");	
	        alert.getDialogPane().setPrefSize(450, 170);
	        alert.setTitle("appReport");
	        alert.setHeaderText("Erro ao conectar ao banco de dados");	        
	        alert.showAndWait();	
		}		
		return connect;
	}

	public Connection getConnect() {
		return connect;
	}
}
