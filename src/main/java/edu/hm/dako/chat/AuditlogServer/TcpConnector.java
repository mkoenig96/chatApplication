package edu.hm.dako.chat.AuditlogServer;

import java.io.*;
import java.net.Socket;
/**
 * TcpConnector acts as Client that sends messages to the AuditlogServer. *
 *
 * @author Sebastian Waterloo, Matthias König, Justin Bitterlich, Sophie Wienhues
 * @version 1.0
 */
public class TcpConnector {

  private DataOutputStream os;

  /**
   * the socket that should connect to the TCP-Server creates an outputstream to send messages to
   * the AuditlogServer
   *
   * @param socket
   */
  public TcpConnector(Socket socket) throws IOException {
    this.os = new DataOutputStream(socket.getOutputStream());
  }

  /**
   * contains all the data that needs to be logged
   *
   * @param pdu
   * @throws Exception if the messsage cant be sent sends a message to the AuditlogServer
   */
  public void sendMessage(AuditlogPDU pdu) throws Exception {
    String message = pdu.toString();
    os.writeUTF(message);
  }
}
