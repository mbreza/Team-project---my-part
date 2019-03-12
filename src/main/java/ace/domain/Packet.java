package ace.domain;

import java.io.Serializable;
import java.util.ArrayList;

@SuppressWarnings("serial")
public class Packet implements Serializable{
	
	//TYP
	private PacketType type;
	
	//logowanie, tworzenie userow
	private String name;
	private String password;
	private String email;
	
	//czat
	private String message;
	private ArrayList<String> list;
	private String color;
	
	//pokoje gier
	private String roomName;
	private ArrayList<CheatRoom> pokerList;
	private CheatRoom cheatRoom;

	//usless
	private int count;
	
	public Packet() {}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}
	
	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public PacketType getType() {
		return type;
	}

	public void setType(PacketType type) {
		this.type = type;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public int getCount() {
		return count;
	}

	public void setCount(int count) {
		this.count = count;
	}

	public ArrayList<String> getList() {
		return list;
	}

	public void setList(ArrayList<String> list) {
		this.list = list;
	}

	public String getColor() {
		return color;
	}

	public void setColor (String color) {
		this.color = color;
	}

	public String getRoomName() {
		return roomName;
	}

	public void setRoomName(String roomName) {
		this.roomName = roomName;
	}

	public ArrayList<CheatRoom> getPokerList() {
		return pokerList;
	}

	public void setPokerList(ArrayList<CheatRoom> pokerList) {
		this.pokerList = pokerList;
	}

	public CheatRoom getCheatRoom() {
		return cheatRoom;
	}

	public void setCheatRoom(CheatRoom cheatRoom) {
		this.cheatRoom = cheatRoom;
	}
	
	
	
}
