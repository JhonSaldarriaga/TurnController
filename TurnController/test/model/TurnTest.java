package model;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

class TurnTest {

	@Test
	public void TurnTest() {
		//Prueba del constructor, añade correctamente los parametros al objeto
		char letter = 'A';  
		String number = "00";
		User user = new User("123", null, null, null, null, null);
		Turn turn = new Turn(letter, number, user);
		assertEquals(letter, turn.getLetter(), "Letter is incorrect");
		assertEquals(number, turn.getNumber(), "Number is incorrect");
		assertEquals(user.getId(), turn.getUser().getId(), "The user is incorrect");
	}
}
