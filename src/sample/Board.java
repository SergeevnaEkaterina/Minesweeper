package sample;


import javafx.scene.image.Image;
import javafx.scene.paint.Color;
import javafx.scene.paint.ImagePattern;
import javafx.scene.shape.Polygon;

import java.util.*;

public class Board {
    public int xCells;
    public int yCells;
    public int bombCount;
    public int flagCount = 0;
    public boolean end = false;
    public Element[][] elem;
    private ArrayList<Image> images;
    private Polygon[][] honeyComb;
    public Board(int xCells, int yCells, int bombCount) {
        this.xCells = xCells;
        this.yCells = yCells;
        this.bombCount = bombCount;
        elem = new Element[xCells][yCells];
    }



    public void createBoard() {  //создаем поле
        Random random1 = new Random();
        Random random2 = new Random();
        for (int x = 0; x < xCells; x++) {
            for (int y = 0; y < yCells; y++) {
                Element e = new Element(x, y, false);
                elem[x][y] = e;
            }
        }
        for (int i = 0; i < bombCount; i++) { //расставляем бомбы
            int x = random1.nextInt(xCells);
            int y = random2.nextInt(yCells);
            while (elem[x][y].getBomb()) {
                x = random1.nextInt(xCells);
                y = random2.nextInt(yCells);
            }
            elem[x][y].setBomb(true);
        }

        for (int x = 0; x < xCells; x++) { //количество заминированных соседей
            for (int y = 0; y < yCells; y++) {
                Element e = elem[x][y];
                if (e.getBomb()) {
                    continue;
                }
                long amount = getNeighbors(e).stream().filter(Element::getBomb).count();
                e.setMinedNear(amount);
            }
        }
    }


    public List<Element> getNeighbors(Element e) { //ищем соседей
        List<Element> neighbors = new ArrayList<>();
        int[] dif1 = {-1, 0, -1, -1, 0, -1, 1, 0, 0, 1, -1, 1};
        int[] dif2 = {-1, 0, 0, -1, 1, -1, 1, 0, 1, 1, 0, 1};
        if (e.getVertical() % 2 == 1) {
            for (int i = 0; i < 11; i += 2) {
                int x = e.getHorizontal() + dif2[i];
                int y = e.getVertical() + dif2[i + 1];
                if (x >= 0 && x < xCells && y >= 0 && y < yCells)
                    neighbors.add(elem[x][y]);
            }
        } else {
            for (int i = 0; i < 11; i += 2) {
                int x = e.getHorizontal() + dif1[i];
                int y = e.getVertical() + dif1[i + 1];
                if (x >= 0 && x < xCells && y >= 0 && y < yCells)
                    neighbors.add(elem[x][y]);
            }
        }
        return neighbors;
    }
    public Element getElement(int x, int y) {
        return elem[x][y];
    }



    public void flag(Element e) {
        if (!e.getFlagged()) {
            e.setFlagged(true);
            flagCount++;
        }
        else {
            e.setFlagged(false);
            flagCount--;
        }
    }

    public void reveal(Element e) {
        e.setOpened(true);
        if (e.getBomb()) {
            end = true;
        }

    }

    void openElement(Element element) {
        Polygon hexagon = honeyComb[element.getHorizontal()][element.getVertical()];
        if (element.getOpened() || getEnd() || element.getFlagged()) return;
        reveal(element);
        if (element.getBomb()) {
            hexagon.setFill(Color.RED);//если бомба

            return;
        }

        Image count = images.get((int) element.getMinedNear());//берем картинку из массива, где номер- количество мин рядом
        hexagon.setFill(new ImagePattern(count)); //устанавливаем ее на шестиугольник


        if (!(element.getBomb()) && element.getMinedNear() == 0) {
            getNeighbors(element).forEach(this::openElement); //открываем незаминированную область
        }


    }
    public boolean getEnd() {
        return end;
    }


    public void setImages(ArrayList<Image> images) {
        this.images = images;
    }

    public void setHoneyComb(Polygon[][] honeyComb) {
        this.honeyComb = honeyComb;
    }
}