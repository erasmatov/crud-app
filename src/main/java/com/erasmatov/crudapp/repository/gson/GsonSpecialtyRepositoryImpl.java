package com.erasmatov.crudapp.repository.gson;

import com.erasmatov.crudapp.model.Specialty;
import com.erasmatov.crudapp.model.Status;
import com.erasmatov.crudapp.repository.SpecialtyRepository;
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

public class GsonSpecialtyRepositoryImpl implements SpecialtyRepository {

    private final Gson GSON = new Gson();
    private final String SPECIALTIES_FILE_PATH = "src/main/resources/specialties.json";

    private List<Specialty> getAllSpecialtiesFromFile() {
        String json;
        try {
            json = new String(Files.readAllBytes(Paths.get(SPECIALTIES_FILE_PATH)));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        Type targetClassType = new TypeToken<ArrayList<Specialty>>() {
        }.getType();
        return new Gson().fromJson(json, targetClassType);
    }

    private void writeSpecialtiesToFile(List<Specialty> specialty) {
        String json = GSON.toJson(specialty);
        try {
            Files.write(Paths.get(SPECIALTIES_FILE_PATH), json.getBytes());
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    private Integer generateNewId(List<Specialty> specialty) {
        Specialty specialtyWithMaxId = specialty.stream()
                .max(Comparator.comparing(Specialty::getId))
                .orElse(null);
        return Objects.nonNull(specialtyWithMaxId) ? specialtyWithMaxId.getId() + 1 : 1;
    }

    @Override
    public List<Specialty> getAll() {
        return getAllSpecialtiesFromFile();
    }

    @Override
    public Specialty getById(Integer id) {
        return getAllSpecialtiesFromFile().stream()
                .filter(s -> s.getId().equals(id))
                .findFirst().orElse(null);
    }

    @Override
    public void deleteById(Integer id) {
        List<Specialty> currentSpecialtiesInFile = getAllSpecialtiesFromFile();
        Objects.requireNonNull(currentSpecialtiesInFile.stream()
                        .filter(s -> s.getId().equals(id)).findFirst().orElse(null))
                .setStatus(Status.DELETED);
        writeSpecialtiesToFile(currentSpecialtiesInFile);
    }

    @Override
    public Specialty save(Specialty specialty) {
        List<Specialty> currentSpecialtiesInFile = getAllSpecialtiesFromFile();
        specialty.setId(generateNewId(currentSpecialtiesInFile));
        currentSpecialtiesInFile.add(specialty);
        writeSpecialtiesToFile(currentSpecialtiesInFile);
        return specialty;
    }

    @Override
    public Specialty updated(Specialty specialty) {
        List<Specialty> currentSpecialtiesInFile = getAllSpecialtiesFromFile();
        Specialty currentSpecialty = currentSpecialtiesInFile.stream()
                .filter(s -> s.getId().equals(specialty.getId()))
                .findFirst().orElse(specialty);
        currentSpecialtiesInFile.remove(currentSpecialty);
        currentSpecialty.setName(specialty.getName());
        currentSpecialty.setStatus(specialty.getStatus());
        currentSpecialtiesInFile.add(currentSpecialty);
        writeSpecialtiesToFile(currentSpecialtiesInFile);
        return specialty;
    }
}
