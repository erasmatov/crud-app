package com.erasmatov.crudapp.controller;

import com.erasmatov.crudapp.model.Skill;
import com.erasmatov.crudapp.model.Status;
import com.erasmatov.crudapp.repository.SkillRepository;
import com.erasmatov.crudapp.repository.gson.GsonSkillRepositoryImpl;

import java.util.List;

public class SkillController {
    private final SkillRepository skillRepository = new GsonSkillRepositoryImpl();

    public Skill createSkill(String name) {
        Skill skill = new Skill();
        skill.setName(name);
        skill.setStatus(Status.ACTIVE);
        return skillRepository.save(skill);
    }

    public List<Skill> getSkills() {
        return skillRepository.getAll();
    }

}
