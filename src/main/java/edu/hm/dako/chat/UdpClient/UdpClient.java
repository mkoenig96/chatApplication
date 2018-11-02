package edu.hm.dako.chat.UdpClient;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.SocketException;

public class UdpClient implements Runnable {
  private final int port;

  public UdpClient(int port) {
    this.port = port;
  }


  public void writeLog(String message) {
    String fileName = "C:\\Users\\sebastian\\chat\\chatApplication\\src\\main\\logs\\log1.txt";
    try {
      BufferedWriter writer = new BufferedWriter(new FileWriter(fileName));
      writer.write(message);
      writer.close();
    } catch (IOException e) {
      e.printStackTrace();
    }
  }


  @Override
  public void run() {
    try (DatagramSocket clientSocket = new DatagramSocket(this.port)) {
      byte[] buffer = new byte[65507];
      while (true) {
        DatagramPacket datagramPacket = new DatagramPacket(buffer, 0, buffer.length);
        clientSocket.receive(datagramPacket);
        String receivedMessages = new String(datagramPacket.getData());
        System.out.println(receivedMessages);
        writeLog(receivedMessages);
      }
    } catch (SocketException e) {
      e.printStackTrace();
    } catch (IOException e) {
      e.printStackTrace();
    }
  }
}
