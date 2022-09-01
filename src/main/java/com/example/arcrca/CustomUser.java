package com.example.arcrca;

import com.example.arcrca.asymcrypt.AsymParty;
import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;


@Entity
public class CustomUser {
    @Id
    @GeneratedValue
    private Long id;

    private String login;
    private String password;

    @Enumerated(EnumType.STRING)
    private UserRole role;

    private String email;
    private String phone;
    private String address;

    @Transient
    AsymParty asymParty = new AsymParty();

    public CustomUser() {
    }
    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL)
    private List<CustomMessage> messages = new ArrayList<>();
    public CustomUser(String login, String password, UserRole role,
                      String email, String phone, String address) {
        this.login = login;
        this.password = password;
        this.role = role;
        this.email = email;
        this.phone = phone;
        this.address = address;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public UserRole getRole() {
        return role;
    }

    public void setRole(UserRole role) {
        this.role = role;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public AsymParty getAsymParty() {
        return asymParty;
    }

    public void setAsymParty(AsymParty asymParty) {
        this.asymParty = asymParty;
    }

    public List<CustomMessage> getMessages() {
        return messages;
    }

    public void setMessages(List<CustomMessage> messages) {
        this.messages = messages;
    }
}
