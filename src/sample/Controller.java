package sample;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;


import java.net.URL;
import java.util.ResourceBundle;

public class Controller {
    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    private TextField bombs;

    @FXML
    private TextField width;

    @FXML
    private Button start;

    @FXML
    private TextField height;

    @FXML
    void initialize() {
    start.setOnAction(event ->{

    });

    }
}
