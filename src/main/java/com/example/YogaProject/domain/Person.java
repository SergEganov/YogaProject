package com.example.YogaProject.domain;


import java.time.LocalDate;
import java.util.Set;

public class Person {

    private Integer id;
    private String firstName;
    private String lastName;
    private String email;
    private String phoneNumber;
    private LocalDate birth;
    private Set<Role> roles;
}
