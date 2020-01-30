package com.example.YogaProject.domain;

import javax.persistence.*;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
public class Activity {
    public Activity(String name, String tag, Integer capacity, LocalDateTime dateTime, BigDecimal price, Boolean isAvailable, User mentor, ActivityType activityType, Lounge lounge, List<User> users) {
        this.name = name;
        this.tag = tag;
        this.capacity = capacity;
        this.dateTime = dateTime;
        this.price = price;
        this.isAvailable = isAvailable;
        this.mentor = mentor;
        this.activityType = activityType;
        this.lounge = lounge;
        this.users = users;
    }

    public Activity() {
    }

    public Activity(String name, String tag) {
        this.name = name;
        this.tag = tag;
    }

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "activity_id")
    private Long id;

    private String name;
    private String tag;
    private Integer capacity;
    private LocalDateTime dateTime;
    private BigDecimal price;
    private Boolean isAvailable;


    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "mentor_id")
    private User mentor;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "activity_type_id")
    private ActivityType activityType;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "lounge_id")
    private Lounge lounge;

    @ManyToMany
    @JoinTable(
            name = "activity_users",
            joinColumns = { @JoinColumn(name = "activity_id") },
            inverseJoinColumns = { @JoinColumn(name = "user_id") }
    )
    private List<User> users = new ArrayList<>();

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public String getTag() {
        return tag;
    }
    public void setTag(String tag) {
        this.tag = tag;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getCapacity() {
        return capacity;
    }

    public void setCapacity(Integer capacity) {
        this.capacity = capacity;
    }

    public List<User> getUsers() {
            return users;
    }

    public void setUsers(List<User> users) {
        this.users = users;
    }

    public User getMentor() {
        return mentor;
    }

    public void setMentor(User mentor) {
        this.mentor = mentor;
    }

    public Lounge getLounge() {
        return lounge;
    }

    public void setLounge(Lounge lounge) {
        this.lounge = lounge;
    }

    public ActivityType getActivityType() {
        return activityType;
    }

    public void setActivityType(ActivityType activityType) {
        this.activityType = activityType;
    }

    public LocalDateTime getDateTime() {
        return dateTime;
    }

    public void setDateTime(LocalDateTime dateTime) {
        this.dateTime = dateTime;
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
}
