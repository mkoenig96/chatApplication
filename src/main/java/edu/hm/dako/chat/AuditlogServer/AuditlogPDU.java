package edu.hm.dako.chat.AuditlogServer;

import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.Date;

import edu.hm.dako.chat.common.ChatPDU;
import edu.hm.dako.chat.common.PduType;

/**
 * <p/>
 * Nachrichtenaufbau fuer Chat-Protokoll (fuer alle Nachrichtentypen: Request,
 * Response, Event, Confirm)
 *
 * @author Mandl
 */
public class AuditlogPDU implements Serializable {


    // Kommandos bzw. PDU-Typen
    private PduType pduType;

    // Login-Name des Clients
    private String userName;

    // Name des Client-Threads, der den Request absendet
    private String clientThreadName;

    // Name des Threads, der den Request im Server
    private String serverThreadName;

    // Nutzdaten (eigentliche Chat-Nachricht in Textform)
    private String message;


    public AuditlogPDU() {
        pduType = PduType.UNDEFINED;
        userName = null;
        clientThreadName = null;
        serverThreadName = null;
        message = null;
    }

    private String timeStamp = new SimpleDateFormat("dd.MM.yyyy, HH:mm:ss").format(new Date());

    public String toString() {
        return timeStamp + "|" + this.pduType + "|tnC: " + this.clientThreadName + "|tnS: "
                + this.serverThreadName + "|msg: " +  this.message;
    }



    public void setPduType(PduType pduType) {
        this.pduType = pduType;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public void setClientThreadName(String threadName) {
        this.clientThreadName = threadName;
    }

    public void setServerThreadName(String threadName) {
        this.serverThreadName = threadName;
    }

    public void setMessage(String msg) {
        this.message = msg;
    }

    public String getUserName() {
        return userName;
    }

    public String getMessage() {
        return (message);
    }


    /**
     * Erzeugen einer Logout-Event-PDU
     *
     * @param receivedPdu Empfangene PDU (Logout-Request-PDU)
     * @return Erzeugte PDU
     */
    public static AuditlogPDU createLogoutEventPdu(ChatPDU receivedPdu) {

        AuditlogPDU pdu = new AuditlogPDU();
        pdu.setPduType(PduType.LOGOUT_EVENT);
        pdu.setUserName(receivedPdu.getUserName());
        pdu.setServerThreadName(Thread.currentThread().getName());
        pdu.setClientThreadName(receivedPdu.getClientThreadName());
        return pdu;
    }

    /**
     * Erzeugen einer Login-Event-PDU
     *
     * @param receivedPdu Empfangene PDU (Login-Request-PDU)
     * @return Erzeugte PDU
     */
    public static AuditlogPDU createLoginEventPdu(ChatPDU receivedPdu) {

        AuditlogPDU pdu = new AuditlogPDU();
        pdu.setPduType(PduType.LOGIN_EVENT);
        pdu.setUserName(receivedPdu.getUserName());
        pdu.setServerThreadName(Thread.currentThread().getName());
        pdu.setClientThreadName(receivedPdu.getClientThreadName());
        return pdu;
    }

    /**
     * Erzeugen einer Chat-Message-Event-PDU
     *
     * @param receivedPdu (Chat-Message-Request-PDU)
     * @return Erzeugte PDU
     */
    public static AuditlogPDU createChatMessageEventPdu(ChatPDU receivedPdu) {

        AuditlogPDU pdu = new AuditlogPDU();
        pdu.setPduType(PduType.CHAT_MESSAGE_EVENT);
        pdu.setServerThreadName(Thread.currentThread().getName());
        pdu.setClientThreadName(receivedPdu.getClientThreadName());
        pdu.setUserName(receivedPdu.getUserName());
        pdu.setMessage(receivedPdu.getMessage());
        return pdu;
    }


}