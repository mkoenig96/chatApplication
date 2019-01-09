package edu.hm.dako.chat.AuditlogServer;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.SocketException;

/**
 * AuditlogServer for Udp-connection.
 */
public class UdpServer implements Runnable {

    private AuditlogServer auditlogServer;

    /**
     * constructor for Udp-Server.
     * @param port for the Server
     * @param auditlogServer the running instance of AuditlogServer
     * @throws SocketException if connection cant be established
     */
    UdpServer(int port, AuditlogServer auditlogServer) throws SocketException {
        new DatagramSocket(port);
        this.auditlogServer = auditlogServer;
    }

    /**
     * runs the Udp-Server in a new Thread.
     */
    @Override
    public void run() {
        try (DatagramSocket serverSocket = new DatagramSocket(50900)) {
            byte[] buffer = new byte[128];

            //awaits a incomming package and logs it to the logfile
            while (true) {
                DatagramPacket datagramPacket = new DatagramPacket(buffer, 0, buffer.length);
                buffer = new byte[128];
                serverSocket.receive(datagramPacket);
                String receivedMessages = new String(datagramPacket.getData());

                //if the message is 'shutdownAuditlog' the ServerThread will be stopped
                if (receivedMessages.contains("shutdownAuditlog")) {
                    this.auditlogServer.terminateAuditlogServer();
                    System.exit(0);

                    //if the message is not 'shutdownAuditlog' the message will be written to the Logfile
                } else {
                    datagramPacket.setData(new byte[128]);
                    Logger.writeLog(receivedMessages);
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}

