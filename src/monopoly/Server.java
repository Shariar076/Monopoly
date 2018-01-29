/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package monopoly;

/**
 *
 * @author Himel
 */
import java.io.*;
import java.net.*;
import java.util.logging.Level;
import java.util.logging.Logger;

public class Server {

    public static void main(String argv[]) throws Exception {
        int workerThreadCount = 0;
        int id = 1;
        ServerSocket welcomeSocket = new ServerSocket(7789);
        while (true) {
            Socket connectionSocket = welcomeSocket.accept();
            WorkerThread wt = new WorkerThread(connectionSocket, id);
            Thread t = new Thread(wt);
            t.start();
            workerThreadCount++;
            System.out.println("Client [" + id + "] is now connected. No. of worker threads = " + workerThreadCount);
            id++;
        }

    }
}

class WorkerThread implements Runnable {

    private Socket connectionSocket;
    private int id;

    public WorkerThread(Socket ConnectionSocket, int id) {
        this.connectionSocket = ConnectionSocket;
        this.id = id;
    }

    public void run() {
        String clientSentence;
        String capitalizedSentence;
        try {
            //ObjectOutputStream outToServer = new ObjectOutputStream(connectionSocket.getOutputStream());
            ObjectInputStream inFromServer1 = new ObjectInputStream(connectionSocket.getInputStream());
            Object clientObj1 = inFromServer1.readObject();
            FileOutputStream fout1 = new FileOutputStream("player1.txt");
            ObjectOutputStream outTFile1 = new ObjectOutputStream(fout1);
            outTFile1.writeObject(clientObj1);
            outTFile1.flush();
            System.out.println("id 1");
            ObjectInputStream inFromServer2 = new ObjectInputStream(connectionSocket.getInputStream());
            Object clientObj2 = inFromServer2.readObject();
            FileOutputStream fout2 = new FileOutputStream("player2.txt");
            ObjectOutputStream outTFile2 = new ObjectOutputStream(fout2);
            outTFile2.writeObject(clientObj2);
            outTFile2.flush();
            System.out.println("id 2");

        } catch (ClassNotFoundException e) {
            System.out.println("server.WorkerThread.run()");
        } catch (IOException ex) {
            System.out.println("monopoly.WorkerThread.run()");
        }

    }
}
