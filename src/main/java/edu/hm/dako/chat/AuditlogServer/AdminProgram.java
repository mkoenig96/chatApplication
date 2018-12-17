package edu.hm.dako.chat.AuditlogServer;

import java.io.BufferedReader;
import java.io.FileReader;

public class AdminProgram {

    public static void main(String[] args) throws Exception {
        FileReader fr = new FileReader("src/main/logs/log1.txt");
        BufferedReader br = new BufferedReader(fr);

        int msgEventCount = 0;
        int loginEventCount = 0;
        int logoutEventCount = 0;
        int msgTextEventCount = 0;


        for (String line = br.readLine(); line != null; line = br.readLine()) {

            if (line.contains("Chat-Message-Event")) {
                msgEventCount++;
            }

            if (line.contains("Login-Event")) {
                loginEventCount++;
            }

            if (line.contains("Logout-Event")) {
                logoutEventCount++;
            }

            if (line.contains("msg") && !line.contains("null")) {
                msgTextEventCount++;
            }

        }

        System.out.println(
                "Logins: " + loginEventCount + "\n"
                        + "MessageEvents: " + msgEventCount + "\n"
                        + "Logouts: " + logoutEventCount + "\n"
                        + "MessageText: " + msgTextEventCount
        );

    }
}

