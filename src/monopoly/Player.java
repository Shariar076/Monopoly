/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package monopoly;

import java.io.Serializable;
import java.util.Arrays;

/**
 *
 * @author Himel
 */
public class Player implements Serializable{

    String name;
    int jailPass;
    int money;
    int[] property = new int[40];
    int count;
    int position;
    Player(String name) {
        this.name = name;
        this.money = 1500;
        this.count = 0;
        this.position = 0;
        this.jailPass = 0;
    }

    Player() {

    }
    void setName(String name){
        this.name=name;
    }
    void setPrperty(int pos) {
        property[pos] = 1;
    }

    void getPass() {
        jailPass++;
    }

    boolean usePass() {
        if (jailPass == 0) {
            return false;
        }
        jailPass--;
        return true;
    }

    void setMoney(int money) {
        this.money = money;
    }

    void setPosition(int pos) {
        position = pos;
    }

    boolean buy(int am, int id) {
        if (money > am) {
            money -= am;
            property[id] = 1;
            count++;
            return true;
        } else {
            return false;
        }
    }

    void sell(int am) {
        money += am;
    }

    void getVat(int am) {
        money += am;
    }

    void givVat(int am) {
        money -= am;
    }

    void print() {
        System.out.println(Arrays.toString(property));
    }
}
