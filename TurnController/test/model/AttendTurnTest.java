package model;

import static org.junit.jupiter.api.Assertions.*;

import java.util.ArrayList;

import org.junit.jupiter.api.Test;

import customExceptions.TurnNotFoundException;

class AttendTurnTest {

	AttendTurn at;
	public void setup1() {
		at = new AttendTurn();
	}
	
	public void setup2() {
		at = new AttendTurn();
		ArrayList<User> users = new ArrayList<User>();
		users.add(new User("123", "citizenship_card", "Alejandro", "Saldarriaga", "123", "123"));
		at.setUsers(users);
		ArrayList<Turn> turns = new ArrayList<Turn>();
		turns.add(new Turn('A', "00", users.get(0)));
		at.setTurns(turns);
	}
	
	@Test
	public void passTurnTest() {
		//pasar del turno A00 al A01
		setup1();
		Turn turn = new Turn('A', "00", null);
		at.setTurn(turn);
		at.passTurn();
		String actualTurn = at.getActualTurn();
		assertEquals("A01", actualTurn, "Turn not passed correctly");
		
		//pasar del turno D99 al E00
		setup1();
		turn = new Turn('D', "99", null);
		at.setTurn(turn);
		at.passTurn();
		actualTurn = at.getActualTurn();
		assertEquals("E00", actualTurn, "Turn not passed correctly");
		
		//pasar del turno Z99 al A00
		setup1();
		turn = new Turn('Z', "99", null);
		at.setTurn(turn);
		at.passTurn();
		actualTurn = at.getActualTurn();
		assertEquals("A00", actualTurn, "Turn not passed correctly");
	}
	
	@Test
	public void searchPersonTest() {
		//Si ya existe el usuario
		setup2();
		int position = at.searchPerson("123");
		assertEquals(0, position,"User not found");
		
		//Si no existe el usuario al buscar y el programa está vacío
		setup1();
		position = at.searchPerson("456");
		assertEquals(-1,position,"Found an inexistent user");
		
		//Si no existe el usuario y el programa no está vacío
		setup2();
		position = at.searchPerson("456");
		assertEquals(-1,position,"Found an inexistent user");
	}
	
	@Test
	public void attendUserTest() {
		//Si el turno existe y el usuario tambien, y ambos concuerdan con el turno actual
		setup2();
		try {
			boolean attended = at.attendUser("123", true);
			assertEquals(true, attended, "User has not been attended");
		} catch (TurnNotFoundException e) {
			fail("User or turn has not been found: "+e.getMessage());
		}
		
		//Si el turno existe y el usuario tambien, pero no concuerdan con el turno actual
		setup2();
		at.setTurn(new Turn('A', "01", null));
		try {
			at.attendUser("123", true);
			fail("an undue turn has been taken care of");
		} catch (TurnNotFoundException e) {
			assertTrue(true);
		}
		
		//Si el turno no existe
		setup1();
		try {
			at.attendUser("123", true);
			fail("an undue turn has been taken care of");
		} catch (TurnNotFoundException e) {
			assertTrue(true);
		}
	}
	
	@Test
	public void getActualTurnTest() {
		//Comprobar que retorne el turno actual en un String
		setup1();
		String actual = at.getActualTurn();
		assertEquals("A00", actual, "current turn not received");
	}
	
	@Test
	public void resetActualTurnTest() {
		//Resetear el marcador de turno actual, dejandolo en A00
		setup1();
		at.setTurn(new Turn('Z',"09", null));
		at.resetTurn();
		assertEquals("A00", at.getActualTurn(), "Turn has not been reset");
	}
	
	@Test
	public void showTurnsInLineTest() {
		//Mostrar con un formato especifico los turnos que están en cola
		setup2();
		String show = at.showTurnsInLine();
		assertEquals("A00/ID: 123\n", show, "System dont show turns correctly");
	}

}
