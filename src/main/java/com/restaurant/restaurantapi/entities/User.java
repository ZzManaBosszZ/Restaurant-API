package com.restaurant.restaurantapi.entities;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.Data;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.sql.Date;
import java.util.Collection;
import java.util.List;

@Data
@Table(name = "user")
@Entity

public class User extends BaseEntity implements UserDetails {

    @Column(name = "FullName", nullable = false)
    private String fullName;

    @Column(name = "Email", nullable = false, unique = true)
    private String email;

    @Column(name = "password", nullable = false)
    private String password;

    @Column(name = "Phone")
    private String phone;

    @Column(name = "Birthday")
    private Date BirthDay;

    @Column(name = "ResetToken")
    private String resetToken;

    @Column(name = "ResetTokenExpiry")
    private Date resetTokenExpiry;

    private Role role;

    @Column(name = "status")
    private  Integer status;

    @Column(name = "Address")
    private String Address;

    @Column(name = "createdby")
    private String createdBy;

    private String userType;

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return List.of(new SimpleGrantedAuthority(role.name()));
    }

    @Override
    public String getUsername() {
        return email;
    }

    @Override
    public boolean isAccountNonExpired() {
        return UserDetails.super.isAccountNonExpired();
    }

    @Override
    public boolean isAccountNonLocked() {
        return UserDetails.super.isAccountNonLocked();
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return UserDetails.super.isCredentialsNonExpired();
    }

    @Override
    public boolean isEnabled() {
        return UserDetails.super.isEnabled();
    }

}
