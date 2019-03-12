package ace.game;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;

import ace.domain.Card;
import ace.domain.CardType;
import ace.domain.CheatRoom;
import ace.domain.ClientUser;
import ace.domain.MoveType;
import ace.domain.Packet;
import ace.domain.PacketType;
import ace.server.MyServer;

public class Cheat {

	int val;

	public ArrayList<Card> generateDeck() throws IOException {

		ArrayList<Card> deck = new ArrayList<Card>();

		for (int i = 9; i <= 14; i++) {
			deck.add(new Card(i, CardType.CLOVER, ""));
			deck.add(new Card(i, CardType.HEART, ""));
			deck.add(new Card(i, CardType.PIKE, ""));
			deck.add(new Card(i, CardType.TILE, ""));
		}
		return deck;
	}

	public void play(Packet packet) throws IOException {

		Packet packetOut = new Packet();
		packetOut.setType(PacketType.MOVE);

		ArrayList<String> names = new ArrayList<String>();
		ArrayList<Card> deckOut = new ArrayList<Card>();
		ArrayList<Card> cardsPlayedOut = new ArrayList<Card>();
		int onGame;

		for (Iterator<CheatRoom> iterator = MyServer.cheatListOut.iterator(); iterator.hasNext();) {
			CheatRoom cheatRoomOut = iterator.next();

			if (cheatRoomOut.getName().equals(packet.getCheatRoom().getName())) {

				cheatRoomOut.setActuallyPlayed(new Card(packet.getCheatRoom().getActuallyPlayed().getNumber(),
						packet.getCheatRoom().getActuallyPlayed().getType(), ""));
				cheatRoomOut.setAllegedlyPlayed(new Card(packet.getCheatRoom().getAllegedlyPlayed().getNumber(),
						packet.getCheatRoom().getAllegedlyPlayed().getType(), ""));
				cheatRoomOut.getCardsPlayed().add(new Card(packet.getCheatRoom().getActuallyPlayed().getNumber(),
						packet.getCheatRoom().getActuallyPlayed().getType(), ""));

				for (int i = cheatRoomOut.getDeck().size() - 1; i >= 0; i--) {
					if (cheatRoomOut.getDeck().get(i).getType()
							.equals(packet.getCheatRoom().getActuallyPlayed().getType())
							&& cheatRoomOut.getDeck().get(i).getNumber() == packet.getCheatRoom().getActuallyPlayed()
									.getNumber()) {
						cheatRoomOut.getDeck().remove(i);
					}
				}

				if (cheatRoomOut.getPlayerOnGame() == 3) {
					cheatRoomOut.setPlayerOnGame(0);
					onGame = 0;
				} else {
					cheatRoomOut.setPlayerOnGame(cheatRoomOut.getPlayerOnGame() + 1);
					onGame = cheatRoomOut.getPlayerOnGame();
				}

				for (Card card : cheatRoomOut.getDeck()) {
					deckOut.add(card);
				}

				for (String user : cheatRoomOut.getListNames()) {
					names.add(user);
				}

				for (Card card : cheatRoomOut.getCardsPlayed()) {
					cardsPlayedOut.add(card);
				}

				Boolean ifWinner = checkWinner(packet);

				CheatRoom cr = new CheatRoom();
				cr.setName(cheatRoomOut.getName());
				cr.setListNames(names);
				cr.setDeck(deckOut);
				cr.setCardsPlayed(cardsPlayedOut);
				cr.setPlayerOnGame(onGame);
				cr.setAllegedlyPlayed(new Card(packet.getCheatRoom().getAllegedlyPlayed().getNumber(),
						packet.getCheatRoom().getAllegedlyPlayed().getType(), ""));

				if (ifWinner) {

					cr.setMoveType(MoveType.WINNER);
				} else {

					cr.setMoveType(MoveType.PLAY);
				}
				packetOut.setCheatRoom(cr);
			}
		}

		for (CheatRoom cheatRoom : MyServer.cheatList) {
			if (cheatRoom.getName().equals(packet.getCheatRoom().getName())) {
				for (ClientUser clientList : cheatRoom.getList()) {
					clientList.getOutput().writeObject(packetOut);
				}
			}
		}

	}

