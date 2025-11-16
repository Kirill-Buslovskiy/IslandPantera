package com.javarush.island.buslovskii.entity.animals.herbivore;

import com.javarush.island.buslovskii.api.AnimalConfig;

@AnimalConfig(weight = 2, maxPerCell = 150, maxSpeed = 2, foodRequired = 0.45)
public class Rabbit extends Herbivore {
    @Override
    protected double getReproductionProbability() {
        return 0.4;
    }

    @Override
    protected double getPlantEatingProbability() {
        return 0.9;
    }
}