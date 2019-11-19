package rentacar.view;


import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

import org.hibernate.Session;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;
import rentacar.Operator;

public class OperatorMainView implements Initializable{

	@FXML private BorderPane mainBP;
	@FXML	AnchorPane rentCarAP= new AnchorPane();
	@FXML	AnchorPane returnCarAP= new AnchorPane();
	@FXML	AnchorPane newClientAP= new AnchorPane();
	@FXML	AnchorPane newCarap= new AnchorPane();
	@FXML	AnchorPane availableCarsAP= new AnchorPane();
	@FXML	AnchorPane historyOfCarsAP= new AnchorPane();
	@FXML	AnchorPane operatorsWorkAP= new AnchorPane();
	@FXML	AnchorPane clinetRatingAP= new AnchorPane();
	@FXML	AnchorPane statisticskAP= new AnchorPane();
	@FXML private AnchorPane mainOperatorAP;
	
	@Override
	public void initialize(URL location, ResourceBundle resources) {
		// TODO Auto-generated method stub

	}
	

	@FXML	public void rentCarMenuItem() throws IOException {	
		rentCarAP.getChildren().clear();
		rentCarAP.getChildren().add(FXMLLoader.load(getClass().getResource("RentCarView.fxml")));
		mainBP.setCenter(rentCarAP);
	}
	
	@FXML	public void returnMenuItem() throws IOException {
		returnCarAP.getChildren().clear();
		returnCarAP.getChildren().add(FXMLLoader.load(getClass().getResource("ReturnCarView.fxml")));
		mainBP.setCenter(returnCarAP);
	}
	
	@FXML	public void NewCarMenuItem() throws IOException {	
		newCarap.getChildren().clear();
		newCarap.getChildren().add(FXMLLoader.load(getClass().getResource("RegisterCarView.fxml")));
		mainBP.setCenter(newCarap);
	}
	@FXML	public void NewClientMenuItem() throws IOException {	
		newClientAP.getChildren().clear();
		newClientAP.getChildren().add(FXMLLoader.load(getClass().getResource("RegisterClientView.fxml")));
		mainBP.setCenter(newClientAP);
	}
	
	@FXML	public void availableCarsMenuItem() throws IOException {		
		availableCarsAP.getChildren().clear();
		availableCarsAP.getChildren().add(FXMLLoader.load(getClass().getResource("spravki/AvailableCarsView.fxml")));
		mainBP.setCenter(availableCarsAP);
	}
	@FXML	public void historyOfCarsMenuItem() throws IOException {		
		historyOfCarsAP.getChildren().clear();
		historyOfCarsAP.getChildren().add(FXMLLoader.load(getClass().getResource("spravki/HistoryOfCarsView.fxml")));
		mainBP.setCenter(historyOfCarsAP);
	}
	@FXML	public void operatorsWorkMenuItem() throws IOException {		
		operatorsWorkAP.getChildren().clear();
		operatorsWorkAP.getChildren().add(FXMLLoader.load(getClass().getResource("spravki/OperatorsWorkView.fxml")));
		mainBP.setCenter(operatorsWorkAP);
	}
	
	@FXML	public void clientRatingMenuItem() throws IOException {		
		clinetRatingAP.getChildren().clear();
		clinetRatingAP.getChildren().add(FXMLLoader.load(getClass().getResource("spravki/ClientRatingView.fxml")));
		mainBP.setCenter(clinetRatingAP);
	}
	@FXML	public void statisticsMenuItem() throws IOException {		
		statisticskAP.getChildren().clear();
		statisticskAP.getChildren().add(FXMLLoader.load(getClass().getResource("spravki/StatisticsView.fxml")));
		mainBP.setCenter(statisticskAP);
	}
	@FXML	public void homeMenuItem() throws IOException {		
		mainBP.setCenter(mainOperatorAP);
	}
	
	@FXML	public void logout() throws IOException {		
		 Stage stage = (Stage) mainBP.getScene().getWindow();
		 stage.close();
		 
		 Operator loged = Singleton.getInstance().getLogedOperator();
		 Session session = rentacar.HibernateUtil.getSessionFactory().openSession();
		 session.beginTransaction();
		 loged.setStatusLogin(false);
		 session.update(loged);
		 session.getTransaction().commit();
		 
		Stage primaryStage = new Stage();		
			try {			
				Parent root = FXMLLoader.load(getClass().getResource("LoginView.fxml"));
				Scene scene = new Scene(root);
				primaryStage.setScene(scene);
				primaryStage.getIcons().add(new Image("/photos/icon1.png"));
				primaryStage.setTitle("Коли под наем");
				primaryStage.show();
			} catch(Exception e) {
				e.printStackTrace();
			}
		 
	}



	
	
	


	
	
	
	
	
}
