package com.javarush.island.buslovskii.entity;

import com.javarush.island.buslovskii.api.Eatable;
import com.javarush.island.buslovskii.api.Movable;
import com.javarush.island.buslovskii.api.Reproducable;


public abstract class Animal extends Organism implements Eatable, Movable, Reproducable {

    public Animal(String name, String icon, double maxWeight, int maxInCell, int maxSpeed, double maxFood, int flockSize) {
        super(name, icon, maxWeight, maxInCell, maxSpeed, maxFood, flockSize);
    }

    @Override
    public boolean isAlive() {
        return true;
    }

    @Override
    public boolean eat() {

        return false;
    }

    @Override
    public boolean move() {

        return false;
    }

    @Override
    public boolean reproduce() {

        return false;
    }
}
