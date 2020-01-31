package com.example.YogaProject.service;

import com.example.YogaProject.domain.UserEntity;
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

    public UserEntity findById(Long id) {
        return userRepo.getOne(id);
    }

    public List<UserEntity> findAll(){
        return userRepo.findAll();
    }

    public UserEntity saveUser(UserEntity user) {
        return userRepo.save(user);
    }

    public void deleteById(Long id) {
        userRepo.deleteById(id);
    }

    public List<UserEntity> findByLastName(String lastName) {
        return userRepo.findByLastName(lastName);
    }

}
