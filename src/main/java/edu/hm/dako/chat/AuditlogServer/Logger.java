package edu.hm.dako.chat.AuditlogServer;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;

class Logger {

    static void writeLog(String message) {
        try {
            FileWriter fw = new FileWriter("src/main/logs/log1.txt", true);
            BufferedWriter writer = new BufferedWriter(fw);
            writer.write(message + "\n");
            writer.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
