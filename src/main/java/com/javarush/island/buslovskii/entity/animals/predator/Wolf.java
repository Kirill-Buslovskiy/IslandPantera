package com.javarush.island.buslovskii.entity.animals.predator;

import com.javarush.island.buslovskii.api.OrganismSettings;


@OrganismSettings(name = "Волк", icon = "🐺", maxWeight = 50, maxInCell = 30, maxSpeed = 3, maxFood = 8, flockSize = 10)
public class Wolf extends Predator {

    public Wolf(String name, String icon, double maxWeight, int maxInCell, int maxSpeed, double maxFood, int flockSize) {
        super(name, icon, maxWeight, maxInCell, maxSpeed, maxFood, flockSize);
    }
}
