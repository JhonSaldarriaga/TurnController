package model;

import java.io.*;

import java.util.*;

import customExceptions.TurnNotFoundException;

public class AttendTurn {

	//Por favor modificar la ruta correspondiente a donde tengas la carpeta TurnController
	private final String TURN_FILE = "N:/Semestre 3/APO2/Espacio de trabajo/eclipse_Lab/TurnController/data/turn.save";
	private final String USER_FILE = "N:/Semestre 3/APO2/Espacio de trabajo/eclipse_Lab/TurnController/data/user.save";
	private Turn turn;
	private ArrayList<Turn> turns;
	private ArrayList<User> users;
	private Alphabet[] alphabet;
	
	public AttendTurn() {
		turns = new ArrayList<Turn>();
		users = new ArrayList<User>();
		turn = new Turn('A',"00", null);
		alphabet = Alphabet.values();
	}
	
	/**
	 * Este metodo cambia el objeto turno, a un turno consecutivo.
	 * <b>pre:</b> La lista turns no debe ser null.<br>
	 * <b>pre:</b> El objeto turn no puede ser null, y debe de estar inicializado en A00.<br>
	 * <b>pos:</b> El atributo Turn ha cambiado a un turno consecutivo.<br>
	 */
	public void passTurn() {
		char letter = turn.getLetter();
		String position = turn.getNumber();
		
		if(Integer.parseInt(position)==99) {
			position = "00";
			if(letter!='Z') {
				for(int i=0; i<alphabet.length; i++) {
					if(alphabet[i].getLetter()==letter) {
						letter=alphabet[i+1].getLetter();
						break;
					}
				}
			}
			else
				letter = 'A';
			
			turn = new Turn(letter, position, null);
			
		}else {
			if(Integer.parseInt(position)<99 && Integer.parseInt(position)>=9) {
				position = ""+(Integer.parseInt(position)+1);
				turn = new Turn(letter, position, null);
			}
			else {
				if(Integer.parseInt(position)<9) {
					position = "0"+(Integer.parseInt(position)+1);
					turn = new Turn(letter, position, null);
				}
			}
		}
	}
	
	/**
	 * Este metodo permite cargar el ArrayList(users) desde un archivo en la computadora.
	 * <b>pre:</b> La constante USER_FILE debe de ser una ruta que exista en la computadora. No hace falta que el archivo de donde se cargará el objeto exista.<br>
	 * <b>pos:</b> El arrayList(users) se ha actualizado con la información cargada.<br>
	 * @throws IOException Si se intentará cargar y el archivo no existiera.
	 * @throws ClassNotFoundException Si la clase del archivo cargado no coincidiera con ArrayList(User).
	 */
	public void loadUsers() throws IOException, ClassNotFoundException {
		File file = new File(USER_FILE);
		if(file.exists()) {
			FileInputStream fi = new FileInputStream(USER_FILE);
			ObjectInputStream oi = new ObjectInputStream(fi);
			users = (ArrayList<User>)oi.readObject();
		}
	}
	
	/**
	 * Este metodo permite cargar el ArrayList(turns) desde un archivo en la computadora.
	 * <b>pre:</b> La constante TURN_FILE debe de ser una ruta que exista en la computadora. No hace falta que el archivo donde de donde se cargará el objeto exista.<br>
	 * <b>pos:</b> El arratList(turns) se ha actualizado con la información cargada.<br>
	 * @throws IOException Si se intentará cargar y el archivo no existiera.
	 * @throws ClassNotFoundException Si la clase del archivo cargado no coincidiera con ArrayList(User).
	 */
	public void loadTurns() throws IOException, ClassNotFoundException {
		File file = new File(TURN_FILE);
		if(file.exists()) {
			FileInputStream fi = new FileInputStream(TURN_FILE);
			ObjectInputStream oi = new ObjectInputStream(fi);
			turns = (ArrayList<Turn>)oi.readObject();	
		}
	}
	
	/**
	 * Este metodo permite buscar a un usuario en su arrayList y retornar su posicion.
	 * <b>pre:</b> El arrayList (users) debe de estar inicializado.<br>
	 * @param id Número de cédula del usuario a buscar. id != null.
	 * @return Retorna la posicion en donde se encuentra el usuario en el ArrayList, si no lo encontró, retornará -1.
	 */
	public int searchPerson(String id) {
		int position = -1;
		for(int i = 0; i<users.size(); i++) {
			if(users.get(i).getId().equals(id)) {
				position = i;
				break;
			}
		}
		
		return position;
	}
	
