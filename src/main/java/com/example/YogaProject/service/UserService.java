package com.example.YogaProject.service;

import com.example.YogaProject.domain.User;
import com.example.YogaProject.repos.UserRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserService {

    private final UserRepo userRepo;

    @Autowired
    public UserService(UserRepo userRepo) {
        this.userRepo = userRepo;
    }

    public User findById(Long id) {
        return userRepo.getOne(id);
    }

    public List<User> findAll(){
        return userRepo.findAll();
    }

    public User saveUser(User user) {
        return userRepo.save(user);
    }

    public void deleteById(Long id) {
        userRepo.deleteById(id);
    }

    public User findByFirstNameAndLastName(String firstName, String lastName) {
        return userRepo.findByFirstNameAndLastName(firstName,lastName);
    }

    public User findByEmail(String email){
        return userRepo.findByEmail(email);
    }

    public List<User> findByLastName(String lastName) {
        return userRepo.findByLastName(lastName);
    }

}
