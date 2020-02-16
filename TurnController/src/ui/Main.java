package ui;

import java.io.*;
import java.util.*;

import customExceptions.MandatoryParameterNotTypeException;
import customExceptions.TurnNotFoundException;
import customExceptions.UserNotRegisterException;
import model.*;

public class Main {

	private Scanner scan;
	private TurnManager tm;
	private AttendTurn at;
	
	public static void main(String [] args) {
		Main main = new Main();
		main.chooseTypeEmployee();
	}
	
	public Main() {
		scan = new Scanner(System.in);
		tm = new TurnManager();
		at = new AttendTurn();
	}
	
	/**
	 * Permite escoger la modalidad del programa.
	 * <b>pre:</b> El Scanner scan debe de estar inicializado.<br>
	 */
	public void chooseTypeEmployee() {
		boolean continueOption;
		int option = 0;
		boolean finish = false;
		System.out.println("-----------------------");
		System.out.println("     W E L C O M E     ");
		System.out.println("-----------------------");
		
		while(!finish) {
			System.out.println("1. Turn manager.\n2. Serve users.\n3. Finish program");
			continueOption = true;
			do {
				try{
					option = Integer.parseInt(scan.nextLine());
					continueOption = false;
				} catch(NumberFormatException e) {
					System.out.println("ERROR: the option must be int, try again");
					continueOption = true;
				}
			} while(continueOption);
			
			switch(option) {
			case 1:try {
					tm.loadUsers();
					tm.loadTurns();
					menuTurnManager();
				}catch(FileNotFoundException e1) {
					System.out.println(e1.getMessage());
				}catch (ClassNotFoundException e2) {
					System.out.println(e2.getMessage());
				}catch (IOException e) {
					System.out.println(e.getMessage());
				}
				break;
			case 2: try {
					at.loadTurns();
					at.loadUsers();
					menuServeUsers();
				} catch (ClassNotFoundException e) {
					System.out.println(e.getMessage());
				} catch (IOException e) {
					System.out.println(e.getMessage());
				}
				break;
			case 3: finish = true;
				break;
			default: System.out.println("not valid option. Please, type a valid option");
				break;
			}
			
			System.out.println("-----------------------");
		}
	}
	
	/**
	 * Este metodo es el menu de opciones de la modalidad TurnManager.
	 * <b>pre:</b> El TurnManager tm debe de estar inicializado.<br>
	 */
	public void menuTurnManager() {
		boolean back = false;
		boolean continueOption;
		int option = 0;
		System.out.println("-----------------------");
		System.out.println("      TURN MANAGER     ");
		System.out.println("-----------------------");
		while(!back) {
			System.out.println("1. Add user.\n2. Assign turn.\n3. Change day.\n4. Show users registered.\n5. Back");
			continueOption = true;
			do {
				try{
					option = Integer.parseInt(scan.nextLine());
					continueOption = false;
				} catch(NumberFormatException e) {
					System.out.println("ERROR: the option must be int, try again");
					continueOption = true;
				}
			} while(continueOption);
			
			switch(option) {
			case 1:	addUser();
				break;
			case 2: assignTurn();
				break;
			case 3: tm.changeDay();
				break;
			case 4: System.out.println(tm.showUsersAdded());
				break;
			case 5: back = true;
				try {
					tm.saveUsers();
					tm.saveTurns();
				} catch (IOException e) {
					System.out.println(e.getMessage());
				}
					System.out.println("-----------------------");
					System.out.println("Choose type employee");
				break;
			default: System.out.println("not valid option. Please, type a valid option");
				break;
			}
			
			System.out.println("-----------------------");
		}
	}
	
	/**
	 * Este metodo es una de las opciones del menu de TurnManager, permite añadir un usuario al programa TurnManager(tm).
	 * <b>pre:</b> El TurnManager(tm) debe de estar inicializado.<br>
	 * <b>pos:</b> Se ha añadido un nuevo usuario al programa TurnManager(tm).<br>
	 */
	public void addUser() {
		User user = new User(null, null, null, null, null, null);
		System.out.println("Type name");
		String name = scan.nextLine();
		System.out.println("Type lastname");
		String lastName = scan.nextLine();
		System.out.println("Type address");
		String address = scan.nextLine();
		System.out.println("Type cell");
		String cell = scan.nextLine();
		System.out.println("Choose an option for type id: ");
		System.out.println("");
		String typeId = null;
		boolean choose = false;
		boolean continueOption;
		int option = 0;
		int cont = 1;
		while(!choose) {
			for(TypeId type : TypeId.values()) {
				System.out.println(cont+". "+type.getType());
				cont++;
			}
			continueOption = true;
			do {
				try{
					option = Integer.parseInt(scan.nextLine());
					continueOption = false;
				} catch(NumberFormatException e) {
					System.out.println("ERROR: the option must be int, try again");
					continueOption = true;
				}
			} while(continueOption);
			
			switch(option) {
			case 1:	typeId = user.getType().CD.getType();
					choose = true;
				break;
			case 2: typeId = user.getType().CR.getType();
					choose = true;
				break;
			case 3: typeId = user.getType().FC.getType();
					choose = true;
				break;	
			case 4: typeId = user.getType().ID.getType();
					choose = true;
				break;
			case 5:	typeId = user.getType().PP.getType();
					choose = true;
				break;
			default: System.out.println("not valid option. Please, type a valid option");
				break;
			}
		}
		
		System.out.println("Type number id");
		String numberId = scan.nextLine();

		try {
			if(!name.equals("") && !lastName.equals("") && !numberId.equals("")) {
				tm.addUser(numberId, typeId, name, lastName, address, cell);
			}
			else
				throw new MandatoryParameterNotTypeException(name, lastName, numberId, typeId);
			
		}catch (MandatoryParameterNotTypeException e) {
			System.out.println(e.getMessage()+"\n"+e.getProblem());
		}
	}
	