	public void take(Packet packet) throws IOException {

		Packet packetOut = new Packet();
		packetOut.setType(PacketType.MOVE);
		ArrayList<String> names = new ArrayList<String>();
		ArrayList<Card> deckOut = new ArrayList<Card>();
		ArrayList<Card> cardsPlayedOut = new ArrayList<Card>();
		int onGame;

		for (Iterator<CheatRoom> iterator = MyServer.cheatListOut.iterator(); iterator.hasNext();) {
			CheatRoom cheatRoomOut = iterator.next();

			if (cheatRoomOut.getName().equals(packet.getCheatRoom().getName())) {
				if (cheatRoomOut.getCardsPlayed().size() > 0) {
					int counter = 0;

					for (int i = cheatRoomOut.getCardsPlayed().size() - 1; i >= 0; i--) {
						cheatRoomOut.getDeck().add(new Card(cheatRoomOut.getCardsPlayed().get(i).getNumber(),
								cheatRoomOut.getCardsPlayed().get(i).getType(), packet.getName()));
						cheatRoomOut.getCardsPlayed().remove(i);
						counter++;
						if (counter >= 3) {
							break;
						}
					}
				}

				if (cheatRoomOut.getPlayerOnGame() == 3) {
					cheatRoomOut.setPlayerOnGame(0);
					onGame = 0;
				} else {
					cheatRoomOut.setPlayerOnGame(cheatRoomOut.getPlayerOnGame() + 1);
					onGame = cheatRoomOut.getPlayerOnGame();
				}

				for (Card card : cheatRoomOut.getDeck()) {
					deckOut.add(card);
				}

				for (String user : cheatRoomOut.getListNames()) {
					names.add(user);
				}

				for (Card card : cheatRoomOut.getCardsPlayed()) {
					cardsPlayedOut.add(card);
				}

				CheatRoom cr = new CheatRoom();
				cr.setName(cheatRoomOut.getName());
				cr.setListNames(names);
				cr.setDeck(deckOut);
				cr.setCardsPlayed(cardsPlayedOut);
				cr.setPlayerOnGame(onGame);
				cr.setMoveType(MoveType.TAKE);

				packetOut.setCheatRoom(cr);
			}
		}

		for (CheatRoom cheatRoom : MyServer.cheatList) {
			if (cheatRoom.getName().equals(packet.getCheatRoom().getName())) {
				for (ClientUser clientList : cheatRoom.getList()) {
					clientList.getOutput().writeObject(packetOut);
				}
			}
		}

	}

	public void check(Packet packet) throws IOException {
		val = 0;
		Packet packetOut = new Packet();
		packetOut.setType(PacketType.MOVE);
		ArrayList<String> names = new ArrayList<String>();
		ArrayList<Card> deckOut = new ArrayList<Card>();
		ArrayList<Card> cardsPlayedOut = new ArrayList<Card>();
		int onGame;

		for (Iterator<CheatRoom> iterator = MyServer.cheatListOut.iterator(); iterator.hasNext();) {
			CheatRoom cheatRoomOut = iterator.next();

			if (cheatRoomOut.getName().equals(packet.getCheatRoom().getName())) {
				if (cheatRoomOut.getCardsPlayed().size() > 0) {
					if (cheatRoomOut.getActuallyPlayed().getNumber() == cheatRoomOut.getAllegedlyPlayed().getNumber()
							&& cheatRoomOut.getActuallyPlayed().getType()
									.equals(cheatRoomOut.getAllegedlyPlayed().getType())) {
						for (int i = cheatRoomOut.getCardsPlayed().size() - 1; i >= 0; i--) {
							cheatRoomOut.getDeck().add(new Card(cheatRoomOut.getCardsPlayed().get(i).getNumber(),
									cheatRoomOut.getCardsPlayed().get(i).getType(), packet.getName()));
							cheatRoomOut.getCardsPlayed().remove(i);
						}
					} else {
						int player = cheatRoomOut.getPlayerOnGame();
						if (player == 0) {
							player = 3;
						} else {
							player = player - 1;
						}

						for (int i = cheatRoomOut.getCardsPlayed().size() - 1; i >= 0; i--) {
							cheatRoomOut.getDeck()
									.add(new Card(cheatRoomOut.getCardsPlayed().get(i).getNumber(),
											cheatRoomOut.getCardsPlayed().get(i).getType(),
											cheatRoomOut.getListNames().get(player)));
							cheatRoomOut.getCardsPlayed().remove(i);
						}
					}

				}

				for (Card card : cheatRoomOut.getDeck()) {
					deckOut.add(card);
				}

				for (String user : cheatRoomOut.getListNames()) {
					names.add(user);
				}

				for (Card card : cheatRoomOut.getCardsPlayed()) {
					cardsPlayedOut.add(card);
				}
				onGame = cheatRoomOut.getPlayerOnGame();
				CheatRoom cr = new CheatRoom();
				cr.setName(cheatRoomOut.getName());
				cr.setListNames(names);
				cr.setDeck(deckOut);
				cr.setCardsPlayed(cardsPlayedOut);
				cr.setPlayerOnGame(onGame);

				Boolean ifWinner = checkWinner(packet);
				if (ifWinner) {
					cr.setMoveType(MoveType.END);
					val = 3;
					removeRoom(packet);
				} else {
					cr.setMoveType(MoveType.CHECK);
					cheatRoomOut.setEndGame(0);
				}
				packetOut.setCheatRoom(cr);
			}
		}

		for (CheatRoom cheatRoom : MyServer.cheatList) {
			if (cheatRoom.getName().equals(packet.getCheatRoom().getName())) {
				for (ClientUser clientList : cheatRoom.getList()) {
					clientList.getOutput().writeObject(packetOut);
				}
			}
		}

		if (val >= 3) {
			removeRoom(packet);
		}

	}

