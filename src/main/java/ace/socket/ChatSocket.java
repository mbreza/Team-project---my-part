package ace.socket;

import java.io.IOException;
import java.io.ObjectOutputStream;

import ace.domain.CheatRoom;
import ace.domain.ClientUser;
import ace.domain.Packet;
import ace.domain.PacketType;
import ace.server.MyServer;

public class ChatSocket {

	public void messagesAll(Packet packet, ObjectOutputStream output) throws IOException {

		Packet packetOut = new Packet();
		packetOut.setName(packet.getName());

		for (ClientUser client1 : MyServer.clientList) {
			if (client1.getName().equals(packet.getName())) {
				packetOut.setColor(client1.getColor());
			}

		}
		packetOut.setMessage(packet.getMessage());
		packetOut.setType(PacketType.MESSAGESALL);

		for (ClientUser client : MyServer.clientList) {
			client.getOutput().writeObject(packetOut);
		}
	}
	
	public void messagesRoom(Packet packet, ObjectOutputStream output) throws IOException {

		Packet packetOut = new Packet();
		packetOut.setName(packet.getName());
		packetOut.setMessage(packet.getMessage());
		packetOut.setType(PacketType.MESSAGESROOM);
		
		for (ClientUser client : MyServer.clientList) {
			if (client.getName().equals(packet.getName())) {
				packetOut.setColor(client.getColor());
			}

		}
		
		for (CheatRoom cheatRoom : MyServer.cheatList) {
			if(cheatRoom.getName().equals(packet.getRoomName())) {
				for(ClientUser clientList : cheatRoom.getList()) {
					clientList.getOutput().writeObject(packetOut);
				}
			}
		}
	}	

}
