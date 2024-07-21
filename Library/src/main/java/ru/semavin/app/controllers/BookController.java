package ru.semavin.app.controllers;

import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import ru.semavin.app.models.Book;
import ru.semavin.app.models.Person;
import ru.semavin.app.service.BookService;
import ru.semavin.app.service.PersonService;
import ru.semavin.app.util.BookValidator;

import java.util.*;


@Controller
@RequestMapping("/book")
public class BookController {
    private final BookService bookService;
    private final BookValidator bookValidator;
    private final PersonService personService;
    @Autowired
    public BookController(BookService bookService, BookValidator bookValidator, PersonService personService) {
        this.bookService = bookService;
        this.bookValidator = bookValidator;
        this.personService = personService;
    }
    @GetMapping("/{id}")
    public String show(@PathVariable("id") Integer id, Model model, @ModelAttribute("person") Person person){
        Book book = null;
        if (bookService.findById(id).isPresent()) {
            book = bookService.findById(id).get();
            model.addAttribute("book", book);
        }
        if (Objects.requireNonNull(book).getOwner() != null)
            model.addAttribute("person_with", book.getOwner());
        model.addAttribute("personList", personService.findAll());

        return "book/show";
    }

    @GetMapping()
    public String showAll(Model model,
                          @RequestParam(value = "page", required = false) Integer page,
                          @RequestParam(value = "books_per_page", required = false) Integer books_per_page,
                          @RequestParam(value = "sort_by", required = false) String SortBy){
        if (((page != null && page > -1) & books_per_page != null && SortBy != null)) {
            model.addAttribute("books", bookService.findAll(PageRequest.of(page, books_per_page, Sort.by(SortBy))));
            model.addAttribute("page", page);
            model.addAttribute("book_per_page", books_per_page);
        }
        else if (((page != null && page > -1) & books_per_page != null)){
            model.addAttribute("books", bookService.findAll(PageRequest.of(page, books_per_page)));
            model.addAttribute("page", page);
            model.addAttribute("book_per_page", books_per_page);
        }
        else
            model.addAttribute("books", bookService.findAll());
        return "book/showAll";
    }
    @GetMapping("/new")
    public String newPerson(@ModelAttribute("book") Book book){
        return "book/new";
    }
    @PostMapping
    public String create(@ModelAttribute("book") @Valid Book book,
                         BindingResult bindingResult){
        bookValidator.validate(book, bindingResult);
        if(bindingResult.hasErrors()){
            return "book/new";
        }

        bookService.save(book);
        return "redirect:/book";
    }
    @GetMapping("/{id}/edit")
    public String edit(@PathVariable("id") int id, Model model){
        model.addAttribute("book", bookService.findById(id).get());
        return "book/edit";
    }
    @GetMapping("/search")
    public String search(@RequestParam(value = "query", required = false) String query, Model model){
        List<Book> bookList = bookService.findByName(query);
        if (query != null)
            model.addAttribute("query", bookList);
        return "/book/search";
    }
    @PatchMapping("/{id}/addpeople")
    public String addPeopleToBook(@PathVariable("id") Integer id,
                                  @ModelAttribute("person_to_add") Person person){
        bookService.addPeopleToBook(id, person).ifPresent(book -> bookService.edit(id, book));
        return "redirect:/book/" + id;
    }


    @PatchMapping("/{id}")
    public String update(@ModelAttribute("book") @Valid Book book,
                         BindingResult bindingResult,
                         @PathVariable("id") Integer id){
        bookValidator.validate(book, bindingResult);
        if(bindingResult.hasErrors()){
            return "book/edit";
        }

        bookService.edit(id, book);
        return "redirect:/book";
    }
    @DeleteMapping("/{id}")
    public String delete(@PathVariable("id") Integer id ){
        bookService.delete(id);
        return "redirect:/book";
    }
    @DeleteMapping("/{id}/delpeople")
    public String delPeople(@PathVariable("id") int id){
        bookService.delPeople(id).ifPresent(book -> bookService.edit(id, book));
        return "redirect:/book/" + id;
    }
}
