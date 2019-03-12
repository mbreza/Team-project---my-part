package ace.server;

import java.util.List;

import ace.domain.User;

public interface UserDAO{
	public boolean addUser(String login, String password, String email, int ID);
	public List<User> getAllUsers();
	public boolean logIn(String login, String password);
}