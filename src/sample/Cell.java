package sample;

public class Cell {
    private boolean flagged = false;
    private boolean isMine;
    private final int HORIZONT;
    private final int VERTICAL;
    private boolean opened = false;
    private long bombsClose;
    public Cell(int HORIZONT, int VERTICAL, boolean isMine) {
        this.HORIZONT = HORIZONT;
        this.VERTICAL = VERTICAL;
        this.isMine = isMine;
    }
    public int getHor() {
        return HORIZONT;
    }
    public int getVert() {
        return VERTICAL;
    }
    public boolean getBomb() {
        return isMine;
    }
    public boolean getFlagged() {
        return flagged;
    }
    public void setBomb(boolean isMine) {
        this.isMine = isMine;
    }
    public void setFlagged(boolean flag) {
        flagged = flag;
    }
    public long getNearBombs() {//заминированные соседние клетки
        return bombsClose;
    }
    public void setNearBombs(long count) {
        bombsClose = count;
    }
    public boolean getOpened() {
        return opened;
    }
    public void setOpened(boolean isOpen) {
        opened = isOpen;
    }
}