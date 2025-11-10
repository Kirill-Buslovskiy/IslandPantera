package com.javarush.island.buslovskii.entity.animals.predator;

import com.javarush.island.buslovskii.entity.Animal;

public abstract class Predator extends Animal {
    public Predator(String name, String icon, double maxWeight, int maxInCell, int maxSpeed, double maxFood, int flockSize) {
        super(name, icon, maxWeight, maxInCell, maxSpeed, maxFood, flockSize);
    }
}
