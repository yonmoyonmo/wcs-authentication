package com.wonmocyberschool.authenticationserver.entity;

import com.wonmocyberschool.authenticationserver.AuthProvider;
import net.minidev.json.annotate.JsonIgnore;

import javax.persistence.*;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotNull;

@Entity
@Table(name = "wcs_user",
uniqueConstraints = {@UniqueConstraint(columnNames = "email")})
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;
    @Email
    @Column(nullable = false)
    private String email;
    private String imageUrl;
    @Column(nullable = false)
    private Boolean emailVerified = false;
    @JsonIgnore
    private String password;
    @NotNull
    @Enumerated
    private AuthProvider provider;
    private String providerId;

    //lombok 추가 까먹은 김에 안씀
    //getters and setters
    public Long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getEmail() {
        return email;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public Boolean getEmailVerified() {
        return emailVerified;
    }

    public String getPassword() {
        return password;
    }

    public AuthProvider getProvider() {
        return provider;
    }

    public String getProviderId() {
        return providerId;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public void setEmailVerified(Boolean emailVerified) {
        this.emailVerified = emailVerified;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public void setProvider(AuthProvider provider) {
        this.provider = provider;
    }

    public void setProviderId(String providerId) {
        this.providerId = providerId;
    }

}
