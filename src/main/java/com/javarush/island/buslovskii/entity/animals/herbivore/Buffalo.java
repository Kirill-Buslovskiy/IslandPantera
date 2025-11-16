package com.javarush.island.buslovskii.entity.animals.herbivore;

import com.javarush.island.buslovskii.api.AnimalConfig;

@AnimalConfig(weight = 700, maxPerCell = 10, maxSpeed = 3, foodRequired = 100)
public  class Buffalo extends Herbivore {
    @Override
    protected double getReproductionProbability() {
        return 0.15;
    }

    @Override
    protected double getPlantEatingProbability() {
        return 0.7;
    }
}
