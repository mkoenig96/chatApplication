package edu.hm.dako.chat.AuditlogServer;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import static java.lang.System.out;


public class TcpServer {

    int port;
    public static boolean isRunning;
    private ServerSocket server = null;


    TcpServer(int port) {
        this.port = port;

    }



    public void startServer() throws IOException {

        server = new ServerSocket(this.port);
        out.println("Server Booted");
        out.println("Any client can stop the server by sending -1");
        while (true) {
            Socket client = server.accept();
            new Thread(new ServerThread(client, this)).start();
        }

    }

    private static class ServerThread implements Runnable {

        TcpServer server;
        Socket client;
        DataInputStream is;

        ServerThread(Socket client, TcpServer server) throws IOException {

            this.client = client;
            this.server = server;
            is = new DataInputStream(client.getInputStream());

        }




        public void writeLog(String message) {
            try {
                FileWriter fw = new FileWriter("src/main/logs/log1.txt", true);
                BufferedWriter writer = new BufferedWriter(fw);
                writer.write(message + "\n");
                writer.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        @Override
        public void run() {
            try {
                while (!Thread.currentThread().isInterrupted()) {
                    String zeile = is.readUTF();
                    writeLog(zeile);
                }
            } catch (java.io.IOException e) {
                e.printStackTrace();
            }
        }

    }
}

