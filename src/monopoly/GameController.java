/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package monopoly;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.concurrent.locks.ReentrantLock;
import java.util.concurrent.locks.Lock;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.URL;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.shape.Circle;
import javax.swing.JOptionPane;

/**
 * FXML Controller class
 *
 * @author Himel
 */
public class GameController implements Initializable {

    Monopoly main;
    Image one = new Image(Monopoly.class.getResourceAsStream("1.jpg"));
    Image two = new Image(Monopoly.class.getResourceAsStream("2.jpg"));
    Image three = new Image(Monopoly.class.getResourceAsStream("3.jpg"));
    Image four = new Image(Monopoly.class.getResourceAsStream("4.jpg"));
    Image five = new Image(Monopoly.class.getResourceAsStream("5.jpg"));
    Image six = new Image(Monopoly.class.getResourceAsStream("6.jpg"));

    int currentPlayer = 1;
    int Move = 0, dice1, dice2, cycles;
    int position = 0;
    int unBuyable[] = new int[40];
    int chests[] = new int[40];
    int chances[] = new int[40];

    double xCor[] = {0.0, -50.0, -100.0, -150.0, -200.0, -250.0, -300.0, -350.0, -400.0,
        -450.0, -543.0, -543.0, -543.0, -543.0, -543.0, -543.0, -543.0, -543.0, -543.0,
        -543.0, -501.0, -451.0, -401.0, -351.0, -301.0, -251.0, -201.0, -151.0, -101.0,
        -51.0, 42.0, 42.0, 42.0, 42.0, 42.0, 42.0, 42.0, 42.0, 42.0, 42.0};
    double yCor[] = {0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, -42.0, -92.0, -142.0,
        -192.0, -242.0, -292.0, -342.0, -392.0, -442.0, -492.0, -585.0, -585.0, -585.0,
        -585.0, -585.0, -585.0, -585.0, -585.0, -585.0, -585.0, -543.0, -493.0, -443.0,
        -393.0, -343.0, -293.0, -243.0, -193.0, -143.0, -93.0};
    boolean changable = false;
    Player pl1 = null;
    Player pl2 = null;
    Player com = null;
    @FXML
    private Button buy;
    @FXML
    private Button sell;
    @FXML
    private Button chance;
    @FXML
    private Button exit;
    @FXML
    private Button dice;
    @FXML
    private Button move;
    @FXML
    private Button chest;
    @FXML
    private Circle purple;
    @FXML
    private Label notice;
    @FXML
    private Label player1;
    @FXML
    private Label money1;
    @FXML
    private Label money2;
    @FXML
    private Label player2;
    @FXML
    private Label player3;
    @FXML
    private Label money3;
    @FXML
    private Circle blue;
    @FXML
    private Circle red;
    @FXML
    private Button end;
    Thread th = Thread.currentThread();
    @FXML
    private ImageView diceImage2;
    @FXML
    private ImageView diceImage1;

    void computerPlay() {
        Lock print = new ReentrantLock();
        currentPlayer = 1;

        try {
            Move = rollDice();

            Thread.sleep(200);
//                    notice.setText("Dice one show : " + dice1 + ", Dice two show : " + dice2);
            int decision = (int) (Math.random() * 2 + 1);
            if (decision == 2) {
                doMove(Move, red, com);
            }
            if (decision == 1) {
                doMove(Move, red, com);
                System.out.println(com.position);
                buy(com);
            }
        } catch (InterruptedException ex) {
            System.out.println(".run()");
        }

        notice.setText(pl1.name + "'s Turn");
    }

    void jail(int until) {
        dice.setDisable(true);
        move.setDisable(true);
        if (cycles == until) {
            dice.setDisable(false);
            move.setDisable(false);
        }
    }

    void setMoney() {
        if (pl1 != null) {
            money1.setText(Integer.toString(pl1.money));
        }
        if (com != null) {
            money2.setText(Integer.toString(com.money));
        }
        if (pl2 != null) {
            money2.setText(Integer.toString(pl2.money));
        }
    }

