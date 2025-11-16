package com.javarush.island.buslovskii.controller;

import com.javarush.island.buslovskii.entity.animals.Animal;
import com.javarush.island.buslovskii.entity.animals.Herd;
import com.javarush.island.buslovskii.entity.map.Cell;
import com.javarush.island.buslovskii.entity.map.GameMap;
import com.javarush.island.buslovskii.viev.Visualizer;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.*;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Collectors;


public class Simulator {
    private final GameMap gameMap;
    private volatile boolean running;
    private final AtomicInteger currentTick;
    private final int availableProcessors;
    private final ScheduledExecutorService visualizationExecutor;

    private static final double PLANT_GROWTH_RATE = 15.0;
    private static final int MAX_TICKS = 1000;

    public Simulator(int width, int height) {
        this.gameMap = new GameMap(width, height);
        this.running = true;
        this.currentTick = new AtomicInteger(0);
        this.availableProcessors = Math.max(2, Runtime.getRuntime().availableProcessors());
        this.visualizationExecutor = Executors.newScheduledThreadPool(1);
    }

    public void startSimulation() {
        System.out.println("=== СИМУЛЯЦИЯ ОСТРОВА ===");

        startDynamicVisualization();

        ScheduledExecutorService scheduler = gameMap.getScheduler();

        ScheduledFuture<?> simulationTask = scheduler.scheduleAtFixedRate(
                this::runSimulationTick, 1, 2, TimeUnit.SECONDS);

        Runtime.getRuntime().addShutdownHook(new Thread(() -> {
            running = false;
            simulationTask.cancel(true);
            shutdown();
            System.out.println("\n=== СИМУЛЯЦИЯ ЗАВЕРШЕНА ===");
        }));

        try {
            while (running && currentTick.get() < MAX_TICKS) {
                Thread.sleep(2000);
            }
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        } finally {
            running = false;
            shutdown();
        }
    }

    private void startDynamicVisualization() {
        visualizationExecutor.scheduleAtFixedRate(() -> {
            if (running) {
                try {
                    Visualizer.visualize(gameMap);
                    gameMap.getStatistics().print(currentTick.get());
                } catch (Exception e) {
                    System.err.println("Ошибка визуализации: " + e.getMessage());
                }
            }
        }, 0, 1, TimeUnit.SECONDS);
    }

    private void runSimulationTick() {
        if (!running) return;

        int tick = currentTick.incrementAndGet();
        long startTime = System.currentTimeMillis();

        try {
            growPlantsPhase();
            feedingPhase();
            movementPhase();
            reproductionPhase();
            satietyPhase();
            cleanupPhase();
            updateStatistics(tick, startTime);

        } catch (Exception e) {
            System.err.println("Критическая ошибка в такте " + tick + ": " + e.getMessage());
            e.printStackTrace();
        }

        if (tick % 10 == 0) {
            System.out.println("Симуляция продолжается...");
        }
    }

    private void movementPhase() {
        ForkJoinTask<?> task = gameMap.getWorkerPool().submit(() -> {
            List<Animal> allAnimals = new ArrayList<>();
            for (Cell location : gameMap.getAllCells()) {
                location.getLock().lock();
                try {
                    for (Herd herd : location.getHerds().values()) {
                        allAnimals.addAll(herd.getAnimals().stream()
                                .filter(animal -> animal.isAlive() && animal.getMaxSpeed() > 0)
                                .collect(Collectors.toList()));
                    }
                } finally {
                    location.getLock().unlock();
                }
            }

            Collections.shuffle(allAnimals);
            allAnimals.parallelStream()
                    .limit(allAnimals.size() / 4)
                    .forEach(Animal::move);

        });
        task.join();
    }

    private void growPlantsPhase() {
        ForkJoinTask<?> task = gameMap.getWorkerPool().submit(() -> {
            gameMap.getAllCells().parallelStream().forEach(cell -> {
                cell.growPlants(PLANT_GROWTH_RATE);
            });
        });
        task.join();
    }

    private void feedingPhase() {
        ForkJoinTask<?> task = gameMap.getWorkerPool().submit(() -> {
            gameMap.getAllCells().parallelStream().forEach(cell -> {
                cell.getLock().lock();
                try {
                    for (Herd herd : cell.getHerds().values()) {
                        for (Animal animal : herd.getAnimals()) {
                            if (animal.isAlive()) {
                                animal.eat();
                            }
                        }
                    }
                } finally {
                    cell.getLock().unlock();
                }
            });
        });
        task.join();
    }

    private void reproductionPhase() {
        ForkJoinTask<?> task = gameMap.getWorkerPool().submit(() -> {
            gameMap.getAllCells().parallelStream().forEach(cell -> {
                cell.getLock().lock();
                try {
                    for (Herd herd : cell.getHerds().values()) {
                        List<Animal> aliveAnimals = herd.getAnimals().stream()
                                .filter(Animal::isAlive)
                                .collect(Collectors.toList());

                        if (aliveAnimals.size() >= 2) {
                            Collections.shuffle(aliveAnimals);
                            for (int i = 0; i < aliveAnimals.size() - 1; i += 2) {
                                Animal parent1 = aliveAnimals.get(i);
                                Animal parent2 = aliveAnimals.get(i + 1);

                                if (parent1.isAlive() && parent2.isAlive()) {
                                    Animal offspring = parent1.reproduce(parent2);
                                    if (offspring != null && herd.canAcceptMore()) {
                                        cell.addAnimal(offspring);
                                    }
                                }
                            }
                        }
                    }
                } finally {
                    cell.getLock().unlock();
                }
            });
        });
        task.join();
    }

    private void satietyPhase() {
        ForkJoinTask<?> task = gameMap.getWorkerPool().submit(() -> {
            gameMap.getAllCells().parallelStream().forEach(cell -> {
                cell.getLock().lock();
                try {
                    for (Herd herd : cell.getHerds().values()) {
                        for (Animal animal : herd.getAnimals()) {
                            if (animal.isAlive()) {
                                animal.decreaseSatiety();
                            }
                        }
                    }
                } finally {
                    cell.getLock().unlock();
                }
            });
        });
        task.join();
    }

    private void cleanupPhase() {
        ForkJoinTask<?> task = gameMap.getWorkerPool().submit(() -> {
            gameMap.getAllCells().parallelStream().forEach(Cell::cleanDeadAnimals);
        });
        task.join();
    }

    private void updateStatistics(int tick, long startTime) {
        gameMap.getStatistics().update(gameMap);

        long endTime = System.currentTimeMillis();
        System.out.printf("Такт %d завершен | Время: %dмс | Активные потоки: %d\n",
                tick, (endTime - startTime), gameMap.getWorkerPool().getActiveThreadCount());
    }

    public void shutdown() {
        running = false;
        gameMap.shutdown();
        visualizationExecutor.shutdown();
        try {
            if (!visualizationExecutor.awaitTermination(2, TimeUnit.SECONDS)) {
                visualizationExecutor.shutdownNow();
            }
        } catch (InterruptedException e) {
            visualizationExecutor.shutdownNow();
            Thread.currentThread().interrupt();
        }
    }
}
