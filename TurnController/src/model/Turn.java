package model;

import java.io.Serializable;

@SuppressWarnings("serial")
public class Turn implements Serializable{

	private char letter;
	private String number;
	
	private User user;
	
	/**
	 * Constructor de la clase Turn.
	 * @param letter Letra del turno.
	 * @param number Numero del turno(posición).
	 * @param user Usuario que posee el turno.
	 */
	public Turn(char letter, String number, User user) {
		this.letter = letter;
		this.number = number;
		this.user = user;
	}

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	public char getLetter() {
		return letter;
	}

	public String getNumber() {
		return number;
	}
}
