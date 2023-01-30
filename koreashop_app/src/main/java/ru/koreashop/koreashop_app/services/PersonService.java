package ru.koreashop.koreashop_app.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.koreashop.koreashop_app.models.Person;
import ru.koreashop.koreashop_app.models.enums.Role;
import ru.koreashop.koreashop_app.repositories.PeopleRepository;

import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

@Service
@Transactional(readOnly = true)
public class PersonService {

    private final PeopleRepository peopleRepository;

    @Autowired
    public PersonService(PeopleRepository peopleRepository) {
        this.peopleRepository = peopleRepository;
    }

    public List<Person> allPeople() {
        return peopleRepository.findAll();
    }

    @Transactional
    public void banPerson(Long id) {
        Person person = peopleRepository.findById(id).orElse(null);
        if (person != null) {
            if (person.isActive()) {
                person.setActive(false);
            } else {
                person.setActive(true);
            }
        }
        peopleRepository.save(person);
    }

    @Transactional
    public void changePersonRoles(Person person, Map<String, String> form) {
        Set<String> roles = Arrays.stream(Role.values())
                .map(Role::name)
                .collect(Collectors.toSet());
        person.getRoles().clear();
        for (String key : form.keySet()) {
            if (roles.contains(key)) {
                person.getRoles().add(Role.valueOf(key));
            }
        }
        peopleRepository.save(person);
    }
}
