package com.erasmatov.crudapp.view;

import com.erasmatov.crudapp.controller.DeveloperController;
import com.erasmatov.crudapp.controller.SkillController;
import com.erasmatov.crudapp.controller.SpecialtyController;
import com.erasmatov.crudapp.model.Developer;
import com.erasmatov.crudapp.model.Skill;
import com.erasmatov.crudapp.model.Specialty;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.Scanner;

public class DeveloperView {
    private final DeveloperController developerController = new DeveloperController();
    private final SpecialtyController specialtyController = new SpecialtyController();
    private final SkillController skillController = new SkillController();

    private final Scanner scanner = new Scanner(System.in);

    public void getDevelopers() {
        System.out.print("\n" + developerController.getDevelopers());
    }

    public void createDeveloper() {

        System.out.print("\n" + "Введи имя > ");
        String firstName = scanner.nextLine();
        System.out.print("\n" + "Введи фамилию > ");
        String lastName = scanner.nextLine();
        System.out.println();
        List<Skill> skills = getSkillForDev();
        Specialty specialty = getSpecialtyForDev();
        Developer createDeveloper = developerController.createDeveloper(firstName, lastName, skills, specialty);
        System.out.print("\n" + "Создан: " + "\n" + createDeveloper);
    }

    private List<Skill> getSkillForDev() {
        List<Skill> skillList = skillController.getSkills();
        List<Skill> currentSkills = new ArrayList<>();
        System.out.println("\n" + "Выбери навыки по одному: ");
        System.out.println(skillList);
        System.out.println();

        while (true) {
            System.out.print("Выбери навык по id или введи 0 для завершения > ");
            Integer input = scanner.nextInt();

            if (input == 0) {
                System.out.println("Завершено."
                        + "\n" + currentSkills);
                break;
            }
            Optional<Skill> optionalSkill = skillList.stream().filter(s -> s.getId().equals(input)).findFirst();
            if (optionalSkill.isPresent()) {
                Skill skill = optionalSkill.get();
                currentSkills.add(skill);
                System.out.println("\n" + currentSkills);
            }
            if (optionalSkill.isEmpty()) {
                System.out.println("Введи доступный навык...");
            }
        }
        return currentSkills;
    }

    private Specialty getSpecialtyForDev() {
        List<Specialty> specialties = specialtyController.getSpecialties();
        System.out.println();
        System.out.println(specialties);
        System.out.print("Выбери специальность по id > ");
        Integer input = scanner.nextInt();
        return specialties.stream()
                .filter(s -> s.getId().equals(input))
                .findFirst().orElse(null);
    }

}