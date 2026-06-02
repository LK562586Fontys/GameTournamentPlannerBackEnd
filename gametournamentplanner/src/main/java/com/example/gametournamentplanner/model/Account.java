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

    private String naam;
    public String getNaam() {return naam;}
    public void setNaam(String naam) { this.naam = naam;}

    private String emailAddress;
    public String getEmailAddress() {return emailAddress;}
    public void setEmailAddress(String emailAddress) {this.emailAddress = emailAddress;}

    private String password;
    public String getPassword() {return password;}
    public void setPassword(String password) {this.password = password;}
}
