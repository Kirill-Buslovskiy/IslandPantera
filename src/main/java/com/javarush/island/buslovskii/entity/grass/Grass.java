package com.javarush.island.buslovskii.entity.grass;

import com.javarush.island.buslovskii.api.IslandEntity;
import lombok.Getter;

public class Grass implements IslandEntity {
    @Getter
    private double mass;
    private boolean alive;
    private static final double MAX_MASS = 200.0;

    public Grass(double mass) {
        this.mass = Math.min(mass, MAX_MASS);
        this.alive = true;
    }

    public void consume(double amount) {
        this.mass = Math.max(0, this.mass - amount);
        if (this.mass <= 0) alive = false;
    }
    public void grow(double amount) {
        this.mass = Math.min(this.mass + amount, MAX_MASS);
        if (this.mass > 0) alive = true;
    }

    @Override public boolean isAlive() { return alive && mass > 0; }
    @Override public void die() { this.alive = false; }
    @Override public double getWeight() { return mass; }
}