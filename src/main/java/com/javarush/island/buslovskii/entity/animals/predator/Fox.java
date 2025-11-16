package com.javarush.island.buslovskii.entity.animals.predator;

import com.javarush.island.buslovskii.api.AnimalConfig;
import com.javarush.island.buslovskii.entity.animals.herbivore.*;
import com.javarush.island.buslovskii.entity.animals.omnivore.*;

import java.util.HashMap;
import java.util.Map;

@AnimalConfig(weight = 8, maxPerCell = 30, maxSpeed = 2, foodRequired = 2)
public class Fox extends Predator {
    @Override
    public Map<Class<?>, Double> getEatingChances() {
        Map<Class<?>, Double> chances = new HashMap<>();
        chances.put(Rabbit.class, 0.7);
        chances.put(Mouse.class, 0.9);
        chances.put(Duck.class, 0.6);
        chances.put(Caterpillar.class, 0.4);
        return chances;
    }

    @Override
    protected double getReproductionProbability() {
        return 0.3;
    }
}