package com.javarush.island.buslovskii.entity.map;

import com.javarush.island.buslovskii.entity.animals.Animal;
import com.javarush.island.buslovskii.entity.animals.herbivore.*;
import com.javarush.island.buslovskii.entity.animals.omnivore.*;
import com.javarush.island.buslovskii.entity.animals.predator.*;
import com.javarush.island.buslovskii.services.Statistics;
import lombok.Getter;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.concurrent.*;

public class GameMap {
    private final Cell[][] cells;
    @Getter
    private final int width;
    @Getter
    private final int height;
    @Getter
    private final Statistics statistics;
    @Getter
    private final ScheduledExecutorService scheduler;
    @Getter
    private final ForkJoinPool workerPool;

    public GameMap(int width, int height) {
        this.width = width;
        this.height = height;
        this.cells = new Cell[width][height];
        this.statistics = new Statistics();
        this.scheduler = Executors.newScheduledThreadPool(2);
        this.workerPool = new ForkJoinPool(Math.max(2, Runtime.getRuntime().availableProcessors() / 2));

        initializeLocations();
        populateInitialAnimals();
    }

    private void initializeLocations() {
        for (int x = 0; x < width; x++) {
            for (int y = 0; y < height; y++) {
                cells[x][y] = new Cell(x, y, this);
            }
        }
    }

    private void populateInitialAnimals() {
        java.util.Map<Class<? extends Animal>, Integer> initialCounts = new HashMap<>();
        initialCounts.put(Wolf.class, 15);
        initialCounts.put(Snake.class, 20);
        initialCounts.put(Fox.class, 25);
        initialCounts.put(Bear.class, 3);
        initialCounts.put(Eagle.class, 12);
        initialCounts.put(Horse.class, 25);
        initialCounts.put(Deer.class, 20);
        initialCounts.put(Rabbit.class, 80);
        initialCounts.put(Mouse.class, 100);
        initialCounts.put(Goat.class, 60);
        initialCounts.put(Sheep.class, 60);
        initialCounts.put(Boar.class, 30);
        initialCounts.put(Buffalo.class, 8);
        initialCounts.put(Duck.class, 80);
        initialCounts.put(Caterpillar.class, 150);

        for (java.util.Map.Entry<Class<? extends Animal>, Integer> entry : initialCounts.entrySet()) {
            for (int i = 0; i < entry.getValue(); i++) {
                try {
                    Animal animal = entry.getKey().getDeclaredConstructor().newInstance();
                    for (int attempt = 0; attempt < 5; attempt++) {
                        if (getRandomLocation().addAnimal(animal)) break;
                    }
                } catch (Exception e) {
                    System.err.println("Ошибка создания " + entry.getKey().getSimpleName());
                }
            }
        }
    }

    public Cell getCell(int x, int y) {
        if (x >= 0 && x < width && y >= 0 && y < height) {
            return cells[x][y];
        }
        return null;
    }

    public Cell getRandomLocation() {
        int x = ThreadLocalRandom.current().nextInt(width);
        int y = ThreadLocalRandom.current().nextInt(height);
        return cells[x][y];
    }

    public List<Cell> getAllCells() {
        List<Cell> allLocations = new ArrayList<>(width * height);
        for (int x = 0; x < width; x++) {
            for (int y = 0; y < height; y++) {
                allLocations.add(cells[x][y]);
            }
        }
        return allLocations;
    }

    public void shutdown() {
        scheduler.shutdown();
        workerPool.shutdown();
        try {
            if (!scheduler.awaitTermination(3, TimeUnit.SECONDS)) scheduler.shutdownNow();
            if (!workerPool.awaitTermination(3, TimeUnit.SECONDS)) workerPool.shutdownNow();
        } catch (InterruptedException e) {
            scheduler.shutdownNow();
            workerPool.shutdownNow();
            Thread.currentThread().interrupt();
        }
    }
}
