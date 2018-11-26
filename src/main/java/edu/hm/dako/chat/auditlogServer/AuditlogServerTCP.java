package edu.hm.dako.chat.auditlogServer;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.BindException;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.SocketException;

import edu.hm.dako.chat.connection.Connection;
import edu.hm.dako.chat.connection.ServerSocketInterface;

/**
 * Server-Socket Implementierung auf TCP-Basis
 *
 * @author Peter Mandl
 */
public class AuditlogServerTCP implements Runnable{


  @Override
  public void run() {
    String clientSentence = null;
    String capitalizedSentence;
    ServerSocket welcomeSocket;
    try {
      welcomeSocket = new ServerSocket(20313);
    } catch (java.io.IOException e){
      e.printStackTrace();
      welcomeSocket = null;
    }

    while (true) {
      Socket connectionSocket = null;
      if(welcomeSocket != null){
        try {
          connectionSocket = welcomeSocket.accept();
        } catch (java.io.IOException e){
          e.printStackTrace();
        }

      }
      DataOutputStream outToClient = null;
      try {
        BufferedReader inFromClient =
          new BufferedReader(new InputStreamReader(connectionSocket.getInputStream()));
        outToClient = new DataOutputStream(connectionSocket.getOutputStream());

        clientSentence = inFromClient.readLine();
      } catch (IOException e) {
        e.printStackTrace();
      }
      System.out.println("Received: " + clientSentence);
      capitalizedSentence = clientSentence.toUpperCase() + 'n';
      try {
        outToClient.writeBytes(capitalizedSentence);
      } catch (java.io.IOException e){
        e.printStackTrace();
      }
    }
  }

}
