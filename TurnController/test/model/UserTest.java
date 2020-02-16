package model;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

class UserTest {

	@Test
	public void UserTest(){
		//Prueba del constructor, añade correctamente los parametros al objeto
		String id = "123";
		String typeId = "CD";
		String name = "ALEJANDRO";
		String lastName = "SALDARRIAGA";
		String address = "KKKKKKK";
		String cell = "3153290416";
		User user = new User(id, typeId, name, lastName, address, cell);
		
		assertEquals(id, user.getId(), "id is incorrect");
		assertEquals(typeId, user.getTypeId(), "typeId is incorrect");
		assertEquals(name, user.getName(), "is incorrect");
		assertEquals(lastName, user.getLastName(), "is incorrect");
		assertEquals(address, user.getAddress(), "is incorrect");
		assertEquals(cell, user.getCell(), "is incorrect");
	}

}
