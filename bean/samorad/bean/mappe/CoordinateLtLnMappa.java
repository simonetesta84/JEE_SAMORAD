package samorad.bean.mappe;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

public class CoordinateLtLnMappa implements Serializable {
	
	private Integer idVoloRigaKP;
	private Integer idVoloRigaKS;
	private Integer indexDataVolo;
	private Date dataOraPoint;
	private Double latitudine;
	private Double longitudine;
	
	private String colourPotassio;
	private String colourUranio;
	private String colourTorio;
	private String colourCesio;
	private String colourTorioUranio;
	private String colourPotassioUranio;
	private String colourChiQuadro;
	private String colourAllarmi;
	private Double raggioCerhioCoordinata;
	
	public CoordinateLtLnMappa(){}
	
	public CoordinateLtLnMappa(Integer idVoloRigaKP, Integer idVoloRigaKS, Date dataOraPoint,
			Double latitudine, Double longitudine, String colourPotassio,
			String colourUranio, String colourTorio, String colourCesio, Integer indexDataVolo, String colourTorioUranio, String colourPotassioUranio, String colourChiQuadro, Double raggioCerhioCoordinata, String colourAllarmi) {
		super();
		this.idVoloRigaKP = idVoloRigaKP;
		this.idVoloRigaKS = idVoloRigaKS;
		this.indexDataVolo = indexDataVolo;
		this.dataOraPoint = dataOraPoint;
		this.latitudine = latitudine;
		this.longitudine = longitudine;
		this.colourPotassio = colourPotassio;
		this.colourUranio = colourUranio;
		this.colourTorio = colourTorio;
		this.colourCesio = colourCesio;
		this.colourTorioUranio = colourTorioUranio;
		this.colourPotassioUranio = colourPotassioUranio;
		this.colourChiQuadro = colourChiQuadro;
		this.raggioCerhioCoordinata = raggioCerhioCoordinata;
		this.colourAllarmi = colourAllarmi;
	}
	
	public Integer getIdVoloRigaKP() {
		return idVoloRigaKP;
	}
	public void setIdVoloRigaKP(Integer idVoloRigaKP) {
		this.idVoloRigaKP = idVoloRigaKP;
	}
	public Integer getIdVoloRigaKS() {
		return idVoloRigaKS;
	}
	public void setIdVoloRigaKS(Integer idVoloRigaKS) {
		this.idVoloRigaKS = idVoloRigaKS;
	}
	public Double getLatitudine() {
		return latitudine;
	}
	public void setLatitudine(Double latitudine) {
		this.latitudine = latitudine;
	}
	public Double getLongitudine() {
		return longitudine;
	}
	public void setLongitudine(Double longitudine) {
		this.longitudine = longitudine;
	}
	public String getColourPotassio() {
		return colourPotassio;
	}
	public void setColourPotassio(String colourPotassio) {
		this.colourPotassio = colourPotassio;
	}
	public String getColourUranio() {
		return colourUranio;
	}
	public void setColourUranio(String colourUranio) {
		this.colourUranio = colourUranio;
	}
	public String getColourTorio() {
		return colourTorio;
	}
	public void setColourTorio(String colourTorio) {
		this.colourTorio = colourTorio;
	}
	public String getColourCesio() {
		return colourCesio;
	}
	public void setColourCesio(String colourCesio) {
		this.colourCesio = colourCesio;
	}

	public Date getDataOraPoint() {
		return dataOraPoint;
	}

	public void setDataOraPoint(Date dataOraPoint) {
		this.dataOraPoint = dataOraPoint;
	}

	public Integer getIndexDataVolo() {
		return indexDataVolo;
	}

	public void setIndexDataVolo(Integer indexDataVolo) {
		this.indexDataVolo = indexDataVolo;
	}

	public String getColourTorioUranio() {
		return colourTorioUranio;
	}

	public void setColourTorioUranio(String colourTorioUranio) {
		this.colourTorioUranio = colourTorioUranio;
	}

	public String getColourPotassioUranio() {
		return colourPotassioUranio;
	}

	public void setColourPotassioUranio(String colourPotassioUranio) {
		this.colourPotassioUranio = colourPotassioUranio;
	}

	public String getColourChiQuadro() {
		return colourChiQuadro;
	}

	public void setColourChiQuadro(String colourChiQuadro) {
		this.colourChiQuadro = colourChiQuadro;
	}

	public Double getRaggioCerhioCoordinata() {
		return raggioCerhioCoordinata;
	}

	public void setRaggioCerhioCoordinata(Double raggioCerhioCoordinata) {
		this.raggioCerhioCoordinata = raggioCerhioCoordinata;
	}

	public String getColourAllarmi() {
		return colourAllarmi;
	}

	public void setColourAllarmi(String colourAllarmi) {
		this.colourAllarmi = colourAllarmi;
	}
}