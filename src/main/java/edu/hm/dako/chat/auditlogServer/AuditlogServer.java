package edu.hm.dako.chat.auditlogServer;

import java.io.IOException;
import java.net.*;

public class AuditlogServer implements Runnable {
  DatagramSocket serverSocket = new DatagramSocket(50003);
  private final int clientPort;

  public AuditlogServer (int clientPort) throws SocketException {
    this.clientPort = clientPort;
  }

  public void sendDummyMessage (String message) {
    try{
      DatagramPacket datagramPacket = new DatagramPacket(message.getBytes(), message.length(), InetAddress.getLocalHost(), this.clientPort);
      serverSocket.send(datagramPacket);
    } catch (SocketException e){
      e.printStackTrace();
    } catch (UnknownHostException e) {
      e.printStackTrace();
    } catch (IOException e) {
      e.printStackTrace();
    }

  }

  @Override
  public void run(){
    System.out.println("running");
  }
}
