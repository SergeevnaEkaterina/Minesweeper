package sample;

import javafx.scene.image.Image;
import javafx.scene.paint.Color;
import javafx.scene.paint.ImagePattern;
import javafx.scene.shape.Polygon;
import java.util.ArrayList;

public class Graphics {
    Board board;
    ArrayList<Image> images;
    public Graphics(Board board, ArrayList<Image> images){
        this.board = board;
        this.images = images;
    }

    public void updateUI(Polygon hexagon,Element element){
        Image count = images.get(element.getMinedNear());//берем картинку из массива, где номер- количество мин рядом
        hexagon.setFill(new ImagePattern(count)); //устанавливаем ее на шестиугольник
    }
    public void redColor(Polygon hexagon){
        hexagon.setFill(Color.RED);//если бомба под клеткой рядом

    }

}
