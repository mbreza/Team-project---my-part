package ace.domain;

import java.io.Serializable;
import java.util.ArrayList;

@SuppressWarnings("serial")
public class CheatRoom implements Serializable {
	private String name;
	private String owner;
	private ArrayList<ClientUser> list;
	private ArrayList<String> listNames;
	private ArrayList<Card> deck;
	private ArrayList<Card> cardsPlayed;
	private ArrayList<Card> cardsPickUp;
	private int playerOnGame;
	private Card allegedlyPlayed;
	private Card actuallyPlayed;
	private MoveType moveType;
	private int endGame = 0;
	

	
	
	public CheatRoom(String name, ArrayList<String> listNames, ArrayList<Card> deck, ArrayList<Card> cardsPlayed,
			int playerOnGame, MoveType moveType) {
		super();
		this.name = name;
		this.listNames = listNames;
		this.deck = deck;
		this.cardsPlayed = cardsPlayed;
		this.playerOnGame = playerOnGame;
		this.moveType = moveType;
	}

	public CheatRoom(String name, ArrayList<ClientUser> list, ArrayList<String> listNames, ArrayList<Card> deck) {
		super();
		this.name = name;
		this.list = list;
		this.listNames = listNames;
		this.deck = deck;
	}
	
	public CheatRoom(String name, ArrayList<String> listNames) {
		super();
		this.name = name;
		this.listNames = listNames;
	}
	
	public CheatRoom(String name, String owner, ArrayList<ClientUser> list, ArrayList<String> listNames, ArrayList<Card> deck) {
		super();
		this.name = name;
		this.owner = owner;
		this.list = list;
		this.listNames = listNames;
		this.deck = deck;
	}
	
	public CheatRoom(String name, String owner, ArrayList<String> listNames) {
		super();
		this.name = name;
		this.owner = owner;
		this.listNames = listNames;
	}
	
	public CheatRoom() {
		super();
	}
	
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
	
	public String getOwner() {
		return owner;
	}

	public void setOwner(String owner) {
		this.owner = owner;
	}

	public ArrayList<ClientUser> getList() {
		return list;
	}

	public void setList(ArrayList<ClientUser> list) {
		this.list = list;
	}

	public ArrayList<String> getListNames() {
		return listNames;
	}

	public void setListNames(ArrayList<String> listNames) {
		this.listNames = listNames;
	}

	public ArrayList<Card> getDeck() {
		return deck;
	}

	public void setDeck(ArrayList<Card> deck) {
		this.deck = deck;
	}

	public ArrayList<Card> getCardsPlayed() {
		return cardsPlayed;
	}

	public void setCardsPlayed(ArrayList<Card> cardsPlayed) {
		this.cardsPlayed = cardsPlayed;
	}

	public ArrayList<Card> getCardsPickUp() {
		return cardsPickUp;
	}

	public void setCardsPickUp(ArrayList<Card> cardsPickUp) {
		this.cardsPickUp = cardsPickUp;
	}

	public int getPlayerOnGame() {
		return playerOnGame;
	}

	public void setPlayerOnGame(int playerOnGame) {
		this.playerOnGame = playerOnGame;
	}

	public Card getAllegedlyPlayed() {
		return allegedlyPlayed;
	}

	public void setAllegedlyPlayed(Card allegedlyPlayed) {
		this.allegedlyPlayed = allegedlyPlayed;
	}

	public Card getActuallyPlayed() {
		return actuallyPlayed;
	}

	public void setActuallyPlayed(Card actuallyPlayed) {
		this.actuallyPlayed = actuallyPlayed;
	}

	public MoveType getMoveType() {
		return moveType;
	}

	public void setMoveType(MoveType moveType) {
		this.moveType = moveType;
	}

	public int getEndGame() {
		return endGame;
	}

	public void setEndGame(int endGame) {
		this.endGame = endGame;
	}

	
}
