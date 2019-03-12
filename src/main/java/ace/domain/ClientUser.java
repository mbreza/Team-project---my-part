package ace.domain;

import java.io.ObjectOutputStream;
import java.io.Serializable;

@SuppressWarnings("serial")
public class ClientUser implements Serializable{
	private String name;
	private ObjectOutputStream output;
	private String color;

	public ClientUser() {
		super();
	}

	public ClientUser(String name, ObjectOutputStream output, String color) {
		super();
		this.name = name;
		this.output = output;
		this.color = color;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public ObjectOutputStream getOutput() {
		return output;
	}

	public void setOutput(ObjectOutputStream output) {
		this.output = output;
	}

	public String getColor() {
		return color;
	}

	public void setColor(String color) {
		this.color = color;
	}
	
	
}
