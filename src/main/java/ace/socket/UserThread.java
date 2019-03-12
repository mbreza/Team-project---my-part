package ace.socket;

import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.OutputStream;

import javax.net.ssl.SSLSocket;

import ace.domain.Packet;
import ace.server.UserDAO;

public class UserThread extends Thread {

	private SSLSocket socket;
	private ObjectInputStream input;
	private OutputStream os;
	private ObjectOutputStream output;
	private InputStream is;
	UserDAO userManager;
	String navigate;
	UserSocket userSocket;
	ChatSocket chatSocket;
	RoomSocket roomSocket;
	CheatSocket cheatSocket;
	public UserThread(SSLSocket socket) {
		this.socket = socket;
	}

	public void run() {
		try {
			userSocket = new UserSocket();
			chatSocket = new ChatSocket();
			roomSocket = new RoomSocket();
			cheatSocket = new CheatSocket();
			
			is = socket.getInputStream();
			input = new ObjectInputStream(is);
			os = socket.getOutputStream();
			output = new ObjectOutputStream(os);

			while (true) {

				Packet packet = (Packet) input.readObject();

				if (packet != null) {
					switch (packet.getType()) {
					case LOGIN:
						userSocket.logIn(packet, output);
						break;
					case CREATEUSER:
						userSocket.createUser(packet, output);
						break;
					case MESSAGESALL:
						chatSocket.messagesAll(packet, output);
						break;
					case FAILURE:
						break;
					case DISCONNECT:
						userSocket.userDisconnect(packet, output);
						break;
					case ADDTOCHEATROOM:
						roomSocket.addToRoom(packet, output);
						break;
					case CREATCHEATROOM:
						roomSocket.createRoom(packet, output);
						break;
					case DELETECHEATROOM:
						roomSocket.deleteRoom(packet, output);
						break;
					case LEAVEROOM:
						roomSocket.leaveRoom(packet, output);
						break;
					case START:
						cheatSocket.startGame(packet, output);
						break;
					case MOVE:
						cheatSocket.playerMove(packet, output);
						break;
					case MESSAGESROOM:
						chatSocket.messagesRoom(packet, output);
						break;
					default:
						break;
					}
				}
			}

		} catch (IOException e) {
			System.out.println(e);
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
	}

}
