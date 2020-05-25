package sample;

import java.net.URL;
import java.util.ResourceBundle;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.ComboBox;
import javafx.stage.Stage;

public class EntryController {


    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    private ComboBox<Integer> bombAmount;

    @FXML
    private ComboBox<Integer> width;

    @FXML
    private ComboBox<Integer> height;
    public void setStartScreen() {
        ObservableList<Integer> w = FXCollections.observableArrayList(2,4,6,8,10,12);
        ObservableList<Integer> h = FXCollections.observableArrayList(2,4,6,8,10,12);
        ObservableList<Integer> bombs = FXCollections.observableArrayList(4,6,8,10,12);
        width.setItems(w);
        height.setItems(h);
        bombAmount.setItems(bombs);
    }

    @FXML
    void begin(ActionEvent event) {

    }

    @FXML
    void initialize() {


    }

    @FXML
    public void startGame() throws Exception {
        int w = (width.getValue() == null)? 6 : width.getValue();
        int h = (height.getValue() == null )? 6 : height.getValue();
        int bombs = (bombAmount.getValue() == null )? 10 : bombAmount.getValue();




    }

    public void setStage(Stage stage) {
        //this.stage = stage;
    }
}






