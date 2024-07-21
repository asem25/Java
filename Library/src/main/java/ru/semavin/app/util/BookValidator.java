package ru.semavin.app.util;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;
import ru.semavin.app.models.Book;
import ru.semavin.app.service.BookService;

@Component
public class BookValidator implements Validator {
    private final BookService bookService;
    @Autowired
    public BookValidator(BookService bookService) {
        this.bookService = bookService;
    }

    @Override
    public boolean supports(Class<?> clazz) {
        return Book.class.equals(clazz);
    }

    @Override
    public void validate(Object target, Errors errors) {
        Book book = (Book) target;

        if (bookService.findByName(book).isPresent()){
            errors.rejectValue("name", "",
                    "name is already taken");
        }
    }
}
