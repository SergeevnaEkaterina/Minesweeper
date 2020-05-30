package sample;

import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.Scene;
import javafx.scene.control.ChoiceBox;
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

    public static void main(String[] args) {
        launch(args);
    }

    private int lengthX = 12; //количесвто ячеек и бомб по умолчанию
    private int lengthY = 12;
    private int bombCount = 10;
    private Board board;
    private Polygon[][] honeyComb;

    @Override
    public void start(Stage primaryStage) {
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

        int radCir = 30; //радиус вписанной окружности=половины высоты
        for (int x = 0; x < lengthX; x++) {
            for (int y = 0; y < lengthY; y++) {
                Element cell = board.getElement(x, y);
                int centerX; //центр
                int centerY;
                if (y % 2 == 0) {
                    centerX = (2 * x + 1) * radCir;
                    centerY = (3 * y / 2 + 1) * radCir;
                } else {
                    centerX = (2 * x + 2) * radCir;
                    centerY = (3 * y / 2 + 1) * radCir + radCir / 2;
                }
                center.get(x).set(y, new Pair<>(centerX, centerY)); //устанавливаем координаты центра
                double halfRad = radCir / 2.0;   //координаты для шестиугольника
                int x1 = centerX;
                int x2 = centerX + radCir;
                int x3 = centerX - radCir;
                int y1 = centerY - radCir;
                int y2 = (int) (centerY - halfRad);
                int y3 = centerY + radCir;
                int y4 = (int) (centerY + halfRad);
                honeyComb[x][y] = new Polygon( //рисуем шестиугольник
                        x1, y1,
                        x2, y2,
                        x2, y4,
                        x1, y3,
                        x3, y4,
                        x3, y2);
                honeyComb[x][y].setFill(Color.LIGHTGOLDENRODYELLOW);
                honeyComb[x][y].setStroke(Color.ORANGERED);
                honeyComb[x][y].setOnMouseClicked(e -> {
                    if (e.getButton() == MouseButton.PRIMARY)
                        reveal(cell);
                    else {
                        flag(cell);
                    }
                });
            }
        }

        Pane root = new Pane();
        ObservableList<String> field = FXCollections.observableArrayList("16*16", "12*12"); //выбираем размеры поля
        ChoiceBox<String> fieldChoiceBox = new ChoiceBox<>(field);
        fieldChoiceBox.relocate((2 * radCir + 2) * lengthX - 100, 3 * radCir / 2.0 * lengthY + 22);

        ObservableList<String> amount = FXCollections.observableArrayList("10", "20");//количество бомб
        ChoiceBox<String> amountChoiceBox = new ChoiceBox<>(amount);
        amountChoiceBox.relocate((2 * radCir + 2) * lengthX - 200, 3 * radCir / 2.0 * lengthY + 22);

        fieldChoiceBox.setOnAction(e -> {
            if (fieldChoiceBox.getValue().equals("16*16")) {
                lengthX = 16;
                lengthY = 16;
            } else {
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
            if (amountChoiceBox.getValue().equals("10")) {
                bombCount = 10;
            } else {
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

        for (int x = 0; x < lengthX; x++) {
            for (int y = 0; y < lengthY; y++) {
                root.getChildren().add(honeyComb[x][y]);
            }
        }
        primaryStage.setScene(new Scene(root, 2 * radCir * lengthX, 3 * radCir / 2.0 * lengthY + 50));

        primaryStage.show();
    }

    private void flag(Element element) {
        Polygon polygon = honeyComb[element.getHorizontal()][element.getVertical()];
        if (board.getEnd() || element.getOpened()) return;
        if (!(element.getFlagged())) {
            polygon.setFill(Color.BLUE);
        } else {
            polygon.setFill(Color.LIGHTGOLDENRODYELLOW);
        }
        board.flag(element);
    }

    private void reveal(Element element) {
        Polygon hexagon = honeyComb[element.getHorizontal()][element.getVertical()];
        if (element.getOpened() || board.getEnd() || element.getFlagged()) return;
        board.openCell(element);
        if (element.getBomb()) {
            hexagon.setFill(Color.RED);//если бомба
            return;
        }
        //String text = e.getNearBombs() == 0 ? "" : Integer.toString((int) e.getNearBombs());
        //hexagon.setUserData(text);

        Image amount = new Image("/" + element.getMinedNear()  + ".png");//создаем картинку с количеством заминированных соседей
        hexagon.setFill(new ImagePattern(amount)); //устанавливаем ее на шестиугольник

        if (!(element.getBomb()) && element.getMinedNear() == 0) {
            board.getNeighbors(element).forEach(this::reveal); //открываем незаминированную область
        }
    }
}