	/**
	 * Este metodo es una de las opciones del menu de TurnManager, permite asignar un turno a un usuario de TurnManager(tm).
	 * <b>pre:</b> El TurnManager(tm) debe de estar inicializado.<br>
	 * <b>pos:</b> Se ha asignado un turno a un usuario existente en el programa TurnManager(tm).<br>
	 */
	public void assignTurn() {
		System.out.println("Type the number id of the person you want to register");
		String id = scan.nextLine();
		
		Turn t = null;
		try {
			t = tm.assignTurn(id);
		} catch (UserNotRegisterException e) {
			System.out.println(e.getMessage());
		}
		
		if(t!=null) {
			System.out.println("Turn already exist: "+t.getLetter()+t.getNumber());
		}
		else
			System.out.println("Turn added correctly");
	}
	
	/**
	 * Este metodo es el menu de opciones de la modalidad ServeUsers.
	 * <b>pre:</b> El AttendTurn at debe de estar inicializado.<br>
	 */
	public void menuServeUsers() {
		boolean back = false;
		boolean continueOption;
		int option = 0;
		System.out.println("---------------------");
		System.out.println("     SERVE USERS     ");
		System.out.println("---------------------");
		while(!back) {
			System.out.println("Actual turn: "+at.getActualTurn()+"\n");
			System.out.println("1. Attend user.\n2. Reset marker.\n3. Show turns in wait.\n4. Back.");
			continueOption = true;
			do {
				try{
					option = Integer.parseInt(scan.nextLine());
					continueOption = false;
				} catch(NumberFormatException e) {
					System.out.println("ERROR: the option must be int, try again");
					continueOption = true;
				}
			} while(continueOption);
			
			switch(option) {
			case 1:	attendUser();
				break;
			case 2: resetMarket();
				break;
			case 3: System.out.println(at.showTurnsInLine());
				break;
			case 4: back = true;
					System.out.println("-----------------------");
					System.out.println("Choose type employee");
				break;
			default: System.out.println("not valid option. Please, type a valid option");
				break;
			}
			
			System.out.println("-----------------------");
		}
	}
	
	/**
	 * Este metodo es una de las opciones del menu de ServeUsers, permite atender a un usuario en AttendTurn(at).
	 * <b>pre:</b> El TurnManager(tm) debe de estar inicializado.<br>
	 * <b>pos:</b> Se ha atendido un turno del programa AttendTurn(at) y su turno actual ha cambiado de posición.<br>
	 */
	public void attendUser() {
		System.out.println("Type the number ID");
		String id = scan.nextLine();
		boolean choose = false;
		boolean attend;
		boolean continueOption = true;
		int option = 0;
		while(!choose) {
			System.out.println("The person was treated or abandoned the turn\n1. Treated.\n2. Abandoned.");
			do {
				try{
					option = Integer.parseInt(scan.nextLine());
					continueOption = false;
				} catch(NumberFormatException e) {
					System.out.println("ERROR: the option must be int, try again");
					continueOption = true;
				}
			} while(continueOption);
			
			switch(option) {
			case 1: attend = true;
					try {
							at.attendUser(id, attend);
					} catch (TurnNotFoundException e) {
							System.out.println(e.getMessage());
					}
					choose = true;
				break;
			case 2:	attend = false; 
					try {
							at.attendUser(id, attend);
					} catch (TurnNotFoundException e) {
							System.out.println(e.getMessage());
					}
					choose = true;
				break;
			default: System.out.println("not valid option. Please, type a valid option");
				break;
			}
		}
		
		
	}
	
	/**
	 * Este metodo es una de las opciones del menu de ServeUsers, permite reiniciar el turno actual de AttendTurn(at).
	 * <b>pre:</b> El TurnManager(tm) debe de estar inicializado.<br>
	 * <b>pos:</b> Se ha cambiado el turno actual del programa AttendTurn(at) al predeterminado A00.<br>
	 */
	public void resetMarket() {
		boolean choose = false;
		boolean continueOption = true;
		int option = 0;
		while(!choose) {
			System.out.println("Are you sure?\n1. Yes\n2. No");
			do {
				try{
					option = Integer.parseInt(scan.nextLine());
					continueOption = false;
				} catch(NumberFormatException e) {
					System.out.println("ERROR: the option must be int, try again");
					continueOption = true;
				}
			} while(continueOption);
			
			switch(option) {
			case 1: at.resetTurn();
				choose = true;
				break;
			case 2: choose = true;
				break;
			default: System.out.println("not valid option. Please, type a valid option");
				break;
			}
		}
	}
}