    void check(Player pl) {
        if (Monopoly.connectify) {

            if (currentPlayer == 1) {
                if (pl2.property[pl1.position] == 1) {
                    pl1.givVat(pl1.position + 20);
                }
            }
            if (currentPlayer == 2) {
                if (pl1.property[pl2.position] == 1) {
                    pl2.givVat(pl2.position + 20);
                }
            }
            if (chests[pl.position] == 1) {
                chest.setDisable(false);
            }
            if (chances[pl.position] == 1) {
                chance.setDisable(false);
            }
            if (unBuyable[pl.position] != 1) {
                buy.setDisable(false);
            }
            if (pl.property[pl.position] == 1) {
                sell.setDisable(false);
            }
            setMoney();
            if (pl1.money == 0) {
                notice.setText(pl2.name + " Won !!");
                dice.setDisable(true);
                move.setDisable(true);
                end.setDisable(true);
                chance.setDisable(true);
                chest.setDisable(true);
            }
            if (pl2.money == 0) {
                notice.setText(pl1.name + " Won !!");
                dice.setDisable(true);
                move.setDisable(true);
                end.setDisable(true);
                chance.setDisable(true);
                chest.setDisable(true);
            }
        }
        if (!Monopoly.connectify) {

            if (currentPlayer == 1) {
                if (com.property[pl1.position] == 1) {
                    pl1.givVat(pl1.position + 20);
                }
            }
            if (currentPlayer == 3) {
                if (pl1.property[com.position] == 1) {
                    com.givVat(com.position + 20);
                }
            }
            if (chests[pl.position] == 1) {
                chest.setDisable(false);
            }
            if (chances[pl.position] == 1) {
                chance.setDisable(false);
            }
            if (unBuyable[pl.position] != 1) {
                buy.setDisable(false);
            }
            if (pl.property[pl.position] == 1) {
                sell.setDisable(false);
            }
            setMoney();
            if (pl1.money == 0) {
                notice.setText(com.name + " Won !!");
                dice.setDisable(true);
                move.setDisable(true);
                end.setDisable(true);
                chance.setDisable(true);
                chest.setDisable(true);
            }
            if (com.money == 0) {
                notice.setText(pl1.name + " Won !!");
                dice.setDisable(true);
                move.setDisable(true);
                end.setDisable(true);
                chance.setDisable(true);
                chest.setDisable(true);
            }
        }
    }

    void chance(Player pl) {
        int ch = (int) (Math.random() * 3) + 1;
        if (ch == 1) {
            int n = (int) (Math.random() * 250) + 50;
            pl.getVat(n);
            notice.setText(pl.name + " gets " + n);
        }
        if (ch == 2) {
            int n = (int) (Math.random() * 250) + 50;
            pl.givVat(n);
            notice.setText(pl.name + " gives " + n);
        }
        if (ch == 3) {
            notice.setText(pl.name + " goes to jail");
        }
        setMoney();
        chance.setDisable(true);
    }

    void chest(Player pl) {
        int ch = (int) (Math.random() * 3) + 1;

        if (ch == 1) {
            int n = (int) (Math.random() * 250) + 50;
            pl.getVat(n);
            notice.setText(pl.name + " gets " + n);
        }
        if (ch == 2) {
            int n = (int) (Math.random() * 10) + 2;
            Move = n;
            notice.setText(pl.name + " moves " + n + " forward");
        }
        if (ch == 3) {
            pl.getPass();
            notice.setText(pl.name + " gets a jail pass");
        }
        chest.setDisable(true);
        setMoney();
    }

    void buy(Player player) {
        if (unBuyable[player.position] != 1) {
            if (player.buy(player.position * 10 + 100, player.position)) {
                unBuyable[player.position] = 1;
                setMoney();
                notice.setText(player.name + " has bought " + player.position);
            } else {
                notice.setText("Not enough money");
            }
        }
        player.print();
        buy.setDisable(true);
    }

    void sell(Player pl) {
        if (pl.property[pl.position] == 1) {
            pl.getVat((pl.position * 10 + 100) / 2);
        }
        sell.setDisable(true);
    }

