package edu.hm.dako.chat.AuditlogServer;

import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.Date;

import edu.hm.dako.chat.common.ChatPDU;
import edu.hm.dako.chat.common.PduType;

/**
 * A PDU to collect the data. *
 *
 * @author Sebastian Waterloo, Matthias König, Justin Bitterlich, Sophie Wienhues
 * @version 1.0
 */
public class AuditlogPDU implements Serializable {

  // Commands or PDU types
  private PduType pduType;

  // Login name of clients
  private String userName;

  // Name of Client-Threads, that sends the request
  private String clientThreadName;

  // Name of Threads, that works on the server
  private String serverThreadName;

  // usedata
  private String message;

  // constructor
  public AuditlogPDU() {
    pduType = PduType.UNDEFINED;
    userName = null;
    clientThreadName = null;
    serverThreadName = null;
    message = null;
  }

  private String timeStamp = new SimpleDateFormat("dd.MM.yyyy, HH:mm:ss").format(new Date());

  // Auditlog toString method
  public String toString() {
    return timeStamp
        + "|"
        + this.pduType
        + "|tnC: "
        + this.clientThreadName
        + "|tnS: "
        + this.serverThreadName
        + "|msg: "
        + this.message;
  }

  /**
   * Sets the type of the Pdu
   *
   * @param pduType
   */
  public void setPduType(PduType pduType) {
    this.pduType = pduType;
  }

  /**
   * sets the username
   *
   * @param userName
   */
  public void setUserName(String userName) {
    this.userName = userName;
  }

  /**
   * sets the client thread name
   *
   * @param threadName
   */
  public void setClientThreadName(String threadName) {
    this.clientThreadName = threadName;
  }

  /**
   * sets the serve
   *
   * @param threadName
   */
  public void setServerThreadName(String threadName) {
    this.serverThreadName = threadName;
  }

  /**
   * sets the message
   *
   * @param msg
   */
  public void setMessage(String msg) {
    this.message = msg;
  }

  /**
   * getter for username
   *
   * @return username
   */
  public String getUserName() {
    return userName;
  }

  /**
   * getter for message
   *
   * @return message
   */
  public String getMessage() {
    return (message);
  }

  /**
   * Producing a logout event pdu
   *
   * @param receivedPdu received PDU (Logout-Request-PDU)
   * @return produced PDU
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
   * Producing a login event pdu
   *
   * @param receivedPdu received PDU (Login-Request-PDU)
   * @return produced PDU
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
   * Producing a Chat-Message-Event-PDU
   *
   * @param receivedPdu (Chat-Message-Request-PDU)
   * @return produced PDU
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
   * creates shutdown event
   *
   * @param receivedPdu
   * @return shutdown pdu
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
