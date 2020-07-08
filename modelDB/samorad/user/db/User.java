package samorad.user.db;

import java.io.Serializable;
import javax.persistence.*;


/**
 * The persistent class for the user database table.
 * 
 */
@Entity
@NamedQuery(name="User.findAll", query="SELECT u FROM User u")
public class User implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@Column(name="id_kp_user")
	private int idKpUser;

	private String cognomeUser;

	private String nomeUser;

	private String password;

	@Column(name="soglia_allarme")
	private double sogliaAllarme;

	private String username;

	public User() {
	}

	public int getIdKpUser() {
		return this.idKpUser;
	}

	public void setIdKpUser(int idKpUser) {
		this.idKpUser = idKpUser;
	}

	public String getCognomeUser() {
		return this.cognomeUser;
	}

	public void setCognomeUser(String cognomeUser) {
		this.cognomeUser = cognomeUser;
	}

	public String getNomeUser() {
		return this.nomeUser;
	}

	public void setNomeUser(String nomeUser) {
		this.nomeUser = nomeUser;
	}

	public String getPassword() {
		return this.password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public double getSogliaAllarme() {
		return this.sogliaAllarme;
	}

	public void setSogliaAllarme(double sogliaAllarme) {
		this.sogliaAllarme = sogliaAllarme;
	}

	public String getUsername() {
		return this.username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

}