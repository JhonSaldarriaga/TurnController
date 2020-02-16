package customExceptions;

import java.util.ArrayList;

@SuppressWarnings("serial")
public class MandatoryParameterNotTypeException extends Exception{

	private ArrayList<String> parametersNotTyped;
	
	public MandatoryParameterNotTypeException(String name, String lastName, String id, String typeId) {
		super("A mandatory parameter has not been typed");
		parametersNotTyped = new ArrayList<String>();
		chooseGuiltyParameter(name, lastName, id, typeId);
	}
	/**
	 * Permite determinar cual de los atributos está vacío y que, por ende, hayan generado el lanzamiento de la exception.
	 * @param name El nombre del usuario.
	 * @param lastName El apellido del usuario.
	 * @param id El número de documento del usuario.
	 * @param typeId El tipo de documento del usuario.
	 */
	private void chooseGuiltyParameter(String name, String lastName, String id, String typeId) {
		if(name.equals(""))
			parametersNotTyped.add("Name");
		
		if(lastName.equals(""))
			parametersNotTyped.add("lastName");
			
		if(id.equals(""))
			parametersNotTyped.add("ID number");
				
		if(typeId.equals(""))
			parametersNotTyped.add("type ID");
	}
	
	/**
	 * Retorna una lista de los atributos que causaron en lanzamiento de la excepcion.
	 * @return String con la lista de los atributos.
	 */
	public String getProblem() {
		String cause = "Parameters empty are: ";
		for(int i=0; i<parametersNotTyped.size(); i++) {
			cause+=parametersNotTyped.get(i)+"-";
		}
		
		return cause;
	}
}
