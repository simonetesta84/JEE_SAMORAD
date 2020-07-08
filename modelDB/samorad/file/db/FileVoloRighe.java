package samorad.file.db;

import java.io.Serializable;
import javax.persistence.*;
import java.sql.Timestamp;


/**
 * The persistent class for the file_volo_righe database table.
 * 
 */
@Entity
@Table(name="file_volo_righe")
@NamedQuery(name="FileVoloRighe.findAll", query="SELECT f FROM FileVoloRighe f")
public class FileVoloRighe implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@Column(name="id_kp_file_volo_righe")
	private int idKpFileVoloRighe;

	@Column(name="chi_sq")
	private double chiSq;

	@Column(name="coord_geo_ln")
	private double coordGeoLn;

	@Column(name="coord_geo_lt")
	private double coordGeoLt;

	private double cr;

	private double cs;

	private double ct;

	@Column(name="ct_cs")
	private double ctCs;

	@Column(name="ct_eth")
	private double ctEth;

	@Column(name="ct_eu")
	private double ctEu;

	@Column(name="data_volo")
	private Timestamp dataVolo;

	private double dcr;

	private double dcs;

	private double dem;

	private double deth;

	private double deu;

	private double dk;

	@Column(name="du_rn")
	private double duRn;

	private double eth;

	private double eu;

	@Column(name="eu_rn")
	private double euRn;

	@Column(name="h_altezza")
	private double hAltezza;

	@Column(name="h_stp")
	private double hStp;

	@Column(name="id_ks_file_volo_righe")
	private int idKsFileVoloRighe;

	@Column(name="index_data_volo")
	private int indexDataVolo;

	private double k;

	@Column(name="ora_g_volo")
	private double oraGVolo;

	private double pres;

	@Column(name="raggio_cerchio_coordinata")
	private double raggioCerchioCoordinata;

	private double rn;

	private double scs;

	private double sdcs;

	private double sdeu;

	private double sdk;

	private double sedth;

	private double seth;

	private double seu;

	private double sk;

	private double temp;

	@Column(name="tr_tot")
	private double trTot;

	@Column(name="valore_soglia_allarme")
	private double valoreSogliaAllarme;

	private double vel;

	@Column(name="volo_desc")
	private String voloDesc;

	private double xumt;

	private double yumt;

	public FileVoloRighe() {
	}

	public int getIdKpFileVoloRighe() {
		return this.idKpFileVoloRighe;
	}

	public void setIdKpFileVoloRighe(int idKpFileVoloRighe) {
		this.idKpFileVoloRighe = idKpFileVoloRighe;
	}

	public double getChiSq() {
		return this.chiSq;
	}

	public void setChiSq(double chiSq) {
		this.chiSq = chiSq;
	}

	public double getCoordGeoLn() {
		return this.coordGeoLn;
	}

	public void setCoordGeoLn(double coordGeoLn) {
		this.coordGeoLn = coordGeoLn;
	}

	public double getCoordGeoLt() {
		return this.coordGeoLt;
	}

	public void setCoordGeoLt(double coordGeoLt) {
		this.coordGeoLt = coordGeoLt;
	}

	public double getCr() {
		return this.cr;
	}

	public void setCr(double cr) {
		this.cr = cr;
	}

	public double getCs() {
		return this.cs;
	}

	public void setCs(double cs) {
		this.cs = cs;
	}

	public double getCt() {
		return this.ct;
	}

	public void setCt(double ct) {
		this.ct = ct;
	}

	public double getCtCs() {
		return this.ctCs;
	}

	public void setCtCs(double ctCs) {
		this.ctCs = ctCs;
	}

	public double getCtEth() {
		return this.ctEth;
	}

	public void setCtEth(double ctEth) {
		this.ctEth = ctEth;
	}

	public double getCtEu() {
		return this.ctEu;
	}

	public void setCtEu(double ctEu) {
		this.ctEu = ctEu;
	}

	public Timestamp getDataVolo() {
		return this.dataVolo;
	}

	public void setDataVolo(Timestamp dataVolo) {
		this.dataVolo = dataVolo;
	}

	public double getDcr() {
		return this.dcr;
	}

	public void setDcr(double dcr) {
		this.dcr = dcr;
	}

	public double getDcs() {
		return this.dcs;
	}

	public void setDcs(double dcs) {
		this.dcs = dcs;
	}

	public double getDem() {
		return this.dem;
	}

	public void setDem(double dem) {
		this.dem = dem;
	}

	public double getDeth() {
		return this.deth;
	}

	public void setDeth(double deth) {
		this.deth = deth;
	}

	public double getDeu() {
		return this.deu;
	}

	public void setDeu(double deu) {
		this.deu = deu;
	}

	public double getDk() {
		return this.dk;
	}

	public void setDk(double dk) {
		this.dk = dk;
	}

	public double getDuRn() {
		return this.duRn;
	}

	public void setDuRn(double duRn) {
		this.duRn = duRn;
	}

	public double getEth() {
		return this.eth;
	}

	public void setEth(double eth) {
		this.eth = eth;
	}

	public double getEu() {
		return this.eu;
	}

	public void setEu(double eu) {
		this.eu = eu;
	}

	public double getEuRn() {
		return this.euRn;
	}

	public void setEuRn(double euRn) {
		this.euRn = euRn;
	}

	public double getHAltezza() {
		return this.hAltezza;
	}

	public void setHAltezza(double hAltezza) {
		this.hAltezza = hAltezza;
	}

	public double getHStp() {
		return this.hStp;
	}

	public void setHStp(double hStp) {
		this.hStp = hStp;
	}

	public int getIdKsFileVoloRighe() {
		return this.idKsFileVoloRighe;
	}

	public void setIdKsFileVoloRighe(int idKsFileVoloRighe) {
		this.idKsFileVoloRighe = idKsFileVoloRighe;
	}

	public int getIndexDataVolo() {
		return this.indexDataVolo;
	}

	public void setIndexDataVolo(int indexDataVolo) {
		this.indexDataVolo = indexDataVolo;
	}

	public double getK() {
		return this.k;
	}

	public void setK(double k) {
		this.k = k;
	}

	public double getOraGVolo() {
		return this.oraGVolo;
	}

	public void setOraGVolo(double oraGVolo) {
		this.oraGVolo = oraGVolo;
	}

	public double getPres() {
		return this.pres;
	}

	public void setPres(double pres) {
		this.pres = pres;
	}

	public double getRaggioCerchioCoordinata() {
		return this.raggioCerchioCoordinata;
	}

	public void setRaggioCerchioCoordinata(double raggioCerchioCoordinata) {
		this.raggioCerchioCoordinata = raggioCerchioCoordinata;
	}

	public double getRn() {
		return this.rn;
	}

	public void setRn(double rn) {
		this.rn = rn;
	}

	public double getScs() {
		return this.scs;
	}

	public void setScs(double scs) {
		this.scs = scs;
	}

	public double getSdcs() {
		return this.sdcs;
	}

	public void setSdcs(double sdcs) {
		this.sdcs = sdcs;
	}

	public double getSdeu() {
		return this.sdeu;
	}

	public void setSdeu(double sdeu) {
		this.sdeu = sdeu;
	}

	public double getSdk() {
		return this.sdk;
	}

	public void setSdk(double sdk) {
		this.sdk = sdk;
	}

	public double getSedth() {
		return this.sedth;
	}

	public void setSedth(double sedth) {
		this.sedth = sedth;
	}

	public double getSeth() {
		return this.seth;
	}

	public void setSeth(double seth) {
		this.seth = seth;
	}

	public double getSeu() {
		return this.seu;
	}

	public void setSeu(double seu) {
		this.seu = seu;
	}

	public double getSk() {
		return this.sk;
	}

	public void setSk(double sk) {
		this.sk = sk;
	}

	public double getTemp() {
		return this.temp;
	}

	public void setTemp(double temp) {
		this.temp = temp;
	}

	public double getTrTot() {
		return this.trTot;
	}

	public void setTrTot(double trTot) {
		this.trTot = trTot;
	}

	public double getValoreSogliaAllarme() {
		return this.valoreSogliaAllarme;
	}

	public void setValoreSogliaAllarme(double valoreSogliaAllarme) {
		this.valoreSogliaAllarme = valoreSogliaAllarme;
	}

	public double getVel() {
		return this.vel;
	}

	public void setVel(double vel) {
		this.vel = vel;
	}

	public String getVoloDesc() {
		return this.voloDesc;
	}

	public void setVoloDesc(String voloDesc) {
		this.voloDesc = voloDesc;
	}

	public double getXumt() {
		return this.xumt;
	}

	public void setXumt(double xumt) {
		this.xumt = xumt;
	}

	public double getYumt() {
		return this.yumt;
	}

	public void setYumt(double yumt) {
		this.yumt = yumt;
	}

}