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
/**
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

public class Game  {

     static final int fieldSizex = Board.fieldSizex;
     static final int fieldSizey = Board.fieldSizey;

    public static final int height = 50;//высота ячейки
    public static final double halfHeight = height / 2.0; //полувысота
    public static final double side = 2*halfHeight *Math.tan(Math.PI/6); // сторона
    public static final double overflow = side*Math.sin(Math.PI/6); //вынос

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
            if (newX >= 0 && newX < fieldSizex && newY >= 0 && newY < fieldSizey) {
                neighbors.add(Board.grid[newX][newY]);
            }
        }
        return neighbors;
    }
    public static class Tile extends StackPane {
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
                //scene.setRoot(createContent());//заново создаем сетку
                return;
            }
            isOpen = true;
            text.setVisible(true);
            hexagon.setFill(null);

            if (text.getText().isEmpty()) {
               // getNeighbors(this).forEach(Tile::open);//открываем
            }

        }
    }





}
*/
import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.input.MouseButton;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.paint.ImagePattern;
import javafx.scene.shape.Polygon;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import javafx.util.Pair;


import java.util.ArrayList;

public class Game extends Application {

    private int lengthX = 12; //размеры и количество бомб по умолчанию
    private int lengthY = 12;
    private int bombCount = 10;
    private Board board;
    private Polygon[][] honeyComb;
    public void start(Stage primaryStage) throws Exception {
        board = new Board(lengthX, lengthY, bombCount);
        board.createBoard();
        honeyComb = new Polygon[lengthX][lengthY];
        ArrayList<ArrayList<Pair<Integer, Integer>>> center = new ArrayList<>(); //координаты центра
        for (int i = 0; i < lengthX; i++) {
            center.add(new ArrayList<>());
            for (int j = 0; j < lengthY; j++) {
                center.get(i).add(new Pair<>(0, 0));
            }
        }

        int radius = 20; //радиус вписанной окружности = половина высоты
        for (int x = 0; x < lengthX; x++) {
            for (int y = 0; y < lengthY; y++) {
                Cell cell = board.getCell(x, y);
                int centerX; //координаты центра
                int centerY;
                if (y % 2 == 0) {
                    centerX = (2*x + 1)*radius;
                    centerY = (3*y / 2 + 1)*radius;
                } else {
                    centerX = (2*x + 2)*radius;
                    centerY = (3*y / 2 + 1)*radius + radius / 2;
                }
                center.get(x).set(y, new Pair<>(centerX, centerY)); //устанавливаем координаты центра
                double halfRad = radius/2.0;   // координаты шестиугольника
                int x1 = centerX;
                int x2 = centerX + radius;
                int x3 = centerX - radius;
                int y1 = centerY-radius;
                int y2 = (int) (centerY-halfRad);
                int y3 = centerY+radius;
                int y4 = (int) (centerY+halfRad);
                honeyComb[x][y] = new Polygon(x1, y1,  //рисуем шестиугольник
                        x2, y2,
                        x2, y4,
                        x1, y3,
                        x3, y4,
                        x3, y2);
                honeyComb[x][y].setFill(Color.LIGHTGOLDENRODYELLOW);
                honeyComb[x][y].setStroke(Color.ORANGERED);
                honeyComb[x][y].setOnMouseClicked(e -> {
                    if (e.getButton() == MouseButton.PRIMARY)
                        openCell(cell);
                    else {
                        flag(cell);
                    }
                });
            }
        }
        Pane root = new Pane();



            //Label lbl = new Label();
            //lbl.setText("field");
            ObservableList<String> field = FXCollections.observableArrayList("16*16", "12*12");
            ChoiceBox<String> fieldChoiceBox = new ChoiceBox<>(field);
            fieldChoiceBox.relocate((2 * radius + 2) * lengthX - 80, 3 * radius / 2.0*lengthY + 22);
            //lbl.relocate((2 * radius + 2) * xCells - 65, 3 * radius / 2.0 * yCells +10);
            //Label lbl1 = new Label();
            //lbl1.setText("bombs");
            ObservableList<String> amount = FXCollections.observableArrayList("10", "20");
            ChoiceBox<String> amountChoiceBox = new ChoiceBox<>(amount);
            amountChoiceBox.relocate((2 * radius + 2) * lengthX - 110, 3 * radius / 2.0*lengthY + 22);
            //lbl.relocate((2 * radius + 2) * xCells - 65, 3 * radius / 2.0 * yCells +10);


            fieldChoiceBox.setOnAction(e -> {
                if(fieldChoiceBox.getValue().equals("16*16")){
                    lengthX = 16;
                    lengthY = 16;
                }
                else {
                    lengthX = 12;
                    lengthY = 12;
                }
                primaryStage.close();
                try {
                    start(new Stage());
                } catch (Exception ex) {
                    ex.printStackTrace();
                }
            });
            amountChoiceBox.setOnAction(e -> {
                if(amountChoiceBox.getValue().equals("10")){
                    bombCount = 10;
                }
                else {
                    bombCount = 20;
                }
                primaryStage.close();
                try {
                    start(new Stage());
                } catch (Exception ex) {
                    ex.printStackTrace();
                }
            });

            root.getChildren().add(fieldChoiceBox);
            root.getChildren().add(amountChoiceBox);
           // root.getChildren().add(lbl);
            //root.getChildren().add(lbl1);


        for (int x = 0; x < lengthX; x++) {
            for (int y = 0; y < lengthY; y++) {
                root.getChildren().add(honeyComb[x][y]);
            }
        }
        primaryStage.setTitle("Сапёр");
        primaryStage.setScene(new Scene(root, 2*radius*lengthX, 3 * radius/2.0*lengthY + 50));
        primaryStage.show();
    }
    private void openCell(Cell cell) {
        Polygon hexagon = honeyComb[cell.getHor()][cell.getVert()];
        if (cell.getOpened() || board.getEnd() || cell.getFlagged()) return;
        board.openCell(cell);
        if (cell.getBomb()) {
            //hexagon.setFill(Color.RED);//красный если бомба
            hexagon.setFill(new ImagePattern(new Image("resources\\4.png")));//
            return;

        }
       // String text = toString(cell.getNearBombs());
       // Text.setText(text);
        //hexagon.setFill(Color.WHITE);
       // Label lbl2 = new Label();
        //lbl2.setText(""+cell.getNearBombs());
        //hexagon.setFill(new ImagePattern(new Image(path + ".png")));
        //resources/4.png
      //  else hexagon.setFill(new ImagePattern(new Image(path + cell.getNearBombs() + ".png")));
         hexagon.setFill(new ImagePattern(new Image("resources\\4.png")));
        //Image amount = new Image( "src\\sample\\"+ cell.getNearBombs() + ".png");//создаем картинку с количеством заминированных соседей
       // Image amount = new Image( "src\\sample\\"+   "1.png");//создаем картинку с количеством заминированных соседей
       // hexagon.setFill(new ImagePattern(amount)); //устанавливаем картику на шестиугольник
//hexagon.setFill(Color.BLUE);

        //hexagon.setFill(cell.getNearBombs());
        //должно возвращаться количество соседей с бомбами
        if (!cell.getBomb() && cell.getNearBombs() == 0) {
            board.getNeighbors(cell).forEach(this::openCell);
        }
    }
    private void flag(Cell cell) {
        Polygon polygon = honeyComb[cell.getHor()][cell.getVert()];
        if (cell.getOpened() || board.getEnd()) return;
        if (!cell.getFlagged()) {
            polygon.setFill(Color.BLUE);
        }
        else {
            polygon.setFill(Color.LIGHTGOLDENRODYELLOW);
        }
        board.flag(cell);
    }
    public static void main(String[] args) {
        launch(args);
    }
}