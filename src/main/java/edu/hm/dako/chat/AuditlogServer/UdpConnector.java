package edu.hm.dako.chat.AuditlogServer;

import java.io.IOException;
import java.net.*;

/**
 * creates an UdpConnector (UdpConnector is acting as Client to send messages to the AuditlogServer
 * if connectionType is UDP).
 *
 * @author Sebastian Waterloo, Matthias König, Justin Bitterlich, Sophie Wienhues
 * @version 1.0
 */
public class UdpConnector {
  /**
   * constructor for the UdpConnector (UDP-Client)
   *
   * @param port to connect to
   * @throws SocketException if connection to the socket cant be established
   */
  public UdpConnector(int port) throws SocketException {}

  /**
   * sends a message to the Udp-Server
   *
   * @param pdu holds all the needed data to log
   */
  public void sendMessage(AuditlogPDU pdu) {
    String message = pdu.toString();
    try {
      DatagramSocket clientSocket = new DatagramSocket();
      DatagramPacket datagramPacket =
          new DatagramPacket(
              message.getBytes(), message.length(), InetAddress.getByName("localhost"), 50900);
      clientSocket.send(datagramPacket);
      clientSocket.close();
    } catch (IOException e) {
      e.printStackTrace();
    }
  }
}
