package start.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import dao.PersonDao;
import domain.Person;


@Service
public class PersonService {

	@Autowired
	private PersonDao personDao;
	
	
	public PersonService() {
		System.out.println("service 被实例化");
	}


	public Person getPerson() {
		return personDao.getPerson();
	}

}
