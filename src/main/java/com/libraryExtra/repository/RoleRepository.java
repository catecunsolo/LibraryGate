package com.libraryExtra.repository;

import com.libraryExtra.entity.Role;
import com.libraryExtra.entity.UserLogin;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface RoleRepository extends JpaRepository<Role,Integer> {

    @Query("SELECT r FROM Role r WHERE r.name= :name ")
    Role findRoleByName(@Param("name")String name);
}
