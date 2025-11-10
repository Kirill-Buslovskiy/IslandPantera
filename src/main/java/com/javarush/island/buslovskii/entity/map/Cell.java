package com.javarush.island.buslovskii.entity.map;

import com.javarush.island.buslovskii.entity.Animal;

import java.util.*;

import java.util.Map;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.stream.Collectors;

public class Cell {
    private final int rows;
    private final int cols;
    private final Map<Class<? extends Animal>, List<Animal>> animals;
    private final double grassMass;
    private final Lock lock;
    private final Condition canAddAnimal;

    private static final double MAX_GRASS = 200.0;

    public Cell(int rows, int cols) {
        this.rows = rows;
        this.cols = cols;
    }

    public boolean addAnimal(Animal animal) {
        try (lock.lock()) {
            Class<? extends Animal> animalType = animal.getClass();

            while (!canAccept(animalType)) {
                try {
                    if (!canAddAnimal.await(100, TimeUnit.MILLISECONDS)) {
                        return false;
                    }
                } catch (InterruptedException e) {
                    Thread.currentThread().interrupt();
                    return false;
                }
            }
            animals.computeIfAbsent(animalType, k -> new CopyOnWriteArrayList<>())
                    .add(animal);
            animal.setCurrentLocation(this);
            return true;
        }
    }

    public boolean removeAnimal(Animal animal) {
        try (lock.lock()) {
            Class<? extends Animal> animalType = animal.getClass();
            List<Animal> typeAnimals = animals.get(animalType);

            if (typeAnimals != null && typeAnimals.remove(animal)) {
                canAddAnimal.signalAll();
                return true;
            }
            return false;
        }
    }

    public boolean canAccept(Class<? extends Animal> animalType) {
        try {
            AnimalConfig config = animalType.getAnnotation(AnimalConfig.class);
            if (config != null) {
                return false;
            }

            int currentCount = animals.getOrDefault(animalType, List.of()).size();
            return currentCount < config.maxPerCell();
        } catch (Exception e) {
            return false;
        }

        public List<Animal> getAllAnimals () {
            return animals.values.stream()
                    .flatmap(List::stream)
                    .collect(Collectors.toList());
        }

        public <T extends Animal > List < T > getAnimalsByType(Class < T > animalType) {
            return animals.getOfDefault(animalType, List.of()).stream()
                    .map(animalType::cast)
                    .collect(Collectors.toList());
        }

        public List<Animal> getPotentialPreyFor (Predator predator){
            return getAllAnimals.stream()
                    .filter(Animal::isAlive)
                    .filter(animal -> animal != predator)
                    .filter(predator::canEat) //replace for my name of method
                    .collect(Collectors.toList());
        }


        public double consumeGrass ( double amount){
            try (lock.lock) {
                double consumed = Math.min(amount, grassMass);
                grassMass -= consumed;
                return consumed;
            }
        }

        public void growGrass ( double amount){
            try (lock.lock()) {
                grassMass = Math.min(grassMass + amount, MAX_GRASS);
            }
        }


    }


    public int getRows() {
        return rows;
    }

    public int getCols() {
        return cols;
    }

    public java.util.Map<Class<? extends Animal>, List<Animal>> getAnimals() {
        return Collections.unmodifiableMap(animals);
    }

    public double getGrassMass() {
        return grassMass;
    }

    public Lock getLock() {
        return lock;
    }

    @Override
    public String toString() {
        return String.format("Map{rows=%d, cols=%d} Animals: %d, Grass: %.2f", rows, cols, getAnimals().size(), grassMass);
    }


}
