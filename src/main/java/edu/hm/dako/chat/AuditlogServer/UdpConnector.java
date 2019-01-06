package edu.hm.dako.chat.AuditlogServer;

import java.io.IOException;
import java.net.*;

public class UdpConnector {
    private final int port;
    DatagramSocket clientSocket;


    public UdpConnector(int port) throws SocketException {
        this.port = port;
        this.clientSocket = new DatagramSocket(this.port);
    }

    public void sendMessage(AuditlogPDU pdu) {

        String message = pdu.toString();

        try {

            DatagramPacket datagramPacket = new DatagramPacket(message.getBytes(), message.length(), InetAddress.getByName("192.168.178.46"), 50900);
            clientSocket.send(datagramPacket);


        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