	/**
	 * Este metodo pemite atender a una persona, utilizando el turno actual del programa.
	 * <b>pre:</b> El arrayList (turns) debe de estar inicializado.<br>
	 * <b>pre:</b> El arrayList (users) debe de estar inicializado.<br>
	 * <b>pos:</b> El usuario que le pertenece al turno ha cambiado su estado atendido, y el turno actual del programa ha cambiado a uno consecutivo.<br>
	 * @param id Número de cédula del usuario a buscar. id != null.
	 * @param attend Booleano del usuario, siendo true si se le atendió o false si el usuario abandonó el establecimiento. attend != null. 
	 * @return Booleano confirmando si el procedimiento fue correcto.
	 * @throws TurnNotFoundException Se lanza si la persona que se quiere atender existe y tiene un turno existente, pero el turno no concuerda con el turno actual del programa.
	 * @throws TurnNotFoundException Se lanza si la persona a atender no posee ningún turno en el programa.
	 */
	public boolean attendUser(String id, boolean attend) throws TurnNotFoundException {
		String position = turn.getNumber();
		char letter = turn.getLetter();
		boolean found = false;
		
		for(int i=0; i<turns.size() && found==false; i++) {
			if(turns.get(i).getUser().getId().equals(id)) {
				if(turns.get(i).getNumber().equals(position) && turns.get(i).getLetter()==letter) {
					if(attend) {
						turns.get(i).getUser().setAttended(true);
						users.get(searchPerson(id)).setAttended(true);	
					}
					else {
						Turn t = new Turn(turns.get(i).getLetter(), turns.get(i).getNumber(), null);
						turns.set(i, t);
					}
					found = true;
				}
				else
					throw new TurnNotFoundException(turns.get(i).getLetter(),turns.get(i).getNumber());
			}
		}
		
		if (found) {
			passTurn();
			return true;
		}
		else
			throw new TurnNotFoundException(id);
	}
	
	/**
	 * Este metodo retorna un String con el turno actual del programa.
	 * <b>pre:</b> El atributo turn debe de estar inicializado y tener atributos diferentes de null o "".<br>
	 * @return String con el turno actual del programa.
	 */
	public String getActualTurn() {
		return turn.getLetter()+turn.getNumber();
	}
	
	/**
	 * Este metodo reinicia el turno actual del programa poniendolo en la posición inicial de A00.
	 * <b>pos:</b> El atributo Turn(el turno actual del programa) ha cambiado a la posicion predeterminada A00.<br>
	 */
	public void resetTurn() {
		turn=new Turn('A', "00", null);
	}
	
	/**
	 * Este metodo retorna un String donde se encuentra una lista con todos los turnos registrados en el programa. Mostrando el turno y el id del usuario al que le pertenece.
	 * <b>pre:</b> El ArrayList (turns) debe de estar inicializado.<br>
	 * @return Un String con todos los turnos del programa (si existen), o un String diciendo "Turns not found in the program" si no se encuentra ningún turno.
	 */
	public String showTurnsInLine() {
		String show = "";
		boolean found = false;
		for(int i = 0; i<turns.size(); i++) {
			if(turns.get(i).getUser()!=null) {
				if(!turns.get(i).getUser().getAttended()) {
					show+=turns.get(i).getLetter()+turns.get(i).getNumber()+"/ID: "+turns.get(i).getUser().getId()+"\n";
					found = true;
				}
			}
		}
	 if(found) 
		return show;
	 else
		 return "Turns not found in the program";
	}
	
	////////////////////////////////////////////
	
	public void setTurn(Turn turn) {
		this.turn = turn;
	}

	public void setTurns(ArrayList<Turn> turns) {
		this.turns = turns;
	}

	public void setUsers(ArrayList<User> users) {
		this.users = users;
	}

	public Turn getTurn() {
		return turn;
	}

	public ArrayList<Turn> getTurns() {
		return turns;
	}

	public ArrayList<User> getUsers() {
		return users;
	}
	
}
