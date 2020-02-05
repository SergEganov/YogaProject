package com.example.YogaProject.service;

import com.example.YogaProject.domain.User;
import com.example.YogaProject.repos.UserRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;

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

    public List<User> findAll() {
        return userRepo.findAll();
    }

    public void saveUser(User user) {
        userRepo.save(user);
    }

    public void deleteById(Long id) {
        userRepo.deleteById(id);
    }

    public User findByEmail(String email) {
        return userRepo.findByEmail(email);
    }

    private Boolean checkUserExist(User user) {
        User userFromDb = userRepo.findByEmail(user.getEmail());
        return userFromDb != null;
    }

    public Boolean createValidation(User user, BindingResult bindingResult) {
        if (checkUserExist(user)) {
            bindingResult.addError(new FieldError(
                    "user",
                    "email",
                    "User with this email: " + user.getEmail() + " is exist!"));
        }
        return bindingResult.hasErrors();
    }
    public Boolean updateValidation(User user, BindingResult bindingResult) {
        if(checkUserExist(user)){
            User userFromDb = userRepo.findByEmail(user.getEmail());
            if (!userFromDb.getId().equals(user.getId())) {
                bindingResult.addError(new FieldError(
                        "user",
                        "email",
                        "User with this email: " + user.getEmail() + " is exist!"));
            }
        }
        return bindingResult.hasErrors();
    }
}
