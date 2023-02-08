package ru.kata.spring.boot_security.demo.service;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.kata.spring.boot_security.demo.model.User;
import ru.kata.spring.boot_security.demo.repository.UserRepositoryIml;

import java.util.List;

@Service
public class UserService {

    public final UserRepositoryIml userRepositoryIml;
    public final PasswordEncoder passwordEncoder;

    public UserService(UserRepositoryIml userRepositoryIml, PasswordEncoder passwordEncoder) {
        this.userRepositoryIml = userRepositoryIml;
        this.passwordEncoder = passwordEncoder;
    }

    // User c формы -вытягиваем по name
    @Transactional (readOnly = true)
    public User getUserNameSc(String username) {
        return userRepositoryIml.findByName(username);
    }

    // User c формы - вытягиваем по email
    @Transactional (readOnly = true)
    public User getUserEmailSc (String email) {
        return userRepositoryIml.findByEmail(email);
    }

    @Transactional (readOnly = true)
    public List <User> index() {
        return userRepositoryIml.findAll();
    }

    @Transactional (readOnly = true)
    public User getUserId(int id) {
        return userRepositoryIml.getById(id);
    }

    @Transactional
    public void saveUser(User user) {
        user.setPass(passwordEncoder.encode(user.getPass()));
        userRepositoryIml.save(user);
    }

    @Transactional
    public void updateUser(int i, User user) {
        User userUp = getUserId(i);
        userUp.setName(user.getName());
        userUp.setAge(user.getAge());
        userUp.setEmail(user.getEmail());
        userUp.setPass(passwordEncoder.encode(user.getPass()));
        userUp.setRoleSet(user.getRoleSet());
        userRepositoryIml.save(userUp);
    }

    @Transactional
    public void deleteUser(int id) {
        userRepositoryIml.deleteById(id);
    }


}
