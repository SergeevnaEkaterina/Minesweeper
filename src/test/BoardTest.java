package test;
import org.junit.jupiter.api.Test;
import sample.Board;
import sample.Element;

import java.util.List;
import java.util.Random;

import static org.junit.jupiter.api.Assertions.*;
class BoardTest {
    Board board = new Board(16, 16, 20);

    @Test
    void test0() {

        board.createBoard();
        Random random = new Random();

        int i = random.nextInt(16);
        int j = random.nextInt(16);

        List<Element> near = null;
        near = board.getNeighbors(board.getElement(i, j));

        for (Element e : near) {
            assertTrue(board.getNeighbors(e).contains(board.getElement(i, j)));
        }
    }


    @Test
    void test1() {

        board.createBoard();

        Random random = new Random();

        for (int k = 0; k < 8; k++) {
            int i = random.nextInt(8);
            int j = random.nextInt(8);

            if (!(board.getElement(i, j).getBomb())) {
                board.reveal(board.getElement(i, j));
                assertTrue(board.getElement(i, j).getOpened());
            } else {
                long expected = board.getElement(i, j).getMinedNear();
                assertEquals(expected, 0);
            }
        }

        assertFalse(board.getEnd());
    }

    @Test
    void test2() {

        board.createBoard();

        Random random = new Random();

        int i = random.nextInt(12);
        int j = random.nextInt(12);

        for (int k = 0; k < 1; k++) { //расставляем бомбы

            while (board.getElement(i, j).getBomb()) {
                i = random.nextInt(12);
                j = random.nextInt(12);
            }
            board.getElement(i, j).setBomb(true);
        }
        boolean expected = board.getElement(i, j).getBomb();
        assertTrue(expected);
    }

    @Test
    void test3() {

        Element e1 = new Element(10, 10, true);
        Element e2 = new Element(16, 16, false);


        assertEquals(10, e1.getHorizontal());
        assertEquals(10, e1.getVertical());
        assertNotEquals(1, e1.getHorizontal());
        assertNotEquals(1, e1.getVertical());
        assertTrue( e1.getBomb());

        assertEquals(16, e2.getHorizontal());
        assertEquals(16, e2.getVertical());
        assertNotEquals(1,e2.getHorizontal());
        assertNotEquals(1, e2.getVertical());
        assertFalse(e2.getBomb());


    }
}