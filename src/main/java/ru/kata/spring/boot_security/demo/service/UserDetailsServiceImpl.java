package ru.kata.spring.boot_security.demo.service;

import org.hibernate.Hibernate;

import org.springframework.context.annotation.Lazy;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.kata.spring.boot_security.demo.model.User;


@Service
public class UserDetailsServiceImpl implements UserDetailsService {

    private  UserService userService;

    public UserDetailsServiceImpl(@Lazy UserService userService) {
        this.userService = userService;
    }

    @Override
    @Transactional
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = userService.getUserNameSc(username);


        if (user == null)
            throw new BadCredentialsException("Нет такого пользователя :(");
        Hibernate.initialize(user.getRoleSet());
        return new User(user);
    }

}
