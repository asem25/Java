package ru.semavin.app.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.semavin.app.models.Book;
import ru.semavin.app.models.Person;

import java.util.List;
import java.util.Optional;

@Repository
public interface BooksRepository extends JpaRepository<Book, Integer> {
    Optional<Book> findByOwner(Person person);
    Optional<Book> findByName(String book);
    List<Book> findBookByNameStartingWith(String name);
}
