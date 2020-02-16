package customExceptions;

@SuppressWarnings("serial")
public class UserNotRegisterException extends Exception{

	public UserNotRegisterException(String id) {
		super("User with id: "+id+", has not been registered");
	}
	
}
