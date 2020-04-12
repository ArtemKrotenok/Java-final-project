package com.gmail.artemkrotenok.mvc.web.controller;

import com.gmail.artemkrotenok.mvc.service.UserService;
import com.gmail.artemkrotenok.mvc.service.model.UserDTO;
import com.gmail.artemkrotenok.mvc.service.util.RoleUtil;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@Controller
@RequestMapping("/users")
public class UserController {

    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping()
    public String getFirstUsersPage() {
        return "redirect:/users/page/1";
    }

    @GetMapping("/page/{numberPage}")
    public String getUsersPage(@PathVariable int numberPage, Model model) {
        Long countUsers = userService.getCountUsers();
        model.addAttribute("countUsers", countUsers);
        model.addAttribute("page", numberPage);
        List<UserDTO> usersDTO = userService.getItemsByPageSorted(numberPage);
        model.addAttribute("users", usersDTO);
        return "users";
    }

    @GetMapping("/role/change/{id}")
    public String getChangeUserRolePage(@PathVariable Long id, Model model) {
        UserDTO userDTO = userService.getUserById(id);
        model.addAttribute("user", userDTO);
        model.addAttribute("roles", RoleUtil.getUserRoles());
        return "user_role_change";
    }

    @PostMapping("/role/change")
    public String changeUserRole(
            @ModelAttribute(name = "user") UserDTO userDTO,
            Model model
    ) {
        userService.changeUserRole(userDTO);
        model.addAttribute("message", "Change user role saved");
        return "message";
    }

    @GetMapping("/password/change/{id}")
    public String changeUserPassword(
            @PathVariable Long id,
            Model model
    ) {
        userService.changeUserPassword(id);
        UserDTO userDTO = userService.getUserById(id);
        model.addAttribute("message", "User password was change and sent to " + userDTO.getEmail());
        return "message";
    }

    @PostMapping("/delete")
    public String deleteUsers(
            @RequestParam(name = "userIds") Long[] userIds,
            Model model
    ) {

        userService.deleteUsersByIds(userIds);
        if (userIds.length == 1) {
            model.addAttribute("message", "User is not selected");
        } else {
            model.addAttribute("message", "Selected users was deleted successfully");
        }
        return "message";
    }

    @GetMapping("/add")
    public String getAddUserPage(Model model) {
        model.addAttribute("user", new UserDTO());
        model.addAttribute("roles", RoleUtil.getUserRoles());
        return "user_add";
    }

    @PostMapping()
    public String addUser(
            Model model,
            @Valid @ModelAttribute(name = "user") UserDTO userDTO,
            BindingResult bindingResult
    ) {
        if (bindingResult.hasErrors()) {
            model.addAttribute("user", userDTO);
            model.addAttribute("roles", RoleUtil.getUserRoles());
            return "user_add";
        }
        userService.add(userDTO);
        model.addAttribute("message", "User was added successfully");
        return "message";
    }
}
