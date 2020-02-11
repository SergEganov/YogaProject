package com.example.YogaProject.service;

import com.example.YogaProject.domain.Activity;
import com.example.YogaProject.domain.Role;
import com.example.YogaProject.domain.User;
import com.example.YogaProject.repos.UserRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;

import java.util.List;
import java.util.Set;

@Service
public class UserService implements UserDetailsService {

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

    public User findByLastName(String lastName) {
        return userRepo.findByLastName(lastName);
    }

    public Set<User> findByRolesContains(Role role){
        return userRepo.findByRolesContains(role);
    }

    public boolean userIsAdmin(User user) {
        return user != null && user.getRoles().contains(Role.ADMIN);
        }

    private Boolean checkUserExist(User user) {
        User userFromDb = userRepo.findByEmail(user.getEmail());
        return userFromDb != null;
    }

    public Boolean createUserValidation(User user, BindingResult bindingResult) {
        if (checkUserExist(user)) {
            bindingResult.addError(new FieldError(
                    "user",
                    "email",
                    "User with this email: " + user.getEmail() + " is exist!"));
            return false;
        }
        return true;
    }
    public Boolean updateUserValidation(User user, BindingResult bindingResult) {
        if(checkUserExist(user)){
            User userFromDb = userRepo.findByEmail(user.getEmail());
            if (!userFromDb.getId().equals(user.getId())) {
                bindingResult.addError(new FieldError(
                        "user",
                        "email",
                        "User with this email: " + user.getEmail() + " is exist!"));
                return false;
            }
        }
        return true;
    }

    public boolean checkUserAlreadySigned(User user, Activity activity, BindingResult bindingResult) {
        User userFromDb = userRepo.findByEmail(user.getEmail());
        if (activity.getUsers().contains(userFromDb)) {
            bindingResult.addError(new FieldError(
                    "user",
                    "email",
                    "Activity is already have user with this email: " + user.getEmail()));
            return false;
        }
        return true;
    }

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        //В шаблоне на реквест парам username. Если меняем - ничего не приходит
        return userRepo.findByEmail(email);
    }
}
