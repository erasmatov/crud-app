package com.erasmatov.crudapp.controller;

import com.erasmatov.crudapp.model.Specialty;
import com.erasmatov.crudapp.model.Status;
import com.erasmatov.crudapp.repository.SpecialtyRepository;
import com.erasmatov.crudapp.repository.gson.GsonSpecialtyRepositoryImpl;

import java.util.List;

public class SpecialtyController {
    private final SpecialtyRepository specialtyRepository = new GsonSpecialtyRepositoryImpl();

    public Specialty createSpecialty(String name){
        Specialty specialty = new Specialty();
        specialty.setName(name);
        specialty.setStatus(Status.ACTIVE);
        return specialtyRepository.save(specialty);
    }

    public List<Specialty> getSpecialties(){
        return specialtyRepository.getAll();
    }
}
