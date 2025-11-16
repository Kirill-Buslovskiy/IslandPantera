package com.javarush.island.buslovskii.entity.animals.herbivore;

import com.javarush.island.buslovskii.api.AnimalConfig;

@AnimalConfig(weight = 300, maxPerCell = 20, maxSpeed = 4, foodRequired = 50)
public class Deer extends Herbivore {
    @Override
    protected double getReproductionProbability() {
        return 0.25;
    }
}