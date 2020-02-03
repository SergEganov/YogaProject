package com.example.YogaProject.service;

import com.example.YogaProject.domain.Lounge;
import com.example.YogaProject.repos.LoungeRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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
}
