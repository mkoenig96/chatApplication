package edu.hm.dako.chat.UdpAuditlogServer;

import java.io.IOException;
import java.net.*;
import java.util.Date;

public class UdpConnector {
    private final int port;
    DatagramSocket clientSocket;


    public UdpConnector(int port) throws SocketException {
        this.port = port;
        this.clientSocket = new DatagramSocket(this.port);
    }

    public void sendMessage(AuditlogPDU pdu) {

        String message = pdu.toString();
        //pdu.getPduType() + "||" + new Date() + "||" + pdu.getUserName() + "||" + pdu.getClientThreadName() + "||" + pdu.getServerThreadName() + "||" + pdu.getMessage();

        try {

            DatagramPacket datagramPacket = new DatagramPacket(message.getBytes(), message.length(), InetAddress.getLocalHost(), 50900);
            clientSocket.send(datagramPacket);


        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
