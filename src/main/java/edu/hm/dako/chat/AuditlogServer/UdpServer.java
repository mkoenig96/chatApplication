package edu.hm.dako.chat.AuditlogServer;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.SocketException;


public class UdpServer implements Runnable {

    private int port;
    public static boolean isRunning;

    DatagramSocket serverSocket = new DatagramSocket(this.port);

    AuditlogServer auditlogServer;

    public UdpServer(int port, AuditlogServer auditlogServer) throws SocketException {
        this.port = port;
        isRunning = true;
        this.auditlogServer = auditlogServer;
    }


    public void writeLog(String message) {
        try {
            FileWriter fw = new FileWriter("src/main/logs/log1.txt", true);
            BufferedWriter writer = new BufferedWriter(fw);
            writer.write(message + "\n");
            writer.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void run() {
        try (DatagramSocket serverSocket = new DatagramSocket(50900)) {


            byte[] buffer = new byte[128];
            while (true) {
                DatagramPacket datagramPacket = new DatagramPacket(buffer, 0, buffer.length);
                buffer = new byte[128];
                serverSocket.receive(datagramPacket);
                String receivedMessages = new String(datagramPacket.getData());

                if (receivedMessages.contains("shutdownAuditlog")) {
                    this.auditlogServer.terminateAuditlogServer();
                    System.exit(0);
                } else {
                    datagramPacket.setData(new byte[128]);
                    writeLog(receivedMessages);
                }

            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}

