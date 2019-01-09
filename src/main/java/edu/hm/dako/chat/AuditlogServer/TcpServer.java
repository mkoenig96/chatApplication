package edu.hm.dako.chat.AuditlogServer;


import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;

import static java.lang.System.out;


class TcpServer {

    //serverport
    private int port;

    // supplys informations of the running AuditlogServer
    private AuditlogServer auditlogServer;

    /**
     * @param port           of the server
     * @param auditlogServer informations of the running AuditlogServer
     */
    TcpServer(int port, AuditlogServer auditlogServer) {
        this.port = port;
        this.auditlogServer = auditlogServer;
    }

    /**
     * starts the TcpServer-Thread, waits for incoming connections and creates a new ServerThread for every client
     *
     * @throws IOException if the Server-Thread cant be started
     */
    void startServer() throws IOException {
        //creates ServerSocket
        ServerSocket server = new ServerSocket(this.port);
        out.println("Server Booted");
        out.println("Any client can stop the server by sending -1");

        //waiting for clients to connect and starts a ServerThread for each client
        while (!Thread.currentThread().isInterrupted()) {
            Socket client = server.accept();
            new Thread(new ServerThread(client, this)).start();
        }
    }

    /**
     * A ServerThread is started for every incoming connection
     */
    private static class ServerThread implements Runnable {

        TcpServer server;
        Socket client;
        DataInputStream is;

        /**
         * @param client clientsocket that is connected
         * @param server the running TcpServer
         * @throws IOException if connection cant be established
         */
        ServerThread(Socket client, TcpServer server) throws IOException {
            this.client = client;
            this.server = server;
            is = new DataInputStream(client.getInputStream());
        }


        /**
         * runs the ServerThread and waits for incoming messages to be logged
         * runs writeLog method if there is a new message
         */
        @Override
        public void run() {
            try {
                while (!Thread.currentThread().isInterrupted()) {
                    String zeile = is.readUTF();
                    if (zeile.contains("shutdownAuditlog")) {
                        client.close();
                        this.server.auditlogServer.terminateAuditlogServer();
                        System.exit(0);
                    } else {
                        Logger.writeLog(zeile);
                    }
                }
            } catch (java.io.IOException e) {
                e.printStackTrace();
            }
        }
    }
}

