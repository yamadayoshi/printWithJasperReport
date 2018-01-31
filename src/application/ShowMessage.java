package application;

import javafx.scene.control.Alert;

public class ShowMessage {

	Alert alert;
	
	public void showMessageAlert(String typeMsg, String msg) {
		alert = new Alert(Alert.AlertType.INFORMATION);
	    alert.setTitle("appReport");
	    alert.setHeaderText(typeMsg);
	    alert.setContentText(msg);
	    alert.showAndWait();	
	}
}
