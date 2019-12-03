package rentacar.view;

import java.time.LocalDate;
import java.time.Month;

import junit.framework.TestCase;
import rentacar.Car;
import rentacar.Category;
import rentacar.Classification;
import rentacar.Client;
import rentacar.Rent;

public class ReturnCarControllerTest extends TestCase {
 
	public void testCalcPrice() {
		LocalDate dateRent = LocalDate.of(2019, Month.NOVEMBER,1);
		LocalDate dateReturn = LocalDate.of(2019, Month.DECEMBER,3);
		Car rentedCar= new Car("","",false,true,0," ",new Classification("",48,7),new Category(" "));
		Rent rent = new Rent(dateRent, dateReturn,new Client());
		   assertEquals(153.0,ReturnCarController.calcPrice(rentedCar, rent, 1,true));  
			}

}
