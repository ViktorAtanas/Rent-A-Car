package rentacar.view;

import static org.junit.Assert.*;

import org.junit.Test;

import rentacar.Client;

public class RegisterClientControllerTest {

	@Test
	public void testCheckCl() {		
		Client c=new Client();
		assertFalse("Client is null",RegisterClientController.checkCl(c));
	}

}
