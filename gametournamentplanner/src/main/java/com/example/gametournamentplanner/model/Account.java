package com.example.gametournamentplanner.model;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
@Entity
public class Account {
    @Id
    @GeneratedValue
    private Long id;

    public Long getId() {
        return id;
    }

    private String name;
    public String getName() {return name;}
    public void setName(String name) { this.name = name;}

    private String emailAddress;
    public String getEmailAddress() {return emailAddress;}
    public void setEmailAddress(String emailAddress) {this.emailAddress = emailAddress;}

    private String password;
    public String getPassword() {return password;}
    public void setPassword(String password) {this.password = password;}
}
