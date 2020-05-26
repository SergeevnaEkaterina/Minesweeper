package sample; /**package sample;*/
/**import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;
import java.io.IOException;

public class Main extends Application {



    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage stage) throws Exception {

        //Scene scene = new Scene(root, 300, 300);
        stage.setTitle("");
        //stage.setScene(scene);
        stage.show();
    }


}
*/
import java.lang.Math;
import javafx.application.Application;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Polygon;
import javafx.scene.text.Text;
import javafx.stage.Stage;

import java.util.ArrayList;
import java.util.List;

public class Game extends Application {
    public int hor;
    public int vert;
    public int n;
    public Game (int hor, int vert, int n) {
        this.hor = hor;
        this.vert = vert;
        this.n = n;
    }
    public int userInputBombs = 30;//считывается со стартового окна
    public static final int WIDTH = 500;//считывается со стартового окна
    public static final int HEIGHT = 500;//считывается со стартового окна
    public static final int horizontCells = 8;
    public static final int verticalCells = 8;

    public static final int height = 50;//высота ячейки
    public static final double halfHeight = height / 2.0; //полувысота
    public static final double side = 2*halfHeight *Math.tan(Math.PI/6); // сторона
    public static final double overflow = side*Math.sin(Math.PI/6); //вынос



    public Tile[][] honeyComb = new Tile[horizontCells][verticalCells];
    public Scene scene;
    public Parent createContent() {
        Pane root = new Pane();
        root.setPrefSize(WIDTH, HEIGHT);
        for (int y = 0; y < verticalCells; y++) { // здесь и далее x и y меняем местам тк массив строк= отзеркаленная матрица
            for (int x = 0; x < horizontCells; x++) {
                Tile element = new Tile(x, y, Math.random() < (double) userInputBombs /100);
                honeyComb[x][y] = element;
                root.getChildren().add(element);
            }
        }
        for (int y = 0; y < verticalCells; y++) {
            for (int x = 0; x < horizontCells; x++) {
                Tile tile = honeyComb[x][y];
                if (tile.isMine)
                    continue;

                long bombs = getNeighbors(tile).stream().filter(t -> t.isMine).count();
                if (bombs > 0)
                    tile.text.setText(String.valueOf(bombs));
            }
        }
        return root;

    }
    public List<Tile> getNeighbors(Tile tile) {//ищем соседей
        List<Tile> neighbors = new ArrayList<>();
        int[] points;
        if (tile.x % 2 == 1) {
            points = new int[]{
                    0, -1,
                    -1, 0,
                    -1, 1,
                    0, 1,
                    1, 1,
                    1, 0
            };
        } else {
            points = new int[]{
                    0, -1,
                    -1, -1,
                    -1, 0,
                    0, 1,
                    1, 0,
                    1, -1
            };
        }

        for (int i = 0; i < points.length; i++) {
            int dx = points[i];
            int dy = points[++i];
            int newX = tile.x + dx;
            int newY = tile.y + dy;
            if (newX >= 0 && newX < horizontCells && newY >= 0 && newY < verticalCells) {
                neighbors.add(honeyComb[newX][newY]);
            }
        }
        return neighbors;
    }
    class Tile extends StackPane {
        public final Polygon hexagon = new Polygon();
        public int x;
        public int y;
        public boolean isMine;
        public boolean isOpen = false;
        public Text text = new Text();
        public Tile(int x, int y, boolean isMine) {
            this.x = x;
            this.y = y;
            this.isMine = isMine;
            text.setText(isMine ? "YES" : "");
            text.setVisible(false);
            double x1 = x * 1.0;  //координаты шестиугольника
            double y1 = y * 1.0;
            double x2 = x + side;
            double x3 = x2 + overflow;
            double y3 = y + halfHeight;
            double y4 = y3 + halfHeight;
            double x6 = x - overflow;

            hexagon.getPoints().addAll(x1, y1,   //рисуем шестиугольник по координатам
                    x2, y1,
                    x3, y3,
                    x2, y4,
                    x1, y4,
                    x6, y3);
            hexagon.setFill(Color.LIGHTGOLDENRODYELLOW);
            hexagon.setStroke(Color.ORANGERED);

            setTranslateX(x * 1.5*side);
            setTranslateY(y * height + (x % 2) * halfHeight);


            getChildren().addAll(hexagon, text);

            setOnMouseClicked(e -> open());
        }
        public void open() {
            if (isOpen)
                return;
            if (isMine) {//проиграно
                scene.setRoot(createContent());//заново создаем сетку
                return;
            }
            isOpen = true;
            text.setVisible(true);
            hexagon.setFill(null);

            if (text.getText().isEmpty()) {
                getNeighbors(this).forEach(Tile::open);
            }

        }
    }
    @Override
    public void start(Stage stage) throws Exception {
        scene = new Scene(createContent());
        stage.setScene(scene);
        stage.setTitle("Сапёр");
        stage.show();
    }
    public static void main(String[] args) {
        launch(args);
    }
}
