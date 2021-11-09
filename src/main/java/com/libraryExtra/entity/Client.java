package com.libraryExtra.entity;

import org.hibernate.annotations.GenericGenerator;
import javax.persistence.*;
import java.util.Comparator;

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
/*@OneToMany(mappedBy = "client")
private List <Loan> loans;*/

    public Client(String id, Long dni, String name, String surname, String phoneNumber) {
        this.dni = dni;
        this.name = name;
        this.surname = surname;
        this.phoneNumber = phoneNumber;
        this.id=id;
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

/*    public List<Loan> getLoans() {
        return loans;
    }

    public void setLoans(List<Loan> loans) {
        this.loans = loans;
    }*/
public static Comparator<Client> compareName = new Comparator<Client>() {
    @Override
    public int compare(Client client1, Client client2) {
        return client1.getName().compareToIgnoreCase(client2.getName());
    }
    };
}
