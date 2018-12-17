package edu.hm.dako.chat.UdpAuditlogServer;

import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Vector;

import edu.hm.dako.chat.common.ChatPDU;
import edu.hm.dako.chat.common.ClientConversationStatus;
import edu.hm.dako.chat.common.PduType;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

/**
 * <p/>
 * Nachrichtenaufbau fuer Chat-Protokoll (fuer alle Nachrichtentypen: Request,
 * Response, Event, Confirm)
 *
 * @author Mandl
 */
public class AuditlogPDU implements Serializable {

    private static final long serialVersionUID = -6172619032079227585L;
    private static final Log log = LogFactory.getLog(AuditlogPDU.class);

    // Kommandos bzw. PDU-Typen
    private PduType pduType;

    // Login-Name des Clients
    private String userName;

    // Name des Clients, von dem ein Event initiiert wurde
    private String eventUserName;

    // Name des Client-Threads, der den Request absendet
    private String clientThreadName;

    // Name des Threads, der den Request im Server
    private String serverThreadName;

    // Zaehlt die uebertragenen Nachrichten eines Clients,
    // optional nutzbar fuer unsichere Transportmechanismen bearbeitet
    private long sequenceNumber;

    // Nutzdaten (eigentliche Chat-Nachricht in Textform)
    private String message;

    // Liste aller angemeldeten User
    private Vector<String> clients;

    // Zeit in Nanosekunden, die der Server fuer die komplette Bearbeitung einer
    // Chat-Nachricht benoetigt (inkl. kompletter Verteilung an alle
    // angemeldeten User).
    // Diese Zeit wird vom Server vor dem Absenden der Response eingetragen
    private long serverTime;

    // Conversation-Status aus Sicht des Servers
    private ClientConversationStatus clientStatus;

    // Fehlercode, derzeit nur 1 Fehlercode definiert
    private int errorCode;
    public final static int NO_ERROR = 0;
    public final static int LOGIN_ERROR = 1;

    // Daten zur statistischen Auswertung, die mit der Logout-Response-PDU
    // mitgesendet werden:

    // Anzahl der verarbeiteten Chat-Nachrichten des Clients
    private long numberOfReceivedChatMessages;

    // Anzahl an gesendeten Events an andere Clients
    private long numberOfSentEvents;

    // Anzahl an empfangenen Bestaetigungen der anderen Clients
    private long numberOfReceivedConfirms;

    // Anzahl verlorener bzw. nicht zugestellten Bestaetigungen anderer Clients
    private long numberOfLostConfirms;

    // Anzahl der Wiederholungen von Nachrichten (nur bei verbindungslosen
    // Transportsystemen)
    private long numberOfRetries;

    public AuditlogPDU() {
        pduType = PduType.UNDEFINED;
        userName = null;
        eventUserName = null;
        clientThreadName = null;
        serverThreadName = null;
        sequenceNumber = 0;
        errorCode = NO_ERROR;
        message = null;
        serverTime = 0;
        clients = null;
        clientStatus = ClientConversationStatus.UNREGISTERED;
        numberOfReceivedChatMessages = 0;
        numberOfSentEvents = 0;
        numberOfReceivedConfirms = 0;
        numberOfLostConfirms = 0;
        numberOfRetries = 0;
    }


    String timeStamp = new SimpleDateFormat("dd.MM.yyyy, HH:mm:ss").format(new Date());

    public String toString() {
        return timeStamp + "|" + this.pduType + "|tnC: " + this.clientThreadName + "|tnS: "
                + this.serverThreadName + "|msg: " +  this.message;
    }


    public void setClients(Vector<String> clients) {
        this.clients = clients;
    }

    public void setPduType(PduType pduType) {
        this.pduType = pduType;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public void setEventUserName(String name) {
        this.eventUserName = name;
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

    public void setServerTime(long time) {
        this.serverTime = time;
    }

    public void setSequenceNumber(long sequenceNumber) {
        this.sequenceNumber = sequenceNumber;
    }

    public PduType getPduType() {
        return pduType;
    }

    public Vector<String> getClients() {
        return clients;
    }

    public String getUserName() {
        return userName;
    }

    public String getEventUserName() {
        return eventUserName;
    }

    public String getClientThreadName() {
        return (clientThreadName);
    }

    public String getServerThreadName() {
        return (serverThreadName);
    }

    public String getMessage() {
        return (message);
    }

    public long getServerTime() {
        return (serverTime);
    }

    public long getSequenceNumber() {
        return (sequenceNumber);
    }

    public ClientConversationStatus getClientStatus() {
        return clientStatus;
    }

    public void setClientStatus(ClientConversationStatus clientStatus) {
        this.clientStatus = clientStatus;
    }

    public long getNumberOfSentEvents() {
        return (numberOfSentEvents);
    }

    public void setNumberOfSentEvents(long nr) {
        this.numberOfSentEvents = nr;
    }

    public long getNumberOfReceivedConfirms() {
        return (numberOfReceivedConfirms);
    }

    public void setNumberOfReceivedEventConfirms(long nr) {
        this.numberOfReceivedConfirms = nr;
    }

    public long getNumberOfLostConfirms() {
        return (numberOfLostConfirms);
    }

    public void setNumberOfLostEventConfirms(long nr) {
        this.numberOfLostConfirms = nr;
    }

    public long getNumberOfRetries() {
        return (numberOfRetries);
    }





    /**
     * Erzeugen einer Logout-Event-PDU
     *
     * @param userName
     *          Client, der Logout-Request-PDU gesendet hat
     * @param receivedPdu
     *          Empfangene PDU (Logout-Request-PDU)
     * @return Erzeugte PDU
     */
    public static AuditlogPDU createLogoutEventPdu(String userName, ChatPDU receivedPdu) {

        AuditlogPDU pdu = new AuditlogPDU();
        pdu.setPduType(PduType.LOGOUT_EVENT);
        pdu.setUserName(userName);
        pdu.setEventUserName(userName);
        pdu.setServerThreadName(Thread.currentThread().getName());
        pdu.setClientThreadName(receivedPdu.getClientThreadName());
        pdu.setClientStatus(ClientConversationStatus.UNREGISTERING);
        return pdu;
    }

    /**
     * Erzeugen einer Login-Event-PDU
     *
     * @param receivedPdu
     *          Empfangene PDU (Login-Request-PDU)
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
     * @param userName
     *          Client, der Chat-Message-Request-PDU gesendet hat
     * @param receivedPdu
     *          (Chat-Message-Request-PDU)
     * @return Erzeugte PDU
     */
    public static AuditlogPDU createChatMessageEventPdu(String userName, ChatPDU receivedPdu) {

        AuditlogPDU pdu = new AuditlogPDU();
        pdu.setPduType(PduType.CHAT_MESSAGE_EVENT);
        pdu.setServerThreadName(Thread.currentThread().getName());
        pdu.setClientThreadName(receivedPdu.getClientThreadName());
        pdu.setUserName(userName);
        pdu.setEventUserName(receivedPdu.getUserName());
        pdu.setSequenceNumber(receivedPdu.getSequenceNumber());
        pdu.setClientStatus(ClientConversationStatus.REGISTERED);
        pdu.setMessage(receivedPdu.getMessage());
        return pdu;
    }



}