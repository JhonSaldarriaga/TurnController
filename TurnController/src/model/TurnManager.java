package model;

import java.io.*;

import java.util.ArrayList;

import customExceptions.UserNotRegisterException;

public class TurnManager {

	//Por favor modificar la ruta correspondiente a donde tengas la carpeta TurnController
	private final String TURN_FILE = "N:/Semestre 3/APO2/Espacio de trabajo/eclipse_Lab/TurnController/data/turn.save";
	private final String USER_FILE = "N:/Semestre 3/APO2/Espacio de trabajo/eclipse_Lab/TurnController/data/user.save";
	private ArrayList<User> users;
	private ArrayList<Turn> turns;
	private Alphabet[] alphabet;
	
	public TurnManager() {
		users = new ArrayList<User>();
		turns = new ArrayList<Turn>();
		alphabet = Alphabet.values();
	}
	
	/**
	 * Este metodo permite añadir un nuevo usuario al programa.
	 * <b>pre:</b> El ArrayList(users) debe de estar inicializado.<br>
	 * <b>pos:</b> El ArrayList(users) se ha actualizado con un nuevo usuario.<br>
	 * @param id Id del usuario a registrar. id != null.
	 * @param typeId Tipo de documento de identificación del usuario a registrar.
	 * @param name Nombre del usuario a registrar.
	 * @param lastName Apellido del usuario a registrar.
	 * @param address Dirección del usuario a registrar
	 * @param cell Numero de telefono del usuario a registrar.
	 * @return Un booleano confirmando si fue efectuado el registro.
	 */
	public boolean addUser(String id, String typeId, String name, String lastName, String address, String cell) {
		
		int add = searchPerson(id);
		
		if(add == -1) {
			users.add(new User(id, typeId, name, lastName, address, cell));
			return true;
		}
		else
			return false;
	}
	
