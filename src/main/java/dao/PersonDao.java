package dao;

import domain.Person;

public class PersonDao {

	
	public PersonDao() {
		System.out.println("dao 示例化");
	}

	public Person getPerson() {
		return new Person("呵呵","怼","嘿嘿");
	}

}
