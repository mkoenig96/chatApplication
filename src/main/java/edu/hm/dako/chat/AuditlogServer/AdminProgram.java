package edu.hm.dako.chat.AuditlogServer;

import java.io.BufferedReader;
import java.io.FileReader;

/**
 * Separate program to analyze the logfile. *
 *
 * @author Sebastian Waterloo, Matthias König, Justin Bitterlich, Sophie Wienhues
 * @version 1.0
 */
public class AdminProgram {

  public static void main(String[] args) throws Exception {

    // create a Filereader for the logfile
    FileReader fr = new FileReader("src/main/logs/log1.txt");
    BufferedReader br = new BufferedReader(fr);

    // counter for logged message events
    int msgEventCount = 0;

    // counter for logged logins
    int loginEventCount = 0;

    // counter for logged logouts
    int logoutEventCount = 0;

    // counter for messages
    int msgTextEventCount = 0;

    // reads the logfile line by line and increments the counter for the logged event
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

    // output of the analyzed data
    System.out.println(
        "Logins: "
            + loginEventCount
            + "\n"
            + "MessageEvents: "
            + msgEventCount
            + "\n"
            + "Logouts: "
            + logoutEventCount
            + "\n"
            + "MessageText: "
            + msgTextEventCount);
  }
}