	/**
	 *Este metodo permite asignarle un turno a un Usuario, el turno es necesariamente consecutivo al ultimo ingresado en el ArrayList(turns).
	 *<b>pre:</b> El ArrayList(turns) debe de estar inicializado.<br>
	 *<b>pre:</b> El ArrayList(users) debe de estar inicializado.<br>
	 *<b>pre:</b> El usuario al que se le va asignar el turno debe de existir en el programa.<br>
	 *<b>pos:</b> Se ha añadido un nuevo Turno al ArrayList(turns), consecutivo al ultimo ingresado.<br>
	 * @param id Id del usuario al que se le asignará el turno. id != null.
	 * @return un objeto Turno el cual será diferente de null si se le quiere asignar a un turno a un usuario que ya tiene un turno anteriormente, en este caso se le retornará el turno que posee.
	 * @throws UserNotRegisterException Esta excepción se lanza cuando se le intenta asignar un turno a un usuario que no existe en el programa.
	 */
	public Turn assignTurn(String id) throws UserNotRegisterException{	
		if(searchPerson(id)==-1) {
			throw new UserNotRegisterException(id);
		}
		else {
			if(searchTurn(id)!=-1)
				return turns.get(searchTurn(id));
			else {
				if(turns.size()>=1) {
					char letter = turns.get(turns.size()-1).getLetter();
					String position = turns.get(turns.size()-1).getNumber();
					
					if(Integer.parseInt(position)==99) {
						position = "00";
						if(letter!='Z') {
							for(int i=0; i<alphabet.length; i++) {
								if(alphabet[i].getLetter()==letter) {
									letter=alphabet[i+1].getLetter();
									break;
								}
							}
						}else
							letter='A';
							
						turns.add(new Turn(letter, position, users.get(searchPerson(id))));
						
						return null;
						
					}else {
						if(Integer.parseInt(position)<99 && Integer.parseInt(position)>=9) {
							position = ""+(Integer.parseInt(position)+1);
							turns.add(new Turn(letter, position, users.get(searchPerson(id))));
							
							return null;
						}
						else {
							if(Integer.parseInt(position)<9) {
								position = "0"+(Integer.parseInt(position)+1);
								turns.add(new Turn(letter, position, users.get(searchPerson(id))));
								
								return null;
							}
							else {
								return null;
							}
						}
					}
				}
				else {
					turns.add(new Turn('A', "00", users.get(searchPerson(id))));
					return null;
				}
			}	
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
	 * Este metodo permite buscar a un turno en su arrayList y retornar su posicion.
	 * <b>pre:</b> El arrayList (turns) debe de estar inicializado.<br>
	 * @param id Número de cédula del usuario que posee el turno. id != null.
	 * @return Retorna la posicion en donde se encuentra el turno en el ArrayList, si no lo encontró, retornará -1.
	 */
	public int searchTurn(String id) {
		int position = -1;
		for(int i=0; i<turns.size(); i++) {
			if(turns.get(i).getUser().getId().equals(id)) {
				position = i;
				break;
			}
		}
		
		return position;
	}
	
	/**
	 * Este metodo permite guardar el ArrayList(turns) a un archivo en la computadora.
	 * <b>pre:</b> La constante TURN_FILE debe de ser una ruta que exista en la computadora. No hace falta que el archivo donde se guardará el objeto exista.<br>
	 * <b>pos:</b> Se ha guardado el objeto ArrayList(turns).<br>
	 * @throws IOException Si se intentará guardar y el archivo no existiera.
	 */
	public void saveTurns() throws IOException {
		File file = new File(TURN_FILE);
		if(!file.exists()) {
			file.createNewFile();
		}
		FileOutputStream fo = new FileOutputStream(TURN_FILE);
		ObjectOutputStream oo = new ObjectOutputStream(fo);
		oo.writeObject(turns);
		oo.close();
	}
	
	/**
	 * Este metodo permite guardar el ArrayList(users) a un archivo en la computadora.
	 * <b>pre:</b> La constante USER_FILE debe de ser una ruta que exista en la computadora. No hace falta que el archivo donde se guardará el objeto exista.<br>
	 * <b>pos:</b> Se ha guardado el objeto ArrayList(users).<br>
	 * @throws IOException Si se intentará guardar y el archivo no existiera.
	 */
	public void saveUsers() throws IOException {
		File file = new File(USER_FILE);
		if(!file.exists()) {
			file.createNewFile();
		}
		FileOutputStream fo = new FileOutputStream(file);
		ObjectOutputStream oo = new ObjectOutputStream(fo);
		oo.writeObject(users);
		oo.close();
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
			FileInputStream fi = new FileInputStream(file);
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
	 * Este metodo reinicia(vacía) el ArrayList(users).
	 * <b>pos:</b> El ArrayList(turns) se reinicia, eliminando todos los turnos existentes.<br>
	 */
	public void changeDay() {
		ArrayList<Turn> turnsEmpty = new ArrayList<Turn>();
		turns = turnsEmpty;
	}

	/**
	 * Este metodo retorna una lista con todos los usuarios que existen en el programa, mostrando su nombre e id.
	 * <b>pre:</b> el ArrayList(users) debe de estar inicializado.<br>
	 * @return String con la información de todos los usuarios que existen en el programa.
	 */
	public String showUsersAdded() {
		String show = "";
		boolean found = false;
		for(int i = 0; i<users.size(); i++) {
			show+="Name: "+users.get(i).getName()+"/ ID: "+users.get(i).getId()+"\n";
			found = true;
		}
		if(found)
			return show;
		else
			return "Users not found";
	}
	
	///////////////////////////////////////////////////////////
	public void setUsers(ArrayList<User> users) {
		this.users = users;
	}

	public void setTurns(ArrayList<Turn> turns) {
		this.turns = turns;
	}

	public ArrayList<User> getUsers() {
		return users;
	}

	public ArrayList<Turn> getTurns() {
		return turns;
	}
	
	public Alphabet[] getAlphabet() {
		return alphabet;
	}
}
