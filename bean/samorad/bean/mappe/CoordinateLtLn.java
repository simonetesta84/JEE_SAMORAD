package samorad.bean.mappe;

import java.io.Serializable;
import java.text.DecimalFormat;
import java.util.Date;
import java.util.List;

public class CoordinateLtLn extends CoordinateLtLnMappa {	
	private String descrCoordinata;
	
	private Double potassio;
	private Double dtPotassio;
	
	private Double uranio;
	private Double dtUranio;
	
	private Double torio;
	private Double dtTorio;
	
	private Double cesio;
	private Double dtCesio;
	
	private Double torioUranio;
	private Double potassioUranio;
	private Double chiQuadro;
	private Double allarmi;
	private Double sogliaAllarme;
	
	private Double dem;
	private Double altitudine;
	private Double altitudineDem;
	private Double velocita;
	private Double temperatura;
	private Double pressione;
	
	public CoordinateLtLn(){}
	
	public CoordinateLtLn(Integer idVoloRigaKP, Integer idVoloRigaKS, Date dataOraVolo,
			Double latitudine, Double longitudine, String descrCoordinata,
			Double potassio, Double dtPotassio, String colourPotassio,
			String colourUranio, Double uranio, Double dtUranio,
			String colourTorio, Double torio, Double dtTorio,
			String colourCesio, Double cesio, Double dtCesio, Double dem,
			Double altitudine, Double velocita, Double temperatura,
			Double pressione, Integer indexDataVolo,
			String colourTorioUranio, String colourPotassioUranio, String colourChiQuadro, Double torioUranio, Double potassioUranio, Double chiQuadro, Double raggioCerhioCoordinata, String colourAllarmi,  Double allarmi, Double sogliaAllarme) {
		super(idVoloRigaKP, idVoloRigaKS, dataOraVolo, latitudine, longitudine, colourPotassio,
			  colourUranio, colourTorio, colourCesio, indexDataVolo, colourTorioUranio, colourPotassioUranio, colourChiQuadro, raggioCerhioCoordinata, colourAllarmi);
		
		this.descrCoordinata = descrCoordinata;
		this.potassio = potassio;
		this.dtPotassio = dtPotassio;
		this.uranio = uranio;
		this.dtUranio = dtUranio;
		this.torioUranio = torioUranio;
		this.potassioUranio = potassioUranio;
		this.chiQuadro = chiQuadro;
		this.allarmi=allarmi;
		this.sogliaAllarme = sogliaAllarme;
		this.torio = torio;
		this.dtTorio = dtTorio;
		this.cesio = cesio;
		this.dtCesio = dtCesio;
		this.dem = dem;
		this.altitudine = altitudine;
		this.velocita = velocita;
		this.temperatura = temperatura;
		this.pressione = pressione;
		getAltitudineDem();
	
	}
	
	public String getDescrCoordinata() {
		return descrCoordinata;
	}
	public void setDescrCoordinata(String descrCoordinata) {
		this.descrCoordinata = descrCoordinata;
	}
	public Double getPotassio() {
		return potassio;
	}
	public void setPotassio(Double potassio) {
		this.potassio = potassio;
	}
	public Double getDtPotassio() {
		return dtPotassio;
	}
	public void setDtPotassio(Double dtPotassio) {
		this.dtPotassio = dtPotassio;
	}
	public Double getUranio() {
		return uranio;
	}
	public void setUranio(Double uranio) {
		this.uranio = uranio;
	}
	public Double getDtUranio() {
		return dtUranio;
	}
	public void setDtUranio(Double dtUranio) {
		this.dtUranio = dtUranio;
	}
	public Double getTorio() {
		return torio;
	}
	public void setTorio(Double torio) {
		this.torio = torio;
	}
	public Double getDtTorio() {
		return dtTorio;
	}
	public void setDtTorio(Double dtTorio) {
		this.dtTorio = dtTorio;
	}
	public Double getCesio() {
		return cesio;
	}
	public void setCesio(Double cesio) {
		this.cesio = cesio;
	}
	public Double getDtCesio() {
		return dtCesio;
	}
	public void setDtCesio(Double dtCesio) {
		this.dtCesio = dtCesio;
	}
	public Double getDem() {
		return dem;
	}
	public void setDem(Double dem) {
		this.dem = dem;
	}
	public Double getAltitudine() {
		return altitudine;
	}
	public void setAltitudine(Double altitudine) {
		this.altitudine = altitudine;
	}
	public Double getAltitudineDem() {
	    return altitudineDem =  Math.round((this.altitudine - this.dem)*100.0)/100.0;  
	}
	public Double getVelocita() {
		return velocita;
	}
	public void setVelocita(Double velocita) {
		this.velocita = velocita;
	}
	public Double getTemperatura() {
		return temperatura;
	}
	public void setTemperatura(Double temperatura) {
		this.temperatura = temperatura;
	}
	public Double getPressione() {
		return pressione;
	}
	public void setPressione(Double pressione) {
		this.pressione = pressione;
	}

	public Double getTorioUranio() {
		return torioUranio;
	}

	public void setTorioUranio(Double torioUranio) {
		this.torioUranio = torioUranio;
	}

	public Double getPotassioUranio() {
		return potassioUranio;
	}

	public void setPotassioUranio(Double potassioUranio) {
		this.potassioUranio = potassioUranio;
	}

	public Double getChiQuadro() {
		return chiQuadro;
	}

	public void setChiQuadro(Double chiQuadro) {
		this.chiQuadro = chiQuadro;
	}

	public Double getAllarmi() {
		return allarmi;
	}

	public void setAllarmi(Double allarmi) {
		this.allarmi = allarmi;
	}

	public Double getSogliaAllarme() {
		return sogliaAllarme;
	}

	public void setSogliaAllarme(Double sogliaAllarme) {
		this.sogliaAllarme = sogliaAllarme;
	}
	
}