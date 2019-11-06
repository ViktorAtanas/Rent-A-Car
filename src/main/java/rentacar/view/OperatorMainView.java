package rentacar.view;


import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import org.hibernate.Query;
import org.hibernate.Session;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.TabPane;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.VBox;
import javafx.stage.FileChooser;
import javafx.stage.Modality;
import javafx.stage.Stage;
import rentacar.App;
import rentacar.Car;
import rentacar.Category;
import rentacar.Classification;

public class OperatorMainView implements Initializable{

	@FXML
	AnchorPane app= new AnchorPane();

	@FXML
	private BorderPane bp;
	
	
	@Override
	public void initialize(URL location, ResourceBundle resources) {
		// TODO Auto-generated method stub
		
		Session session = rentacar.HibernateUtil.getSessionFactory().openSession();
        session.beginTransaction();
        
		Query query = session.createQuery("from Category");
		ObservableList<Category> list = FXCollections.observableArrayList(query.list());
		categoryChoiceBox.setItems(list);
		
		Query query2 = session.createQuery("from Classification");
		ObservableList<Classification> list2 = FXCollections.observableArrayList(query2.list());
		classificationChoiceBox.setItems(list2);
		
        session.getTransaction().commit();
	}
	

	@FXML
	public void rentCar() throws IOException {
		
		app.getChildren().add(FXMLLoader.load(getClass().getResource("Naemane.fxml")));
		bp.setCenter(app);
	}
	
	//ADD CAR
	String  fileAsString="";
	@FXML
	private TextField regNumber;
	@FXML
	private TextField carModel;
	@FXML
	private ChoiceBox<Category> categoryChoiceBox;
	@FXML
	private ChoiceBox<Classification> classificationChoiceBox;
	@FXML
	private TextField currentKM;
	@FXML
	private Button choosePictureBtn;
	@FXML 
	private CheckBox smokingCheckBox;

	 
	@FXML
	private void choosePicture() {
		 
		 		Stage stage = new Stage();
		 		FileChooser.ExtensionFilter imageFilter
		        = new FileChooser.ExtensionFilter("Image Files", "*.jpg", "*.png");
	            FileChooser chooser = new FileChooser();
	            chooser.getExtensionFilters().add(imageFilter);
	            File file = chooser.showOpenDialog(stage);
	            if (file != null) {
	              fileAsString = file.toString();

	               System.out.println(fileAsString);
	            } else {
		              System.out.println("Not choosed image");
	            }
	            
	        }
	


	
	public void addNewCar() {
		String regNumb = regNumber.getText().toString();
		String carMdl = carModel.getText().toString();
		Category carCategory = (Category)categoryChoiceBox.getValue();
		Classification carClassification = (Classification)classificationChoiceBox.getValue();
		Double currKm = Double.parseDouble(currentKM.getText().toString());
		boolean smokingStatus = smokingCheckBox.isSelected();
		
		
		System.out.println(carCategory);
		System.out.println(carClassification);
		
		
		Session session = rentacar.HibernateUtil.getSessionFactory().openSession();
        session.beginTransaction();
        Car car1 = new Car(regNumb, carMdl, smokingStatus, false, currKm, fileAsString, carClassification, carCategory);
        session.save(car1);
        session.getTransaction().commit();
	}
	
	
	//Spravki
	
	
}
