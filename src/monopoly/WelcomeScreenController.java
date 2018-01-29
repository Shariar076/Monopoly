/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package monopoly;

import java.net.URL;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javax.swing.JOptionPane;

/**
 * FXML Controller class
 *
 * @author Himel
 */
public class WelcomeScreenController implements Initializable {

    GameController gm = new GameController();
    Monopoly main;
    @FXML
    private Button multi;
    @FXML
    private Button solo;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }

    @FXML
    private void doConnect(ActionEvent event) {
        Monopoly.connectify = true;
        int decide = JOptionPane.showConfirmDialog(null, "Load saved game?");
        if (decide == 0) {
            main.setScene4();
        } else if (decide == 1) {
            main.setConnnection();
            try {
                main.setScene3();
            } catch (Exception ex) {
                System.out.println("monopoly.WelcomeScreenController.doConnect()");
            }
        }
    }

    @FXML
    private void doSolo(ActionEvent event) {
        int decide = JOptionPane.showConfirmDialog(null, "Load saved game?");
        Monopoly.connectify = false;
        if (decide == 0) {
            main.setScene4();
        } else if (decide == 1) {
            try {
                main.setScene2();
            } catch (Exception ex) {
                System.out.println("monopoly.WelcomeScreenController.doSolo()");
            }
        }
    }

    void setMain(Monopoly scene) {
        main = scene;
    }

}
