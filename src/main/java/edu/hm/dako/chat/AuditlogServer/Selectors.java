package edu.hm.dako.chat.AuditlogServer;

public class Selectors {

    private static boolean UDP;
    private static boolean TCP;

    public Selectors(boolean udp, boolean tcp) {
        UDP = udp;
        TCP = tcp;
        this.stringOut();
    }

    public void stringOut () {
        System.out.println(UDP);
        System.out.println(TCP);
    }

  public static boolean getUDP(){
        boolean isUDP = UDP;
        return isUDP;
    }
    static void setUDP(){
        UDP = !UDP;
    }

    public static boolean getTCP(){
        boolean isTCP = TCP;
        return isTCP;
    }

    static void setTCP(){
        TCP = !TCP;
    }
}
