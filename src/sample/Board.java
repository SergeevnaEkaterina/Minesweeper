package sample;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Random;

public class Board {
    private int xCells;
    private int yCells;
    private int bombCount;
    Game g = new Game();
    public int rest = Objects.requireNonNull(g).getFree();//количество ячеек без бомб

    private boolean end = false;
    private Element[][] elem;



    public Board(int xCells, int yCells, int bombCount) {
        this.xCells = xCells;
        this.yCells = yCells;
        this.bombCount = bombCount;

        elem = new Element[xCells][yCells];
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
        } else {
            e.setFlagged(false);
        }
    }



    public Condition openElement(Element element) {

        if(getEnd()){

            return Condition.END;
        }
        if (element.getOpened()||element.getFlagged()) return Condition.GAME;

        if(!element.getBomb()){
            element.setOpened(true);
        }

         if (element.getBomb()) {
            end = true;



            return Condition.LOSE;
        }

        rest--;

        if ( element.getMinedNear() == 0) {
            getNeighbors(element).forEach(this::openElement);//открываем незаминированную область

        }

        if (rest == 0) {

            end=true;
           return Condition.WIN;
        }
        else return Condition.GAME;

    }



    public boolean getEnd() {
        return end;
    }



}