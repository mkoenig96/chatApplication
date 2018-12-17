package edu.hm.dako.chat.clients;

import java.io.*;
import java.net.*;

public class TCPClient {


  private PrintWriter out;
  private BufferedReader in;
  private int port;
  private InetAddress host;
  private Socket clientSocket;
  private ObjectOutputStream oos;

  public TCPClient(int port) throws IOException {
    this.port = port;
    this.host = InetAddress.getLocalHost();
    this.clientSocket = new Socket(host.getHostName(), 14785);
    this.oos = new ObjectOutputStream(this.clientSocket.getOutputStream());
  }


  public void sendMessage(String message) throws IOException{
    System.out.println("Sending request to Socket Server");
    oos.writeObject(message);

  }


}