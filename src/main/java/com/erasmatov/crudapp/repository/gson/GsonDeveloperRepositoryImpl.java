package com.erasmatov.crudapp.repository.gson;

import com.erasmatov.crudapp.model.Developer;
import com.erasmatov.crudapp.model.Status;
import com.erasmatov.crudapp.repository.DeveloperRepository;
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

public class GsonDeveloperRepositoryImpl implements DeveloperRepository {

    private final Gson GSON = new Gson();
    private final String DEVELOPERS_FILE_PATH = "src/main/resources/developers.json";

    private List<Developer> getAllDevelopersFromFile() {
        String json;
        try {
            json = new String(Files.readAllBytes(Paths.get(DEVELOPERS_FILE_PATH)));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        Type targetClassType = new TypeToken<ArrayList<Developer>>() {
        }.getType();
        return new Gson().fromJson(json, targetClassType);
    }

    private void writeDevelopersToFile(List<Developer> developers) {
        String json = GSON.toJson(developers);
        try {
            Files.write(Paths.get(DEVELOPERS_FILE_PATH), json.getBytes());
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    private Integer generateNewId(List<Developer> developers) {
        Developer developerWithMaxId = developers.stream()
                .max(Comparator.comparing(Developer::getId))
                .orElse(null);
        return Objects.nonNull(developerWithMaxId) ? developerWithMaxId.getId() + 1 : 1;
    }

    @Override
    public List<Developer> getAll() {
        return getAllDevelopersFromFile();
    }

    @Override
    public Developer getById(Integer id) {
        return getAllDevelopersFromFile().stream()
                .filter(s -> s.getId().equals(id))
                .findFirst()
                .orElse(null);
    }

    @Override
    public void deleteById(Integer id) {
        List<Developer> currentDevelopersInFile = getAllDevelopersFromFile();
        Objects.requireNonNull(currentDevelopersInFile.stream()
                        .filter(s -> s.getId().equals(id))
                        .findFirst().orElse(null))
                .setStatus(Status.DELETED);
        writeDevelopersToFile(currentDevelopersInFile);
    }

    @Override
    public Developer save(Developer developer) {
        List<Developer> currentDevelopersInFile = getAllDevelopersFromFile();
        developer.setId(generateNewId(currentDevelopersInFile));
        currentDevelopersInFile.add(developer);
        writeDevelopersToFile(currentDevelopersInFile);
        return developer;
    }

    @Override
    public Developer updated(Developer developer) {
        List<Developer> currentDevelopersInFile = getAllDevelopersFromFile();
        Developer currentDeveloper = currentDevelopersInFile.stream()
                .filter(s -> s.getId().equals(developer.getId()))
                .findFirst()
                .orElse(developer);
        currentDevelopersInFile.remove(currentDeveloper);
        currentDeveloper.setFirstName(developer.getFirstName());
        currentDeveloper.setLastName(developer.getLastName());
        currentDeveloper.setStatus(developer.getStatus());
        currentDevelopersInFile.add(currentDeveloper);
        writeDevelopersToFile(currentDevelopersInFile);
        return developer;
    }
}
