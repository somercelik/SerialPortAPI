package com.appsakademi.kantar.rest;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class WeightDataController {
    private final WeightDataRepository repository;

    WeightDataController(WeightDataRepository repository){
        this.repository = repository;
    }

    @GetMapping("/kantar")
    List<WeightData> all(){
        return repository.findAll();
    }
    @PostMapping("/kantar/")
    WeightData updateData(@RequestBody WeightData newWeight){
        return repository.save(WeightData.getInstance(newWeight));
    }
}
