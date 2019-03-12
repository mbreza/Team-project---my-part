package ace.socket;

import java.io.IOException;
import java.io.ObjectOutputStream;
import java.util.Iterator;
import java.util.List;
import java.util.Random;

import ace.domain.ClientUser;
import ace.domain.Packet;
import ace.domain.PacketType;
import ace.domain.User;
import ace.server.MyServer;
import ace.server.UserDAO;
import ace.service.UserManager;

public class UserSocket {

	UserDAO userManager;

	public void logIn(Packet packet, ObjectOutputStream output) throws IOException {

		userManager = new UserManager();

		Packet packetOut = new Packet();
		Packet packetOnline = new Packet();

		if (userManager.logIn(packet.getName(), packet.getPassword())) {

			Random rand = new Random();
			int randomIndex = rand.nextInt(MyServer.colorList.size());
			String randomElement = MyServer.colorList.get(randomIndex);
			MyServer.clientList.add(new ClientUser(packet.getName(), output, randomElement));

			packetOut.setList(MyServer.names);
			packetOut.setPokerList(MyServer.cheatListOut);
			packetOut.setType(PacketType.LOGIN);
			output.writeObject(packetOut);

			MyServer.names.add(packet.getName());
			packetOnline.setName(packet.getName());
			packetOnline.setType(PacketType.ONLINE);

			for (ClientUser client : MyServer.clientList) {
				client.getOutput().writeObject(packetOnline);
			}
			
			
			
			
		} else {

			packetOut.setType(PacketType.FAILURE);
			output.writeObject(packetOut);
		}

	}

	public void createUser(Packet packet, ObjectOutputStream output) throws IOException {

		userManager = new UserManager();

		Packet packetOut = new Packet();
		String name = packet.getName();
		String password = packet.getPassword();
		String email = packet.getEmail();

		if (!email.matches(".*@.*\\..*")) {
			packetOut.setType(PacketType.FAILURE);
			output.writeObject(packetOut);
			return;
		}

		List<User> users = userManager.getAllUsers();

		for (User user : users) {

			if (name.equals(user.getLogin())) {
				packetOut.setType(PacketType.FAILURE);
				output.writeObject(packetOut);
				return;
			}
			if (email.equals(user.getEamil())) {
				packetOut.setType(PacketType.FAILURE);
				output.writeObject(packetOut);
				return;
			}
		}

		if (userManager.addUser(name, password, email, 2)) {
			packetOut.setType(PacketType.CREATEUSER);
			output.writeObject(packetOut);
		} else {
			packetOut.setType(PacketType.FAILURE);
			output.writeObject(packetOut);
		}

	}

	public void userDisconnect(Packet packet, ObjectOutputStream output) throws IOException {

		for (Iterator<ClientUser> iterator = MyServer.clientList.iterator(); iterator.hasNext();) {
			ClientUser value = iterator.next();
			if (value.getName().equals(packet.getName())) {
				iterator.remove();
			} 
		}

		for (Iterator<String> iterator = MyServer.names.iterator(); iterator.hasNext();) {
			String value = iterator.next();

			if (value.equals(packet.getName())) {

				Packet packetOut = new Packet();
				packetOut.setName(packet.getName());
				packetOut.setType(PacketType.OFFLINE);

				for (ClientUser client : MyServer.clientList) {
					client.getOutput().writeObject(packetOut);
				}

				iterator.remove();
			}
		}

	}

}
