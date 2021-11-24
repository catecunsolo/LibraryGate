package com.libraryExtra.service;

import com.libraryExtra.entity.Role;
import com.libraryExtra.repository.RoleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class RoleService {

    @Autowired
    private RoleRepository roleRepository;

    @Transactional
    public void add(String name){
        Role role = new Role();
        role.setName(name);
        roleRepository.save(role);
    }

    @Transactional(readOnly = true)
    public List<Role> findAll(){
        return roleRepository.findAll();
    }
}
