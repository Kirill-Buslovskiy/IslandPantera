package com.javarush.island.buslovskii.entity.animals.herbivore;

import com.javarush.island.buslovskii.entity.Animal;

public abstract class Herbivore extends Animal {
    public Herbivore(String name, String icon, double maxWeight, int maxInCell, int maxSpeed, double maxFood, int flockSize) {
        super(name, icon, maxWeight, maxInCell, maxSpeed, maxFood, flockSize);
    }
}
