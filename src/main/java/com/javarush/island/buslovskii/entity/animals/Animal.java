package com.javarush.island.buslovskii.entity.animals;

import com.javarush.island.buslovskii.entity.Organism;

public abstract class Animal extends Organism {
    public Animal(String name, String icon, double maxWeight, int maxInCell, int maxSpeed, double maxFood, int flockSize) {
        super( name,  icon,  maxWeight,  maxInCell,  maxSpeed,  maxFood,  flockSize);
    }
}
