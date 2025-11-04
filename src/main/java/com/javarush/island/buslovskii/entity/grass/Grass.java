package com.javarush.island.buslovskii.entity.grass;

import com.javarush.island.buslovskii.entity.Organism;

public class Grass extends Organism {

    public Grass(String name, String icon, double maxWeight, int maxInCell, int maxSpeed, double maxFood, int flockSize) {
        super(name, icon, maxWeight, maxInCell, maxSpeed, maxFood, flockSize);
    }
}
