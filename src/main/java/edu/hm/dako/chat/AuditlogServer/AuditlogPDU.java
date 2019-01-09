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


    // pduType
    private PduType pduType;

    //client login name
    private String userName;

    // name of the clientThread
    private String clientThreadName;

    // name of the serverThread
    private String serverThreadName;

    // chatmessage
    private String message;


    //constructor
    private AuditlogPDU() {
        //pduType is undefined in the beginning
        pduType = PduType.UNDEFINED;
        userName = null;
        clientThreadName = null;
        serverThreadName = null;
        message = null;
    }

    //creates a string timestamp for the log-event
    private String timeStamp = new SimpleDateFormat("dd.MM.yyyy, HH:mm:ss").format(new Date());

    //returns the AuditlogPDU data as String
    public String toString() {
        return timeStamp + "|" + this.pduType + "|Cn: " + userName + "|tnC: " + this.clientThreadName + "|tnS: "
                + this.serverThreadName + "|msg: " +  this.message;
    }


    //setter PduType
    private void setPduType(PduType pduType) {
        this.pduType = pduType;
    }

    //setter userName
    public void setUserName(String userName) {
        this.userName = userName;
    }

    //setter clientThreadName
    private void setClientThreadName(String threadName) {
        this.clientThreadName = threadName;
    }

    //setter serverThreadName
    private void setServerThreadName(String threadName) {
        this.serverThreadName = threadName;
    }

    //setter message text
    public void setMessage(String msg) {
        this.message = msg;
    }

    //getter for client username
    public String getUserName() {
        return userName;
    }

    //getter for message text
    public String getMessage() {
        return (message);
    }


    /**
     * create loggout event PDU
     *
     * @param receivedPdu received PDU (Logout-Request-PDU)
     * @return created PDU
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
     * Create a login Event PDU
     *
     * @param receivedPdu received PDU (Login-Request-PDU)
     * @return created PDU
     */
    public static AuditlogPDU createLoginEventPdu(ChatPDU receivedPdu, String listenerthreadname) {

        AuditlogPDU pdu = new AuditlogPDU();
        pdu.setPduType(PduType.LOGIN_EVENT);
        pdu.setUserName(receivedPdu.getUserName());
        pdu.setServerThreadName(Thread.currentThread().getName());
        pdu.setClientThreadName(listenerthreadname);
        return pdu;
    }

    /**
     * Create a Chat-Message-Event-PDU
     *
     * @param receivedPdu (Chat-Message-Request-PDU)
     * @return created PDU
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

    /**
     * Create a shutdwon-event-PDU
     *
     * @param receivedPdu (shutdown-event-PDU)
     * @return created PDU
     */
    public static AuditlogPDU createShutdownEventPdu(ChatPDU receivedPdu) {
        AuditlogPDU pdu = new AuditlogPDU();
        pdu.setPduType(PduType.SHUTDOWN_EVENT);
        pdu.setServerThreadName(Thread.currentThread().getName());
        pdu.setClientThreadName(receivedPdu.getClientThreadName());
        pdu.setMessage(receivedPdu.getMessage());
        return pdu;
    }


}