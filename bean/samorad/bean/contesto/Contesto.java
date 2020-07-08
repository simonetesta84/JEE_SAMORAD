package samorad.bean.contesto;

import java.io.Serializable;

public class Contesto implements Serializable {

	private boolean disableBottonAnnulla = true;
	
	private Integer idUser;
	private String nomeUser;
	private String cognomeUser;
	private double sogliaAllarme;
	
	private Integer idFile;

	//getter and setter
	public Integer getIdUser() {
		return idUser;
	}

	public void setIdUser(Integer idUser) {
		this.idUser = idUser;
	}

	public String getNomeUser() {
		return nomeUser;
	}

	public void setNomeUser(String nomeUser) {
		this.nomeUser = nomeUser;
	}

	public String getCognomeUser() {
		return cognomeUser;
	}

	public void setCognomeUser(String cognomeUser) {
		this.cognomeUser = cognomeUser;
	}

	public Integer getIdFile() {
		return idFile;
	}

	public void setIdFile(Integer idFile) {
		this.idFile = idFile;
	}

	public boolean isDisableBottonAnnulla() {
		return disableBottonAnnulla;
	}

	public void setDisableBottonAnnulla(boolean disableBottonAnnulla) {
		this.disableBottonAnnulla = disableBottonAnnulla;
	}

	public double getSogliaAllarme() {
		return sogliaAllarme;
	}

	public void setSogliaAllarme(double sogliaAllarme) {
		this.sogliaAllarme = sogliaAllarme;
	}
}
