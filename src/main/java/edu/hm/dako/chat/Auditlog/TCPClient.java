package edu.hm.dako.chat.Auditlog;

import com.sun.corba.se.impl.orbutil.ObjectWriter;

import javax.xml.crypto.Data;
import java.io.*;
import java.net.InetAddress;
import java.net.Socket;

public class TCPClient {

    private Socket socket;
    private DataOutputStream os;

    public TCPClient(Socket socket) throws IOException{
        this.socket = socket;
        this.os = new DataOutputStream(this.socket.getOutputStream());
    }

    public void sendMessage(String message) throws Exception {
        this.os.writeBytes(message);
    }



}
