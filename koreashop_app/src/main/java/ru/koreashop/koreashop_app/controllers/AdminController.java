package ru.koreashop.koreashop_app.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import ru.koreashop.koreashop_app.models.Person;
import ru.koreashop.koreashop_app.models.enums.Role;
import ru.koreashop.koreashop_app.repositories.PeopleRepository;
import ru.koreashop.koreashop_app.services.PersonService;

import java.util.Map;

@Controller
@PreAuthorize("hasAuthority('ROLE_ADMIN')")
public class AdminController {

    private final PersonService personService;
    private final PeopleRepository peopleRepository;

    @Autowired
    public AdminController(PersonService personService, PeopleRepository peopleRepository) {
        this.personService = personService;
        this.peopleRepository = peopleRepository;
    }

    @GetMapping("/admin")
    public String admin(Model model) {
        model.addAttribute("people", personService.allPeople());
        return "admin/admin";
    }

    @PostMapping("/admin/person/ban/{id}")
    public String personBan(@PathVariable("id") Long id) {
        personService.banPerson(id);
        return "redirect:/admin";
    }

    @GetMapping("/admin/person/edit/{id}")
    public String personEdit(@PathVariable("id") Long id, Model model) {
        Person person = peopleRepository.findById(id).orElse(null);
        model.addAttribute("person", person);
        model.addAttribute("roles", Role.values());
        return "admin/person-edit";
    }

    @PostMapping("/admin/person/edit")
    public String personEdit(@RequestParam("personId") Long id, @RequestParam Map<String, String> form) {
        Person person = peopleRepository.findById(id).orElse(null);
        personService.changePersonRoles(person, form);
        return "redirect:/admin";
    }

}
