package edu.hm.dako.chat.AuditlogServer;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.SocketException;

public class UdpServer implements Runnable {

    private int port;
    private AuditlogServer auditlogServer;
    DatagramSocket serverSocket = new DatagramSocket(this.port);

    public UdpServer(int port, AuditlogServer auditlogServer) throws SocketException {
        this.port = port;
        this.auditlogServer = auditlogServer;
    }




    @Override
    public void run() {
        try (DatagramSocket serverSocket = new DatagramSocket(50900)) {
            byte[] buffer = new byte[128];
            while (!Thread.currentThread().isInterrupted()) {
                DatagramPacket datagramPacket = new DatagramPacket(buffer, 0, buffer.length);
                buffer = new byte[128];
                serverSocket.receive(datagramPacket);
                String receivedMessages = new String(datagramPacket.getData());
                datagramPacket.setData(new byte[128]);
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