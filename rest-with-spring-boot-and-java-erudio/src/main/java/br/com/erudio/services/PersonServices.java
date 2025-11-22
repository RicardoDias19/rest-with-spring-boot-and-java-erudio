package br.com.erudio.services;

import br.com.erudio.exception.ResourceNotFoundException;
import br.com.erudio.model.Person;
import br.com.erudio.repository.PersonRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.concurrent.atomic.AtomicLong;
import java.util.logging.Logger;

@Service
public class PersonServices {

    private final AtomicLong counter = new AtomicLong();
    private final Logger logger = Logger.getLogger(PersonServices.class.getName());

    @Autowired
    PersonRepository repository;


    public List<Person> findAll() {
        logger.info("Find All Persons");

        /*List<Person> persons = new ArrayList<>();
        for (int i = 0; i < 8; i++) {
            Person person = mockPerson(i);
            persons.add(person);
        }
        return persons;*/

        return repository.findAll();
    }

    public Person findById(Long id) {
        logger.info("Finding person with id: " + id);

        // Isto aqui é um mock
        /*Person person = new Person();
        person.setId(counter.incrementAndGet());
        person.setFirstName("Ricardo");
        person.setLastName("Dias");
        person.setAddress("Natal - RN - Brasil");
        person.setGender("Male");

        return person;*/

        return repository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("No record found for this id"));
    }

    public Person create(Person person) {
        logger.info("Creating a person!");
       /* person.setId(counter.incrementAndGet());
        person.setFirstName("Ricardo");
        return  person;*/
        return repository.save(person);
    }

    public Person update(Person person) {
        logger.info("Updating one person!");
        Person entity = repository.findById(person.getId())
                .orElseThrow(() -> new ResourceNotFoundException("No record found for this id"));

        entity.setFirstName(person.getFirstName());
        entity.setLastName(person.getLastName());
        entity.setAddress(person.getAddress());
        entity.setGender(person.getGender());
        /*person.setId(counter.incrementAndGet());
        person.setFirstName("Ricardo");
        return  person;*/
        return repository.save(person);
    }

    public void delete(Long id) {
        logger.info("Deleting one person!");
        Person entity = repository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Nor record found for this id"));

        repository.delete(entity);
    }

    private Person mockPerson(int i) {

        Person person = new Person();
        person.setId(counter.incrementAndGet());
        person.setFirstName("FirstName" + i);
        person.setLastName("LastName" + i);
        person.setAddress("Some Address in Brasil");
        person.setGender("Male");

        return person;
    }
}
