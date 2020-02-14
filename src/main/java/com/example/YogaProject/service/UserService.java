package com.example.YogaProject.service;

import com.example.YogaProject.domain.Activity;
import com.example.YogaProject.domain.Role;
import com.example.YogaProject.domain.User;
import com.example.YogaProject.repos.UserRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;

import java.util.List;
import java.util.Set;

@Service
public class UserService implements UserDetailsService {

    private final BCryptPasswordEncoder bCryptPasswordEncoder;

    private final UserRepo userRepo;

    @Autowired
    public UserService(UserRepo userRepo, BCryptPasswordEncoder bCryptPasswordEncoder) {
        this.userRepo = userRepo;
        this.bCryptPasswordEncoder = bCryptPasswordEncoder;
    }

    public boolean isUsersExists() {
        return userRepo.isUsersExists();
    }

    public User findById(Long id) {
        return userRepo.findById(id).orElse(null);
    }

    public List<User> findAll() {
        return userRepo.findAll();
    }

    public void saveUser(User user) {
        if(!checkUserExist(user)) {
            user.getRoles().add(Role.ROLE_USER);
            user.setPassword(bCryptPasswordEncoder.encode(user.getPassword()));
            userRepo.save(user);
        } else {
            userRepo.save(user);
        }
    }

    public void deleteById(Long id) {
        userRepo.deleteById(id);
    }

    public User findByEmail(String email) {
        return userRepo.findByEmail(email);
    }

    public Set<User> findByRolesContains(Role role){
        return userRepo.findByRolesContains(role);
    }

    public boolean passwordConfirm(User user, BindingResult bindingResult) {
        if( !user.getPassword().equals(user.getPasswordConfirm())) {
            bindingResult.addError(new FieldError(
                    "user",
                    "passwordConfirm",
                    "Check password and password confirm - it needs to be equal!"));
            return false;
        }
        return true;
    }

    public boolean checkUserExist(User user) {
        User userFromDb = userRepo.findByEmail(user.getEmail());
        return userFromDb != null;
    }

    public boolean createUserValidation(User user, BindingResult bindingResult) {
        if (checkUserExist(user)) {
            bindingResult.addError(new FieldError(
                    "user",
                    "email",
                    "User with this email: " + user.getEmail() + " is exist!"));
            return false;
        }
        return true;
    }
    public boolean updateUserValidation(User user, BindingResult bindingResult) {
        User userFromDb = userRepo.findByEmail(user.getEmail());
        if (!userFromDb.getId().equals(user.getId())) {
            bindingResult.addError(new FieldError(
                    "user",
                    "email",
                    "User with this email: " + user.getEmail() + " is exist!"));
            return false;
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
        User user = userRepo.findByEmail(email);
        if(user == null) {
            throw new UsernameNotFoundException("User not found");
        }
        return user;
    }
}
