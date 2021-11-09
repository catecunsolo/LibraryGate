package com.libraryExtra.entity;

import javax.persistence.*;

@Entity
@Table(name = "book")
public class Book {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    @Column(nullable = false)
    private Long isbn;
    @Column(nullable = false)
    private String title;
    @Column(nullable = false)
    private Integer year;
    @Column(nullable = false)
    private Integer copies;
    private Integer copiesLoaned;
    @Column(nullable = false)
    private Integer copiesLeft;
    @Column(columnDefinition = "TINYINT(1)")
    private Boolean available; //cambiarlo a string
    @ManyToOne
    private Author author;
    @ManyToOne
    private Editorial editorial;

    public Book(Integer id, Long isbn, String title, Integer year, Integer copies, Integer copiesLoaned, Integer copiesLeft,Author author, Editorial editorial) {
        this.id = id;
        this.isbn = isbn;
        this.title = title;
        this.year = year;
        this.copies = copies;
        this.copiesLoaned = copiesLoaned;
        this.copiesLeft = copiesLeft;
        this.author = author;
        this.editorial = editorial;
    }

/* public Book(List<Loan>loans){
        loans = new ArrayList<>();
    }

    public List<Loan> getLoans() {
        return loans;
    }

    public void setLoans(List<Loan> loans) {
        this.loans = loans;
    }*/

    public Book() {
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Long getIsbn() {
        return isbn;
    }

    public void setIsbn(Long isbn) {
        this.isbn = isbn;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public Integer getYear() {
        return year;
    }

    public void setYear(Integer year) {
        this.year = year;
    }

    public Integer getCopies() {
        return copies;
    }

    public void setCopies(Integer copies) {
        this.copies = copies;
    }

    public Integer getCopiesLoaned() {
        return copiesLoaned;
    }

    public void setCopiesLoaned(Integer copiesLoaned) {
        this.copiesLoaned = copiesLoaned;
    }

    public Integer getCopiesLeft() {
        return copiesLeft;
    }

    public void setCopiesLeft(Integer copiesLeft) {
        this.copiesLeft = copiesLeft;
    }

    public Boolean getAvailable() {
        return available;
    }

    public void setAvailable(Boolean available) {
        this.available = available;
    }

    public Author getAuthor() {
        return author;
    }

    public void setAuthor(Author author) {
        this.author = author;
    }

    public Editorial getEditorial() {
        return editorial;
    }

    public void setEditorial(Editorial editorial) {
        this.editorial = editorial;
    }
}
