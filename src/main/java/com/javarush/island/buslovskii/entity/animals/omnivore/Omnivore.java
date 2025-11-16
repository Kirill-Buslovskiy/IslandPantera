package com.javarush.island.buslovskii.entity.animals.omnivore;

import com.javarush.island.buslovskii.entity.animals.Animal;
import com.javarush.island.buslovskii.entity.animals.Herd;
import com.javarush.island.buslovskii.entity.map.Cell;

import java.util.Map;
import java.util.concurrent.ThreadLocalRandom;

public abstract class Omnivore extends Animal {
    @Override
    public boolean eat() {
        if (!alive || satiety >= foodRequired) return false;
        Cell cell = currentCell;
        if (cell == null) return false;

        if (cell.getPlantMass() > 0 && ThreadLocalRandom.current().nextDouble() < getPlantEatingProbability()) {
            double eatAmount = Math.min(foodRequired - satiety, cell.getPlantMass());
            if (eatAmount > 0) {
                cell.consumePlants(eatAmount);
                satiety += eatAmount;
                return true;
            }
        }

        for (Herd herd : cell.getHerds().values()) {
            if (!Animal.class.isAssignableFrom(herd.getAnimalType()) ||
                    herd.getAnimalType() == this.getClass()) continue;

            Double chance = getEatingChances().get(herd.getAnimalType());
            if (chance != null && herd.getAliveCount() > 0 &&
                    ThreadLocalRandom.current().nextDouble() < chance) {
                Animal prey = herd.getRandomAliveAnimal();
                if (prey != null && prey.isAlive()) {
                    satiety = Math.min(satiety + prey.getWeight(), foodRequired);
                    prey.die();
                    return true;
                }
            }
        }
        return false;
    }

    @Override
    public Animal reproduce(Animal partner) {
        if (partner == null || !partner.isAlive() || partner.getClass() != this.getClass())
            return null;
        if (satiety > foodRequired * 0.6 && partner.getSatiety() > partner.getFoodRequired() * 0.6 && //replace foodRequired
                ThreadLocalRandom.current().nextDouble() < getReproductionProbability()) {
            try {
                return this.getClass().getDeclaredConstructor().newInstance();
            } catch (Exception e) {
                return null;
            }
        }
        return null;
    }

    protected double getPlantEatingProbability() {
        return 0.8;
    }

    protected double getReproductionProbability() {
        return 0.25;
    }
}
