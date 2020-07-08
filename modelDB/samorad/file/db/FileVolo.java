package samorad.file.db;

import java.io.Serializable;
import javax.persistence.*;
import java.sql.Timestamp;


/**
 * The persistent class for the file_volo database table.
 * 
 */
@Entity
@Table(name="file_volo")
@NamedQuery(name="FileVolo.findAll", query="SELECT f FROM FileVolo f")
public class FileVolo implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@Column(name="id_kp_file_volo")
	private int idKpFileVolo;

	@Column(name="data_ins_volo_in_data_base")
	private Timestamp dataInsVoloInDataBase;

	@Column(name="data_volo_end")
	private Timestamp dataVoloEnd;

	@Column(name="data_volo_start")
	private Timestamp dataVoloStart;

	@Column(name="durata_volo")
	private String durataVolo;

	@Column(name="id_ks_user")
	private int idKsUser;

	@Column(name="n_coor_geo")
	private int nCoorGeo;

	@Column(name="n_eventi")
	private int nEventi;

	@Column(name="nome_file")
	private String nomeFile;

	public FileVolo() {
	}

	public int getIdKpFileVolo() {
		return this.idKpFileVolo;
	}

	public void setIdKpFileVolo(int idKpFileVolo) {
		this.idKpFileVolo = idKpFileVolo;
	}

	public Timestamp getDataInsVoloInDataBase() {
		return this.dataInsVoloInDataBase;
	}

	public void setDataInsVoloInDataBase(Timestamp dataInsVoloInDataBase) {
		this.dataInsVoloInDataBase = dataInsVoloInDataBase;
	}

	public Timestamp getDataVoloEnd() {
		return this.dataVoloEnd;
	}

	public void setDataVoloEnd(Timestamp dataVoloEnd) {
		this.dataVoloEnd = dataVoloEnd;
	}

	public Timestamp getDataVoloStart() {
		return this.dataVoloStart;
	}

	public void setDataVoloStart(Timestamp dataVoloStart) {
		this.dataVoloStart = dataVoloStart;
	}

	public String getDurataVolo() {
		return this.durataVolo;
	}

	public void setDurataVolo(String durataVolo) {
		this.durataVolo = durataVolo;
	}

	public int getIdKsUser() {
		return this.idKsUser;
	}

	public void setIdKsUser(int idKsUser) {
		this.idKsUser = idKsUser;
	}

	public int getNCoorGeo() {
		return this.nCoorGeo;
	}

	public void setNCoorGeo(int nCoorGeo) {
		this.nCoorGeo = nCoorGeo;
	}

	public int getNEventi() {
		return this.nEventi;
	}

	public void setNEventi(int nEventi) {
		this.nEventi = nEventi;
	}

	public String getNomeFile() {
		return this.nomeFile;
	}

	public void setNomeFile(String nomeFile) {
		this.nomeFile = nomeFile;
	}

}