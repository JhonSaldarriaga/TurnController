package model;

public enum TypeId {

	CD("citizenship_card"),
	ID("identity_card"),
	CR("civil_registration"),
	PP("passport"),
	FC("foreigner_id");
	
	private String type;
	
	private TypeId(String type) {
		this.type = type;
	}

	public String getType() {
		return type;
	}
}