    void doMove(int x, Circle piece, Player pl) {
        Thread t = Thread.currentThread();
        if (pl.position + x >= 40) {
            cycles++;
            pl.getVat(300);
            setMoney();
        }
        pl.position = ((pl.position + x) % 40);
        System.out.println(pl.position);
        Thread moving;

        int i = 0;
        Lock movingPiece = new ReentrantLock();
        while (i++ != x) {
            if (piece.getCenterX() == -450 && piece.getCenterY() == 0) {
                piece.setCenterX(piece.getCenterX() - 93);
                piece.setCenterY(piece.getCenterY() - 42);
            } else if (piece.getCenterX() > -500 && piece.getCenterY() == 0) {
                piece.setCenterX(piece.getCenterX() - 50);
            } else if (piece.getCenterX() == -543 && piece.getCenterY() == -492) {
                piece.setCenterY(piece.getCenterY() - 93);
                piece.setCenterX(piece.getCenterX() + 42);
            } else if (piece.getCenterY() > -585 && piece.getCenterX() == -543) {
                piece.setCenterY(piece.getCenterY() - 50);
            } else if (piece.getCenterY() == -585 && piece.getCenterX() == -51) {
                piece.setCenterX(piece.getCenterX() + 93);
                piece.setCenterY(piece.getCenterY() + 42);
            } else if (piece.getCenterY() == -585 && piece.getCenterX() < -51) {
                piece.setCenterX(piece.getCenterX() + 50);
            } else if (piece.getCenterX() == 42.0 && piece.getCenterY() == -93.0) {
                piece.setCenterY(piece.getCenterY() + 93);
                piece.setCenterX(piece.getCenterX() - 42);
            } else if (piece.getCenterX() == 42.0 && piece.getCenterY() < -93.0) {
                piece.setCenterY(piece.getCenterY() + 50);
            }
        }

    }

