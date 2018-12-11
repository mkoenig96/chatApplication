package edu.hm.dako.chat.auditlogServer;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.net.*;


public class AuditlogServer implements Runnable {
  private int port;
  DatagramSocket serverSocket = new DatagramSocket(this.port);

  public AuditlogServer (int port) throws SocketException {
    this.port = port;
  }

  public static void main(String [] args) throws SocketException {
    new Thread(new AuditlogServer(50900)).start();
  }

  public void writeLog(String message) {
    String fileName = "src/main/logs/log1.txt"; /*C:\Users\basst\Desktop\chat\chatApplication\src\main\logs\log1.txt*/
    try {
      BufferedWriter writer = new BufferedWriter(new FileWriter(fileName, true));
      writer.write(message + "\n");
      message = "";
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
        serverSocket.receive(datagramPacket);
        String receivedMessages = new String(datagramPacket.getData());
        datagramPacket.setData(new byte[65507]);
        writeLog(receivedMessages);
      }
    } catch (SocketException e) {
      e.printStackTrace();
    } catch (IOException e) {
      e.printStackTrace();
    }
  }
}
