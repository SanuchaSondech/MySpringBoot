package com.sanucha.crudapp;

import com.sanucha.crudapp.entity.Person;
import com.sanucha.crudapp.repository.PersonDIO;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import java.util.List;

@SpringBootApplication
public class CrudappApplication {

	public static void main(String[] args) {
		SpringApplication.run(CrudappApplication.class, args);
	}

    @Bean
    public CommandLineRunner commandLineRunner(PersonDIO dao){
        return runner->{
            upDateData(dao);
        };
    }

    public void saveData(PersonDIO dio){
        Person obj1 = new Person("Ethan", "Miller");
        dio.save(obj1);
        String textComplate = String.format("%s %s Save Complete",obj1.getFirstName(), obj1.getLastName());
        System.out.println(textComplate);
    }

    public void deleteData(PersonDIO dao){
        int id = 2;
        dao.delete(id);
        String textComplate = String.format("%d delete  Complete",id);
        System.out.println(textComplate);
    }

    public void getData(PersonDIO dao){
        int id = 1;
        Person person = dao.get(id);
        System.out.println(person);

    }

    public void getAllData(PersonDIO dao){
        List<Person> data = dao.getAll();
        for (Person person : data){
            System.out.println(person);
        }
    }

    public void upDateData(PersonDIO dao){
        int id = 3;
        Person myPerson = dao.get(id);
        String oldName = myPerson.getFirstName() + " " + myPerson.getLastName();
        myPerson.setFirstName("Liam");
        myPerson.setLastName("Anderson");
        dao.update(myPerson);
        String textComplate = String.format("%s update to %s %s", oldName,myPerson.getFirstName(), myPerson.getLastName() );
        System.out.println(textComplate);
    }
}
