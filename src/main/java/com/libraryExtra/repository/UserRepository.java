package com.libraryExtra.repository;

import com.libraryExtra.entity.UserLogin;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;
@Repository
public interface UserRepository extends JpaRepository <UserLogin, Integer> {

   @Query("SELECT u FROM UserLogin u WHERE u.username= :username ")
   UserLogin findUserByUsername(@Param("username")String username);

    Optional<UserLogin> findByUsername(String username);

    boolean existsUserLoginByUsername(String username);
}
