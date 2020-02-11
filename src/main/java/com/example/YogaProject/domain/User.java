package com.example.YogaProject.domain;

import org.hibernate.validator.constraints.Length;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import javax.persistence.*;
import javax.validation.constraints.*;
import java.time.LocalDate;
import java.util.Collection;
import java.util.Set;

@Entity
@Table(name = "usrs")
public class User implements UserDetails {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "user_id")
    private Long id;

    @Pattern(message = "Enter your first name in format: Jack",
            regexp = "^[A-ZА-Я][a-zа-я]*")
    @NotBlank(message = "First name can't be empty!")
    @Length(min = 2, max = 25, message = "First name length from 2 to 25 characters")
    @Column(name = "first_name", nullable = false)
    private String firstName;

    @Pattern(message = "Enter your last name in format: Jackson",
            regexp = "^[A-ZА-Я][a-zа-я]*")
    @NotBlank(message = "Last name can't be empty!")
    @Length(min = 2, max = 25, message = "Last name length from 2 to 25 characters")
    @Column(name = "last_name", nullable = false)
    private String lastName;

    @NotBlank(message = "Email can't be empty!")
    @Email(message = "Check email form to: Test@test.com")
    @Column(name = "email", unique = true, nullable = false)
    private String email;

    @NotBlank(message = "Phone number can't be empty!")
    @Digits(integer=11, fraction=0, message = "Check that you entered the numbers")
    @Column(name = "phone_number", nullable = false)
    private String phoneNumber;

    @DateTimeFormat(pattern = "yyyy-MM-dd")
    @Past(message = "Please enter correct birthday(date can't be later than today)")
    @Column(name = "birthday")
    private LocalDate birth;

    @ElementCollection(targetClass = Role.class, fetch = FetchType.EAGER)
    @CollectionTable(name = "user_role", joinColumns = @JoinColumn(name = "user_id"))
    @Enumerated(EnumType.STRING)
    private Set<Role> roles;

    private String password;

    public User() {
    }

    public User(String firstName, String lastName, String email, String phoneNumber, LocalDate birth) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        this.phoneNumber = phoneNumber;
        this.birth = birth;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public LocalDate getBirth() {
        return birth;
    }

    public void setBirth(LocalDate birth) {
        this.birth = birth;
    }

    public Set<Role> getRoles() {
        return roles;
    }

    public void setRoles(Set<Role> roles) {
        this.roles = roles;
    }

    public String getPassword() {
        return password;
    }
    public void setPassword(String password) {
        this.password = password;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return getRoles();
    }

    @Override
    //TUT MOZHET BIT OSHIBKA
    public String getUsername() {
        return lastName;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }


}
