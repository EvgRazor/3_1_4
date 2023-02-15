package ru.kata.spring.boot_security.demo.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import ru.kata.spring.boot_security.demo.model.Role;
import ru.kata.spring.boot_security.demo.model.User;
import ru.kata.spring.boot_security.demo.service.RoleService;
import ru.kata.spring.boot_security.demo.service.UserService;
import ru.kata.spring.boot_security.demo.util.UserValidator;
import javax.validation.Valid;
import java.security.Principal;
import java.util.Collection;

@Controller
@RequestMapping("/admin")
public class AdminController {

    private final RoleService roleService;
    private final UserValidator userValidator;
    private final UserService userService ;


    public AdminController(RoleService roleService, UserValidator userValidator, UserService userService) {
        this.roleService = roleService;
        this.userValidator = userValidator;
        this.userService = userService;
    }


    // Get all User
    @GetMapping("")
    public String pageAdmin(Model model, Principal principal) {
        model.addAttribute("_user", userService.index());
        model.addAttribute("_userPrincipal", userService.getUserNameSc(principal.getName()));
        model.addAttribute("_roleSet", getRoleChek ());
        return "/admin/admin";
    }

    // Get one user
    @GetMapping("/{id}")
    public String getOneUserId (@PathVariable("id") Integer id, Model model) {
        model.addAttribute("_user", userService.getUserId(id));
        return "/admin/one_user";
    }

    // Add one user
    @PostMapping("")
    public String pageNewUserPost (@ModelAttribute("_user") User user) {
        System.out.println(user);
        System.out.println("-----------------------------------------------------");
        userService.saveUser(user);
        return "redirect:/admin";
    }
    // Add one user

    // Update user
    @PatchMapping("/{id}")
    public String getUpdate (@ModelAttribute("_userForma") @Valid  User user, BindingResult bindingResult, @PathVariable ("id") int  id) {
        System.out.println(user);
        userService.updateUser(id, user);
        return "redirect:/admin";
    }
    // Update user

    // Delete User
    @DeleteMapping("/{id}")
    public String getDelete (@PathVariable ("id") int  id) {
        userService.deleteUser(id);
        return "redirect:/admin";
    }

    // return roles
    private Collection <Role> getRoleChek () {
        Collection <Role> roleSet = roleService.roleCollectionGet();
        return roleSet;
    }

}
