package com.libraryExtra.entity;

import org.hibernate.annotations.GenericGenerator;
import javax.persistence.*;

@Entity
public class Client {
    @Id
    @GeneratedValue(generator = "uuid")
    @GenericGenerator(name = "uuid", strategy = "uuid2")
    private String id;
    private Long dni;
    private String name;
    private String surname;
    private String phoneNumber;
    @Column(columnDefinition = "TINYINT(1)")
    private Boolean available;
    @OneToOne
    private UserLogin user;

/*@OneToMany(mappedBy = "client")
private List <Loan> loans;*/

    public Client(String id, Long dni, String name, String surname, String phoneNumber, UserLogin user) {
        this.dni = dni;
        this.name = name;
        this.surname = surname;
        this.phoneNumber = phoneNumber;
        this.id=id;
        this.user=user;
    }

    public Client() {
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public Long getDni() {
        return dni;
    }

    public void setDni(Long dni) {
        this.dni = dni;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSurname() {
        return surname;
    }

    public void setSurname(String surname) {
        this.surname = surname;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public Boolean getAvailable() {
        return available;
    }

    public void setAvailable(Boolean available) {
        this.available = available;
    }

    public UserLogin getUser() {
        return user;
    }

    public void setUser(UserLogin user) {
        this.user = user;
    }

/*    public List<Loan> getLoans() {
        return loans;
    }

    public void setLoans(List<Loan> loans) {
        this.loans = loans;
    }*/
}
