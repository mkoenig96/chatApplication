package edu.hm.dako.chat.AuditlogServer;

import java.awt.*;
import java.io.IOException;

public class Shutdown {

    public static void main(String[] args) throws IOException {
       //new ProcessBuilder(" C:\\Users\\BITTEJUS\\Desktop\\Dako\\chatApplication_neu\\src\\main\\java\\edu\\hm\\dako\\chat\\AuditlogServer\\AuditlogServer.java");
      Runtime.getRuntime().exec("cmd /c AuditlogServer.java");
    }
}
