package edu.hm.dako.chat.auditlogServer;

import com.sun.security.ntlm.Server;

import java.io.*;
import java.net.*;

public class TCPServer implements Runnable{

  private int port;

  public TCPServer(int port){
    this.port = port;
  }


  public static void main(String args[]) throws IOException, ClassNotFoundException{
   new Thread(new TCPServer(14785)).start();
  }

  public void writeLog(String message) {
    String fileName = "C:\\Users\\basst\\Desktop\\chat\\chatApplication\\src\\main\\logs\\log1.txt";
    try {
      BufferedWriter writer = new BufferedWriter(new FileWriter(fileName, true));
      writer.write(message + "\n");
      message = "";
      writer.close();
    } catch (IOException e) {
      e.printStackTrace();
    }
  }

  @Override
  public void run(){
    ServerSocket serverSocket = null;
    try {
      serverSocket = new ServerSocket(14785);
    } catch (IOException e) {
      e.printStackTrace();
    }
    while (true){
      System.out.println("Waiting for client request");
      //creating socket and waiting for client connection
      Socket socket = null;
      try {
        socket = serverSocket.accept();
      } catch (IOException e) {
        e.printStackTrace();
      }
      //read from socket to ObjectInputStream object
      ObjectInputStream ois = null;
      try {
        ois = new ObjectInputStream(socket.getInputStream());
      } catch (IOException e) {
        e.printStackTrace();
      }
      //convert ObjectInputStream object to String
      String message = null;
      try {
        message = (String) ois.readObject();
      } catch (IOException e) {
        e.printStackTrace();
      } catch (ClassNotFoundException e) {
        e.printStackTrace();
      }
      writeLog(message);
    }
  }

}