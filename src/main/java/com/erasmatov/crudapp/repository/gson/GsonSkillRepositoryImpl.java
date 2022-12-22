package com.erasmatov.crudapp.repository.gson;

import com.erasmatov.crudapp.model.Skill;
import com.erasmatov.crudapp.model.Status;
import com.erasmatov.crudapp.repository.SkillRepository;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.io.IOException;
import java.lang.reflect.Type;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Objects;

public class GsonSkillRepositoryImpl implements SkillRepository {
    private final Gson GSON = new Gson();
    private final String SKILLS_FILE_PATH = "src/main/resources/skills.json";

    private List<Skill> getAllSkillsFromFile() {
        String json;
        try {
            json = new String(Files.readAllBytes(Paths.get(SKILLS_FILE_PATH)));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        Type targetClassType = new TypeToken<ArrayList<Skill>>() {
        }.getType();
        return new Gson().fromJson(json, targetClassType);
    }

    private void writeSkillsToFile(List<Skill> skills) {
        String json = GSON.toJson(skills);
        try {
            Files.write(Paths.get(SKILLS_FILE_PATH), json.getBytes());
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    private Integer generateNewId(List<Skill> skills) {
        Skill skillWithMaxId = skills.stream()
                .max(Comparator.comparing(Skill::getId))
                .orElse(null);
        return Objects.nonNull(skillWithMaxId) ? skillWithMaxId.getId() + 1 : 1;
    }

    @Override
    public List<Skill> getAll() {
        return getAllSkillsFromFile();
    }

    @Override
    public Skill getById(Integer id) {
        return getAllSkillsFromFile().stream()
                .filter(s -> s.getId().equals(id))
                .findFirst()
                .orElse(null);
    }

    @Override
    public void deleteById(Integer id) {
        List<Skill> currentSkillsInFile = getAllSkillsFromFile();
        Objects.requireNonNull(currentSkillsInFile.stream()
                        .filter(s -> s.getId().equals(id))
                        .findFirst().orElse(null))
                .setStatus(Status.DELETED);
        writeSkillsToFile(currentSkillsInFile);
    }

    @Override
    public Skill save(Skill skill) {
        List<Skill> currentSkillsInFile = getAllSkillsFromFile();
        skill.setId(generateNewId(currentSkillsInFile));
        currentSkillsInFile.add(skill);
        writeSkillsToFile(currentSkillsInFile);
        return skill;
    }

    @Override
    public Skill updated(Skill skill) {
        List<Skill> currentSkillsInFile = getAllSkillsFromFile();
        Skill currentSkill = currentSkillsInFile.stream()
                .filter(s -> s.getId().equals(skill.getId()))
                .findFirst()
                .orElse(skill);
        currentSkillsInFile.remove(currentSkill);
        currentSkill.setName(skill.getName());
        currentSkill.setStatus(skill.getStatus());
        currentSkillsInFile.add(currentSkill);
        writeSkillsToFile(currentSkillsInFile);
        return skill;
    }
}
