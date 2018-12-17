package edu.hm.dako.chat.Auditlog;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import static java.lang.System.out;


public class TCPServer {

  int port;
  private ServerSocket server = null;
  private ExecutorService pool = null;

  public static void main(String[] args) throws IOException {
    TCPServer serverobj = new TCPServer(14785);
    serverobj.startServer();
  }



  TCPServer(int port) {
    this.port = port;
    pool = Executors.newFixedThreadPool(5);
  }



  public void startServer() throws IOException {

    server = new ServerSocket(this.port);
    out.println("Server Booted");
    out.println("Any client can stop the server by sending -1");
    while (true) {
      Socket client = server.accept();
      ServerThread runnable = new ServerThread(client, this);
      pool.execute(runnable);
    }

  }

  private static class ServerThread implements Runnable {

    TCPServer server;
    Socket client;
    DataInputStream is;

    ServerThread(Socket client, TCPServer server) throws IOException {

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
        while (true) {

          String zeile = is.readUTF();
          writeLog(zeile);
        }
      } catch (java.io.IOException e) {
        e.printStackTrace();
      }
    }

  }
}






