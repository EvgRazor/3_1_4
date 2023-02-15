package ru.kata.spring.boot_security.demo.model;


import org.hibernate.annotations.Fetch;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import javax.persistence.*;
import javax.validation.constraints.Email;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;
import java.util.Collection;
import java.util.Set;

import static javax.persistence.CascadeType.DETACH;
import static javax.persistence.CascadeType.PERSIST;


@Entity
@Table(name = "users")
public class User implements UserDetails {

    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(name = "username")
    @NotEmpty(message = "Имя не может быть пустым")
    @Size(min = 3, max = 20, message = "Минимум 3 символа, максимум 20")
    private String name;

    @Column(name = "lastname")
    @NotEmpty(message = "Фамилия не может быть пустой")
    @Size(min = 3, max = 20, message = "Минимум 3 символа, максимум 20")
    private String lastName;

    @Column(name = "email")
    @NotEmpty(message = "Введите email")
    @Email
    private String email;

    @Column(name = "age")
    @Min(value = 16, message = "Минимальны возраст 16 лет")
    private int age;

    @Column(name = "password")
    @NotEmpty(message = "Пароль не может быть пустым")
    @Size(min = 1, max = 100, message = "Минимум 1 символа, максимум 100")
    private String pass;

    @ManyToMany(cascade = {PERSIST, DETACH}, fetch = FetchType.LAZY)
    @JoinTable(
            name = "user_roles",
            joinColumns = @JoinColumn(name = "user_id"),
            inverseJoinColumns = @JoinColumn (name = "role_id")
    )

    @NotEmpty(message = "Роль не может быть пустой")
    private Set<Role> roleSet;

    @Transient
    private User user;

    public User() {

    }

    public User( User user) {
        this.user = user;
    }

    public User(int id, String name, String lastName, String email, int age, String pass, Set<Role> roleSet, User user) {
        this.id = id;
        this.name = name;
        this.lastName = lastName;
        this.email = email;
        this.age = age;
        this.pass = pass;
        this.roleSet = roleSet;
        this.user = user;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public String getPass() {
        return pass;
    }

    public void setPass(String pass) {
        this.pass = pass;
    }

    public Set<Role> getRoleSet() {
        return roleSet;
    }

    public void setRoleSet(Set<Role> roleSet) {
        this.roleSet = roleSet;
    }

    @Override
    public String toString() {
        return "User{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", lastName='" + lastName + '\'' +
                ", email='" + email + '\'' +
                ", age=" + age +
                ", pass='" + pass + '\'' +
                ", roleSet=" + roleSet +
                ", user=" + user +
                '}';
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }
    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return getUser().getRoleSet().stream().map(role -> new SimpleGrantedAuthority(new Role(role).getAuthority())).toList();
    }

    @Override
    public String getPassword() {
        return getUser().getPass();
    }

    @Override
    public String getUsername() {
        return getUser().getName();
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }

}

