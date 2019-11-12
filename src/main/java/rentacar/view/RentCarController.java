package rentacar.view;

import java.io.File;
import java.net.MalformedURLException;
import java.net.URL;
import java.time.LocalDate;
import java.util.ResourceBundle;

import org.hibernate.Query;
import org.hibernate.Session;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import rentacar.Car;
import rentacar.Category;
import rentacar.Classification;
import rentacar.Client;
import rentacar.Operator;
import rentacar.Opis;
import rentacar.Rent;
import javafx.collections.transformation.FilteredList;
import javafx.collections.transformation.SortedList;

public class RentCarController implements Initializable {

	@FXML private TextField clientPIN;
	//@FXML private TextArea clientInfo;
	@FXML private TextField carSpecs;
	@FXML private TableView<Car> cartableView;
	@FXML private TableView<Client> clientTableView;
	@FXML private DatePicker returnDate;
	@FXML private ImageView carImg;

	@FXML private TableColumn<Client,String> clientNames;
	@FXML private TableColumn<Client, String> clientPin;
	@FXML private TableColumn<Client, String> clientDriveLic;
	@FXML private TableColumn<Client, String> clientAddress;
	
	public void showClientInfo() {
		
		Session session = rentacar.HibernateUtil.getSessionFactory().openSession();
        session.beginTransaction();
        Query query = session.createQuery("from Client");
		ObservableList<Client> clientList = FXCollections.observableArrayList(query.list());
        
        session.getTransaction().commit();
        
        clientNames.setCellValueFactory(
                new PropertyValueFactory<Client, String>("clientName"));
        clientPin.setCellValueFactory(
                new PropertyValueFactory<Client, String>("clientPIN"));
        clientAddress.setCellValueFactory(
                new PropertyValueFactory<Client, String>("clientAddress"));       
        clientDriveLic.setCellValueFactory(
                new PropertyValueFactory<Client, String>("clientDriveLicenceNumber"));   
        
        
        clientTableView.setItems(clientList);
        FilteredList<Client> filteredData = new FilteredList<>(clientList, p -> true);

        clientPIN.textProperty().addListener((observable, oldValue, newValue) -> {
            filteredData.setPredicate(person -> {
                if (newValue == null || newValue.isEmpty()) {
                    return true;
                }
                
                String lowerCaseFilter = newValue.toLowerCase();
                
                if (person.getClientName().toLowerCase().contains(lowerCaseFilter)) {
                    return true; 
                } else if (person.getClientPIN().toLowerCase().contains(lowerCaseFilter)) {
                    return true; 
                }
                return false;
            });
        });
    
        SortedList<Client> sortedData = new SortedList<>(filteredData);
        sortedData.comparatorProperty().bind(clientTableView.comparatorProperty());
        clientTableView.setItems(sortedData); 
    	

	}
	
		@FXML TableColumn<Car,String> regNumberCol;
		@FXML TableColumn<Car,String> carModelCol;
		@FXML TableColumn<Car,Category> carCategoryCol;
		@FXML TableColumn<Car,Classification> carClassificatinCol;
		@FXML TableColumn<Car,Boolean> smoking;
	 
	
	public void initCarTableView() {
		Session session = rentacar.HibernateUtil.getSessionFactory().openSession();
        session.beginTransaction();
        
		Query query2 = session.createQuery("from Car c where c.carStatus='0'"); 
		ObservableList<Car> list2 = FXCollections.observableArrayList(query2.list());
        session.getTransaction().commit();
        
        regNumberCol.setCellValueFactory(
                new PropertyValueFactory<Car, String>("regNumber"));

        carModelCol.setCellValueFactory(
                new PropertyValueFactory<Car, String>("carModel"));
        
        smoking.setCellValueFactory(
                new PropertyValueFactory<Car, Boolean>("carStatus"));
      
        carCategoryCol.setCellValueFactory(
                new PropertyValueFactory<Car, Category>("category"));
        
        carClassificatinCol.setCellValueFactory(
                new PropertyValueFactory<Car, Classification>("classification"));
        
        cartableView.setItems(list2);
        
        	//NACHALO NA FILTRACIQ
        FilteredList<Car> filteredData = new FilteredList<>(list2, p -> true);

            carSpecs.textProperty().addListener((observable, oldValue, newValue) -> {
                filteredData.setPredicate(person -> {
                    if (newValue == null || newValue.isEmpty()) {
                        return true;
                    }
                    
                    String lowerCaseFilter = newValue.toLowerCase();
                    
                    if (person.getCarModel().toLowerCase().contains(lowerCaseFilter)) {
                        return true;
                    } else if (person.getCategory().getCategoryType().toLowerCase().contains(lowerCaseFilter)) {
                        return true; 
                    }
                    return false; 
                });
            });
        
            SortedList<Car> sortedData = new SortedList<>(filteredData);
            sortedData.comparatorProperty().bind(cartableView.comparatorProperty());
            cartableView.setItems(sortedData);   
	}


	@Override
	public void initialize(URL location, ResourceBundle resources) {
		initCarTableView();
		showClientInfo();

	}
	
	public void imageViewUpdate() throws MalformedURLException {
		
		Car car1 = cartableView.getSelectionModel().getSelectedItem();
		
		File file = new File(car1.getPhotoPath());
		 String localUrl = file.toURI().toURL().toString();	          
         Image image = new Image(localUrl);
         carImg.setImage(image);
	}
	
	public void rentBtn() throws MalformedURLException {
		LocalDate rtrnDate= returnDate.getValue();
		Operator operator = Singleton.getInstance().getLogedOperator();
		Car car1 = cartableView.getSelectionModel().getSelectedItem();
		Client choosenClient = clientTableView.getSelectionModel().getSelectedItem();
		
		 Rent rent =new Rent(LocalDate.now(), rtrnDate, 0, 0, operator, car1, choosenClient);
		 Opis opis = new Opis(car1.getCurrKM(), LocalDate.now(), "Otdadena bez problem", car1);

		 
		 Session session = rentacar.HibernateUtil.getSessionFactory().openSession();
	     session.beginTransaction();	    
	     session.save(rent);
	     session.save(opis);
		 car1.setCarStatus(true);
		 session.update(car1);
		 
		 session.getTransaction().commit();
	
		 
	}
	
}
