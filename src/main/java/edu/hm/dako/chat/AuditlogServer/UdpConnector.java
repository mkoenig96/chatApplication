package edu.hm.dako.chat.AuditlogServer;

import java.io.IOException;
import java.net.*;

/**
 * creates an UdpConnector (UdpConnector is acting as Client to send messages to the AuditlogServer if connectionType is UDP)
 */
public class UdpConnector {
    private DatagramSocket clientSocket;

    /**
     * constructor for the UdpConnector (UDP-Client)
     *
     * @param port to connect to
     * @throws SocketException if connection to the socket cant be established
     */
    public UdpConnector(int port) throws SocketException {
        this.clientSocket = new DatagramSocket(port);
    }

    /**
     * sends a message to the Udp-Server
     * @param pdu holds all the needed data to log
     */
    public void sendMessage(AuditlogPDU pdu) {
        String message = pdu.toString();
        try {
            DatagramPacket datagramPacket = new DatagramPacket(message.getBytes(), message.length(), InetAddress.getByName("192.168.190.92"), 50900);
            clientSocket.send(datagramPacket);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
