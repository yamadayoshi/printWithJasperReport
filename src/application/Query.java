package application;

import java.sql.Connection;
import java.sql.DriverManager;

import javafx.application.Platform;

public class Query {
	
	Connection connect = null;
	ShowMessage message = new ShowMessage();

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
			
			message.showMessageAlert("Erro", error);			
	        
	        Platform.exit();
		}	
		return connect;
	}	
}
