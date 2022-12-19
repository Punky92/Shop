package ru.koreashop.koreashop_app.services;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.koreashop.koreashop_app.models.Person;
import ru.koreashop.koreashop_app.repositories.PeopleRepository;

@Service
public class RegistrationService {

    private final PeopleRepository peopleRepository;
    private final PasswordEncoder passwordEncoder;

    public RegistrationService(PeopleRepository peopleRepository, PasswordEncoder passwordEncoder) {
        this.peopleRepository = peopleRepository;
        this.passwordEncoder = passwordEncoder;
    }


    //Регистрация
    @Transactional
    public void register(Person person) {
        String encodedPassword = passwordEncoder.encode(person.getPassword()); // шифруем пароль
        person.setPassword(encodedPassword); //обновляем пароль
        person.setRole("ROLE_USER");
        peopleRepository.save(person);
    }
}
