package sample;
import javafx.scene.shape.Polygon;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class Board {
    private int xCells;
    private int yCells;
    private int bombCount;
    private Graphics graphics ;
    private boolean end = false;
    private Element[][] elem;
    private Polygon[][] honeyComb;
    public Message m = new Message();
    public Board(int xCells, int yCells, int bombCount) {
        this.xCells = xCells;
        this.yCells = yCells;
        this.bombCount = bombCount;
        elem = new Element[xCells][yCells];
    }

    public void setGraphics(Graphics graphics){
        this.graphics = graphics;
    }

    public void createBoard() {  //создаем поле

        Random random = new Random();
        for (int x = 0; x < xCells; x++) {
            for (int y = 0; y < yCells; y++) {
                Element e = new Element(x, y, false);
                elem[x][y] = e;
            }
        }
        for (int i = 0; i < bombCount; i++) { //расставляем бомбы
            int x = random.nextInt(xCells);
            int y = random.nextInt(yCells);
            while (elem[x][y].getBomb()) {
                x = random.nextInt(xCells);
                y = random.nextInt(yCells);
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
        }
        else {
            e.setFlagged(false);
        }
    }

    public void reveal(Element e)  {
        e.setOpened(true);
        if (e.getBomb()) {
            end = true;
            System.out.println("you lose");

         m.lose();
        }


    }

    void openElement(Element element)  {
        Polygon hexagon = honeyComb[element.getHorizontal()][element.getVertical()];
        if (element.getOpened() || getEnd() || element.getFlagged()) return;
        reveal(element);
        if (element.getBomb()) {
            graphics.redColor(hexagon);
            return;

        }
        graphics.updateUI(hexagon,element);
        if (!(element.getBomb()) && element.getMinedNear() == 0) {
            getNeighbors(element).forEach(this::openElement); //открываем незаминированную область
        }
    }
    public boolean getEnd() {
        return end;
    }


    public void setHoneyComb(Polygon[][] honeyComb) {
        this.honeyComb = honeyComb;
    }
}