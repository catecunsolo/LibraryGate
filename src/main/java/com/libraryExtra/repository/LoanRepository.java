package com.libraryExtra.repository;

import com.libraryExtra.entity.Loan;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Date;

@Repository
public interface LoanRepository extends JpaRepository<Loan,String> {
    @Modifying
    @Query("UPDATE Loan l SET l.available= :available, l.returnDate= :returnDate WHERE l.id = :id")
    void deActivate(@Param("id") String id, @Param("available") Boolean available, @Param("returnDate") Date returnDate);
}