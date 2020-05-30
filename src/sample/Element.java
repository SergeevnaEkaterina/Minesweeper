package sample;

public class Element {
    private boolean flagged = false;
    private boolean isMine;
    private final int HORIZONTAL;
    private final int VERTICAL;
    private boolean opened = false;
    private long bombsClose;
    public Element(int HORIZONTAL, int VERTICAL, boolean isMine) {
        this.HORIZONTAL = HORIZONTAL;
        this.VERTICAL = VERTICAL;
        this.isMine = isMine;
    }
    public int getHor() {
        return HORIZONTAL;
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