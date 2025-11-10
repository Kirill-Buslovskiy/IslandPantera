package com.javarush.island.buslovskii.entity.animals.herbivore;

import com.javarush.island.buslovskii.api.OrganismSettings;


@OrganismSettings(name = "Лошадь", icon = "🐎",maxWeight = 400, maxInCell = 20,maxSpeed = 4,maxFood = 60, flockSize = 5)
public class Horse extends Herbivore {

    public Horse(String name, String icon, double maxWeight, int maxInCell, int maxSpeed, double maxFood, int flockSize) {
        super(name, icon, maxWeight, maxInCell, maxSpeed, maxFood, flockSize);
    }

}
