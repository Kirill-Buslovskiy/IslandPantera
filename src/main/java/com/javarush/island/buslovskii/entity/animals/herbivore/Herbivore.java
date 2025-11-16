package com.javarush.island.buslovskii.entity.animals.herbivore;

import com.javarush.island.buslovskii.entity.animals.Animal;
import com.javarush.island.buslovskii.entity.grass.Grass;
import com.javarush.island.buslovskii.entity.map.Cell;

import java.util.Collections;
import java.util.Map;
import java.util.concurrent.ThreadLocalRandom;

public abstract class Herbivore extends Animal {
    @Override
    public boolean eat() {
        if (!alive || satiety >= foodRequired) return false;
        Cell cell = currentCell;
        if (cell == null || cell.getPlantMass() <= 0) return false;

        double eatAmount = Math.min(foodRequired - satiety, cell.getPlantMass());
        if (eatAmount > 0 && ThreadLocalRandom.current().nextDouble() < getPlantEatingProbability()) {
            cell.consumePlants(eatAmount);
            satiety += eatAmount;
            return true;
        }
        return false;
    }

    @Override
    public Animal reproduce(Animal partner) {
        if (partner == null || !partner.isAlive() || partner.getClass() != this.getClass())
            return null;
        if (satiety > foodRequired * 0.6 && partner.getSatiety() > partner.getFoodRequired() * 0.6 && //getFood? or without get?
                ThreadLocalRandom.current().nextDouble() < getReproductionProbability()) {
            try {
                return this.getClass().getDeclaredConstructor().newInstance();
            } catch (Exception e) {
                return null;
            }
        }
        return null;
    }

    @Override
    public Map<Class<?>, Double> getEatingChances() {
        return Collections.singletonMap(Grass.class, 1.0);
    }

    protected double getPlantEatingProbability() {
        return 0.8;
    }

    protected double getReproductionProbability() {
        return 0.3;
    }
}
