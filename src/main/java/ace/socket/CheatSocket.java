package ace.socket;

import java.io.IOException;
import java.io.ObjectOutputStream;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;

import ace.domain.Card;
import ace.domain.CardType;
import ace.domain.CheatRoom;
import ace.domain.ClientUser;
import ace.domain.MoveType;
import ace.domain.Packet;
import ace.domain.PacketType;
import ace.game.Cheat;
import ace.server.MyServer;

public class CheatSocket {

	public void startGame(Packet packet, ObjectOutputStream output) throws IOException {
		Packet packetOut = new Packet();
		Cheat cheat =  new Cheat();
		ArrayList<Card> cardsPlayed = new ArrayList<Card>();
		ArrayList<Card> cardsPickUp = new ArrayList<Card>();
		String gofirst = null;
		int j = 0;
		int index9 = 0;
		
		for (Iterator<CheatRoom> iterator1 = MyServer.cheatListOut.iterator(); iterator1.hasNext();) {
			CheatRoom cheatRoom = iterator1.next();
			if (packet.getRoomName().equals(cheatRoom.getName())) {
				cheatRoom.setCardsPlayed(cardsPlayed);
				cheatRoom.setCardsPickUp(cardsPickUp);
				cheatRoom.setDeck(cheat.generateDeck());
				Collections.shuffle(cheatRoom.getDeck());
				for (String playerName : cheatRoom.getListNames()) {
					for (int i = 0; i < 6; i++) {
						if (cheatRoom.getDeck().get(j).getNumber() == 9 && cheatRoom.getDeck().get(j).getType().equals(CardType.HEART)) {
							gofirst = playerName;
							index9 = j;
						}
						cheatRoom.getDeck().get(j).setOwner(playerName);
						j++;
					}
				}
				cheatRoom.getDeck().remove(index9);
				
				ArrayList<String> names = new ArrayList<String>();
				ArrayList<Card> cardListOut =  new ArrayList<Card>();
				
				for (Card card : cheatRoom.getDeck()) {
					cardListOut.add(card);
				}
				
				for (String user : cheatRoom.getListNames()) {
					names.add(user);
				}
				names.remove(gofirst);
				names.add(0, gofirst);
				cheatRoom.setListNames(names);
				
				cheatRoom.setEndGame(0);
				cheatRoom.setPlayerOnGame(1);			
				
				packetOut.setCheatRoom(new CheatRoom(cheatRoom.getName(), names, cardListOut, cheatRoom.getCardsPlayed(),cheatRoom.getPlayerOnGame(), MoveType.START));
				
			}
		
		}
		// nadanie typu
		packetOut.setType(PacketType.START);
		// wysłanie pokoju
		for (CheatRoom cheatRoom : MyServer.cheatList) {
			if (cheatRoom.getName().equals(packet.getRoomName())) {
				for (ClientUser clientList : cheatRoom.getList()) {
					clientList.getOutput().writeObject(packetOut);
				}
			}
		}

	}

	public void playerMove(Packet packet, ObjectOutputStream output) throws IOException {
		Cheat cheat = new Cheat();

		switch (packet.getCheatRoom().getMoveType()) {
		case CHECK:
			cheat.check(packet);
			break;
		case PLAY:
			cheat.play(packet);
			break;
		case TAKE:
			cheat.take(packet);
			break;
		case WINNER:
			cheat.winner(packet);
			break;
		default:
			break;
		}

	}
}
