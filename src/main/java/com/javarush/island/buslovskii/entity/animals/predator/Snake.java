package com.javarush.island.buslovskii.entity.animals.predator;

import com.javarush.island.buslovskii.api.AnimalConfig;
import com.javarush.island.buslovskii.entity.animals.herbivore.*;
import com.javarush.island.buslovskii.entity.animals.omnivore.*;

import java.util.HashMap;
import java.util.Map;

@AnimalConfig(weight = 15, maxPerCell = 30, maxSpeed = 1, foodRequired = 3)
public class Snake extends Predator {
    @Override
    public Map<Class<?>, Double> getEatingChances() {
        Map<Class<?>, Double> chances = new HashMap<>();
        chances.put(Rabbit.class, 0.2); chances.put(Mouse.class, 0.4);
        chances.put(Duck.class, 0.1); chances.put(Fox.class, 0.15);
        return chances;
    }

    @Override
    protected double getReproductionProbability() {
        return 0.15;
    }
}