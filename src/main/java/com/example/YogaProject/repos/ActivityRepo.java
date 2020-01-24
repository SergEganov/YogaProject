package com.example.YogaProject.repos;

import com.example.YogaProject.domain.Activity;
import org.springframework.data.repository.CrudRepository;

public interface ActivityRepo extends CrudRepository<Activity, Integer> {
}
