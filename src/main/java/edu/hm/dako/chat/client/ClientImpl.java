package edu.hm.dako.chat.client;

import edu.hm.dako.chat.common.ExceptionHandler;
import edu.hm.dako.chat.common.SystemConstants;

/**
 * <p/>
 * Verwaltet eine Verbindung zum Server.
 *
 * @author Mandl
 */
public class ClientImpl extends AbstractChatClient {

	/**
	 * Konstruktor
	 * 
	 * @param userInterface
	 *          Schnittstelle zum User-Interface
	 * @param serverPort
	 *          Portnummer des Servers
	 * @param remoteServerAddress
	 *          IP-Adresse/Hostname des Servers
	 */

	public ClientImpl(ClientUserInterface userInterface, int serverPort,
			String remoteServerAddress, String serverType) {

		super(userInterface, serverPort, remoteServerAddress);
		this.serverPort = serverPort;
		this.remoteServerAddress = remoteServerAddress;

		Thread.currentThread().setName("Client"); //TODO: Serververbindung: Name des Client wird zu Thread zugeordnet und Name per getName() geholt (mk)
		threadName = Thread.currentThread().getName();

		try {
			// Simple TCP Server erzeugen
			messageListenerThread = new SimpleMessageListenerThreadImpl(userInterface,
						connection, sharedClientData);

			messageListenerThread.start(); //TODO: Serververbindung: Thread clientseitig starten (mk)

		} catch (Exception e) {
			ExceptionHandler.logException(e);
		}
	}
}