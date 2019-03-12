package ace.socket;

import java.io.IOException;
import java.io.ObjectOutputStream;
import java.util.ArrayList;
import java.util.Iterator;

import ace.domain.ClientUser;
import ace.domain.Packet;
import ace.domain.PacketType;
import ace.domain.CheatRoom;
import ace.game.Cheat;
import ace.server.MyServer;

public class RoomSocket {

	public void createRoom(Packet packet, ObjectOutputStream output) throws IOException {
		Cheat cheat = new Cheat();
		Packet packetOut = new Packet();
		ArrayList<ClientUser> clientGame = new ArrayList<ClientUser>();
		ArrayList<String> namesGame = new ArrayList<String>();

		// weryfikacja czy nazwa zajeta
		for (CheatRoom cheatRoom : MyServer.cheatList) {
			if (packet.getRoomName().equals(cheatRoom.getName())) {
				packetOut.setType(PacketType.FAILURE);
				output.writeObject(packetOut);
				return;
			}
		}

		// odnlezienie osoby w liscie o danej nazwie
		for (ClientUser client : MyServer.clientList) {
			if (packet.getName().equals(client.getName())) {
				clientGame.add(client);
			}
		}

		namesGame.add(packet.getName());
		
		// dodanie do list pokojow
		MyServer.cheatList.add(new CheatRoom(packet.getRoomName(), packet.getName(), clientGame, namesGame, cheat.generateDeck()));
		MyServer.cheatListOut.add(new CheatRoom(packet.getRoomName(), packet.getName(), namesGame));

		packetOut.setType(PacketType.CREATCHEATROOM);
		packetOut.setCheatRoom(new CheatRoom(packet.getRoomName(), packet.getName(), namesGame));
		packetOut.setName(packet.getName());

		for (ClientUser client : MyServer.clientList) {
			client.getOutput().writeObject(packetOut);
		}
	}

	public void addToRoom(Packet packet, ObjectOutputStream output) throws IOException {

		Packet packetOut = new Packet();
		ClientUser cl = new ClientUser();
		CheatRoom pr = new CheatRoom();

		// weryfikacja czy pokoj pelny
		for (CheatRoom pokerRoom : MyServer.cheatList) {
			if (packet.getRoomName().equals(pokerRoom.getName())) {
				if (pokerRoom.getListNames().size() > 3) {
					packetOut.setType(PacketType.FAILURE);
					output.writeObject(packetOut);
					return;
				}
			}
		}

		// odnlezienie osoby w liscie o danej nazwie
		for (ClientUser client : MyServer.clientList) {
			if (packet.getName().equals(client.getName())) {
				cl = client;
			}
		}

		// odnalezienie pokoju i dodanie danej osoby do listy
		for (CheatRoom cheatRoom1 : MyServer.cheatList) {
			if (packet.getRoomName().equals(cheatRoom1.getName())) {
				cheatRoom1.getListNames().add(packet.getName());
				cheatRoom1.getList().add(cl);
				pr = cheatRoom1;
			}
		}

		packetOut.setType(PacketType.ADDTOCHEATROOM);
		packetOut.setName(packet.getName());

		ArrayList<String> names = new ArrayList<String>();

		for (String user : pr.getListNames()) {
			names.add(user);
		}

		packetOut.setCheatRoom(new CheatRoom(pr.getName(), names));
		
		
		for (CheatRoom cheatRoom : MyServer.cheatList) {
			if(cheatRoom.getName().equals(packet.getRoomName())) {
				for(ClientUser clientList : cheatRoom.getList()) {
					clientList.getOutput().writeObject(packetOut);
				}
			}
		}

	}

	public void deleteRoom(Packet packet, ObjectOutputStream output) throws IOException {

		for (Iterator<CheatRoom> iterator = MyServer.cheatList.iterator(); iterator.hasNext();) {
			CheatRoom value = iterator.next();
			if (value.getName().equals(packet.getRoomName())) {
				iterator.remove();
			}
		}

		for (Iterator<CheatRoom> iterator = MyServer.cheatListOut.iterator(); iterator.hasNext();) {
			CheatRoom value = iterator.next();

			if (value.getName().equals(packet.getRoomName())) {

				Packet packetOut = new Packet();
				packetOut.setRoomName(packet.getRoomName());
				packetOut.setType(PacketType.DELETECHEATROOM);

				for (ClientUser client : MyServer.clientList) {
					client.getOutput().writeObject(packetOut);
				}

				iterator.remove();
			}
		}

	}

	public void leaveRoom(Packet packet, ObjectOutputStream output) throws IOException {
		
		
		Packet packetOut = new Packet();
		packetOut.setRoomName(packet.getRoomName());
		packetOut.setName(packet.getName());		
		packetOut.setType(PacketType.LEAVEROOM);

		for (CheatRoom cheatRoom : MyServer.cheatList) {
			if(cheatRoom.getName().equals(packet.getRoomName())) {
				for(ClientUser clientList : cheatRoom.getList()) {
					clientList.getOutput().writeObject(packetOut);
				}
			}
		}
		

		for (Iterator<CheatRoom> iterator1 = MyServer.cheatList.iterator(); iterator1.hasNext();) {
			CheatRoom value1 = iterator1.next();
			if (value1.getName().equals(packet.getRoomName())) {

				for (Iterator<ClientUser> iterator2 = value1.getList().iterator(); iterator2.hasNext();) {
					ClientUser value2 = iterator2.next();
					if (value2.getName().equals(packet.getName())) {
						iterator2.remove();
					}
				}

				for (Iterator<String> iterator3 = value1.getListNames().iterator(); iterator3.hasNext();) {
					String value3 = iterator3.next();
					if (value3.equals(packet.getName())) {
						iterator3.remove();
					}
				}

			}
		}

	}

}
