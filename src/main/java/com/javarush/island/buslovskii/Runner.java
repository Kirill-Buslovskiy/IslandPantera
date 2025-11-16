package com.javarush.island.buslovskii;

import com.javarush.island.buslovskii.controller.Simulator;

public class Runner {
    private static final int ISLAND_WIDTH = 100;
    private static final int ISLAND_HEIGHT = 20;

    public static void main(String[] args) {
        try {
            Simulator simulator = new Simulator(ISLAND_WIDTH, ISLAND_HEIGHT);
            simulator.startSimulation();
        } catch (Exception e) {
            System.err.println("Ошибка: " + e.getMessage());
            e.printStackTrace();
        }
    }
}
