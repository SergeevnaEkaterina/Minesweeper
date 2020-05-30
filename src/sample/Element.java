package sample;


public class Element {
    private boolean flagged = false;
    private boolean isMine;
    private final int HORIZONTAL;
    private final int VERTICAL;
    private boolean opened = false;
    private long bombsClose;

    public Element(int horizontal, int vertical, boolean isMine) {
        this.HORIZONTAL = horizontal;
        this.VERTICAL = vertical;
        this.isMine = isMine;
    }

    public int getHorizontal() {
        return HORIZONTAL;
    }

    public int getVertical() {
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

    public long getMinedNear() {//количество заминированных соседей
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