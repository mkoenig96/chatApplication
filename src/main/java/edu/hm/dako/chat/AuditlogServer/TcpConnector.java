package edu.hm.dako.chat.AuditlogServer;

import java.io.*;
import java.net.InetSocketAddress;
import java.net.Socket;

public class TcpConnector {

    private Socket socket;
    private DataOutputStream os;

    public TcpConnector(Socket socket) throws IOException{
        this.socket = socket;
        this.os = new DataOutputStream(this.socket.getOutputStream());
    }

    public void sendMessage(AuditlogPDU pdu) throws Exception {
        String message = pdu.toString();
        os.writeUTF(message);
    }

    public void stopThread(){
        System.out.println(Thread.currentThread().getName());
        Thread.currentThread().interrupt();
    }


}