package com.company;


import java.awt.*;
import java.util.Arrays;
import java.util.Random;

public class Main {

    public static int bossHealth = 1500;
    public static int bossDamage = 50;
    public static String bossDefence = "";
    public static int[] heroesHealth = {270, 260, 250, 240, 250, 100, 500};
    public static int[] heroesDamage = {25, 15, 20, 30, 20, 50, 5};
    public static String[] heroesAttackType = {
            "Physical", "Magical", "Kinetic", "Archer", "Medic", "Lucky", "Golem"};
    public static int roundCounter = 0;

    public static void main(String[] args) {
        printStatistics();
        while (!isGameOver()) {
            round();
            MedicHeals();
        }
    }

    public static void changeDefence() {
        Random r = new Random();
        int randomIndex = r.nextInt(heroesAttackType.length); // 0,1,2,3
        bossDefence = heroesAttackType[randomIndex];
        System.out.println("Boss choose: " + bossDefence);

    }

    public static boolean isGameOver() {
        if (bossHealth <= 0) {
            System.out.println("Heroes won!!!");
            return true;
        }
        boolean allHeroesDead = true;
        for (int i = 0; i < heroesHealth.length; i++) {
            if (heroesHealth[i] > 0) {
                allHeroesDead = false;
                break;
            }
        }
        if (allHeroesDead) {
            System.out.println("Boss won!!!");
        }
        return allHeroesDead;
    }

    public static void round() {
        if (bossHealth > 0) {
            changeDefence();
            bossHits();
        }
        heroesHit();
        printStatistics();
    }

    public static void bossHits() {
        if (heroesHealth[6] > 0) {                                                                                      // Poluchenie urona Golemom
            heroesHealth[6] -= bossDamage + bossDamage / 5 * heroesHealth.length - 1;
            if (heroesHealth[6] < 0) {
                heroesHealth[6] = 0;
            }
            System.out.println("Golem absorbed: " + (bossDamage + bossDamage / 5 * heroesHealth.length - 1));
            System.out.println("Golem health: " + heroesHealth[6]);
        }

        Random random = new Random();
        int coefForLucky = random.nextInt(5);                                                                     //это относится к Lucky


        for (int i = 0; i < heroesDamage.length - 1; i++) {
            if (heroesHealth[6] > 0) {
                if (heroesHealth[i] - (bossDamage - bossDamage / 5) < 0) {
                    heroesHealth[i] = 0;
                } else {
                    heroesHealth[i] = heroesHealth[i] - (bossDamage - bossDamage / 5);
                    if (coefForLucky == 4) {
                        heroesHealth[5] += (bossDamage - bossDamage / 5);
                    }
                }
            }
            else {
                if (heroesHealth[i] - bossDamage < 0) {                                                                 // Posle smerti Golema
                    heroesHealth[i] = 0;
                } else {
                    heroesHealth[i] -= bossDamage;
                    if (coefForLucky == 4) {
                        heroesHealth[5] += bossDamage;
                    }
                }
            }
        }
        if (coefForLucky == 4) {                                                                                        // Вывод Lucky
            System.out.println("Lucky dodged!!!");
        }


    }

    public static void MedicHeals() {
        if (heroesHealth[4] > 0) {
            Random r1 = new Random();
            int coeff = r1.nextInt(2) + 2;
            for (int i = 0; i < heroesHealth.length - 1; i++) {
                if (heroesHealth[i] < 100 && heroesHealth[i] > 0) {
                    heroesHealth[i] += heroesDamage[4] * coeff;
                    System.out.println("Medic heals: " + heroesAttackType[i] + " on " + heroesDamage[4] * coeff);
                    break;
                }


            }


        }

    }


    public static void heroesHit() {

        for (int i = 0; i < heroesHealth.length - 1; i++) {
            if (heroesHealth[i] > 0 && bossHealth > 0) {
                if (heroesAttackType[i] == bossDefence) {
                    Random r = new Random();
                    int coeff = r.nextInt(7) + 1; //1,2,3,4,5
                    if (bossHealth - heroesDamage[i] * coeff < 0) {
                        bossHealth = 0;
                    } else {
                        bossHealth = bossHealth - heroesDamage[i] * coeff;
                    }
                    if (coeff > 1) {
                        System.out.println("Critical damage: "
                                + heroesDamage[i] * coeff);
                    }
                } else {
                    if (bossHealth - heroesDamage[i] < 0) {
                        bossHealth = 0;
                    } else {
                        bossHealth = bossHealth - heroesDamage[i];
                    }
                }
            }
        }
    }

    public static void printStatistics() {
        System.out.println("________________");
        System.out.println("Round ---- " + roundCounter);
        roundCounter++;
        System.out.println("Boss health: " + bossHealth);
        for (int i = 0; i < heroesHealth.length; i++) {
            System.out.println(heroesAttackType[i] + " health: "
                    + heroesHealth[i]);
        }
        System.out.println("________________");
    }


}