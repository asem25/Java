package ru.semavin.app.models;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.*;
import org.hibernate.annotations.Cascade;

import java.util.Date;
import java.util.List;

@Entity
@Table(name = "person")
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
public class Person {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    @NotEmpty(message = "Name should be is not empty")
    @Size(min = 2, max = 30, message = "2 <= len name <= 30")
    @Column(name = "fullname")
    private String fullname;
    @Pattern(regexp = "[0-9]{2}.[0-9]{2}.[0-9]{4}",
            message = "Example = 01.02.1234")
    @Column(name = "birthday")
    private String birthday;
    @OneToMany(mappedBy = "owner", fetch = FetchType.EAGER)
    private List<Book> bookList;

    @Override
    public String toString() {
        return "id = " + id +
                ", Name = " + fullname;
    }
}
