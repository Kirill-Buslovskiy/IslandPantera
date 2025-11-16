package com.javarush.island.buslovskii.entity.animals;

import com.javarush.island.buslovskii.api.AnimalConfig;
import com.javarush.island.buslovskii.api.IslandEntity;
import com.javarush.island.buslovskii.entity.map.Cell;
import com.javarush.island.buslovskii.entity.map.GameMap;
import lombok.Getter;
import lombok.Setter;

import java.util.Map;
import java.util.concurrent.ThreadLocalRandom;


public abstract class Animal implements IslandEntity {
    @Getter
    protected double weight;
    @Getter
    protected int maxPerCell;
    @Getter
    protected int maxSpeed;
    @Getter
    protected double foodRequired;
    @Getter
    protected double satiety;
    @Getter
    protected boolean alive;
    @Getter
    @Setter
    protected Cell currentCell;

    public Animal() {
        initFromAnnotations();
        this.alive = true;
        this.satiety = foodRequired * 0.7;
    }

    private void initFromAnnotations() {
        AnimalConfig config = this.getClass().getAnnotation(AnimalConfig.class);
        if (config != null) {
            this.weight = config.weight();
            this.maxPerCell = config.maxPerCell();
            this.maxSpeed = config.maxSpeed();
            this.foodRequired = config.foodRequired();
        }
    }

    public abstract boolean eat();

    public abstract Animal reproduce(Animal partner);

    public abstract Map<Class<?>, Double> getEatingChances();

    public void decreaseSatiety() {
        if (alive) {
            satiety -= foodRequired * 0.03;
            if (satiety <= 0) alive = false;
        }
    }

    public int[] chooseDirection() {
        if (maxSpeed == 0) return new int[]{0, 0};
        int dx = ThreadLocalRandom.current().nextInt(-maxSpeed, maxSpeed + 1);
        int dy = ThreadLocalRandom.current().nextInt(-maxSpeed, maxSpeed + 1);
        return new int[]{dx, dy};
    }

    public boolean move() {
        if (!alive || maxSpeed == 0) return false;

        Cell currentCel = this.currentCell;
        if (currentCel == null) return false;

        GameMap island = currentCel.getGameMap();
        if (island == null) return false;

        int[] direction = chooseDirection();
        int newX = currentCel.getX() + direction[0];
        int newY = currentCel.getY() + direction[1];

        if (newX < 0 || newX >= island.getWidth() || newY < 0 || newY >= island.getHeight()) {
            return false;
        }

        Cell newCell = island.getCell(newX, newY);
        if (newCell == null || newCell == currentCel) {
            return false;
        }

        if (newCell.addAnimal(this)) {
            boolean removed = currentCel.removeAnimal(this);
            if (removed) {
                return true;
            } else {
                newCell.removeAnimal(this);
                return false;
            }
        }
        return false;
    }
    public void setSatiety(double satiety) {this.satiety = Math.min(satiety, foodRequired);}

    @Override
    public void die() {
        this.alive = false;
    }
}