	private boolean checkWinner(Packet packet) throws IOException {

		int[] countArray = { 0, 0, 0, 0 };
		int count = 0;

		for (Iterator<CheatRoom> iterator = MyServer.cheatListOut.iterator(); iterator.hasNext();) {
			CheatRoom cheatRoomOut = iterator.next();

			if (cheatRoomOut.getName().equals(packet.getCheatRoom().getName())) {

				for (String name : cheatRoomOut.getListNames()) {

					for (Card cardList : cheatRoomOut.getDeck()) {
						if (cardList.getOwner().equals(name)) {
							countArray[count]++;
						}
					}
					count++;
				}

				if (countArray[0] != 0 && countArray[1] != 0 && countArray[2] != 0 && countArray[3] != 0) {
					return false;
				} else {
					return true;
				}

			}
		}
		return false;
	}

	public void winner(Packet packet) throws IOException {

		int onGame;
		ArrayList<String> names = new ArrayList<String>();

		for (Iterator<CheatRoom> iterator = MyServer.cheatListOut.iterator(); iterator.hasNext();) {
			CheatRoom cheatRoomOut = iterator.next();

			if (cheatRoomOut.getName().equals(packet.getCheatRoom().getName())) {
				val = cheatRoomOut.getEndGame() + 1;

				cheatRoomOut.setEndGame(val);

				if (val >= 3) {
					Packet packetOut = new Packet();
					packetOut.setType(PacketType.MOVE);
					onGame = cheatRoomOut.getPlayerOnGame();
					CheatRoom cr = new CheatRoom();
					cr.setPlayerOnGame(onGame);
					cr.setMoveType(MoveType.END);
					for (String user : cheatRoomOut.getListNames()) {
						names.add(user);
					}
					cr.setListNames(names);
					packetOut.setCheatRoom(cr);
					for (CheatRoom cheatRoom : MyServer.cheatList) {
						if (cheatRoom.getName().equals(packet.getCheatRoom().getName())) {
							for (ClientUser clientList : cheatRoom.getList()) {
								clientList.getOutput().writeObject(packetOut);
							}
						}
					}

				}
			}
		}
		if (val >= 3) {
			removeRoom(packet);
		}
	}

	private void removeRoom(Packet packet) throws IOException {
		for (Iterator<CheatRoom> iterator = MyServer.cheatList.iterator(); iterator.hasNext();) {
			CheatRoom value = iterator.next();
			if (value.getName().equals(packet.getCheatRoom().getName())) {
				iterator.remove();
			}
		}
		for (Iterator<CheatRoom> iterator = MyServer.cheatListOut.iterator(); iterator.hasNext();) {
			CheatRoom value = iterator.next();

			if (value.getName().equals(packet.getCheatRoom().getName())) {

				Packet packetOut = new Packet();
				packetOut.setRoomName(packet.getCheatRoom().getName());
				packetOut.setType(PacketType.DELETECHEATROOM);

				for (ClientUser client : MyServer.clientList) {
					client.getOutput().writeObject(packetOut);
				}

				iterator.remove();
			}
		}

	}

}
