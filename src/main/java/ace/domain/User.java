package ace.domain;

import javax.persistence.*;


@Entity
@Table( name = "User_" )
public class User {
	
    @Id
    @Column(name="id_user")
    @GeneratedValue(strategy=GenerationType.AUTO)
	private int id_user;
    
    @Column(name="login")
	private String login;
    
    @Column(name="password")
	private String password;
  
    @Column(name="email")
	private String eamil;
    
    @Column(name="first_name")
	private String first_name;
    
    @Column(name="last_name")
	private String last_name;
    
    //@Lob
    @Column(name="photo")
	private byte[] photo;
    
    @ManyToOne
	@JoinColumn(name="id_user_role")
	private UserRole userRole;
	
	public User() {}

	public User( String login, String password, String eamil, UserRole userRole) {
		super();
		this.login = login;
		this.password = password;
		this.eamil = eamil;
		this.userRole = userRole;
	}

	public User(int id_user, String login, String password, String eamil, String first_name, String last_name,
			byte[] photo, UserRole userRole) {
		super();
		this.id_user = id_user;
		this.login = login;
		this.password = password;
		this.eamil = eamil;
		this.first_name = first_name;
		this.last_name = last_name;
		this.photo = photo;
		this.userRole = userRole;
	}

	public int getId_user() {
		return id_user;
	}
	
	public void setId_user(int id_users) {
		this.id_user = id_users;
	}
	
	public String getLogin() {
		return login;
	}
	
	public void setLogin(String login) {
		this.login = login;
	}
	
	public String getPassword() {
		return password;
	}
	
	public void setPassword(String password) {
		this.password = password;
	}
	
	public String getEamil() {
		return eamil;
	}

	public void setEamil(String eamil) {
		this.eamil = eamil;
	}

	public String getFirst_name() {
		return first_name;
	}
	
	public void setFirst_name(String first_name) {
		this.first_name = first_name;
	}
	
	public String getLast_name() {
		return last_name;
	}
	
	public void setLast_name(String last_name) {
		this.last_name = last_name;
	}
	
	public byte[] getPhoto() {
		return photo;
	}
	
	public void setPhoto(byte[] photo) {
		this.photo = photo;
	}

	public UserRole getUserRole() {
		return userRole;
	}

	public void setUserRole(UserRole userRole) {
		this.userRole = userRole;
	}

}