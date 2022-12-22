package com.erasmatov.crudapp.view;

import com.erasmatov.crudapp.controller.SkillController;
import com.erasmatov.crudapp.model.Skill;

import java.util.Scanner;

public class SkillView {
    private final SkillController skillController = new SkillController();
    private final Scanner scanner = new Scanner(System.in);

    public void createSkill() {
        System.out.print("\n" + "Введи название навыка > ");
        String name = scanner.nextLine();
        Skill createdSkill = skillController.createSkill(name);
        System.out.print("Навык создан: " + "\n" + createdSkill);
    }

    public void getSkills(){
        System.out.print("\n" + skillController.getSkills());
    }

}
