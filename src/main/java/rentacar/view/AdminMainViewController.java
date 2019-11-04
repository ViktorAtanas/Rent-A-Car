package rentacar.view;

import java.awt.Button;
import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

import org.hibernate.Session;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ComboBox;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
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
	private ComboBox companyComboBox;
	@FXML
	private Button addOperatorBtn;


	public void initialize(URL location, ResourceBundle resources) {
		// TODO Auto-generated method stub
		
		Session session = rentacar.HibernateUtil.getSessionFactory().openSession();
		List <Company> kl = session.createQuery("from Company").list();
		
		companyComboBox.getItems().setAll(kl);
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

	  
}
