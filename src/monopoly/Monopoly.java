/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package monopoly;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Group;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import java.net.*;
import java.io.*;
import java.util.logging.Level;
import java.util.logging.Logger;
import static javafx.application.Application.launch;

/**
 *
 * @author Himel
 */
public class Monopoly extends Application {

    Stage mainStage;
    public Scene scene1, scene2, scene3,scene4;
    public static boolean connectify = false;

    public static String screen1file = "welcomeScreen.fxml";
    public static String screen2file = "StartScreen.fxml";
    public static String screen3file = "MultiStartScreen.fxml";
    public static String screen4file = "Game.fxml";

    public Socket toServer;

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) {
        try {
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(getClass().getResource(screen1file));
            Parent root = loader.load();
            WelcomeScreenController wcScreen = loader.getController();
            wcScreen.setMain(this);
            scene1 = new Scene(root);
            mainStage = primaryStage;
            mainStage.setScene(scene1);
            mainStage.show();
        } catch (IOException ex) {
            System.out.println("monopoly.Monopoly.start()");
        }
    }

    void setConnnection() {
        try {
            toServer = new Socket("localhost", 7789);

        } catch (IOException ex) {
            System.out.println("monopoly.Monopoly.main()");
        }
    }

    public void setScene1() {
        try {
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(getClass().getResource(screen1file));
            Parent root = loader.load();
            WelcomeScreenController wcScreen = loader.getController();
            wcScreen.setMain(this);
            scene1 = new Scene(root);
            mainStage.setScene(scene1);
            mainStage.show();
        } catch (IOException ex) {
            System.out.println("monopoly.Monopoly.setScene1()");
        }
    }

    public void setScene2() {
        try {
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(getClass().getResource(screen2file));
            Parent root = loader.load();
            StartScreenController stScreen = loader.getController();
            stScreen.setMain(this);
            scene2 = new Scene(root);
            mainStage.setScene(scene2);
            mainStage.show();
        } catch (IOException ex) {
            System.out.println("monopoly.Monopoly.setScene2()");
        }
    }

    public void setScene3() {
        try {
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(getClass().getResource(screen3file));
            Parent root = loader.load();
            MultiStartScreenController msScreen = loader.getController();
            msScreen.setMain(this);
            scene3 = new Scene(root);
            mainStage.setScene(scene3);
            mainStage.show();
        } catch (IOException ex) {
            System.out.println("monopoly.Monopoly.setScene3()");
        }
    }
        public void setScene4() {
        try {
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(getClass().getResource(screen4file));
            Parent root = loader.load();
            GameController gmScreen = loader.getController();
            gmScreen.setMain(this);
            scene4 = new Scene(root);
            mainStage.setScene(scene4);
            mainStage.show();
        } catch (IOException ex) {
            System.out.println("monopoly.Monopoly.setScene4()");
            System.err.println(ex.getMessage());
        }
    }

}
