/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package monopoly;

import java.awt.Color;
import java.io.FileInputStream;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import java.io.*;
import java.net.Socket;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * FXML Controller class
 *
 * @author Himel
 */
public class MultiStartScreenController implements Initializable {

    /**
     * Initializes the controller class.
     */
    FileOutputStream player1 = null;
    Monopoly main;
    @FXML
    private Button start;
    @FXML
    private TextField field1;
    @FXML
    private Button back;
    @FXML
    private TextField field2;

    boolean isDone() {

        return field1.getText().isEmpty()||field2.getText().isEmpty();
    }

    @Override
    public void initialize(URL url, ResourceBundle rb) {
            start.setDisable(true);
    }

    @FXML
    private void startGame(ActionEvent event) {
        
        main.setScene4();
    }

    @FXML
    private void getName1(ActionEvent event) {

        
            
        if (field1.getText().length() != 0) {
            start.setDisable(isDone());
        }
        try {
            String sentence;
            ObjectOutputStream outToServer = new ObjectOutputStream(main.toServer.getOutputStream());
            sentence = field1.getText();
            Player player= new Player(sentence);
            outToServer.writeObject(player);

        } catch (IOException ex) {
            System.out.println("monopoly.StartScreenController.startGame()");
        }
    }

    @FXML
    private void goBack(ActionEvent event) {
        main.setScene1();
    }

    void setMain(Monopoly scene) {
        main = scene;
    }

    @FXML
    private void getName2(ActionEvent event) {
        if (field2.getText().length() != 0) {
            start.setDisable(isDone());
        }
        try {
            String sentence;
            ObjectOutputStream outToServer = new ObjectOutputStream(main.toServer.getOutputStream());
            sentence = field2.getText();
            Player player= new Player(sentence);
            outToServer.writeObject(player);

        } catch (IOException ex) {
            System.out.println("monopoly.StartScreenController.startGame()");
        }
    }


}
