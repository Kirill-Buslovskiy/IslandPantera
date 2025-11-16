package com.javarush.island.buslovskii.viev;

import com.javarush.island.buslovskii.entity.animals.Animal;
import com.javarush.island.buslovskii.entity.animals.herbivore.*;
import com.javarush.island.buslovskii.entity.animals.omnivore.*;
import com.javarush.island.buslovskii.entity.animals.predator.*;
import com.javarush.island.buslovskii.entity.map.Cell;
import com.javarush.island.buslovskii.entity.map.GameMap;

import java.util.HashMap;
import java.util.Map;

public class Visualizer {
    private static final Map<Class<? extends Animal>, String> SYMBOLS = new HashMap<>();

    static {
        SYMBOLS.put(Wolf.class, "🐺");
        SYMBOLS.put(Snake.class, "🐍");
        SYMBOLS.put(Fox.class, "🦊");
        SYMBOLS.put(Bear.class, "🐻");
        SYMBOLS.put(Eagle.class, "🦅");
        SYMBOLS.put(Horse.class, "🐎");
        SYMBOLS.put(Deer.class, "🦌");
        SYMBOLS.put(Rabbit.class, "🐇");
        SYMBOLS.put(Mouse.class, "🐁");
        SYMBOLS.put(Goat.class, "🐐");
        SYMBOLS.put(Sheep.class, "🐑");
        SYMBOLS.put(Boar.class, "🐗");
        SYMBOLS.put(Buffalo.class, "🐃");
        SYMBOLS.put(Duck.class, "🦆");
        SYMBOLS.put(Caterpillar.class, "🐛");
    }

    public static void visualize(GameMap gameMap) {
        int width = gameMap.getWidth();
        int height = gameMap.getHeight();

        System.out.println("\nКАРТА ОСТРОВА:");

        for (int y = 0; y < height; y++) {
            for (int x = 0; x < width; x++) {
                Cell location = gameMap.getCell(x, y);
                String symbol = getCellSymbol(location);
                System.out.print(symbol);
            }
            System.out.println();
        }

        System.out.println("\n 🐺-Волк, 🐍-Удав, 🦊-Лиса, 🐻-Медведь, 🦅-Орел");
        System.out.println("   🐎-Лошадь, 🦌-Олень, 🐇-Кролик, 🐁-Мышь, 🐐-Коза");
        System.out.println("   🐑-Овца, 🐗-Кабан, 🐃-Буйвол, 🦆-Утка, 🐛-Гусеница");
        System.out.println("   🌿-Растения, ·-Мало растений, _-Пусто");
    }

    private static String getCellSymbol(Cell cell) {
        Class<? extends Animal> dominantType = cell.getDominantAnimalType();
        if (dominantType != null) {
            String symbol = SYMBOLS.get(dominantType);
            return symbol != null ? symbol : "?";
        }

        if (cell.getPlantMass() > 50) return "🌿";
        if (cell.getPlantMass() > 10) return "·";

        return "_";
    }
}

