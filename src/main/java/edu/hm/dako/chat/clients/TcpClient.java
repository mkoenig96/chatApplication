package edu.hm.dako.chat.clients;

import java.io.*;
import java.net.*;

public class TcpClient {

  private int port;
  public TcpClient(int port){
    this.port = port;
  }

  public void sendMessage() throws Exception {
    String sentence;
    String modifiedSentence;
    BufferedReader inFromUser = new BufferedReader(new InputStreamReader(System.in));
    Socket clientSocket = new Socket("localhost", 20313);
    DataOutputStream outToServer = new DataOutputStream(clientSocket.getOutputStream());
    BufferedReader inFromServer = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
    sentence = inFromUser.readLine();
    outToServer.writeBytes(sentence + 'n');
    modifiedSentence = inFromServer.readLine();
    System.out.println("FROM SERVER: " + modifiedSentence);
    clientSocket.close();
  }


}