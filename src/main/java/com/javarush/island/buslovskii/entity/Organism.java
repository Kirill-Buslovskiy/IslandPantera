package com.javarush.island.buslovskii.entity;

import com.javarush.island.buslovskii.api.Reproducable;

public abstract class Organism implements Reproducable, Cloneable {
    private final String name;
    private final String icon;
    private final double maxWeight;
    private final int maxInCell;
    private final int maxSpeed;
    private final double maxFood;
    private final int flockSize;

    public String getName() {
        return name;
    }

    public String getIcon() {
        return icon;
    }

    public double getMaxWeight() {
        return maxWeight;
    }

    public int getMaxInCell() {
        return maxInCell;
    }

    public int getMaxSpeed() {
        return maxSpeed;
    }

    public double getMaxFood() {
        return maxFood;
    }

    public int getFlockSize() {
        return flockSize;
    }

    public Organism(String name, String icon, double maxWeight, int maxInCell, int maxSpeed, double maxFood, int flockSize) {
        this.name = name;
        this.icon = icon;
        this.maxWeight = maxWeight;
        this.maxInCell = maxInCell;
        this.maxSpeed = maxSpeed;
        this.maxFood = maxFood;
        this.flockSize = flockSize;
    }

    public abstract boolean isAlive();
}
