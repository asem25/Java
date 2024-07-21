package ru.semavin.app.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.semavin.app.models.Person;
import ru.semavin.app.repositories.PersonRepository;

import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class PersonService {
    private PersonRepository personRepository;
    @Autowired
    public PersonService(PersonRepository personRepository) {
        this.personRepository = personRepository;
    }

    public Optional<Person> findById(Integer id){
        return personRepository.findById(id);
    }
    public List<Person> findAll(){
        return personRepository.findAll();
    }
    public void save(Person person){
        personRepository.save(person);
    }
    public void edit(Integer id, Person person){
        person.setId(id);
        personRepository.save(person);
    }
    public void delete(Integer id){
        personRepository.deleteById(id);
    }
    public Optional<Person> findByName(Person person){
        return personRepository.findByFullname(person.getFullname());
    }
}
