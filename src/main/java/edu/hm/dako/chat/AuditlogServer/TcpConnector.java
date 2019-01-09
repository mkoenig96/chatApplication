package edu.hm.dako.chat.AuditlogServer;

import java.io.*;
import java.net.Socket;


//TcpConnector acts as Client that sends messages to the AuditlogServer
public class TcpConnector {

    private DataOutputStream os;

   /**
   *
   * @param socket the socket that should connect to the TCP-Server
   * creates an outputstream to send messages to the AuditlogServer
   *
    */
    public TcpConnector(Socket socket) throws IOException{
        this.os = new DataOutputStream(socket.getOutputStream());
    }

    /**
     *
     * @param pdu contains all the data that needs to be logged
     * @throws Exception if the messsage cant be sent
     * sends a message to the AuditlogServer
     */

    public void sendMessage(AuditlogPDU pdu) throws Exception {
        String message = pdu.toString();
        os.writeUTF(message);
    }
}