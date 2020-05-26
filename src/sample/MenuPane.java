package sample;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;

import javafx.scene.input.MouseEvent;
import javafx.scene.layout.VBox;

public class MenuPane extends VBox {

        public MenuPane() {
            ComboBox<Integer> bombAmount1 = new ComboBox<>() ;
            ComboBox<Integer> x = new ComboBox<>() ;
            ComboBox<Integer> y = new ComboBox<>() ;
            ObservableList<Integer> bombs = FXCollections.observableArrayList(4,6,8,10,12);
            ObservableList<Integer> w = FXCollections.observableArrayList(4,6,8,10,12);
            ObservableList<Integer> h = FXCollections.observableArrayList(4,6,8,10,12);
            bombAmount1.setItems(bombs);
            x.setItems(w);
            y.setItems(h);
            //устанавливаем значения по умолчанию
            bombAmount1.setValue(10);
            x.setValue(10);
            y.setValue(10);
            Label bombl = new Label("Выберите количество бомб");
            Label wl = new Label("Выберите ширину поля");
            Label hl = new Label("Выберите высоту поля");


            Button begin = new Button("Старт");
            getChildren().addAll(
                    bombl,
                    bombAmount1,
                    wl,
                    x,
                    hl,
                    y,
                    begin
            );

            begin.addEventFilter(MouseEvent.MOUSE_CLICKED, event -> {
                int horizont = x.getValue();
                int vertical = y.getValue();
                int n = bombAmount1.getValue();
                Game.createContent(horizont, vertical, n);

            });
        }
}