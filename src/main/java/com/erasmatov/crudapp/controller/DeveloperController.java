package com.erasmatov.crudapp.controller;

import com.erasmatov.crudapp.model.Developer;
import com.erasmatov.crudapp.model.Skill;
import com.erasmatov.crudapp.model.Specialty;
import com.erasmatov.crudapp.model.Status;
import com.erasmatov.crudapp.repository.DeveloperRepository;
import com.erasmatov.crudapp.repository.gson.GsonDeveloperRepositoryImpl;

import java.util.List;

public class DeveloperController {
    private final DeveloperRepository developerRepository = new GsonDeveloperRepositoryImpl();

    public Developer createDeveloper(String firstName, String lastName, List<Skill> skills, Specialty specialty) {
        Developer developer = new Developer();
        developer.setFirstName(firstName);
        developer.setLastName(lastName);
        developer.setSkills(skills);
        developer.setSpecialty(specialty);
        developer.setStatus(Status.ACTIVE);
        return developerRepository.save(developer);
    }

    public List<Developer> getDevelopers(){
        return developerRepository.getAll();
    }

}
