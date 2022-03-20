package com.elib.userclient.model;

import lombok.*;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Entity
@Table(name = "custom_user")
@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class CustomUser {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    private String firstName;
    private String lastName;
    @Embedded
    private Address address;
    @Embedded
    private AccountInfo accountInfo;

    @ElementCollection(fetch = FetchType.EAGER)
    @CollectionTable(name = "user_books", joinColumns = @JoinColumn(name = "user_id"))
    @Column(name = "book_id")
    private Set<Integer> books = new HashSet<>();

    public void addBook(Integer bookId){
        this.books.add(bookId);
    }

    public void removeBook(Integer bookId){
        this.books.remove(bookId);
    }
}
