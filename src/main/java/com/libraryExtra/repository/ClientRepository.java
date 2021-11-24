package com.libraryExtra.repository;

import com.libraryExtra.entity.Client;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ClientRepository extends JpaRepository<Client,String> {

    @Modifying
    @Query("UPDATE Client c SET c.available = :available WHERE c.id = :id")
    void deActivate(@Param("id") String id, @Param("available") Boolean available);

    @Query("SELECT c FROM Client c WHERE c.dni= :dni ")
    Client findDNI(@Param("dni") Long dni);
    @Modifying
    @Query("UPDATE Client c SET c.name = :name, c.surname = :surname, c.phoneNumber = :phoneNumber WHERE c.id = :id")
    void modify(@Param("id") String id, @Param("name") String name, @Param("surname") String surname, @Param("phoneNumber") String phoneNumber);

}

