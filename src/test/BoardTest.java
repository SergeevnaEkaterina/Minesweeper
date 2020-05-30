package test;
import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.*;
import java.util.Random;
import sample.Board;
import sample.Element;
import java.util.List;

class BoardTest {
    Board board = new Board(16,16,20);

    @Test
    void test0() {

        board.createBoard();
        Random random0 = new Random();
        Random random1 = new Random();

        int i = random0.nextInt(16);
        int j = random1.nextInt(16);

        List<Element> near = null;
        near=board.getNeighbors(board.getElement(i,j));

        for(Element e: near) {
            assertTrue(board.getNeighbors(e).contains(board.getElement(i,j)));
        }
    }


    @Test
    void test1() {

        board.createBoard();
        Random random0 = new Random();
        Random random1 = new Random();

        for (int k=0; k<8; k++) {
            int i = random0.nextInt(8);
            int j = random1.nextInt(8);

            if (!(board.getElement(i,j).getBomb())) {
                board.openCell(board.getElement(i,j));
                assertTrue(board.getElement (i,j).getOpened());
            }
            else {
                long expected = board.getElement(i,j).getMinedNear();
                assertEquals(expected, 0);
            }
        }

        assertFalse(board.getEnd());
    }

    @Test
    void test2() {

        board.createBoard();
        Random random0 = new Random();
        Random random1 = new Random();

        int i = random0.nextInt(12);
        int j = random1.nextInt(12);

        for (int k = 0; k<1; k++) { //расставляем бомбы
            // int i = random0.nextInt(12);
            //int j = random1.nextInt(12);
            while (board.getElement(i,j).getBomb()) {
                i = random0.nextInt(12);
                j = random1.nextInt(12);
            }
            board.getElement(i,j).setBomb(true);
        }
        boolean expected = board.getElement(i,j).getBomb();
        assertTrue(expected);
    }



}