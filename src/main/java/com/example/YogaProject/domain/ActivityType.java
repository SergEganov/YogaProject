package com.example.YogaProject.domain;

import javax.persistence.*;

@Entity
@Table(name = "activity_types")
public class ActivityType {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "activity_type_id")
    private int id;

    @Column(name = "activity_type_name")
    private String name;

    public ActivityType() {
    }

    public ActivityType(String name) {
        this.name = name;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
