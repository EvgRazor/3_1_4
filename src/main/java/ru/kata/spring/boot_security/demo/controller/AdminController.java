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
    public String pageAdmin(Model model){
        model.addAttribute("_user", userService.index());
        return "/admin/index_user";
    }

    // Get one user
    @GetMapping("/{id}")
    public String getOneUserId (@PathVariable("id") Integer id, Model model) {
        model.addAttribute("_user", userService.getUserId(id));
        return "/admin/one_user";
    }

    // Add one user
    @GetMapping("/new")
    public String pageNewUser (@ModelAttribute("_user") User user, Model model) {
        model.addAttribute("_roleSet", getRoleChek ());
        return "/admin/new";
    }

    @PostMapping("/new")
    public String pageNewUserPost (@ModelAttribute("_user") @Valid User user, BindingResult bindingResult, Model model) {
        userValidator.validate(user, bindingResult);
        if (bindingResult.hasErrors()) {
            model.addAttribute("_roleSet", getRoleChek ());
            return "/admin/new";
        }
        userService.saveUser(user);
        return "redirect:/admin";
    }
    // Add one user

    // Update user
    @GetMapping("/{id}/edit") // Редактируем человека - переходим на формe куда подтягиваем нашего юзера по ID
    public String getEdit(Model model, @PathVariable("id") int id) {
        model.addAttribute("_user", userService.getUserId(id));
        model.addAttribute("_roleSet", getRoleChek ());
        return "/admin/edit";
    }

    @PatchMapping("/{id}")
    public String getUpdate (@ModelAttribute("_user") @Valid User user, BindingResult bindingResult, @PathVariable ("id") int  id, Model model) {
        if (bindingResult.hasErrors()) {
            model.addAttribute("_roleSet", getRoleChek ());
            return  "/admin/edit";
        }
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
