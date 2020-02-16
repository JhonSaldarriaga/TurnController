package customExceptions;

@SuppressWarnings("serial")
public class TurnNotFoundException extends Exception{

	public TurnNotFoundException(String id) {
		super("Turn not found for the user:"+id);
	}
	
	public TurnNotFoundException(char letter, String number) {
		super("User has another turn: "+letter+number);
	}
}
