package rentacar;



import org.hibernate.Session;

import javafx.application.Application;

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;


public class App extends Application 
{
	
	
	public void start(Stage primaryStage) {
		try {
			//BorderPane root = new BorderPane();
			Parent root = FXMLLoader.load(getClass().getResource("view/LoginView.fxml"));
			Scene scene = new Scene(root);
			scene.getStylesheets().add(getClass().getResource("application.css").toExternalForm());
			primaryStage.setScene(scene);
			primaryStage.show();
		} catch(Exception e) {
			e.printStackTrace();
		}
	}
	
	
    public static void main( String[] args )
    {    	
    	Session session = HibernateUtil.getSessionFactory().openSession();
    	session.beginTransaction();
    	session.getTransaction().commit();
    	launch(args);
    	
    	
    	/*Session session = HibernateUtil.getSessionFactory().openSession();
        session.beginTransaction();
        
         Client cl = new Client("Atanas","980206","P.Volov 5",50,"252516");
         
         Category cat = new Category(1,"kombi");
         Classification clasf = new Classification(2,"family",50,20);
         Company cmp = new Company("naso OOD", "p Volov5");
         Car car1 = new Car("H2526 AX","Fiat",true,false,165,"bla",clasf, cat);
         Operator oper =new Operator("nasko", "123", "Atanas", false, cmp);
         
         Date today = new Date();
         Date today2 = new Date(2019-1900,10-1,31);
         Opis op = new Opis(35,today,"no problems",car1);
       
        // Rent re = new Rent(today, today2, 2.6, 15.6, oper, car1, cl);

    	//session.save(re);
         
         List <Rent> kl = session.createQuery("from Rent").list();
         System.out.println("Done query");
         
         Rent k=kl.get(1);
         System.out.println(k.getCar().checkStatus()); 
        
    	session.getTransaction().commit();
    	
		System.out.println("Get complete: "+ kl);
        System.out.println( "Hello World!" + today2);
        
        */
  
        
    	System.exit(0);
    	
    }



    
}
