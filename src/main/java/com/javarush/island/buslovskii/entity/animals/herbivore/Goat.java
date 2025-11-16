package com.javarush.island.buslovskii.entity.animals.herbivore;

import com.javarush.island.buslovskii.api.AnimalConfig;

@AnimalConfig(weight = 60, maxPerCell = 140, maxSpeed = 3, foodRequired = 10)
public class Goat extends Herbivore {
    @Override
    protected double getReproductionProbability() {
        return 0.25;
    }
}