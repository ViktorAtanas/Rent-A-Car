package rentacar.view;

import java.io.IOException;
import java.util.List;

import org.hibernate.Session;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.layout.BorderPane;
import javafx.stage.Modality;
import javafx.stage.Stage;
import rentacar.App;
import rentacar.Operator;

public class LoginControler {

	@FXML
	private TextField textUsername;

	@FXML
	private PasswordField textPassword;
	
	@FXML
	private Label loginStatus;
	
	@FXML
	private void login() throws IOException {
		
		String username = textUsername.getText().toString();
		String password = textPassword.getText().toString();
		
		

		if(username.equals("admin") && password.equals("admin"))
		{
			//Opening OperatorMainView
			FXMLLoader loader = new FXMLLoader();
			loader.setLocation(App.class.getResource("view/AdminMainView.fxml"));
			BorderPane OperatorMainView =loader.load();
			
			Stage operatorMainStage = new Stage();
			operatorMainStage.setTitle("ADMIN *****");
			operatorMainStage.initModality(Modality.WINDOW_MODAL);
			
			Scene scene = new Scene(OperatorMainView);
			operatorMainStage.setScene(scene);
			operatorMainStage.showAndWait();
			
			//loginStatus.setText("Admin loged in");
		}
		else {
			Session session = rentacar.HibernateUtil.getSessionFactory().openSession();
	        List <Operator> kl = session.createQuery("from Operator s where s.userName='"+username+"' AND s.password='"+password+"'").list();
			/*Operator operat;
			 operat = (Operator)session.createQuery("from Operator s where s.userName='"+username+"' AND s.password='"+password+"'").uniqueResult();
			//session.getTransaction().commit();
			*/
			if(!kl.isEmpty())
			{
				//Opening OperatorMainView
				FXMLLoader loader = new FXMLLoader();
				loader.setLocation(App.class.getResource("view/OperatorMainView.fxml"));
				BorderPane OperatorMainView =loader.load();
				
				Stage operatorMainStage = new Stage();
				operatorMainStage.setTitle("Operator *****");
				operatorMainStage.initModality(Modality.WINDOW_MODAL);
				//operatorMainStage.initOwner(main.primaryStage);
				
				Scene scene = new Scene(OperatorMainView);
				operatorMainStage.setScene(scene);
				operatorMainStage.showAndWait();
			}
		else
			loginStatus.setText("Wrong username or password!");
		}
		
		
	}
}
