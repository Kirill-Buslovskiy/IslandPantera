package com.javarush.island.buslovskii.services;


import com.javarush.island.buslovskii.entity.animals.Animal;
import com.javarush.island.buslovskii.entity.animals.Herd;
import com.javarush.island.buslovskii.entity.map.GameMap;

import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicLong;
import java.util.concurrent.atomic.AtomicReference;

public class Statistics {
    private final AtomicLong totalAnimals = new AtomicLong();
    private final ConcurrentHashMap<Class<? extends Animal>, AtomicLong> animalsByType = new ConcurrentHashMap<>();
    private final AtomicReference<Double> totalBiomass = new AtomicReference<>(0.0);
    private final AtomicReference<Double> totalGrassMass = new AtomicReference<>(0.0);

    public void update(GameMap gameMap) {
        totalAnimals.set(0);
        totalBiomass.set(0.0);
        totalGrassMass.set(0.0);
        animalsByType.clear();

        gameMap.getAllCells().forEach(location -> {
            totalGrassMass.accumulateAndGet(location.getPlantMass(), Double::sum);

            for (Herd herd : location.getHerds().values()) {
                int aliveCount = herd.getAliveCount();
                if (aliveCount > 0) {
                    totalAnimals.addAndGet(aliveCount);

                    double herdBiomass = herd.getAnimals().stream()
                            .filter(Animal::isAlive)
                            .mapToDouble(Animal::getWeight)
                            .sum();
                    totalBiomass.accumulateAndGet(herdBiomass, Double::sum);

                    animalsByType.computeIfAbsent(
                            herd.getAnimalType(),
                            k -> new AtomicLong()
                    ).addAndGet(aliveCount);
                }
            }
        });
    }

    public void print(int tick) {
        System.out.println("\n" + "═".repeat(50));
        System.out.printf(" ТАКТ %-4d ", tick);
        System.out.println("═".repeat(50));
        System.out.printf("Животные: %d | Растения: %.1f\n", totalAnimals.get(), totalGrassMass.get());

        animalsByType.entrySet().stream()
                .sorted((e1, e2) -> Long.compare(e2.getValue().get(), e1.getValue().get()))
                .limit(8)
                .forEach(entry -> {
                    String name = entry.getKey().getSimpleName();
                    long count = entry.getValue().get();
                    System.out.printf("%s: %d  ", name, count);
                });
        System.out.println("\n" + "═".repeat(50));
    }
}