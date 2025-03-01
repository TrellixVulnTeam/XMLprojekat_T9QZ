package com.example.securityservice.Dto;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

//DTO koji preuzima podatke sa forme za registraciju
@AllArgsConstructor
@NoArgsConstructor
public class UserRequest {
    private Long id;

    private String korisnicko;

    private String password;

    private String firstname;

    private String lastname;

    private String email;
    private String gender;
    private String profilePicture;
    private String dateOfBirth;
    private String mobile;

    public String getDateOfBirth() {
        return dateOfBirth;
    }

    public void setDateOfBirth(String dateOfBirth) {
        this.dateOfBirth = dateOfBirth;
    }

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public String getProfilePicture() {
        return profilePicture;
    }

    public void setProfilePicture(String profilePicture) {
        this.profilePicture = profilePicture;
    }

    public String getKorisnicko() {
        return korisnicko;
    }

    public void setKorisnicko(String username) {
        this.korisnicko = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getFirstname() {
        return firstname;
    }

    public void setFirstname(String firstname) {
        this.firstname = firstname;
    }

    public String getLastname() {
        return lastname;
    }

    public void setLastname(String lastname) {
        this.lastname = lastname;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }
}
