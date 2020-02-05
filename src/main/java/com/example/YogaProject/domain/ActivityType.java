package com.example.YogaProject.domain;

import org.hibernate.validator.constraints.Length;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;

@Entity
@Table(name = "activity_types")
public class ActivityType {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "activity_type_id")
    private Long id;

    @Pattern(message = "Bad formed person first name",
            regexp = "^[A-ZА-Я][a-zа-я]*")
    @NotBlank
    @Length(min = 2, max = 25)
    @Column(name = "activity_type_name")
    private String name;

    @Column(name = "available")
    private Boolean available;

    public ActivityType() {
    }

    public ActivityType(String name) {
        this.name = name;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Boolean getAvailable() {
        return available;
    }

    public void setAvailable(Boolean available) {
        this.available = available;
    }
}
