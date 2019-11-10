package rentacar.view;

import java.net.URL;
import java.time.LocalDate;
import java.util.ResourceBundle;

import org.hibernate.Query;
import org.hibernate.Session;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.CheckBox;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import rentacar.Car;
import rentacar.Client;
import rentacar.Rent;

public class ReturnCarController implements Initializable{
	@FXML TextField clientPin;
	@FXML ListView<Rent> rentListView;
	@FXML TextField currKM;
	@FXML CheckBox problemsCheckBox;
	@FXML TextField problemsTextField;
	@FXML Label problemsLabel;

	
		@FXML public void enableProblemsField() {
	
		if(problemsCheckBox.isSelected())
		{
			problemsTextField.setVisible(true);
			problemsLabel.setVisible(true);
			problemsTextField.requestFocus();
		}
		else
		{
			problemsTextField.setVisible(false);
			problemsLabel.setVisible(false);
		}
		
	}
		private Client cl1;
		
		@FXML private void returnCarBtn() {
			
			Double currKm = Double.parseDouble(currKM.getText().toString());

		}
		
		
		@FXML private TableView<Rent> rentTableView;
		@FXML private TableColumn<Rent,LocalDate> rentDayColumn;
		@FXML private TableColumn<Rent, Car> rentCarColumn;
		
		public void updateRentListView() {
			
			String clPin = clientPin.getText().toString();
			double dd =0;
			
			 Session session = rentacar.HibernateUtil.getSessionFactory().openSession();
		     session.beginTransaction();
		     
		    cl1 = (Client) session.createQuery("from Client s where s.clientPIN='"+clPin+"'").uniqueResult();
		    int clientId = cl1.getIdClient();
			    
			Query query = session.createQuery("from Rent s where s.client='"+clientId+"'");
			ObservableList<Rent> list1 = FXCollections.observableArrayList(query.list());
			
			session.getTransaction().commit();
			
			rentDayColumn.setCellValueFactory(
	                new PropertyValueFactory<Rent, LocalDate>("dateRent"));
			rentCarColumn.setCellValueFactory(
	                new PropertyValueFactory<Rent, Car>("car"));
			
			System.out.println("LIST:"+list1);
			rentTableView.setItems(list1);
			
		    
			
		}

		@Override
		public void initialize(URL location, ResourceBundle resources) {
			// TODO Auto-generated method stub

		}



}
