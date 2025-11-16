package com.javarush.island.buslovskii.entity.animals.herbivore;

import com.javarush.island.buslovskii.api.AnimalConfig;
import com.javarush.island.buslovskii.entity.animals.Animal;
import com.javarush.island.buslovskii.entity.grass.Grass;
import com.javarush.island.buslovskii.entity.map.Cell;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ThreadLocalRandom;

@AnimalConfig(weight = 0.01, maxPerCell = 1000, maxSpeed = 0, foodRequired = 0)
public class Caterpillar extends Animal {
    @Override
    public Map<Class<?>, Double> getEatingChances() {
        Map<Class<?>, Double> chances = new HashMap<>();
        chances.put(Grass.class, 1.0);
        return chances;
    }

    @Override
    public Animal reproduce(Animal partner) {
        return (partner instanceof Caterpillar && satiety > foodRequired * 0.6 &&
                ThreadLocalRandom.current().nextDouble() < 0.4) ? new Caterpillar() : null;
    }

    @Override
    public boolean eat() {
        if (!alive || satiety >= foodRequired) return false;
        Cell cell = currentCell;
        if (cell == null || cell.getPlantMass() <= 0) return false;

        double eatAmount = Math.min(foodRequired - satiety, cell.getPlantMass() * 0.01);
        if (eatAmount > 0 && ThreadLocalRandom.current().nextDouble() < 0.9) {
            cell.consumePlants(eatAmount);
            satiety += eatAmount;
            return true;
        }
        return false;
    }

    @Override
    public int[] chooseDirection() {
        return new int[]{0, 0};
    }
}