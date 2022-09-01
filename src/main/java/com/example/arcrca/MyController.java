package com.example.arcrca;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.Collection;
import java.util.List;

@Controller
public class MyController {
    private final UserService userService;
    private final PasswordEncoder passwordEncoder;

    private final MessageRepository messageRepository;

    public MyController(UserService userService, PasswordEncoder passwordEncoder, MessageRepository messageRepository) {
        this.userService = userService;
        this.passwordEncoder = passwordEncoder;
        this.messageRepository = messageRepository;
    }


    @GetMapping("/")
    public String index(){
        return "index";
    }

    @GetMapping("/profile")
    public String profile(Model model){
        User user = getCurrentUser();

        String login = user.getUsername();
        CustomUser dbUser = userService.findByLogin(login);

        model.addAttribute("login", login);
        model.addAttribute("roles", user.getAuthorities());
        model.addAttribute("admin", isAdmin(user));
        model.addAttribute("moderator", isModerator(user));
        model.addAttribute("email", dbUser.getEmail());
        model.addAttribute("phone", dbUser.getPhone());
        model.addAttribute("address", dbUser.getAddress());

        return "profile";
    }

    @PostMapping(value = "/update")
    public String update(@RequestParam(required = false) String email,
                         @RequestParam(required = false) String phone,
                         @RequestParam(required = false) String address) {
        User user = getCurrentUser();

        String login = user.getUsername();
        userService.updateUser(login, email, phone, address);

        return "redirect:/";
    }

    @PostMapping(value = "/newuser")
    public String update(@RequestParam String login,
                         @RequestParam String password,
                         @RequestParam(required = false) String email,
                         @RequestParam(required = false) String phone,
                         @RequestParam(required = false) String address,
                         Model model) {
        String passHash = passwordEncoder.encode(password);

        if ( ! userService.addUser(login, passHash, UserRole.USER, email, phone, address)) {
            model.addAttribute("exists", true);
            model.addAttribute("login", login);
            return "register";
        }

        return "redirect:/";
    }

    @PostMapping(value = "/delete")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public String delete(@RequestParam(name = "toDelete[]", required = false) List<Long> ids,
                         Model model) {
        userService.deleteUsers(ids);
        model.addAttribute("users", userService.getAllUsers());

        return "admin";
    }

    @GetMapping("/send")
    public String send(Model model){

        model.addAttribute("users", userService.getAllUsers());

        return "send";
    }

    @PostMapping(value = "/message")
    public String message(@RequestParam(name = "check") Long id,
                          @RequestParam String message,
                          Model model){
        User user = getCurrentUser();

        String login = user.getUsername();
        String m = message + " from " + login;
        userService.sendMessage(login ,id, m);
        model.addAttribute("users", userService.getAllUsers());
        return "index";
    }


    @GetMapping("/accept")
    public String accept(Model model){
        User user = getCurrentUser();

        String login = user.getUsername();
        CustomUser dbuser = userService.findByLogin(login);

        model.addAttribute("login", login);
        model.addAttribute("messages", userService.findAllById(dbuser.getId()));

        return "accept";
    }

    @PostMapping(value = "/encrypt")
    public String encrypt(@RequestParam(name = "check") Long id,
                          Model model){
        User user = getCurrentUser();

        String login = user.getUsername();
        CustomUser dbuser = userService.findByLogin(login);

        model.addAttribute("login", login);
        model.addAttribute("messages", userService.findAllById(dbuser.getId()));
        model.addAttribute("mes", userService.encryptMessage(login, id));
        return "accept";
    }

    @GetMapping("/login")
    public String loginPage() {
        return "login";
    }

    @GetMapping("/register")
    public String register() {
        return "register";
    }

    @GetMapping("/admin")
    @PreAuthorize("hasRole('ROLE_ADMIN') or hasRole('ROLE_MODERATOR')") // !!!
    public String admin(Model model) {
        User user = getCurrentUser();
        model.addAttribute("users", userService.getAllUsers());
        model.addAttribute("admin", isAdmin(user));
        return "admin";
    }

    @GetMapping("/unauthorized")
    public String unauthorized(Model model){
        User user = getCurrentUser();
        model.addAttribute("login", user.getUsername());
        return "unauthorized";
    }

    // ----

    private User getCurrentUser() {
        return (User)SecurityContextHolder
                .getContext()
                .getAuthentication()
                .getPrincipal();
    }

    private boolean isAdmin(User user) {
        Collection<GrantedAuthority> roles = user.getAuthorities();

        for (GrantedAuthority auth : roles) {
            if ("ROLE_ADMIN".equals(auth.getAuthority()))
                return true;
        }

        return false;
    }

    private boolean isModerator(User user) {
        Collection<GrantedAuthority> roles = user.getAuthorities();

        for (GrantedAuthority auth : roles) {
            if ("ROLE_MODERATOR".equals(auth.getAuthority()))
                return true;
        }

        return false;
    }
}
