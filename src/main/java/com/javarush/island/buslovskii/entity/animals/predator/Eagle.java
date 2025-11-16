package com.javarush.island.buslovskii.entity.animals.predator;

import com.javarush.island.buslovskii.api.AnimalConfig;
import com.javarush.island.buslovskii.entity.animals.herbivore.*;
import com.javarush.island.buslovskii.entity.animals.omnivore.*;

import java.util.HashMap;
import java.util.Map;

@AnimalConfig(weight = 6, maxPerCell = 20, maxSpeed = 3, foodRequired = 1)
public class Eagle extends Predator {
    @Override
    public Map<Class<?>, Double> getEatingChances() {
        Map<Class<?>, Double> chances = new HashMap<>();
        chances.put(Rabbit.class, 0.9);
        chances.put(Mouse.class, 0.9);
        chances.put(Duck.class, 0.8);
        chances.put(Fox.class, 0.1);
        return chances;
    }

    @Override
    protected double getReproductionProbability() {
        return 0.2;
    }
}