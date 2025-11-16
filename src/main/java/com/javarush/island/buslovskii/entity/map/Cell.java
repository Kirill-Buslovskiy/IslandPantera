package com.javarush.island.buslovskii.entity.map;

import com.javarush.island.buslovskii.entity.animals.Animal;
import com.javarush.island.buslovskii.entity.animals.Herd;
import com.javarush.island.buslovskii.entity.grass.Grass;
import lombok.Getter;

import java.util.Collections;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ThreadLocalRandom;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class Cell {
    @Getter
    private final int x;
    @Getter
    private final int y;
    @Getter
    private GameMap gameMap;
    private Map<Class<? extends Animal>, Herd> herds;
    private Grass grass;
    private Lock locationLock;

    public Cell(int x, int y, GameMap gameMap) {
        this.x = x;
        this.y = y;
        this.gameMap = gameMap;
        this.herds = new ConcurrentHashMap<>();
        this.grass = new Grass(ThreadLocalRandom.current().nextDouble(50, 150));
        this.locationLock = new ReentrantLock(true);
    }

    public boolean addAnimal(Animal animal) {
        locationLock.lock();
        try {
            Class<? extends Animal> type = animal.getClass();
            Herd herd = herds.computeIfAbsent(type, k -> new Herd(type));
            if (herd.addAnimal(animal)) {
                animal.setCurrentCell(this);
                return true;
            }
            return false;
        } finally {
            locationLock.unlock();
        }
    }

    public boolean removeAnimal(Animal animal) {
        locationLock.lock();
        try {
            Herd herd = herds.get(animal.getClass());
            if (herd != null) {
                boolean removed = herd.removeAnimal(animal);
                if (removed && herd.getTotalCount() == 0) {
                    herds.remove(animal.getClass());
                }
                return removed;
            }
            return false;
        } finally {
            locationLock.unlock();
        }
    }

    public Herd getHerd(Class<? extends Animal> animalType) {
        return herds.get(animalType);
    }

    public Map<Class<? extends Animal>, Herd> getHerds() {
        return Collections.unmodifiableMap(herds);
    }

    public double getPlantMass() {
        return grass.getMass();
    }

    public void consumePlants(double amount) {
        locationLock.lock();
        try {
            grass.consume(amount);
        } finally {
            locationLock.unlock();
        }
    }

    public void growPlants(double amount) {
        locationLock.lock();
        try {
            grass.grow(amount);
        } finally {
            locationLock.unlock();
        }
    }

    public Lock getLock() {
        return locationLock;
    }

    public void cleanDeadAnimals() {
        locationLock.lock();
        try {
            for (Herd herd : herds.values()) {
                herd.cleanDeadAnimals();
            }
            herds.entrySet().removeIf(entry -> entry.getValue().getTotalCount() == 0);
        } finally {
            locationLock.unlock();
        }
    }

    public Class<? extends Animal> getDominantAnimalType() {
        locationLock.lock();
        try {
            Class<? extends Animal> dominantType = null;
            int maxCount = 0;

            for (Map.Entry<Class<? extends Animal>, Herd> entry : herds.entrySet()) {
                int count = entry.getValue().getAliveCount();
                if (count > maxCount) {
                    maxCount = count;
                    dominantType = entry.getKey();
                }
            }
            return dominantType;
        } finally {
            locationLock.unlock();
        }
    }
}