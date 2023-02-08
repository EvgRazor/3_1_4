package ru.kata.spring.boot_security.demo.service;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.kata.spring.boot_security.demo.model.Role;
import ru.kata.spring.boot_security.demo.repository.RoleRepositoryImpl;

import java.util.Collection;

@Service
public class RoleService {

    private final RoleRepositoryImpl roleRepository;


    public RoleService(RoleRepositoryImpl roleRepository) {
        this.roleRepository = roleRepository;
    }

    @Transactional
    public Collection <Role> roleCollectionGet () {
        Collection <Role> roleSet = roleRepository.findAll();
        return roleSet;
    }

}
