package com.javarush.island.buslovskii.entity.animals.predator;

import com.javarush.island.buslovskii.api.AnimalConfig;
import com.javarush.island.buslovskii.entity.animals.herbivore.*;
import com.javarush.island.buslovskii.entity.animals.omnivore.Boar;
import com.javarush.island.buslovskii.entity.animals.omnivore.Mouse;

import java.util.HashMap;
import java.util.Map;

@AnimalConfig(weight = 500, maxPerCell = 5, maxSpeed = 2, foodRequired = 80)
public class Bear extends Predator {
    @Override
    public Map<Class<?>, Double> getEatingChances() {
        Map<Class<?>, Double> chances = new HashMap<>();
        chances.put(Horse.class, 0.4);
        chances.put(Deer.class, 0.8);
        chances.put(Rabbit.class, 0.8);
        chances.put(Mouse.class, 0.9);
        chances.put(Goat.class, 0.7);
        chances.put(Sheep.class, 0.7);
        chances.put(Boar.class, 0.5);
        chances.put(Buffalo.class, 0.2);
        chances.put(Snake.class, 0.8);
        chances.put(Fox.class, 0.1);
        return chances;
    }

    @Override
    protected double getReproductionProbability() {
        return 0.1;
    }
}