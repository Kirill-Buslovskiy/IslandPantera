package com.javarush.island.buslovskii.entity.animals;

import com.javarush.island.buslovskii.api.AnimalConfig;
import lombok.Getter;

import java.util.Collections;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.concurrent.ThreadLocalRandom;
import java.util.stream.Collectors;

public class Herd {
    private final List<Animal> animals;
    @Getter
    private final Class<? extends Animal> animalType;

    public Herd(Class<? extends Animal> animalType) {
        this.animalType = animalType;
        this.animals = new CopyOnWriteArrayList<>();
    }

    public boolean addAnimal(Animal animal) {
        if (canAcceptMore()) {
            animals.add(animal);
            return true;
        }
        return false;
    }

    public boolean removeAnimal(Animal animal) {
        return animals.remove(animal);
    }

    public int getTotalCount() {
        return animals.size();
    }

    public int getAliveCount() {
        return (int) animals.stream().filter(Animal::isAlive).count();
    }

    public Animal getRandomAliveAnimal() {
        List<Animal> aliveAnimals = animals.stream()
                .filter(Animal::isAlive)
                .collect(Collectors.toList());
        return aliveAnimals.isEmpty() ? null :
                aliveAnimals.get(ThreadLocalRandom.current().nextInt(aliveAnimals.size()));
    }

    public List<Animal> getAnimals() {
        return Collections.unmodifiableList(animals);
    }

    public boolean canAcceptMore() {
        if (animals.isEmpty()) return true;
        AnimalConfig config = animalType.getAnnotation(AnimalConfig.class);
        return config != null && getAliveCount() < config.maxPerCell();
    }

    public void cleanDeadAnimals() {
        animals.removeIf(animal -> !animal.isAlive());
    }
}