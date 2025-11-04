package com.javarush.island.buslovskii;

public class Map {
    private final Cell[][] cells;

    public Map(int rows, int cols) {
        this.cells = new Cell[rows][cols];
    }

    public Cell[][] getCells() {
        return cells;
    }

    public int getRows() {
        return cells.length;
    }

    public int getCols() {
        return cells[0].length;
    }

}
