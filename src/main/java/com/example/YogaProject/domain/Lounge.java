package com.example.YogaProject.domain;

import javax.persistence.*;
import java.time.Duration;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Entity
@Table(name = "lounges")
public class Lounge {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "lounge_id")
    private Long id;

    @Column(name = "lounge_name")
    private String name;

    @Column(name = "lounge_address")
    private String address;

    @Column(name = "lounge_capacity")
    private int capacity;

    @Column(name = "lounge_worktime")
    private Duration workTime;

    @OneToMany(mappedBy = "lounge", cascade = CascadeType.ALL)
    private List<Activity> activities;

    @ManyToMany
    @JoinTable(
            name = "lounge_activity_types",
            joinColumns = { @JoinColumn(name = "lounge_id") },
            inverseJoinColumns = { @JoinColumn(name = "activity_type_id") }
    )
    private Set<ActivityType> activityTypes = new HashSet<>();


    public Lounge() {
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

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public int getCapacity() {
        return capacity;
    }

    public void setCapacity(int capacity) {
        this.capacity = capacity;
    }

    public List<Activity> getActivities() {
        return activities;
    }

    public void setActivities(List<Activity> activities) {
        this.activities = activities;
    }

    public Set<ActivityType> getActivityTypes() {
        return activityTypes;
    }

    public void setActivityTypes(Set<ActivityType> activityTypes) {
        this.activityTypes = activityTypes;
    }

    public Duration getWorkTime() {
        return workTime;
    }

    public void setWorkTime(Duration workTime) {
        this.workTime = workTime;
    }
}
