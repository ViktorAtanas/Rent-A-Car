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
	@FXML private TextArea clientInfo;
	@FXML private TextField carSpecs;
	@FXML private TableView<Car> cartableView;
	@FXML private DatePicker returnDate;
	@FXML private ImageView carImg;
	private Client choosenClient;
	
	
	public void showClientInfo() {
		String clPIN = clientPIN.getText().toString();
		clientInfo.clear();
		
		Session session = rentacar.HibernateUtil.getSessionFactory().openSession();
        session.beginTransaction();
        
        choosenClient = (Client) session.createQuery("from Client s where s.clientPIN='"+clPIN+"'").uniqueResult();
        session.getTransaction().commit();
        
        if(choosenClient!=null)
    	clientInfo.setText(choosenClient.toString());

	}
	
		@FXML TableColumn<Car,String> regNumberCol;
		@FXML TableColumn<Car,String> carModelCol;
		@FXML TableColumn<Car,Category> carCategoryCol;
		@FXML TableColumn<Car,Classification> carClassificatinCol;
		@FXML TableColumn<Car,Boolean> smoking;
	 
	
	public void initCarTableView() {
		Session session = rentacar.HibernateUtil.getSessionFactory().openSession();
        session.beginTransaction();
        
		Query query2 = session.createQuery("from Car"); 
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
                    // If filter text is empty, display all persons.
                    if (newValue == null || newValue.isEmpty()) {
                        return true;
                    }
                    
                    // Compare first name and last name of every person with filter text.
                    String lowerCaseFilter = newValue.toLowerCase();
                    
                    if (person.getCarModel().toLowerCase().contains(lowerCaseFilter)) {
                        return true; // Filter matches first name.
                    } else if (person.getCategory().getCategoryType().toLowerCase().contains(lowerCaseFilter)) {
                        return true; // Filter matches last name.
                    }
                    return false; // Does not match.
                });
            });
        
            SortedList<Car> sortedData = new SortedList<>(filteredData);
            sortedData.comparatorProperty().bind(cartableView.comparatorProperty());
            cartableView.setItems(sortedData);   
	}


	@Override
	public void initialize(URL location, ResourceBundle resources) {
		initCarTableView();
		
	}
	
	public void imageViewUpdate() throws MalformedURLException {
		
		Car car1 = cartableView.getSelectionModel().getSelectedItem();
		
		File file = new File(car1.getPhotoPath());
		 String localUrl = file.toURI().toURL().toString();	          
         Image image = new Image(localUrl);
         carImg.setImage(image);
		
		
		
		/*
		 String imageSource = "C:/Users/Atanas/Desktop/download.jpg";
		  Image image = new Image(imageSource);
	        carImg.setImage(image);
	        */
	}
	
	public void rentBtn() throws MalformedURLException {
		LocalDate rtrnDate= returnDate.getValue();
		Operator operator = Singleton.getInstance().getLogedOperator();
		Car car1 = cartableView.getSelectionModel().getSelectedItem();
		 
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
