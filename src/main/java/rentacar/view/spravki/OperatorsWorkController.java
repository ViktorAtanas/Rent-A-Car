package rentacar.view.spravki;

import java.net.URL;
import java.time.LocalDate;
import java.util.ResourceBundle;

import org.hibernate.Session;
import org.hibernate.query.Query;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import rentacar.Car;
import rentacar.Client;
import rentacar.Operator;
import rentacar.Rent;

public class OperatorsWorkController implements Initializable {

	
	 
	@FXML private DatePicker dateFrom;
	@FXML private DatePicker dateTo;
	@FXML private ComboBox<Operator> operatorsComboBox;
	@FXML private TableView<Rent> rentTableView;
	@FXML private TableColumn<Rent,Integer> rentID;
	@FXML private TableColumn<Rent,Client> rentClient;
	@FXML private TableColumn<Rent,Car> rentCar;
		
	@FXML private void OperatorsAndTheirWork() {
		
			LocalDate startDate = dateFrom.getValue();
			LocalDate endDate = dateTo.getValue();
			Operator choosenOperator = (Operator) operatorsComboBox.getValue();
			Session session = rentacar.HibernateUtil.getSessionFactory().openSession();
		    session.beginTransaction();
		    
		    Query query = session.createQuery("from Rent r where Operator_idOperator='"+choosenOperator.getIdOperator()+"' AND r.dateRent BETWEEN '"+startDate+"' AND '"+endDate+"'"); 
			ObservableList<Rent> listRent = FXCollections.observableArrayList(query.list());
			
		    session.getTransaction().commit();
		    System.out.println(listRent);
		    rentTableView.setItems(listRent);
		    
		}

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		// TODO Auto-generated method stub
		Session session = rentacar.HibernateUtil.getSessionFactory().openSession();
	    session.beginTransaction();
	    Query query = session.createQuery("from Operator"); 
		ObservableList<Operator> listOperators = FXCollections.observableArrayList(query.list());
	    session.getTransaction().commit();
	    
	    operatorsComboBox.setItems(listOperators);
	    
	    rentID.setCellValueFactory(
	            new PropertyValueFactory<Rent, Integer>("idRent"));
	    rentClient.setCellValueFactory(
	            new PropertyValueFactory<Rent, Client>("client"));
	    rentCar.setCellValueFactory(
	            new PropertyValueFactory<Rent, Car>("car"));
	    
	}

}
	

