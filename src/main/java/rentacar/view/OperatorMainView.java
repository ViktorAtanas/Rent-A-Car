package rentacar.view;


import java.io.IOException;
import java.net.URL;
import java.time.LocalDate;
import java.util.ResourceBundle;
import java.util.Timer;
import java.util.TimerTask;

import org.controlsfx.control.Notifications;
import org.hibernate.Session;
import org.hibernate.query.Query;

import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.geometry.Pos;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;
import rentacar.Operator;
import rentacar.Rent;

public class OperatorMainView implements Initializable {

	@FXML private BorderPane mainBP;
	@FXML private AnchorPane rentCarAP= new AnchorPane();
	@FXML private AnchorPane returnCarAP= new AnchorPane();
	@FXML private AnchorPane newClientAP= new AnchorPane();
	@FXML private AnchorPane newCarap= new AnchorPane();
	@FXML private AnchorPane availableCarsAP= new AnchorPane();
	@FXML private AnchorPane historyOfCarsAP= new AnchorPane();
	@FXML private AnchorPane operatorsWorkAP= new AnchorPane();
	@FXML private AnchorPane clinetRatingAP= new AnchorPane();
	@FXML private AnchorPane statisticskAP= new AnchorPane();
	@FXML private AnchorPane mainOperatorAP;


	
	@Override
	public void initialize(URL location, ResourceBundle resources) {
		// TODO Auto-generated method stub
		try {
			rentCarMenuItem();
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		new Notification().start();

        /*TimerTask timerTask = new TimerTask() {

            @Override
            public void run() {
        		LocalDate today = LocalDate.now();
        		Session session = rentacar.HibernateUtil.getSessionFactory().openSession();
        		session.beginTransaction();
        		Query query = session.createQuery("from Rent r where r.completedStatus='0' AND r.dateReturn<='"+today+"'");
        		ObservableList<Rent> rentList = FXCollections.observableArrayList(query.list());
        		session.getTransaction().commit();

        		 Platform.runLater(()->{
                     Notifications.create().title("Изтекли наемания").text(rentList.toString()).position(Pos.TOP_RIGHT).showInformation();
                 });
            }
        };

        Timer timer = new Timer("MyTimer");//create a new Timer

        timer.scheduleAtFixedRate(timerTask, 30, 3000);
        
    	*/
    	
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
				primaryStage.getIcons().add(new Image("/photos/icon11.png"));
				primaryStage.setTitle("Коли под наем");
				primaryStage.show();
			} catch(Exception e) {
				e.printStackTrace();
			}
		 
	}

}

class Notification extends Thread{
	
	public void run() {
		
		LocalDate today = LocalDate.now();
		Session session = rentacar.HibernateUtil.getSessionFactory().openSession();
		session.beginTransaction();
		Query query = session.createQuery("from Rent r where r.completedStatus='0' AND r.dateReturn<='"+today+"'");
		ObservableList<Rent> rentList = FXCollections.observableArrayList(query.list());
		session.getTransaction().commit();

		 Platform.runLater(()->{
             Notifications.create().title("Изтекли наемания").text(rentList.toString()).position(Pos.TOP_RIGHT).showInformation();
         });
	}
	
}
