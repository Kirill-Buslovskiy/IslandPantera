package com.javarush.island.buslovskii.entity.animals.herbivore;

import com.javarush.island.buslovskii.entity.animals.Animal;

public class Horse extends Animal {

    public Horse(String name, String icon, double maxWeight, int maxInCell, int maxSpeed, double maxFood, int flockSize) {
        super(name, icon, maxWeight, maxInCell, maxSpeed, maxFood, flockSize);
    }
}
