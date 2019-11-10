package rentacar.view;


import java.io.File;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ResourceBundle;
import org.hibernate.Query;
import org.hibernate.Session;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import rentacar.Car;
import rentacar.Category;
import rentacar.Classification;
import rentacar.Client;

public class OperatorMainView implements Initializable{

	@FXML	AnchorPane app= new AnchorPane();
	@FXML	AnchorPane ap2= new AnchorPane();
	@FXML	AnchorPane ap3= new AnchorPane();
	@FXML	private BorderPane bp;
	
	
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
	

	@FXML	public void rentCarBtn() throws IOException {		
		app.getChildren().add(FXMLLoader.load(getClass().getResource("RentCarView.fxml")));
		bp.setCenter(app);
	}
	
	@FXML	public void returnCarBtn() throws IOException {
		ap2.getChildren().add(FXMLLoader.load(getClass().getResource("ReturnCarView.fxml")));
		bp.setCenter(ap2);
	}
	@FXML	public void homeBtn() throws IOException {		
		app.getChildren().add(FXMLLoader.load(getClass().getResource("HomeView.fxml")));
		bp.setCenter(ap3);
	}
	
	//ADD CAR
	String  fileAsString="";
	@FXML	private TextField regNumber;
	@FXML	private TextField carModel;
	@FXML	private ChoiceBox<Category> categoryChoiceBox;
	@FXML	private ChoiceBox<Classification> classificationChoiceBox;
	@FXML	private TextField currentKM;
	@FXML	private Button choosePictureBtn;
	@FXML 	private CheckBox smokingCheckBox;
	@FXML private ImageView img1;
	
	@FXML	private void choosePicture() throws MalformedURLException {
		 		img1.setImage(null);
		 		Stage stage = new Stage();
		 		FileChooser.ExtensionFilter imageFilter
		        = new FileChooser.ExtensionFilter("Image Files", "*.jpg", "*.png");
	            FileChooser chooser = new FileChooser();
	            chooser.getExtensionFilters().add(imageFilter);
	            File file = chooser.showOpenDialog(stage);
	            if (file != null) {
	            fileAsString = file.toString();
	               String localUrl = file.toURI().toURL().toString();	          
	               Image image = new Image(localUrl);
	               img1.setImage(image);
	               
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

		Session session = rentacar.HibernateUtil.getSessionFactory().openSession();
        session.beginTransaction();
        Car car1 = new Car(regNumb, carMdl, smokingStatus, false, currKm, fileAsString, carClassification, carCategory);
        session.save(car1);
        session.getTransaction().commit();
	}
	//Add Client
	@FXML	private TextField clPin;
	@FXML 	private TextField clNames;
	@FXML	private TextField clAddress;
	@FXML 	private TextField clDrLicence;
	@FXML 	private GridPane clGridPane;
	@FXML 	private Label clStatusAdd;
	
	@FXML private void addOperatorBtn() {
		String cPin = clPin.getText().toString();
		String cNames = clNames.getText().toString();
		String cAddress = clAddress.getText().toString();
		String cDrLicence = clDrLicence.getText().toString();
		
		
		Session session = rentacar.HibernateUtil.getSessionFactory().openSession();
        session.beginTransaction();
        Client client1 = new Client(cNames, cPin, cAddress, 50, cDrLicence);
        session.save(client1);
        session.getTransaction().commit();
        
        clPin.clear(); clNames.clear(); clAddress.clear(); clDrLicence.clear();
        clStatusAdd.setText("Успешно регистриран клиент!");
        
	}
	
	//Spravki
	
	
}
