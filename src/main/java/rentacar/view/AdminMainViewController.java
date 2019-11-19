package rentacar.view;

import java.awt.Button;
import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

import org.hibernate.Query;
import org.hibernate.Session;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.ComboBox;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import rentacar.Category;
import rentacar.Classification;
import rentacar.Company;
import rentacar.Operator;

public class AdminMainViewController implements Initializable {
	
	
	//Add Company Tab
	@FXML
	private TextField companyName;
	@FXML
	private TextField companyAddress;
	
	@FXML
	private void addCompany() {
		
		String companyName1 = companyName.getText().toString();
		String companyAddress1 = companyAddress.getText().toString();
		Company cmpn =new Company(companyName1, companyAddress1);
		
		Session session = rentacar.HibernateUtil.getSessionFactory().openSession();
        session.beginTransaction();
        session.save(cmpn);
        session.getTransaction().commit();
	}
	
	
	//Add operator Tab
	@FXML
	private TextField operatorUsername;
	@FXML
	private PasswordField operatorPassword;
	@FXML
	private TextField operatorNames;
	@FXML
	private ComboBox<Company> companyComboBox;
	@FXML
	private Button addOperatorBtn;

	


	public void initialize(URL location, ResourceBundle resources) {
		// TODO Auto-generated method stub
		
		Session session = rentacar.HibernateUtil.getSessionFactory().openSession();
		Query query = session.createQuery("from Company");
		ObservableList<Company> list = FXCollections.observableArrayList(query.list());
		companyComboBox.getItems().setAll(list);
	}
	@FXML
	private void addOperator() {
		String opUsername = operatorUsername.getText().toString();
		String opPassword = operatorPassword.getText().toString();
		String opNames = operatorNames.getText().toString();
		Company cmp=(Company)companyComboBox.getValue();
		System.out.println(cmp.getCompanyName());
		
		Operator oper = new Operator(opUsername, opPassword, opNames, false, cmp);

		Session session = rentacar.HibernateUtil.getSessionFactory().openSession();
        session.beginTransaction();
        session.save(oper);
        session.getTransaction().commit();
	}

		//Add Classification
	@FXML private TextField typeClassification;
	@FXML private TextField classificationPricePerDay;
	@FXML private TextField classificationPricePerKM;
	
	public void addClassification() {
		String classificationType = typeClassification.getText().toString();
		Double pricePerDay = Double.parseDouble(classificationPricePerDay.getText().toString());
		Double pricePerKM = Double.parseDouble(classificationPricePerKM.getText().toString());
		
		 Classification cl = new Classification(classificationType, pricePerDay, pricePerKM);
		 Session session = rentacar.HibernateUtil.getSessionFactory().openSession();
	     session.beginTransaction();	
	     session.save(cl);
	     session.getTransaction().commit();
	}
	
	 	//Add Category
	@FXML private TextField typeCategory;
	
	public void addCategory() {
		 String CategoryType = typeCategory.getText().toString();
		 Category ctgr = new Category(CategoryType);
		 Session session = rentacar.HibernateUtil.getSessionFactory().openSession();
	     session.beginTransaction();	
	     session.save(ctgr);
	     session.getTransaction().commit();
	}
	
}
