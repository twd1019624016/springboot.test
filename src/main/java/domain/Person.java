package domain;

public class Person {

	private String personName1;
	
	private String action;
	
	private String personName2;

	public Person() {
		
	}

	public Person(String personName1, String action, String personName2) {
		super();
		this.personName1 = personName1;
		this.action = action;
		this.personName2 = personName2;
	}

	public String getPersonName1() {
		return personName1;
	}

	public void setPersonName1(String personName1) {
		this.personName1 = personName1;
	}

	public String getAction() {
		return action;
	}

	public void setAction(String action) {
		this.action = action;
	}

	public String getPersonName2() {
		return personName2;
	}

	public void setPersonName2(String personName2) {
		this.personName2 = personName2;
	}
	
	
	

}
