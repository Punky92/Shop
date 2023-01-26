package ru.koreashop.koreashop_app.services;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.koreashop.koreashop_app.models.Cart;
import ru.koreashop.koreashop_app.models.Person;
import ru.koreashop.koreashop_app.repositories.CartRepository;
import ru.koreashop.koreashop_app.repositories.PeopleRepository;

@Service
public class RegistrationService {

    private final PeopleRepository peopleRepository;
    private final CartRepository cartRepository;
    private final PasswordEncoder passwordEncoder;

    public RegistrationService(PeopleRepository peopleRepository, CartRepository cartRepository, PasswordEncoder passwordEncoder) {
        this.peopleRepository = peopleRepository;
        this.cartRepository = cartRepository;
        this.passwordEncoder = passwordEncoder;
    }


    //Регистрация
    @Transactional
    public void register(Person person) {
        String encodedPassword = passwordEncoder.encode(person.getPassword()); // шифруем пароль
        person.setPassword(encodedPassword); //обновляем пароль
        person.setRole("ROLE_USER");
        Cart cart = new Cart();
        peopleRepository.save(person);
        cart.setId(person.getId());
        cartRepository.save(cart);
    }
}
