package model;

import java.util.*;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

import customExceptions.UserNotRegisterException;

class TurnManagerTest {

	TurnManager tm;
	ArrayList<Turn> turns;
	
	public void setup1() {
		tm = new TurnManager();
	}
	
	public void setup2() {
		tm = new TurnManager();
		turns=new ArrayList<Turn>();
		turns.add(new Turn('A', "00", new User("123", "citizenship_card", "Alejandro", "Saldarriaga", "123", "123")));
		tm.addUser("456", "identity_card", "Valentina", "Arias", "123", "123");
		tm.setTurns(turns);
	}
	
	public void setup3() {
		tm = new TurnManager();
		turns=new ArrayList<Turn>();
		turns.add(new Turn('D', "99", new User("123", "identity_card", "Valentina", "Arias", "123", "123")));
		tm.addUser("456", "citizenship_card", "Alejandro", "Saldarriaga", "123", "123");
		tm.setTurns(turns);
	}
	
	public void setup4() {
		tm = new TurnManager();
		turns=new ArrayList<Turn>();
		turns.add(new Turn('Z', "99", new User("123", "citizenship_card", "Alejandro", "Saldarriaga", "123", "123")));
		tm.addUser("456", "identity_card", "Valentina", "Arias", "123", "123");
		tm.setTurns(turns);
	}
	
	public void setup5() {
		tm = new TurnManager();
		tm.addUser("456", "identity_card", "Valentina", "Arias", "123", "123");
	}
	
	@Test
	public void addUserTest() {
		//Sin usuarios en el programa
		setup1();
		boolean add = tm.addUser("123", "null", "null", "null", "null", "null");
		assertEquals(true, add, "User has not been added");
		//Añadiendo el mismo usuario
		add = tm.addUser("123", "null", "null", "null", "null", "null");
		assertEquals(false, add, "User has been added again");
		//Con usuarios en el programa
		setup5();
		add = tm.addUser("123", "null", "null", "null", "null", "null");
		assertEquals(true, add, "User has not been added");
	}
	
	@Test
	public void assignTurnTest() {
		//Cuando se genera el primer turno a un usuario existente
		setup1();
		setup5();
		try {
			Turn at = tm.assignTurn("456");
			assertEquals(null, at, "Turn has not been added");
		} catch (UserNotRegisterException e) {
			fail(e.getMessage());
		}
		
		//Cuando se intenta crear un turno a un usuario con un turno existente
		try {
			Turn at = tm.assignTurn("456");
			assertEquals("456", at.getUser().getId(), "Turn has been added again");
		} catch (UserNotRegisterException e) {
			fail(e.getMessage());
		}
		
		//Cuando se crea un turno a un usuario inexistente
		try {
			tm.assignTurn("123");
			fail("Exception has not been throw");
		} catch (UserNotRegisterException e) {
			assertTrue(true);
		}
		
		//Generar turno consecutivo al anterior (De A00 a A01)
		setup2();
		try {
			tm.assignTurn("456");
			assertEquals('A', tm.getTurns().get(1).getLetter(),"Letter is different");
			assertEquals("01", tm.getTurns().get(1).getNumber(),"Number is different");
		} catch (UserNotRegisterException e) {
			fail(e.getMessage());
		}
		
		//Generar primer turno (A00)
		setup1();
		setup5();
		try {
			tm.assignTurn("456");
			assertEquals('A', tm.getTurns().get(0).getLetter(),"Letter is different");
			assertEquals("00", tm.getTurns().get(0).getNumber(),"Number is different");
		} catch (UserNotRegisterException e) {
			fail(e.getMessage());
		}
		
		//Pasar del turno D99 al E00
		setup3();
		try {
			tm.assignTurn("456");
			assertEquals('E', tm.getTurns().get(1).getLetter(),"Letter is different");
			assertEquals("00", tm.getTurns().get(1).getNumber(),"Number is different");
		} catch (UserNotRegisterException e) {
			fail(e.getMessage());
		}
		
		//Pasar del turno Z99 al A00
		setup4();
		try {
			tm.assignTurn("456");
			assertEquals('A', tm.getTurns().get(1).getLetter(),"Letter is different");
			assertEquals("00", tm.getTurns().get(1).getNumber(),"Number is different");
		} catch (UserNotRegisterException e) {
			fail(e.getMessage());
		}
	}
	
	@Test
	public void searchPersonTest() {
		//Si ya existe el usuario
		setup1();
		setup5();
		int position = tm.searchPerson("456");
		assertEquals(0,position,"User not found");
		
		//Si no existe el usuario al buscar y el programa está vacío
		setup1();
		position = tm.searchPerson("456");
		assertEquals(-1,position,"Found an inexistent user");
		
		//Si no existe el usuario y el programa no está vacío
		setup1();
		setup5();
		position = tm.searchPerson("123");
		assertEquals(-1,position,"Found an inexistent user");
	}
	
	@Test
	public void searchTurnTest() {
		//Si ya existe el turno
		setup1();
		setup2();
		int position = tm.searchTurn("123");
		assertEquals(0,position,"Turn not found");
		
		//Si no existe el turno al buscar y el programa está vacío
		setup1();
		position = tm.searchTurn("123");
		assertEquals(-1,position,"Found an inexistent turn");
		
		//Si no existe el turno y el programa no está vacío
		setup1();
		setup2();
		position = tm.searchTurn("456");
		assertEquals(-1,position,"Found an inexistent turn");
	}
	
	@Test
	public void changeDayTest() {
		//Que reinicie el arrayList de turnos
		setup2();
		tm.changeDay();
		try {
			tm.getTurns().get(0);
			fail("the day was not reset");
		} catch(IndexOutOfBoundsException e) {
			assertTrue(true);
		}
	}
	
	@Test
	public void showUsersAddedTest() {
		//Que muestre con un formato especifico los usuarios que tiene añadidos 
		setup5();
		String added = tm.showUsersAdded();
		assertEquals("Name: Valentina/ ID: 456\n", added, "System dont show added users");
	}
}
