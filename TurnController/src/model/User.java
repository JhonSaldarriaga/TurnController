package model;

import java.io.Serializable;

@SuppressWarnings("serial")
public class User implements Serializable{

	private String id;
	private String typeId;
	private String name;
	private String lastName;
	private String address;
	private String cell;
	private boolean attended;
	
	private TypeId type;

	/**
	 * Constructor de la clase User.
	 * @param id Numero de identificación del usuario.
	 * @param typeId Tipo de documento del usuario.
	 * @param name Nombre del usuario.
	 * @param lastName Apellido del usuario.
	 * @param address Direccion del usuario.
	 * @param cell Numero de telefono del usuario.
	 */
	public User(String id, String typeId, String name, String lastName, String address, String cell) {
		this.id = id;
		this.typeId = typeId;
		this.name = name;
		this.lastName = lastName;
		this.address = address;
		this.cell = cell;
		attended = false;
	}

	public String getId() {
		return id;
	}

	public String getTypeId() {
		return typeId;
	}

	public String getName() {
		return name;
	}

	public String getLastName() {
		return lastName;
	}

	public String getAddress() {
		return address;
	}

	public String getCell() {
		return cell;
	}

	public boolean getAttended() {
		return attended;
	}

	public TypeId getType() {
		return type;
	}

	public void setAttended(boolean attended) {
		this.attended = attended;
	}
	
	
}
