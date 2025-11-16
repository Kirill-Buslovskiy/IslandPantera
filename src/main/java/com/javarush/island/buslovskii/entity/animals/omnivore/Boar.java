package com.javarush.island.buslovskii.entity.animals.omnivore;

import com.javarush.island.buslovskii.api.AnimalConfig;
import com.javarush.island.buslovskii.entity.animals.herbivore.Caterpillar;

import java.util.HashMap;
import java.util.Map;

@AnimalConfig(weight = 400, maxPerCell = 50, maxSpeed = 2, foodRequired = 50)
public class Boar extends Omnivore {
    @Override
    public Map<Class<?>, Double> getEatingChances() {
        Map<Class<?>, Double> chances = new HashMap<>();
        chances.put(Mouse.class, 0.5);
        chances.put(Caterpillar.class, 0.9);
        return chances;
    }

    @Override
    protected double getReproductionProbability() {
        return 0.2;
    }

    @Override
    protected double getPlantEatingProbability() {
        return 0.7;
    }
}