package com.javarush.island.buslovskii.services;

import com.javarush.island.buslovskii.entity.animals.herbivore.Horse;

import java.util.Map;

public class Setting {
    private Map<Class<?>, Double> eatingChance;

    public Setting chanceToEat() {
        eatingChance = Map.of(
                Horse.class, 0.1
        );
        return (Setting) eatingChance;
    }
}
