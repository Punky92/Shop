package ru.koreashop.koreashop_app.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import ru.koreashop.koreashop_app.models.Person;
import ru.koreashop.koreashop_app.repositories.PeopleRepository;


@Controller
@RequestMapping("/cart")
public class CartController {
    private PeopleRepository peopleRepository;

    @Autowired
    public CartController(PeopleRepository peopleRepository) {
        this.peopleRepository = peopleRepository;
    }

    //Метод на представление корзины
    @GetMapping("/getCartById")
    public String getCartById(Model model) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        String username = auth.getName();
        Person person = peopleRepository.findByEmail(username).get();
        model.addAttribute("cart", person.getCart());
        model.addAttribute("cartDetails", person.getCart().getCartDetails());
        return "prod/cart";
    }
}