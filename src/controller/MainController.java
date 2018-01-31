package controller;

import java.io.IOException;
import java.net.URISyntaxException;
import java.net.URL;
import java.sql.Connection;
import java.util.HashMap;
import java.util.ResourceBundle;

import application.Main;
import application.ShowMessage;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JasperCompileManager;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.JasperReport;
import net.sf.jasperreports.engine.design.JasperDesign;
import net.sf.jasperreports.engine.xml.JRXmlLoader;
import net.sf.jasperreports.view.JasperViewer;

public class MainController implements Initializable{
	
	@FXML
	private Button btnPrint;
	
	@FXML
	private Button btnCancel;	
	
	@FXML
	private DatePicker datePickerInicio1, datePickerFim1;
	
	@FXML
	private Pane paneDate, paneFilter1, paneFilter2;	
	
	@FXML
	private ImageView searchIcon;
	
	@FXML
	private ComboBox<String> cmbPeriodo;
	
	@FXML
	private ComboBox<String> cmbOrdenacao;
	
	@FXML
	private TextField edtSearch1;
	
	@FXML
	public Label lblSelection1;
	
	private AnchorPane rootLayout;
	
	private Stage searchStage;
			
	private SearchController search;
	
	private Connection connection;
	
	private String currentFolder;
	
	private HashMap<String, Object> parameters = new HashMap<>();
	
	private ShowMessage message;	

	private String reportId;
	
	@Override
	public void initialize(URL location, ResourceBundle resources) {
		//set images in imageView
		Image icon = new Image("images/search.png");
		searchIcon.setImage(icon);
				
		//populate combobox
		cmbPeriodo.getItems().addAll("Data de emissão", "Data de vencimento");
		cmbPeriodo.getSelectionModel().selectFirst();
		
		//populate order by combobox
		cmbOrdenacao.getItems().addAll("Sample1", "Sample2");
		cmbPeriodo.getSelectionModel().selectFirst();
		
		//message class
		message = new ShowMessage();
		
		
		//get jar's path / cuts path string until the last "/" and replace "file:/" to ""  
		try {
			currentFolder = Main.class.getProtectionDomain().getCodeSource().getLocation().toURI().toString().replace("file:", "");		
			
			currentFolder = currentFolder.substring(0, currentFolder.lastIndexOf("/")) + "/";		
		} catch (URISyntaxException e) {
			message.showMessageAlert("Erro", "Erro ao obter o caminho do executáve\nFavor verificar as permissões");	
		}	
	}
	
	public void search() {
		try {
			//load fxml file
			FXMLLoader loader = new FXMLLoader(getClass().getResource("Search.fxml"));
			rootLayout = (AnchorPane) loader.load();
			
			//passa conexão para search
			search = loader.getController();			
			search.setConnection(getConnection());			
			
			//show scene
			Scene scene = new Scene(rootLayout);	
			searchStage = new Stage();
			searchStage.setScene(scene);
			searchStage.showAndWait();
			
			edtSearch1.setText((String) search.getId());
			lblSelection1.setText(search.getContent());			
		} catch (IOException e) {			
			message.showMessageAlert("Erro", "Ocorreu um erro: " + e.getMessage());			
		}
	}
	
	public void validate() {		
		//datePicker		
		if (datePickerInicio1.getValue() != null) {
			if (datePickerFim1.getValue() == null) {
				message.showMessageAlert("Aviso", "Favor preencher a data de fim");
			}
			else if (datePickerInicio1.getValue().isAfter(datePickerFim1.getValue())) {
				message.showMessageAlert("Aviso", "Data inicial é maior que a data final");
			}
			else {
				setParameters("data_picker_inicio_1", datePickerInicio1.getValue().toString()+ "00:00:00");
				setParameters("data_picker_fim_1", datePickerFim1.getValue().toString() + "23:59:59");
			}
		}else			
			message.showMessageAlert("Aviso", "Favor preencher a data de inicio");	
		
		//search filter
		if (!edtSearch1.getText().isEmpty()) 
			setParameters("id", edtSearch1.getText());		
		
		//configure parameters
		
		setParameters("ordenacao", "FIELD_ONE");
		setParameters("ordenadopor", "FIELD ONE");
		
		setParameters("dbname", "mydbname");
		
		setParameters("path_subreport", currentFolder);
	}
	
	private void setParameters(String key, String parameter) {
		parameters.put(key, parameter);
	}
	
	public void print() {
		try {
			validate();			
			
			JasperDesign jasperDesign = JRXmlLoader.load(currentFolder+"reports/report.jrxml");
			
			JasperReport jasperReport = JasperCompileManager.compileReport(jasperDesign);
			
			JasperPrint jasperPrint = JasperFillManager.fillReport(jasperReport, parameters, connection);
			
			if (jasperPrint.getPages().size() > 0) {
				JasperViewer jasperViewer = new JasperViewer(jasperPrint, true);				
				jasperViewer.setVisible(true);
			}else
				message.showMessageAlert("Aviso", "Não existe páginas para serem impressas");					

		} catch (JRException e) { 		
			if (e.getMessage().contains("(No such file or directory)")) {
				message.showMessageAlert("Erro", "Arquivo do relatório não encontrado");
			}
			else {
				System.out.println(e.getMessage());
				message.showMessageAlert("Erro", e.getMessage());	
			}
		}
	}
	
	@FXML
	 private void handleKeyPressed(KeyEvent key){
		if (key.getCode().toString().equals("ESCAPE")) {
			close();
		}	   
	 }
	
	public void close() {
		Platform.exit();
	}	

	public Connection getConnection() {
		return connection;
	}

	public void setConnection(Connection connection) {
		this.connection = connection;
	}

	public String getReportId() {
		return reportId;
	}

	public void setReportId(String reportId) {
		this.reportId = reportId;
	}	
}
