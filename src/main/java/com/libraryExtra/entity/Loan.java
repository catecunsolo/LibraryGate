package com.libraryExtra.entity;


import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.util.Date;
import java.util.List;

@Entity
public class Loan {
    @Id
    @GeneratedValue(generator = "uuid")
    @GenericGenerator(name = "uuid", strategy = "uuid2")
    private String id;
    @Temporal(TemporalType.DATE)
    private Date loanDate;
    @Temporal(TemporalType.DATE)
    private Date returnDate;
    @Column(columnDefinition = "TINYINT(1)")
    private Boolean available;
    @ManyToOne
    @JoinColumn(nullable = false)
    private Book book;
    @ManyToOne
    @JoinColumn(nullable = false)
    private Client client;

    public Loan(Date loanDate, Date returnDate, Book book,Client client) {
        this.loanDate = loanDate;
        this.returnDate = returnDate;
        this.book = book;
        this.client = client;
    }

    public Loan() {
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public Date getLoanDate() {
        return loanDate;
    }

    public void setLoanDate(Date loanDate) {
        this.loanDate = loanDate;
    }

    public Date getReturnDate() {
        return returnDate;
    }

    public void setReturnDate(Date returnDate) {
        this.returnDate = returnDate;
    }

    public Boolean getAvailable() {
        return available;
    }

    public void setAvailable(Boolean available) {
        this.available = available;
    }

    public Book getBook() {
        return book;
    }

    public void setBook(Book book) {
        this.book = book;
    }

    public Client getClient() {
        return client;
    }

    public void setClient(Client client) {
        this.client = client;
    }
}
