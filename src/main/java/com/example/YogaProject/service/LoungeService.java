package com.example.YogaProject.service;

import com.example.YogaProject.domain.Lounge;
import com.example.YogaProject.repos.LoungeRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;

import java.time.LocalTime;
import java.util.List;

@Service
public class LoungeService {

    private final LoungeRepo loungeRepo;

    @Autowired
    public LoungeService(LoungeRepo loungeRepo) {
        this.loungeRepo = loungeRepo;
    }

    public List<Lounge> findAll() {
        return loungeRepo.findAll();
    }

    public void save(Lounge lounge) {
        loungeRepo.save(lounge);
    }

    public void deleteById(Long id) {
        loungeRepo.deleteById(id);
    }

    public Lounge findById(Long id) {
        return loungeRepo.getOne(id);
    }


    private Boolean checkLoungeExist(Lounge lounge) {
        Lounge loungeFromDb = loungeRepo.findByName(lounge.getName());
        return loungeFromDb != null;
    }

    public Boolean createLoungeValidation(Lounge lounge, BindingResult bindingResult){
        if (checkLoungeExist(lounge)) {
            bindingResult.addError(new FieldError(
                    "lounge",
                    "name",
                    "Lounge with name: " + lounge.getName() + " is exist!"));
        }
        return !bindingResult.hasErrors();
    }

    public Boolean updateLoungeValidation(Lounge lounge, BindingResult bindingResult) {
        if(checkLoungeExist(lounge)){
            Lounge loungeFromDb = loungeRepo.findByName(lounge.getName());
            if (!loungeFromDb.getId().equals(lounge.getId())) {
                bindingResult.addError(new FieldError(
                        "lounge",
                        "name",
                        "Lounge with name: " + lounge.getName() + " is exist!"));
            }
        }
        return !bindingResult.hasErrors();
    }
}
