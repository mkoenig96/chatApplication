package edu.hm.dako.chat.AuditlogServer;

import java.io.*;
import java.net.*;


public class AuditlogServer implements Runnable {


    private int port;
    DatagramSocket serverSocket = new DatagramSocket(this.port);

    public AuditlogServer(int port) throws SocketException {
        this.port = port;
    }


    public static void main(String[] args) throws SocketException {




        String fileName = "src/main/logs/log1.txt";
        File f = new File(fileName);

        if (!f.exists()) {
            try {
                f.createNewFile();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }


        new Thread(new AuditlogServer(50900)).start();

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

            byte[] buffer = new byte[65507];
            while (true) {
                DatagramPacket datagramPacket = new DatagramPacket(buffer, 0, buffer.length);
                buffer = new byte[60000];
                serverSocket.receive(datagramPacket);
                String receivedMessages = new String(datagramPacket.getData());
                datagramPacket.setData(new byte[65507]);
                writeLog(receivedMessages);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
