package com.libraryExtra.repository;

import com.libraryExtra.entity.Author;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface AuthorRepository extends JpaRepository<Author,Integer> {
    @Modifying
    @Query("UPDATE Author a SET a.name = :name WHERE a.id = :id")
    void edit(@Param("id") Integer id, @Param("name") String name);

    @Modifying
    @Query("UPDATE Author a SET a.available = :available WHERE a.id = :id")
    void deActivate(@Param("id") Integer id, @Param("available") Boolean available);

 /*   @Query("SELECT a FROM Author a WHERE a.available = true")
    List<Author> findActives();

    List<Author> findByAvailableTrueOrderByNameAsc();
    */

    List<Author> findByAvailableTrue();

    @Query("SELECT a FROM Author a WHERE a.name= :name")
    Author findAuthorByName(@Param("name") String name);

}