    @Override
    public void initialize(URL url, ResourceBundle rb) {

        purple.setVisible(false);
        blue.setVisible(false);
        red.setVisible(false);
        chance.setDisable(true);
        chest.setDisable(true);
        buy.setDisable(true);
        sell.setDisable(true);
        cycles = 0;
        if (Monopoly.connectify) {
            File play1 = new File("player1.txt");
            File play2 = new File("player2.txt");
            if (play1.length() != 0) {
                try {
                    FileInputStream fin = new FileInputStream(play1);
                    ObjectInputStream fromServer = new ObjectInputStream(fin);
                    Object o = fromServer.readObject();
                    if (o instanceof Player) {
                        pl1 = (Player) o;
                    }
                } catch (IOException ex) {
                    System.out.println("IO Exception monopoly.GameController.initialize()");
                } catch (ClassNotFoundException ex) {
                    System.out.println("Object Not Found monopoly.GameController.makeMove()");
                }
                if (pl1.money == 0) {
                    pl1.setMoney(1500);
                }
                System.out.println(pl1.position);
                doMove(pl1.position, purple, pl1);
                player1.setText(pl1.name);
                money1.setText(Integer.toString(pl1.money));
            }

            if (play2.length() != 0) {
                try {
                    FileInputStream fin = new FileInputStream(play2);
                    ObjectInputStream fromServer = new ObjectInputStream(fin);
                    Object o = fromServer.readObject();
                    if (o instanceof Player) {
                        pl2 = (Player) o;

                    }
                } catch (IOException ex) {
                    System.out.println("IO Exception 2 monopoly.GameController.initialize()");
                } catch (ClassNotFoundException ex) {
                    System.out.println("Object 2 Not Found monopoly.GameController.makeMove()");
                }
                if (pl2.money == 0) {
                    pl2.setMoney(1500);
                }
                System.out.println(pl2.position);
                doMove(pl2.position, blue, pl2);
                player2.setText(pl2.name);
                money2.setText(Integer.toString(pl2.money));
            }

            purple.setVisible(true);
            blue.setVisible(true);
            red.setVisible(false);
        }
        if (!Monopoly.connectify) {
            File play1 = new File("player1.txt");
            File play2 = new File("computer.txt");
            if (play1.length() != 0) {
                try {
                    FileInputStream fin = new FileInputStream(play1);
                    ObjectInputStream fromServer = new ObjectInputStream(fin);
                    Object o = fromServer.readObject();
                    if (o instanceof Player) {
                        pl1 = (Player) o;
                    }
                } catch (IOException ex) {
                    System.out.println("IO Exception monopoly.GameController.initialize()");
                } catch (ClassNotFoundException ex) {
                    System.out.println("Object Not Found monopoly.GameController.makeMove()");
                }
                if (pl1.money == 0) {
                    pl1.setMoney(1500);
                }
                System.out.println(pl1.position);
                doMove(pl1.position, purple, pl1);
                player1.setText(pl1.name);
                money1.setText(Integer.toString(pl1.money));

            }
            if (play2.length() != 0) {
                try {
                    FileInputStream fin = new FileInputStream(play2);
                    ObjectInputStream fromServer = new ObjectInputStream(fin);
                    Object o = fromServer.readObject();
                    if (o instanceof Player) {
                        com = (Player) o;

                    }
                } catch (IOException ex) {
                    System.out.println("IO Exception 2 monopoly.GameController.initialize()");
                } catch (ClassNotFoundException ex) {
                    System.out.println("Object 2 Not Found monopoly.GameController.makeMove()");
                }
                if (com.money == 0) {
                    com.setMoney(1500);
                }
                System.out.println(com.position);
                doMove(com.position, red, com);
                player2.setText(com.name);
                money2.setText(Integer.toString(com.money));
            }

            purple.setVisible(true);
            blue.setVisible(false);
            red.setVisible(true);
        }
        notice.setText(pl1.name + "'s Turn");
        unBuyable[0] = 1;
        unBuyable[2] = 1;
        unBuyable[4] = 1;
        unBuyable[7] = 1;
        unBuyable[10] = 1;
        unBuyable[17] = 1;
        unBuyable[20] = 1;
        unBuyable[22] = 1;
        unBuyable[33] = 1;
        unBuyable[36] = 1;
        unBuyable[38] = 1;
        unBuyable[39] = 1;
        chests[2] = 1;
        chests[17] = 1;
        chests[33] = 1;
        chances[7] = 1;
        chances[22] = 1;
        chances[36] = 1;

    }

    @FXML
    private void buyAction(ActionEvent event) {
        if (currentPlayer == 1||currentPlayer == 3) {
            buy(pl1);
        }
        if (currentPlayer == 2) {
            buy(pl2);
        }
    }

    @FXML
    private void sellAction(ActionEvent event) {
        if (currentPlayer == 1||currentPlayer == 3) {
            sell(pl1);
        }
        if (currentPlayer == 2) {
            sell(pl2);
        }
    }

    @FXML
    private void chanceAction(ActionEvent event) {
        if (currentPlayer == 1||currentPlayer == 3) {
            chance(pl1);
        }
        if (currentPlayer == 2) {
            chance(pl2);
        }
    }

