package edu.hm.dako.chat.Auditlog;

import java.io.*;
import java.net.Socket;

public class TCPClient {

    private Socket socket;
    private DataOutputStream os;

    public TCPClient(Socket socket) throws IOException{
        this.socket = socket;
        this.os = new DataOutputStream(this.socket.getOutputStream());
    }

    public void sendMessage(AuditlogPDU pdu) throws Exception {
        String message = pdu.toString();
        os.writeUTF(message);
    }



}
