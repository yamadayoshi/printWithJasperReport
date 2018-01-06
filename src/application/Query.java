package application;

import java.sql.DriverManager;

import com.mysql.jdbc.Connection;

import javafx.application.Platform;
import javafx.scene.control.Alert;

public class Query {
	
	Connection connect = null;

	public Connection connect(String host, String port, String user, String passwd) {
		try {
			Class.forName("com.mysql.jdbc.Driver");
			
			connect = (Connection) DriverManager.getConnection(host+":"+port, user, passwd);
		}
		catch (Exception e) {
			String error = null;
			
			if (e.getMessage().contains("Communications link failure")) 
				error = "Favor verificar a conexão com o banco de dados";
			else if(e.getMessage().contains("using password: YES"))
				error = "Favor verificar usuário e senha";			
			
	        Alert alert = new Alert(Alert.AlertType.INFORMATION, error);	
	        alert.getDialogPane().setPrefSize(400, 170);
	        alert.setTitle("appReport");
	        alert.setHeaderText("Erro ao conectar ao banco de dados");	        
	        alert.showAndWait();
	        
	        Platform.exit();
		}		
		return connect;
	}
}
