package com.erasmatov.crudapp.view;

import com.erasmatov.crudapp.controller.SpecialtyController;
import com.erasmatov.crudapp.model.Specialty;

import java.util.List;
import java.util.Scanner;

public class SpecialtyView {
    public final SpecialtyController specialtyController = new SpecialtyController();
    Scanner scanner = new Scanner(System.in);

    public void createSpecialty() {
        System.out.print("\n" + "Введи название специальности > ");
        String name = scanner.nextLine();
        Specialty createdSkill = specialtyController.createSpecialty(name);
        System.out.print("Специальность создана: " + "\n" + createdSkill);
    }

    public void getSpecialties(){
        List<Specialty> specialties = specialtyController.getSpecialties();
        System.out.print("\n" + specialties);
    }
}
