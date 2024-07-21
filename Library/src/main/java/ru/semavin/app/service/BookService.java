package ru.semavin.app.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import ru.semavin.app.models.Book;
import ru.semavin.app.models.Person;
import ru.semavin.app.repositories.BooksRepository;

import java.util.Date;
import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class BookService {
    private final BooksRepository booksRepository;
    @Autowired
    public BookService(BooksRepository booksRepository) {
        this.booksRepository = booksRepository;
    }

    public Optional<Book> findByOwner(Person person){
        return booksRepository.findByOwner(person);
    }
    public Optional<Book> findById(Integer id){
        return booksRepository.findById(id);
    }
    public List<Book> findAll(){
        return booksRepository.findAll();
    }
    public List<Book> findAll(Pageable var){return booksRepository.findAll(var).getContent();}
    public void save(Book book){
        booksRepository.save(book);
    }
    public void edit(Integer id, Book book){
        book.setId(id);
        booksRepository.save(book);
    }
    public void delete(Integer id){
        booksRepository.deleteById(id);
    }
    public Optional<Book> findByName(Book book){
        return booksRepository.findByName(book.getName());
    }
    public List<Book> findByName(String query){
        return booksRepository.findBookByNameStartingWith(query);
    }
    public Optional<Book> addPeopleToBook(Integer id, Person person){
        Optional<Book> book = booksRepository.findById(id);
        if (book.isPresent()){
            book.get().setOwner(person);
            book.get().setCreatedAt(new Date());
        }
        return book;
    }
    public Optional<Book> delPeople(int id){
        Optional<Book> book = booksRepository.findById(id);
        if (book.isPresent()){
            book.get().setOwner(null);
            book.get().setCreatedAt(null);
        }
        return book;
    }
}
