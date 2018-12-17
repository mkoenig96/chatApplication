package edu.hm.dako.chat.Auditlog;

import com.sun.security.ntlm.Server;

import javax.xml.crypto.Data;
import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Scanner;
import java.util.concurrent.Executor;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import static java.lang.System.in;
import static java.lang.System.out;


public class TCPServer {

  int port;
  ServerSocket server = null;
  Socket client = null;
  ExecutorService pool = null;
  int clientcount = 0;

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
      client = server.accept();
      clientcount++;
      ServerThread runnable = new ServerThread(client, clientcount, this);
      pool.execute(runnable);
    }

  }

  private static class ServerThread implements Runnable {

    TCPServer server = null;
    Socket client = null;
    BufferedReader cin;
    PrintStream cout;
    Scanner sc = new Scanner(System.in);
    int id;
    String s;
    DataInputStream is;
    DataOutputStream os;

    ServerThread(Socket client, int count, TCPServer server) throws IOException {

      this.client = client;
      this.server = server;
      this.id = count;
      is = new DataInputStream(client.getInputStream());
      os = new DataOutputStream(client.getOutputStream());
      cin = new BufferedReader(new InputStreamReader(client.getInputStream()));
      cout = new PrintStream(client.getOutputStream());
    }

    public void writeLog(String message) {
      try {
        FileWriter fw = new FileWriter("src/main/logs/log1.txt", true);
        BufferedWriter writer = new BufferedWriter(fw);
        writer.write(message);
        writer.close();
      } catch (IOException e) {
        e.printStackTrace();
      }
    }

    @Override
    public void run() {
      try {
        String tmp = "";
        while (true) {
          byte tmpByte = is.readByte();
          char ch = (char) tmpByte;
          tmp = tmp + ch;
          if(is.available() <= 0){
            tmp = tmp + " " + id + "\n";
            this.writeLog(tmp + id);
          }
        }
      } catch (java.io.IOException e) {
        e.printStackTrace();
      }
    }

  }
}






