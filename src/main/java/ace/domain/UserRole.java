package ace.domain;

import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;

@Entity
@Table( name = "User_role" )
public class UserRole {

    @Id
    @Column(name="id_user_role")
    @GeneratedValue(strategy=GenerationType.AUTO)
	private int id_user_role;
	
    @Column(name="role")
	private String role;
    
	@OneToMany(mappedBy="userRole")
	private Set<User> user;
	
	public UserRole() {}

	public UserRole(Set<User> user) {
		super();
		this.user = user;
	}

	public int getId_user_role() {
		return id_user_role;
	}

	public void setId_user_role(int id_user_role) {
		this.id_user_role = id_user_role;
	}

	public String getRole() {
		return role;
	}

	public void setRole(String role) {
		this.role = role;
	}

	public Set<User> getUser() {
		return user;
	}

	public void setUser(Set<User> user) {
		this.user = user;
	}
}
