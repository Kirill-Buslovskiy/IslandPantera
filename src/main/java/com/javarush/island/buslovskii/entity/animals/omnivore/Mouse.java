package com.javarush.island.buslovskii.entity.animals.omnivore;

import com.javarush.island.buslovskii.api.AnimalConfig;
import com.javarush.island.buslovskii.entity.animals.herbivore.Caterpillar;

import java.util.HashMap;
import java.util.Map;

@AnimalConfig(weight = 0.05, maxPerCell = 500, maxSpeed = 1, foodRequired = 0.01)
public class Mouse extends Omnivore {
    @Override
    public Map<Class<?>, Double> getEatingChances() {
        Map<Class<?>, Double> chances = new HashMap<>();
        chances.put(Caterpillar.class, 0.9);
        return chances;
    }

    @Override
    protected double getReproductionProbability() {
        return 0.3;
    }

    @Override
    protected double getPlantEatingProbability() {
        return 0.8;
    }
}