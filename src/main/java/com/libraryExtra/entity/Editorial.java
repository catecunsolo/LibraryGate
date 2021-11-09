package com.libraryExtra.entity;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "editorial")
public class Editorial {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer id;
    @Column(nullable=false)
    private String name;
    @Column(columnDefinition = "TINYINT(1)")
    private Boolean available;
    @OneToMany(mappedBy = "editorial")
    private List<Book> books;

    public Editorial(Integer id, String name, Boolean available) {
        this.id = id;
        this.name = name;
        this.available = available;
    }

    public Editorial(List<Book> books) {
        books = new ArrayList<>();
    }

    public Editorial() {
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Boolean getAvailable() {
        return available;
    }

    public void setAvailable(Boolean available) {
        this.available = available;
    }

    public List<Book> getBooks() {
        return books;
    }

    public void setBooks(List<Book> books) {
        this.books = books;
    }
}