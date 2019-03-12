package ace.domain;

import java.io.Serializable;

@SuppressWarnings("serial")
public class Card implements Serializable {
	private int number;
	private CardType type;
	private String  owner;

	public Card(int number, CardType type, String  owner) {
		super();
		this.number = number;
		this.type = type;
		this.owner = owner;
	}

	public int getNumber() {
		return number;
	}

	public void setNumber(int number) {
		this.number = number;
	}

	public CardType getType() {
		return type;
	}

	public void setType(CardType type) {
		this.type = type;
	}

	public String getOwner() {
		return owner;
	}

	public void setOwner(String owner) {
		this.owner = owner;
	}

	@Override
    public String toString() {
        return number + " " +type;
    }
}
