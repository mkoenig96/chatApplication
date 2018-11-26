package edu.hm.dako.chat.clients;

import java.io.IOException;
import java.net.*;

public class UdpConnector {
  private final int port;
  DatagramSocket clientSocket;


  public UdpConnector(int port) throws SocketException {
    this.port = port;
    this.clientSocket = new DatagramSocket(this.port);
  }

  public void sendMessage (String message) {
    try {
      DatagramPacket datagramPacket = new DatagramPacket(message.getBytes(), message.length(), InetAddress.getLocalHost(), 50900);
      clientSocket.send(datagramPacket);
    } catch (SocketException e) {
      e.printStackTrace();
    } catch (UnknownHostException e) {
      e.printStackTrace();
    } catch (IOException e) {
      e.printStackTrace();
    }
  }

}
