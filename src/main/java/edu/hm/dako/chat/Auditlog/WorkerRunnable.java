package edu.hm.dako.chat.Auditlog;

import java.io.*;
import java.net.Socket;

/**

 */
public class WorkerRunnable extends Thread {

    private Socket clientSocket;

    public WorkerRunnable(Socket clientSocket) throws IOException, ClassNotFoundException {
        this.clientSocket = clientSocket;
    }



    public void run() {
        try {
            int i = 0;
            String clientSentence;
            String capitalizedSentence;
            while (true) {
                BufferedReader inFromClient =
                        new BufferedReader(new InputStreamReader(this.clientSocket.getInputStream()));
                DataOutputStream outToClient = new DataOutputStream(this.clientSocket.getOutputStream());
                clientSentence = inFromClient.readLine();
                System.out.println("Received: " + clientSentence);
                capitalizedSentence = clientSentence.toUpperCase() + 'n';
                outToClient.writeBytes(capitalizedSentence);
                i++;
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

    }


}