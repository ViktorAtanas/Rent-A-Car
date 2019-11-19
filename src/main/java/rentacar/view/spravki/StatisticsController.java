package rentacar.view.spravki;

import java.net.URL;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.ResourceBundle;
import java.util.Set;

import org.hibernate.Session;
import org.hibernate.query.Query;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.chart.BarChart;
import javafx.scene.chart.CategoryAxis;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.XYChart;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.DatePicker;
import rentacar.Car;
import rentacar.Category;
import rentacar.Classification;

public class StatisticsController implements Initializable{
	@FXML private DatePicker fromDate;
	@FXML private DatePicker toDate;
	@FXML private ChoiceBox<String> filterChoiseBox;
	
	CategoryAxis xAxis    = new CategoryAxis();
	NumberAxis yAxis = new NumberAxis();
	@FXML BarChart  barChart = new BarChart(xAxis, yAxis);

	
		@Override
	public void initialize(URL location, ResourceBundle resources) {
		// TODO Auto-generated method stub
		filterChoiseBox.getItems().add("Клас");
		filterChoiseBox.getItems().add("Категория");
		filterChoiseBox.getSelectionModel().selectFirst();

	}
	

	public void initBarChart() {
		LocalDate startDate = fromDate.getValue();
		LocalDate endDate = toDate.getValue();
		
		Session session = rentacar.HibernateUtil.getSessionFactory().openSession();
	    session.beginTransaction();
	    Query<Car> query1 = session.createQuery("Select s.car from Rent s where s.dateRent BETWEEN '"+startDate+"' AND '"+endDate+"'"); 
	    ObservableList<Car> listCar = FXCollections.observableArrayList(query1.list());

		session.getTransaction().commit();
		
		barChart.getData().clear();
		
		int p=0;
			if(filterChoiseBox.getSelectionModel().getSelectedItem()=="Клас")
			{
				p=0;

				XYChart.Series<String, Number> dataSeries1 = new XYChart.Series<String, Number>();
				dataSeries1.setName("Клас");
				Set<Category> categoriesList = new HashSet<Category>(); 
				for (Car car : listCar) {
					categoriesList.add(car.getCategory());
				}
				ArrayList<Integer> numCategory = new ArrayList<Integer>();
				int k=1, index=0;
				for (Category category : categoriesList) {
					numCategory.add(index, 0);
					for (Car car : listCar) {							
						if(car.getCategory().equals(category))
							{
								numCategory.set(index, k);
								k++;
								System.out.println(car.getCategory().getCategoryType()+" "+ category.getCategoryType()+" K: "+k);
							}				
						}					
				    k=1;
				    index++;
				}
				for (Category category : categoriesList) {
					dataSeries1.getData().add(new XYChart.Data<String, Number>(category.getCategoryType(), numCategory.get(p)));
					p++;
				}

				barChart.getData().add(dataSeries1);
			}
			else if (filterChoiseBox.getSelectionModel().getSelectedItem()=="Категория")
			{
				p=0;
				XYChart.Series<String, Number> dataSeries2 = new XYChart.Series<String, Number>();
				dataSeries2.setName("Категория");
				Set<Classification> classificationSet = new HashSet<Classification>(); 
				for (Car car : listCar) {
					classificationSet.add(car.getClassification());
				}
				ArrayList<Integer> numCategory = new ArrayList<Integer>();
				int k=1, index=0;
				for (Classification classification : classificationSet) {
					numCategory.add(index, 0);
					for (Car car : listCar) {							
						if(car.getClassification().equals(classification))
							{
								numCategory.set(index, k);
								k++;
							}				
						}					
				    k=1;
				    index++;
				}
				for (Classification classification : classificationSet) {
					dataSeries2.getData().add(new XYChart.Data<String, Number>(classification.getClassificationType(), numCategory.get(p)));
					p++;
				}

				barChart.setData(FXCollections.observableArrayList(dataSeries2));
				
			}
			
			
			
			
			
			
	}
	
	
}
