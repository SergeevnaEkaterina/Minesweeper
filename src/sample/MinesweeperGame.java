package sample;
import javafx.scene.paint.Color;
import java.util.ArrayList;
import java.util.List;



public class MinesweeperGame {
    private static final int SIDE = 9;
    private static final String MINE = "\uD83D\uDCA3";
    private static final String FLAG = "\uD83D\uDEA9";
    private GameObject[][] gameField = new GameObject[SIDE][SIDE];
    private int countMinesOnField;
    private int countFlags;
    private boolean isGameStopped;


    public void initialize(){
        createGame();
    }


    private void createGame(){//рисование поля
        setScreenSize(SIDE, SIDE);
        isGameStopped = false;
        for(int x = 0; x < SIDE; x++){
            for(int y = 0; y < SIDE; y++){
                gameField[y][x]=new GameObject(x, y, Math.random() < 1);
                if(Math.random() < 1) countMinesOnField++;
                setCellColor(x, y, Color.ORANGE);
            }
        }
        countFlags = countMinesOnField;
        //countMineNeighbors();
    }

    private void setScreenSize(int side, int side1) {
    }



    private List getNeighbors(GameObject gameObject) {//нахождение соседей
        List getNeighbours = new ArrayList<>();
        int x = gameObject.x;
        int y = gameObject.y;
        if (y-1 >= 0 && x-1 >= 0) getNeighbours.add(String.valueOf(gameField[y-1][x-1]));
        if (x-1 >= 0) getNeighbours.add(String.valueOf(gameField[y][x-1]));
        if (x-1 >= 0 && y+1 < SIDE) getNeighbours.add(String.valueOf(gameField[y+1][x-1]));
        if (y-1 >= 0) getNeighbours.add(String.valueOf(gameField[y-1][x]));
        if (y+1 < SIDE) getNeighbours.add(String.valueOf(gameField[y+1][x]));
        if (y-1 >= 0 && x+1 < SIDE) getNeighbours.add(String.valueOf(gameField[y-1][x+1]));
        if (x+1 < SIDE) getNeighbours.add(String.valueOf(gameField[y][x+1]));
        if (y+1 < SIDE && x+1 < SIDE) getNeighbours.add(String.valueOf(gameField[y+1][x+1]));

        return getNeighbours;
    }

   /** private void countMineNeighbors() {//нахождение количества мин рядом
        for (int i = 0; i < SIDE; i++) {
            for (int j = 0; j < SIDE; j++) {
                if (!gameField[j][i].isMine) {
                    for (int k = 0; k < getNeighbors(String.valueOf(gameField[j][i]).size()); k++) {
                        if (getNeighbors(gameField[j][i]).get(k).isMine) {
                            gameField[j][i].countMineNeighbors++;
                        }
                    }
                }
            }
        }
    }
*/
    private void openTile(int x, int y){
        if(isGameStopped) return;
        gameField[y][x].isOpen = true;
        setCellColor(x, y, Color.GREEN);

        if (gameField[y][x].isMine) {
            setCellValue(x, y, MINE);
            //setCellValueEx(x, y, Color.RED, MINE);
            setCellColor(x, y, Color.RED);
            gameOver();
        }
        else {
            setCellNumber(x, y, gameField[y][x].countMineNeighbors);
        }

        if (gameField[y][x].countMineNeighbors == 0){

           /** for (Object gameObject : getNeighbors(gameField[y][x])) {
                if (!gameObject.isOpen){
                    openTile(gameObject.x,gameObject.y);
                } else if(gameField[y][x].isMine){return;}
            }
            */
        }
        else {
            setCellNumber(x, y, gameField[y][x].countMineNeighbors);
        }
    }

    private void setCellNumber(int x, int y, int countMineNeighbors) {
    }

    public void onMouseLeftClick(int x, int y) {
        openTile(x,y);
    }

    public void onMouseRightClick(int x, int y) {
        markTile(x,y);
    }

    private void markTile(int x, int y) {//помечаем флагом
        if(isGameStopped) return;
        GameObject gameObject = gameField[y][x];
        if(gameObject.isOpen ) {return;}
        if(countFlags == 0 & !gameObject.isFlag) {return;}
        if (gameObject.isFlag){
            gameObject.isFlag = false;
            countFlags++;
            setCellValue(x, y, "");
            setCellColor(x, y, Color.ORANGE);
        }
        else {
            gameObject.isFlag = true;
            countFlags =  countFlags -1 ;
            setCellValue(x, y, FLAG);
            setCellColor(x, y, Color.PINK);
        }

    }

    private void setCellValue(int x, int y, String flag) {
    }

    private void setCellColor(int x, int y, Color pink) {
    }

    private void gameOver() {//
        isGameStopped = true;



    }
}
