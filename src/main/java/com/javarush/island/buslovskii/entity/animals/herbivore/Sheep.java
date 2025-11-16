package com.javarush.island.buslovskii.entity.animals.herbivore;

import com.javarush.island.buslovskii.api.AnimalConfig;

@AnimalConfig(weight = 70, maxPerCell = 140, maxSpeed = 3, foodRequired = 15)
public class Sheep extends Herbivore {
    @Override
    protected double getReproductionProbability() {
        return 0.25;
    }
}