    @FXML
    private void exitAction(ActionEvent event) {
        int decide = JOptionPane.showConfirmDialog(null, "Do you want to save?");
        if (decide == 0) {
            if (Monopoly.connectify) {
                try {
                    FileOutputStream fout1 = new FileOutputStream("player1.txt");
                    ObjectOutputStream obout1 = new ObjectOutputStream(fout1);
                    obout1.writeObject(pl1);
                    System.out.println(pl1.position);
                    obout1.flush();
                    FileOutputStream fout2 = new FileOutputStream("player2.txt");
                    ObjectOutputStream obout2 = new ObjectOutputStream(fout2);
                    System.out.println(pl2.position);
                    obout2.writeObject(pl2);
                    obout2.flush();
                } catch (FileNotFoundException ex) {
                    Logger.getLogger(GameController.class.getName()).log(Level.SEVERE, null, ex);
                } catch (IOException ex) {
                    Logger.getLogger(GameController.class.getName()).log(Level.SEVERE, null, ex);
                }
                main.mainStage.close();
            }
            if (!Monopoly.connectify) {
                try {
                    FileOutputStream fout1 = new FileOutputStream("player1.txt");
                    ObjectOutputStream obout1 = new ObjectOutputStream(fout1);
                    obout1.writeObject(pl1);
                    System.out.println(pl1.position);
                    obout1.flush();
                    FileOutputStream fout2 = new FileOutputStream("computer.txt");
                    ObjectOutputStream obout2 = new ObjectOutputStream(fout2);
                    System.out.println(com.position);
                    obout2.writeObject(com);
                    obout2.flush();
                } catch (FileNotFoundException ex) {
                    Logger.getLogger(GameController.class.getName()).log(Level.SEVERE, null, ex);
                } catch (IOException ex) {
                    Logger.getLogger(GameController.class.getName()).log(Level.SEVERE, null, ex);
                }
                main.mainStage.close();
            }
        } else if (decide == 1) {
            main.mainStage.close();
        } else if (decide == 2) {

        }
    }

    int rollDice() {
        dice1 = (int) (Math.random() * 6 + 1);
        dice2 = (int) (Math.random() * 6 + 1);

        switch (dice1) {
            case 1:
                diceImage1.setImage(one);
                break;
            case 2:
                diceImage1.setImage(two);
                break;
            case 3:
                diceImage1.setImage(three);
                break;
            case 4:
                diceImage1.setImage(four);
                break;
            case 5:
                diceImage1.setImage(five);
                break;
            case 6:
                diceImage1.setImage(six);
                break;
        }
        switch (dice2) {
            case 1:
                diceImage2.setImage(one);
                break;
            case 2:
                diceImage2.setImage(two);
                break;
            case 3:
                diceImage2.setImage(three);
                break;
            case 4:
                diceImage2.setImage(four);
                break;
            case 5:
                diceImage2.setImage(five);
                break;
            case 6:
                diceImage2.setImage(six);
                break;
        }
        if (currentPlayer == 1 || currentPlayer == 2) {
            notice.setText("Dice one show : " + dice1 + ", Dice two show : " + dice2);
        }
        return dice1 + dice2;
    }

    @FXML
    private void diceAction(ActionEvent event) {
        Move = rollDice();
        dice.setDisable(true);
    }

    @FXML
    private void makeMove(ActionEvent event) {
        FileInputStream in = null;
        diceImage1.setImage(null);
        diceImage2.setImage(null);
        System.out.println("move " + Move);
        System.out.println("player " + currentPlayer);
        if (currentPlayer == 1 || currentPlayer == 3) {
            doMove(Move, purple, pl1);
            Move = 0;
            check(pl1);
            changable = true;
            if (unBuyable[pl1.position] != 1) {
                notice.setText("Buy For : " + (pl1.position * 10 + 100) + "?");
            }
        }
        if (currentPlayer == 2) {
            doMove(Move, blue, pl2);
            Move = 0;
            check(pl2);
            changable = true;
            if (unBuyable[pl2.position] != 1) {
                notice.setText("Buy For : " + (pl2.position * 10 + 100) + "?");
            }
        }
    }

    @FXML
    private void openChest(ActionEvent event) {
        if (currentPlayer == 1||currentPlayer == 3) {
            chest(pl1);
        }
        if (currentPlayer == 2) {
            chest(pl2);
        }
    }

    @FXML
    private void endMove(ActionEvent event) {
        if (Monopoly.connectify) {
            if (currentPlayer == 1) {
                currentPlayer = 2;
                notice.setText(pl2.name + "'s turn");
            } else {
                currentPlayer = 1;
                notice.setText(pl1.name + "'s turn");
            }
        } else {
            notice.setText("");
            notice.setText("Computer's Turn");
            computerPlay();

            currentPlayer = 3;
        }
        dice.setDisable(false);
    }

    void setMain(Monopoly scene) {
        main = scene;
    }

}
