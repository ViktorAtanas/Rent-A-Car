package rentacar.view;

import java.net.URL;
import java.util.ResourceBundle;

import org.hibernate.Session;
import org.hibernate.query.Query;

import DataValidation.DataValidation;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import rentacar.Category;
import rentacar.Classification;
import rentacar.Company;
import rentacar.Operator;

public class AdminMainViewController implements Initializable {

	// Add Company Tab
	@FXML
	private TextField companyName;
	@FXML
	private TextField companyAddress;
	@FXML
	private Label companyNameLabel;
	@FXML
	private Label companyAddressLabel;
	@FXML
	private Label addCompanyStatus;

	@FXML
	private void addCompany() {

		boolean nameCheck = DataValidation.textFieldIsNull(companyName, companyNameLabel, "Въведете име на фирма!");
		boolean addressCheck = DataValidation.textFieldIsNull(companyAddress, companyAddressLabel,
				"Въведете адрес на фирма!");

		if (!nameCheck && !addressCheck) {
			String companyName1 = companyName.getText().toString();
			String companyAddress1 = companyAddress.getText().toString();

			Session session = rentacar.HibernateUtil.getSessionFactory().openSession();
			session.beginTransaction();
			Company companyCheck = (Company) session
					.createQuery("from Company c where c.companyName='" + companyName1 + "'").uniqueResult();
			if (companyCheck == null) {
				Company cmpn = new Company(companyName1, companyAddress1);
				session.save(cmpn);
				addCompanyStatus.setText("Успешно добавена фирма");
				companyName.clear();
				companyAddress.clear();

			} else
				addCompanyStatus.setText("Вече съществува такава фирма!");

			session.getTransaction().commit();

		}
	}

	// Add operator Tab
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

	@FXML private Label usernameLabel;
	@FXML private Label passwordLabel;
	@FXML private Label namesLabel;
	@FXML private Label companyLabel;
	@FXML private Label addOperatorStatusLabel;
	
	@FXML
	private void addOperator() {
		 boolean usernameCheck = DataValidation.textFieldIsNull(operatorUsername, usernameLabel, "Веведете потребителско име");
		 boolean passwordCheck = DataValidation.textFieldIsNull(operatorPassword, passwordLabel, "Веведете парола");
		 boolean namesCheck = DataValidation.textAlphabet(operatorNames, namesLabel, "Въведете коректни имена");
		 if(companyComboBox.getSelectionModel().isEmpty())
				companyLabel.setText("Изберете фирма");
		 else
			 companyLabel.setText("");
		 
		 if(!usernameCheck && !passwordCheck && namesCheck && !companyComboBox.getSelectionModel().isEmpty())
		 {
			String opUsername = operatorUsername.getText().toString();
			String opPassword = operatorPassword.getText().toString();
			String opNames = operatorNames.getText().toString();
			Company cmp = (Company) companyComboBox.getValue();
			Session session = rentacar.HibernateUtil.getSessionFactory().openSession();
			session.beginTransaction();
			Operator OperatorCheck = (Operator) session
					.createQuery("from Operator o where o.userName='" + opUsername + "'").uniqueResult();
			if(OperatorCheck==null)
			{
						Operator oper = new Operator(opUsername, opPassword, opNames, false, cmp);
						session.save(oper);
						addOperatorStatusLabel.setText("Успешно добавен оператор");
						operatorUsername.clear();
						operatorPassword.clear();
						operatorNames.clear();
						companyComboBox.getSelectionModel().clearSelection();
			}
			else
				addOperatorStatusLabel.setText("Вече съществува такъв оператор!");
				session.getTransaction().commit();
		 }
		 
	}

	// Add Classification
	@FXML
	private TextField typeClassification;
	@FXML
	private TextField classificationPricePerDay;
	@FXML
	private TextField classificationPricePerKM;
	@FXML
	private Label labelClassT;
	@FXML
	private Label labelClassP;
	@FXML
	private Label labelClassK;
	@FXML
	private Label labelClassSucc;

	public void addClassification() {
		
		boolean alphabetName = DataValidation.textAlphabet(typeClassification, labelClassT,
				"Моля, въведете коректен тип клас");
		boolean numericPriceDay = DataValidation.textDouble(classificationPricePerDay, labelClassP,
				"Моля, въведете коректно число");
		boolean numericPriceKM = DataValidation.textDouble(classificationPricePerKM, labelClassK,
				"Моля, въведете коректно число");
		if (alphabetName && numericPriceDay && numericPriceKM) {
			String classificationType = typeClassification.getText().toString();
			Double pricePerDay = Double.parseDouble(classificationPricePerDay.getText().toString());
			Double pricePerKM = Double.parseDouble(classificationPricePerKM.getText().toString());
			Session session = rentacar.HibernateUtil.getSessionFactory().openSession();
			session.beginTransaction();
			Classification classificationCheck = (Classification) session
					.createQuery("from Classification c where c.classificationType='" + classificationType + "'").uniqueResult();
			if (classificationCheck == null) {
			Classification cl = new Classification(classificationType, pricePerDay, pricePerKM);
			session.save(cl);
			session.getTransaction().commit();
			labelClassSucc.setText("Успешно добавен клас");
			typeClassification.clear();
			classificationPricePerDay.clear();
			classificationPricePerKM.clear();
			}
			else
				labelClassSucc.setText("Вече съществува такъв клас!");
			session.getTransaction().commit();
		} 
		
	}

	// Add Category
	@FXML
	private TextField typeCategory;
	@FXML
	private Label categoryLabel;

	public void addCategory() {
		boolean categoryCheck = DataValidation.textFieldIsNull(typeCategory, categoryLabel, "Моля, въведете категория!");

		if (!categoryCheck) {
			String categoryTypeString = typeCategory.getText().toString();
			Session session = rentacar.HibernateUtil.getSessionFactory().openSession();
			session.beginTransaction();
			Category categoryCheckDubl = (Category) session
					.createQuery("from Category c where CategoryType='" + categoryTypeString + "'").uniqueResult();
			if(categoryCheckDubl==null)
			{
			Category ctgr = new Category(categoryTypeString);
			session.save(ctgr);
			categoryLabel.setText("Успешно въведена категория!");
			typeCategory.clear();
			
			}			
			else
			categoryLabel.setText("Вече съществува такава категория!");
			session.getTransaction().commit();
			
		} 

	}

}
