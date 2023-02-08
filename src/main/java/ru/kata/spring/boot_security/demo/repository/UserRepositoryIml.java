package ru.kata.spring.boot_security.demo.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import ru.kata.spring.boot_security.demo.model.User;

import java.util.List;

@Repository
public interface UserRepositoryIml extends JpaRepository<User, Integer> {
    User findByName (String username); // Поиск и вывод одного юзера по имени
    User findByEmail (String email); //  Поиск и вывод одного юзера по email






}
