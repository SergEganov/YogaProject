package com.example.YogaProject.domain;

import javax.persistence.*;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.*;

@Entity
@Table(name = "activities")
public class Activity {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "activity_id")
    private Long id;

    @Column(name = "activity_name")
    private String name;

    @Column(name = "activity_tag")
    private String tag;

    @Column(name = "activity_capacity")
    private Integer capacity;

    @Column(name = "activity_time")
    private LocalDateTime time;

    @Column(name = "activity_date")
    private LocalDate date;

    @Column(name = "activity_price")
    private BigDecimal price;

    @Column(name = "available")
    private Boolean isAvailable;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "mentor_id")
    private UserEntity mentor;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "activity_type_id")
    private ActivityType activityType;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "lounge_id")
    private Lounge lounge;

    @ManyToMany
    @JoinTable(
            name = "activity_users",
            joinColumns = {@JoinColumn(name = "activity_id")},
            inverseJoinColumns = {@JoinColumn(name = "user_id")}
    )
    private Set<UserEntity> users = new HashSet<>();

    public Activity() {
    }

    public Activity(String name, String tag) {
        this.name = name;
        this.tag = tag;
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

    public String getTag() {
        return tag;
    }

    public void setTag(String tag) {
        this.tag = tag;
    }

    public Integer getCapacity() {
        return capacity;
    }

    public void setCapacity(Integer capacity) {
        this.capacity = capacity;
    }

    public LocalDateTime getTime() {
        return time;
    }

    public void setTime(LocalDateTime time) {
        this.time = time;
    }

    public LocalDate getDate() {
        return date;
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }

    public Boolean getAvailable() {
        return isAvailable;
    }

    public void setAvailable(Boolean available) {
        isAvailable = available;
    }

    public UserEntity getMentor() {
        return mentor;
    }

    public void setMentor(UserEntity mentor) {
        this.mentor = mentor;
    }

    public ActivityType getActivityType() {
        return activityType;
    }

    public void setActivityType(ActivityType activityType) {
        this.activityType = activityType;
    }

    public Lounge getLounge() {
        return lounge;
    }

    public void setLounge(Lounge lounge) {
        this.lounge = lounge;
    }

    public Set<UserEntity> getUsers() {
        return users;
    }

    public void setUsers(Set<UserEntity> users) {
        this.users = users;
    }
}