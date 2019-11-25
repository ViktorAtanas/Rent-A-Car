package rentacar.view;

import java.net.URL;
import java.time.LocalDate;
import java.time.Period;
import java.util.ResourceBundle;


import org.hibernate.Session;
import org.hibernate.query.Query;

import DataValidation.DataValidation;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.collections.transformation.SortedList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import rentacar.Car;
import rentacar.Client;
import rentacar.Opis;
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
		@FXML private TableView<Rent> rentTableView;
		@FXML private TableColumn<Rent,LocalDate> rentDayColumn;
		@FXML private TableColumn<Rent, Car> rentCarColumn;
		@FXML private TableColumn<Rent, Integer> rentIdRentColumn;
		@FXML private TableColumn<Rent, Client> rentClientColumn;
		
		ObservableList<Rent> list1;
		public void updateRentListView() {
			Session session = rentacar.HibernateUtil.getSessionFactory().openSession();
			session.beginTransaction();
			Query query = session.createQuery("from Rent s where s.completedStatus='0'");
			list1 = FXCollections.observableArrayList(query.list());
			 session.getTransaction().commit();
			
			rentDayColumn.setCellValueFactory(
	                new PropertyValueFactory<Rent, LocalDate>("dateRent"));
			rentCarColumn.setCellValueFactory(
	                new PropertyValueFactory<Rent, Car>("car"));
			rentIdRentColumn.setCellValueFactory(
	                new PropertyValueFactory<Rent, Integer>("idRent"));
			rentClientColumn.setCellValueFactory(
	                new PropertyValueFactory<Rent, Client>("client"));
			
			rentTableView.setItems(list1);
			
			  FilteredList<Rent> filteredData = new FilteredList<>(list1, p -> true);

			clientPin.textProperty().addListener((observable, oldValue, newValue) -> {
	            filteredData.setPredicate(rent -> {
	                if (newValue == null || newValue.isEmpty()) {
	                    return true;
	                }
	                String lowerCaseFilter = newValue.toLowerCase();
	                
	                if (rent.getClient().getClientPIN().toLowerCase().contains(lowerCaseFilter)) {
	                    return true;
	                }
	                	return false;
	                              
	            });
	        });
	    
	        SortedList<Rent> sortedData = new SortedList<>(filteredData);
	        sortedData.comparatorProperty().bind(rentTableView.comparatorProperty());
	        rentTableView.setItems(sortedData); 
		
		}

		@Override
		public void initialize(URL location, ResourceBundle resources) {
			// TODO Auto-generated method stub
			updateRentListView();

		}
		
		@FXML private Label rentChoosenLabel;
		@FXML private Label correctKMLabel;
		@FXML private Label retrunStatus;

		@FXML private void returnCarBtn() {
			
			boolean numericKM = DataValidation.textNumeric(currKM, correctKMLabel, "Въведете коректни данни 0-9");
			if(rentTableView.getSelectionModel().getSelectedItem()==null)
			rentChoosenLabel.setText("Изберете наемане");
			else
				rentChoosenLabel.setText("");
			
			if(numericKM && rentTableView.getSelectionModel().getSelectedItem()!=null)
			{
			Double newKm = Double.parseDouble(currKM.getText().toString());
			Rent rent= rentTableView.getSelectionModel().getSelectedItem();
			Car rentedCar = rent.getCar();
			LocalDate today = LocalDate.now();
			
			Double traveledKm = newKm - rentedCar.getCurrKM();
			Period p = Period.between(rent.getDateRent(),rent.getDateReturn());
			Period extra = Period.between(rent.getDateReturn(),today);
			
			//Price Calculating
			Double totalPrice = p.getDays()*rentedCar.getClassification().getPricePerDay()+rentedCar.getClassification().getPricePerKM()*traveledKm;
			
			if(rent.getDateReturn().isBefore(today)) {
				totalPrice+=extra.getDays()*rentedCar.getClassification().getPricePerDay();
				totalPrice+=totalPrice*0.08;  //8% vrushtane sled sroka
				rent.setDateReturn(today);
				rent.getClient().setClientRating(rent.getClient().getClientRating()-5);
			}
			if(problemsCheckBox.isSelected()) {
				totalPrice+=100;
				rent.getClient().setClientRating(rent.getClient().getClientRating()-3);
			}
			
			rent.setTraveledKM(traveledKm);
			rent.setTotalPrice(totalPrice);
			rent.setCompletedStatus(true);

			
			rentedCar.setCarStatus(false);
			rentedCar.setCurrKM(newKm);
	
			
			String returnProblems = "Върната без проблем";
			if(problemsCheckBox.isSelected())
				returnProblems = problemsTextField.getText().toString();
			
			Opis returnOpis = new Opis(newKm, today, returnProblems, rentedCar);
			
			 Session session = rentacar.HibernateUtil.getSessionFactory().openSession();
		     session.beginTransaction();	
		     session.update(rent);
		     session.update(rentedCar);
		     session.save(returnOpis);
		     list1.remove(rent);
		     session.getTransaction().commit();
			
			currKM.clear();
			clientPin.clear();
			}
			

			

		}


}
