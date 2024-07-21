package ru.semavin.app.models;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Pattern;
import lombok.*;
import org.springframework.stereotype.Component;

import java.util.Date;

@Entity
@Table(name = "book")
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
public class Book {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private int id;


    @NotEmpty(message = ("Name is empty"))
    @Column(name = "name")
    private String name;

    @NotEmpty(message = ("Author is empty"))
    @Column(name = "author")
    private String author;

    @NotEmpty
    @Pattern(regexp = "[0-9]{4}",
    message = "Example: 1111")
    @Column(name = "year_of_production")
    private String date_of_prod;

    @Column(name = "created_at")
    @Temporal(TemporalType.TIMESTAMP)
    private Date createdAt;

    @ManyToOne
    @JoinColumn(name = "person_id", referencedColumnName = "id")
    private Person owner;

    public boolean isOverdue(){
        Date ndate = new Date();
        return ndate.getTime() - createdAt.getTime() > 10000;
    }
}
