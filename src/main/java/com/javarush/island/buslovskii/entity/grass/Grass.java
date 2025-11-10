package com.javarush.island.buslovskii.entity.grass;

import com.javarush.island.buslovskii.api.OrganismSettings;
import com.javarush.island.buslovskii.entity.Organism;

@OrganismSettings(name = "Трава", icon = "🌾", maxWeight = 1, maxInCell = 200, maxSpeed = 0, maxFood = 0, flockSize = 20)
public class Grass extends Organism {

    public Grass(String name, String icon, double maxWeight, int maxInCell, int maxSpeed, double maxFood, int flockSize) {
        super(name, icon, maxWeight, maxInCell, maxSpeed, maxFood, flockSize);
    }
}
