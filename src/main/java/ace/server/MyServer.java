package ace.server;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import javax.net.ssl.SSLServerSocket;
import javax.net.ssl.SSLServerSocketFactory;
import javax.net.ssl.SSLSocket;

import ace.domain.ClientUser;
import ace.domain.CheatRoom;
import ace.socket.UserThread;



public class MyServer{
	
	 private static final int PORT = 1099;
	 public static ArrayList<ClientUser> clientList =  new ArrayList<ClientUser>();
	 public static ArrayList<CheatRoom> cheatList =  new ArrayList<CheatRoom>();
	 public static ArrayList<CheatRoom> cheatListOut =  new ArrayList<CheatRoom>();
	 public static ArrayList<String> names =  new ArrayList<String>();
	 public static final List<String> colorList = Arrays.asList("green", "red", "blue", "purple", "gold", "orange", "brown");
	 public static final String KEYSTORE_LOCATION = "/home/xyz/AceInTheHole/Ace_in_the_hole/src/main/resources/keystore";
	 public static final String KEYSTORE_PASSWORD = "wisniaposysa1";

	public static void main(String[] args) throws Exception {

		 System.setProperty("javax.net.ssl.keyStore", KEYSTORE_LOCATION);
		 System.setProperty("javax.net.ssl.keyStorePassword", KEYSTORE_PASSWORD);
		
		
        System.out.println("The server is running.");        
        SSLServerSocketFactory socketFactory = (SSLServerSocketFactory) SSLServerSocketFactory.getDefault();
        SSLServerSocket listener = (SSLServerSocket) socketFactory.createServerSocket(PORT);
        
        try {
            while (true) {
                new UserThread((SSLSocket) listener.accept()).start();
            }
        } finally {
        	listener.close(); 
        }

    }
    
}
