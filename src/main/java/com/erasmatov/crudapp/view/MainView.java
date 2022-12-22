package com.erasmatov.crudapp.view;

import java.util.Scanner;

public class MainView {
    private final Scanner scanner = new Scanner(System.in);
    private final SkillView skillView = new SkillView();
    private final DeveloperView developerView = new DeveloperView();
    private final SpecialtyView specialtyView = new SpecialtyView();

    public void showMainMenu() {

        System.out.println("Пользователь, выбери действие: "
                + "\n" + "1. Создание"
                + "\n" + "2. Получение"
                + "\n" + "3. Редактирование"
                + "\n" + "4. Удаление");
        System.out.print("Введи номер действия > ");
        Integer choice = scanner.nextInt();
        System.out.println();

        switch (choice) {
            case 1:
                showCreateMenu();
                break;
            case 2:
                showGetMenu();
                break;
            default:
                System.out.println("Введи доступное действие...");
        }
    }

    private void showCreateMenu() {
        System.out.println("Создание: "
                + "\n" + "1. Developer"
                + "\n" + "2. Skill"
                + "\n" + "3. Specialty");

        System.out.print("Введи номер действия > ");
        Integer choice = scanner.nextInt();

        switch (choice) {
            case 1:
                developerView.createDeveloper();
                break;
            case 2:
                skillView.createSkill();
                break;
            case 3:
                specialtyView.createSpecialty();
                break;
            default:
                System.out.println("Введи доступное действие...");
        }
    }

    private void showGetMenu(){
        System.out.println("Получение: "
                + "\n" + "1. Developer"
                + "\n" + "2. Skill"
                + "\n" + "3. Specialty");

        System.out.print("Введи номер действия > ");
        Integer choice = scanner.nextInt();

        switch (choice) {
            case 1:
                developerView.getDevelopers();
                break;
            case 2:
                skillView.getSkills();
                break;
            case 3:
                specialtyView.getSpecialties();
                break;
            default:
                System.out.print("Введи доступное действие...");
        }
    }

}
