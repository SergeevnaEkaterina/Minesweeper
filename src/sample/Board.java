package sample;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class Board {
    private int xCells;
    private int yCells;
    private int bombCount;
    private boolean end = false;
    private Element[][] grid;

    public Board(int xCells, int yCells, int bombCount) {
        this.xCells = xCells;
        this.yCells = yCells;
        this.bombCount = bombCount;
        grid = new Element[xCells][yCells];
    }

    public void createBoard() {  //создаем поле
        Random random1 = new Random();
        Random random2 = new Random();
        for (int x = 0; x < xCells; x++) {
            for (int y = 0; y < yCells; y++) {
                Element cell = new Element(x, y, false);
                grid[x][y] = cell;
            }
        }
        for (int i = 0; i < bombCount; i++) { //расставляем бомбы
            int x = random1.nextInt(xCells);
            int y = random2.nextInt(yCells);
            while (grid[x][y].getBomb()) {
                x = random1.nextInt(xCells);
                y = random2.nextInt(yCells);
            }
            grid[x][y].setBomb(true);
        }

        for (int x = 0; x < xCells; x++) { //считаем количество заминированных соседей
            for (int y = 0; y < yCells; y++) {
                Element cell = grid[x][y];
                if (cell.getBomb()) {
                    continue;
                }
                long amount = getNeighbors(cell).stream().filter(Element::getBomb).count();
                cell.setNearBombs(amount);
            }
        }
    }


    public List<Element> getNeighbors(Element cell) { //считаем соседей
        List<Element> neighbors = new ArrayList<>();
        int[] dif1 = {-1, 0, -1, -1, 0, -1, 1, 0, 0, 1, -1, 1};
        int[] dif2 = {-1, 0, 0, -1, 1, -1, 1, 0, 1, 1, 0, 1};
        if (cell.getVert()%2 == 1) {
            for (int i = 0; i < 11; i += 2) {
                int x = cell.getHor() + dif2[i];
                int y = cell.getVert() + dif2[i + 1];
                if (x >= 0 && x < xCells && y >= 0 && y < yCells)
                    neighbors.add(grid[x][y]);
            }
        }
        else {
            for (int i = 0; i < 11; i += 2) {
                int x = cell.getHor() + dif1[i];
                int y = cell.getVert() + dif1[i + 1];
                if (x >= 0 && x < xCells && y >= 0 && y < yCells)
                    neighbors.add(grid[x][y]);
            }
        }
        return neighbors;
    }
    public void flag(Element e) {
        if (!e.getFlagged()) {
            e.setFlagged(true);
        }
        else {
            e.setFlagged(false);
        }
    }
    public Element getCell(int x, int y) {
        return grid[x][y];
    }

    public void openCell(Element e) {
        e.setOpened(true);
        if (e.getBomb()) {
            end = true;
        }
    }
    public boolean getEnd() {
        return end;
    }
}