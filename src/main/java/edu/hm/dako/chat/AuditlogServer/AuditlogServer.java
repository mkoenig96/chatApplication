package edu.hm.dako.chat.AuditlogServer;


import java.io.File;
import java.io.IOException;
import java.net.SocketException;
import java.util.Scanner;

public class AuditlogServer {

    public static void main(String[] args) throws SocketException {

        AuditlogServer auditlogServer = new AuditlogServer();
        Scanner scanner = new Scanner(System.in);

        System.out.println("AuditlogServer wurde gestartet!");

        //creating a file to write the logs to
        String fileName = "src/main/logs/log1.txt";
        File f = new File(fileName);

        //creates file if file does not exist
        if (!f.exists()) {
            try {
                boolean isCreated = f.createNewFile();
                if(isCreated){
                    System.out.println("Es wurde eine neue Datei erzeugt");
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        } else {
            System.out.println("Die Datei log1.txt existiert bereits.");
        }

        System.out.println("Das Protokoll befindet sich im Pfad: " + fileName);

        //prints information for the user
        System.out.println("Gebe für eine UDP Verbindung [1] ein.");
        System.out.println("Gebe für eine TCP Verbindung [2] ein.");

        //input of the user
        String eingabe = scanner.nextLine();

        //depending on user input a tcp- or udp-server will be started
        if (eingabe.equals("1")) {
            new Thread(new UdpServer(50900, auditlogServer)).start();
            System.out.println("UDP Thread gestartet");
        } else {
            if (eingabe.equals("2")) {
                try {
                    new TcpServer(50800, auditlogServer).startServer();
                } catch (IOException e) {
                    e.printStackTrace();
                }
                System.out.println("TCP Thread gestartet");
            } else {
                System.out.println("Keine Verbindung!");
            }
        }
    }

    //stops the auditlogserver if a clients send the message "shutdownAuditlog"
    void terminateAuditlogServer() {
        System.exit(0);
    }
}
