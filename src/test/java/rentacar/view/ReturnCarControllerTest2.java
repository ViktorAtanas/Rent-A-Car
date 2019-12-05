package rentacar.view;

import static org.junit.Assert.*;

import java.time.LocalDate;
import java.time.Month;

import org.junit.Test;

import rentacar.Car;
import rentacar.Category;
import rentacar.Classification;
import rentacar.Client;
import rentacar.Rent;

public class ReturnCarControllerTest2 {

	@Test
	public void testCalcRaiting() {
		Client c = new Client("", "", "", 50, "");
		assertEquals(30,ReturnCarController.calcRaiting(c, 20),0);  

	}

	@Test
	public void testCalcPrice() {

		LocalDate dateRent = LocalDate.of(2019, Month.DECEMBER,1);
		LocalDate dateReturn = LocalDate.of(2019, Month.DECEMBER,2);
		Car rentedCar= new Car("","",false,true,0," ",new Classification("",1,1),new Category(" "));
		Rent rent = new Rent(dateRent, dateReturn,new Client());
		   assertEquals(5.4,ReturnCarController.calcPrice(rentedCar, rent, 1,false),0);  
	}

}
