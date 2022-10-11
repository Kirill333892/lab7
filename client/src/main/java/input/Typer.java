package input;

import base.*;

import java.util.ResourceBundle;
import java.util.Scanner;

public class Typer {
    private final ResourceBundle RB = ResourceBundle.getBundle("typer");
    private final Scanner scanner = new Scanner(System.in);

    public String typeName() {
        System.out.print(RB.getString("name") + "\n>");
        String name = Validator.validateName(scanner.nextLine());
        if (name == null) {
            System.out.println(RB.getString("error") + ": " + RB.getString("emptyLine"));
            return typeName();
        }
        return name;
    }

    public Coordinates typeCoordinates() {
        System.out.print(RB.getString("coords") + "\n>");
        Coordinates coordinates = Validator.validateCoordinates(scanner.nextLine());
        if (coordinates == null) {
            System.out.println(RB.getString("error") + ":");
            System.out.println(RB.getString("needDotCom") + ";");
            System.out.println("y > " + City.getLimitation().get("coordinateY") + ".");
            return typeCoordinates();
        }
        return coordinates;
    }

    public Float typeArea() {
        System.out.print(RB.getString("area") + "\n>");
        Float area = Validator.validateArea(scanner.nextLine());
        if (area == null) {
            System.out.println(RB.getString("error") + ":");
            System.out.println("area > " + City.getLimitation().get("area") + ".");
            return typeArea();
        }
        return area;
    }

    public int typePopulation() {
        System.out.print(RB.getString("popul") + "\n>");
        Integer population = Validator.validatePopulation(scanner.nextLine());
        if (population == null) {
            System.out.println(RB.getString("error") + ":");
            System.out.println("population > " + City.getLimitation().get("population") + ".");
            return typePopulation();
        }
        return population;
    }

    public float typeMetersAboveSeaLevel() {
        System.out.print(RB.getString("masl") + "\n>");
        Float masl = Validator.validateMetersAboveSeaLevel(scanner.nextLine());
        if (masl == null) {
            System.out.println(RB.getString("error") + ".");
            return typeMetersAboveSeaLevel();
        }
        return masl;
    }

    public StandardOfLiving typeStandardOfLiving() {
        System.out.println(
                RB.getString("choose") + " " + RB.getString("number") + " (" + RB.getString("standard") + "):");
        int i = 1;
        for (StandardOfLiving standard : StandardOfLiving.values()) {
            System.out.println(i++ + ") " + standard.toString());
        }
        System.out.print(">");
        StandardOfLiving standardOfLiving = Validator.validateStandardOfLivingNum(scanner.nextLine());
        if (standardOfLiving == null) {
            System.out.println(RB.getString("error") + ".");
            return typeStandardOfLiving();
        }
        return standardOfLiving;
    }

    public Human typeHuman() {
        System.out
                .println(RB.getString("choose") + " " + RB.getString("number") + " (" + RB.getString("leader") + "):");

        int i = 1;
        for (Leaders king : Leaders.values()) {
            System.out.println(i++ + ") " + king.toString());
        }
        System.out.print(">");
        Leaders leader = Validator.validateLeaderNum(scanner.nextLine());
        if (leader == null) {
            System.out.println(RB.getString("error"));
            return typeHuman();
        }
        return Human.newHumanByLeader(leader);
    }

}
