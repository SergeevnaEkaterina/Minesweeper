package sample;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class Board {
    private int xCells;
    private int yCells;
    private int bombCount;
    private boolean end = false;
    private Cell[][] grid;

    public Board(int xCells, int yCells, int bombCount) {
        this.xCells = xCells;
        this.yCells = yCells;
        this.bombCount = bombCount;
        grid = new Cell[xCells][yCells];
    }

    public void createBoard() {

        Random random1 = new Random();
        Random random2 = new Random();

        for (int x = 0; x < xCells; x++) {
            for (int y = 0; y < yCells; y++) {
                Cell cell = new Cell(x, y, false);
                grid[x][y] = cell;
            }
        }

        for (int i = 0; i < bombCount; i++) {
            int x = random1.nextInt(xCells);
            int y = random2.nextInt(yCells);
            while (grid[x][y].getBomb()) {
                x = random1.nextInt(xCells);
                y = random2.nextInt(yCells);
            }
            grid[x][y].setBomb(true);
        }

        for (int x = 0; x < xCells; x++) {
            for (int y = 0; y < yCells; y++) {
                Cell cell = grid[x][y];
                if (cell.getBomb()) {
                    continue;
                }
                long amount = getNeighbors(cell).stream().filter(Cell::getBomb).count();
                cell.setNearBombs(amount);
            }
        }
    }

    public Cell getCell(int x, int y) {
        return grid[x][y];
    }
    public boolean getEnd() {
        return end;
    }
    public List<Cell> getNeighbors(Cell cell) {
        List<Cell> neighbors = new ArrayList<>();
        int[] deltas1 = {-1, 0, -1, -1, 0, -1, 1, 0, 0, 1, -1, 1};
        int[] deltas2 = {-1, 0, 0, -1, 1, -1, 1, 0, 1, 1, 0, 1};
        if (cell.getVert() % 2 == 1) {
            for (int i = 0; i < 11; i += 2) {
                int x = cell.getHor() + deltas2[i];
                int y = cell.getVert() + deltas2[i + 1];
                if (x >= 0 && x < xCells && y >= 0 && y < yCells)
                    neighbors.add(grid[x][y]);
            }
        }
        else {
            for (int i = 0; i < 11; i += 2) {
                int x = cell.getHor() + deltas1[i];
                int y = cell.getVert() + deltas1[i + 1];
                if (x >= 0 && x < xCells && y >= 0 && y < yCells)
                    neighbors.add(grid[x][y]);
            }
        }
        return neighbors;
    }

    public void openCell(Cell cell) {
        cell.setOpened(true);
        if (cell.getBomb()) {
            end = true;
        }
    }
    public void flag(Cell cell) {
        if (!cell.getFlagged()) {
            cell.setFlagged(true);
        }
        else {
            cell.setFlagged(false);
        }
    }
}
