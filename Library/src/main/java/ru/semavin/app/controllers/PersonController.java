package ru.semavin.app.controllers;

import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import ru.semavin.app.models.Book;
import ru.semavin.app.models.Person;
import ru.semavin.app.repositories.PersonRepository;
import ru.semavin.app.service.BookService;
import ru.semavin.app.service.PersonService;
import ru.semavin.app.util.PersonValidator;

@Controller
@RequestMapping("/person")
public class PersonController {
    private final PersonService personService;
    private final PersonValidator personValidator;
    private final BookService bookService;
    @Autowired
    public PersonController(PersonService personService, PersonValidator personValidator, BookService bookService) {
        this.personService = personService;
        this.personValidator = personValidator;
        this.bookService = bookService;
    }
    @GetMapping("/{id}")
    public String show(@PathVariable("id") Integer id, Model model, @ModelAttribute("book") Book book){
        if (personService.findById(id).isPresent()) {
            Person personbyId = personService.findById(id).get();
            model.addAttribute("person", personbyId);
            if (!personbyId.getBookList().isEmpty()){
                model.addAttribute("booksList", personbyId.getBookList());
            }
        }
        return "person/show";
    }
    @GetMapping()
    public String showAll(Model model){
        model.addAttribute("people", personService.findAll());
        return "person/showAll";
    }
    @GetMapping("/new")
    public String newPerson(@ModelAttribute("person")Person person){
        return "person/new";
    }
    @PostMapping
    public String create(@ModelAttribute("person") @Valid Person person,
                         BindingResult bindingResult){
        personValidator.validate(person, bindingResult);

        if (bindingResult.hasErrors())
            return "person/new";
        personService.save(person);
        return "redirect:/person";
    }
    @GetMapping("/{id}/edit")
    public String edit(@PathVariable("id") int id, Model model){
        model.addAttribute("person", personService.findById(id));
        return "person/edit";
    }
    @PatchMapping("/{id}")
    public String update(@ModelAttribute("person") @Valid Person person,
                         BindingResult bindingResult,
                         @PathVariable("id") Integer id){
        personValidator.validate(person, bindingResult);

        if (bindingResult.hasErrors())
            return "person/edit";
        personService.edit(id, person);
        return "redirect:/person";
    }

    @DeleteMapping("/{id}")
    private String delete(@PathVariable("id") int id){
        personService.delete(id);

        return "redirect:/person";
    }
}
