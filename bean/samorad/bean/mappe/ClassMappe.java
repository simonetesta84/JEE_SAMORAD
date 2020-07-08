package samorad.bean.mappe;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

import org.apache.log4j.Logger;

import com.google.gson.Gson;

public class ClassMappe implements Serializable{

	private List<CoordinateLtLnMappa> tracciatoVolo;
	private List<ClassRangeColour> rangePotassio;
	private List<ClassRangeColour> rangeUranio;
	private List<ClassRangeColour> rangeTorio;
	private List<ClassRangeColour> rangeCesio;
	private List<ClassRangeColour> rangeTorioUranio;
	private List<ClassRangeColour> rangePotassioUranio;
	private List<ClassRangeColour> rangeChiQuadro;
	private List<ClassRangeColour> rangeAllarmi;
	private Double maxLat = null;
	private Double minLat = null;
	private Double maxLon = null;
	private Double minLon = null;
	
	public ClassMappe() {
		setTracciatoVolo(new ArrayList<CoordinateLtLnMappa>());		
		setRangePotassio(new ArrayList<ClassRangeColour>());
		setRangeUranio(new ArrayList<ClassRangeColour>());
		setRangeTorio(new ArrayList<ClassRangeColour>());
		setRangeCesio(new ArrayList<ClassRangeColour>());
		setRangeTorioUranio(new ArrayList<ClassRangeColour>());
		setRangePotassioUranio(new ArrayList<ClassRangeColour>());
		setRangeChiQuadro(new ArrayList<ClassRangeColour>());
		setRangeAllarmi(new ArrayList<ClassRangeColour>());
	}		

	public void addLnLtTracciatoVoloMappe(Integer idVoloRigaKP, Integer idVoloRigaKS, Date dataOraPoint,
			Double latitudine, Double longitudine, String colourPotassio,
			String colourUranio, String colourTorio, String colourCesio, Integer indexDataVolo, 
			String colourTorioUranio, String colourPotassioUranio, String colourChiQuadro, Double raggioCerhioCoordinata, String colourAllarmi){

		//definisco il tracciato percorso dall'alicottero
		CoordinateLtLnMappa ltLnMappa = new CoordinateLtLnMappa(
				idVoloRigaKP, idVoloRigaKS, dataOraPoint, 
				latitudine,	longitudine, colourPotassio, colourUranio, 
				colourTorio, colourCesio, indexDataVolo, colourTorioUranio, colourPotassioUranio, colourChiQuadro, raggioCerhioCoordinata, colourAllarmi);		
		getTracciatoVolo().add(ltLnMappa);		
		if(getMaxLat()==null || getMaxLat()<latitudine){setMaxLat(latitudine);}
		if(getMinLat()==null || getMinLat()>latitudine){setMinLat(latitudine);}
		if(getMaxLon()==null || getMaxLon()<longitudine){setMaxLon(longitudine);}
		if(getMinLon()==null || getMinLon()>longitudine){setMinLon(longitudine);}
	}
	
	public String returnColourRange(List<ClassRangeColour> element, Double valElemento){		
		String colourReturn ="#000000";
		int j =1;
		for (Iterator iterator = element.iterator(); iterator.hasNext();) {
			ClassRangeColour classRangeColour = (ClassRangeColour) iterator.next();
			if(j == element.size() && classRangeColour.getRangeColourDa()<=valElemento /*&& classRangeColour.getRangeColourA()>=valElemento*/){
				colourReturn = classRangeColour.getLabelColour();
			}else{
				if(classRangeColour.getRangeColourDa()<=valElemento && classRangeColour.getRangeColourA()>valElemento) {
					colourReturn = classRangeColour.getLabelColour();				
				}
			}
			j++;
		}
		return colourReturn;
	}
	
	//----getter e setter-----
	public String getFromObjectToJsonMap(ClassMappe classMap){
		Gson gson = new Gson();
		return gson.toJson(classMap);
	}
	public List<CoordinateLtLnMappa> getTracciatoVolo() {
		return tracciatoVolo;
	}
	public void setTracciatoVolo(List<CoordinateLtLnMappa> tracciatoVolo) {
		this.tracciatoVolo = tracciatoVolo;
	}
	public Double getMaxLat() {
		return maxLat;
	}
	public void setMaxLat(Double maxLat) {
		this.maxLat = maxLat;
	}
	public Double getMinLat() {
		return minLat;
	}
	public void setMinLat(Double minLat) {
		this.minLat = minLat;
	}
	public Double getMaxLon() {
		return maxLon;
	}
	public void setMaxLon(Double maxLon) {
		this.maxLon = maxLon;
	}
	public Double getMinLon() {
		return minLon;
	}
	public void setMinLon(Double minLon) {
		this.minLon = minLon;
	}
	public List<ClassRangeColour> getRangePotassio() {
		return rangePotassio;
	}
	public List<ClassRangeColour> getRangeUranio() {
		return rangeUranio;
	}
	public void setRangeUranio(List<ClassRangeColour> rangeUranio) {
		this.rangeUranio = rangeUranio;
	}
	public List<ClassRangeColour> getRangeTorio() {
		return rangeTorio;
	}
	public void setRangeTorio(List<ClassRangeColour> rangeTorio) {
		this.rangeTorio = rangeTorio;
	}
	public List<ClassRangeColour> getRangeCesio() {
		return rangeCesio;
	}
	public void setRangeCesio(List<ClassRangeColour> rangeCesio) {
		this.rangeCesio = rangeCesio;
	}
	public void setRangePotassio(List<ClassRangeColour> rangePotassio) {
		this.rangePotassio = rangePotassio;
	}

	public List<ClassRangeColour> getRangeTorioUranio() {
		return rangeTorioUranio;
	}

	public void setRangeTorioUranio(List<ClassRangeColour> rangeTorioUranio) {
		this.rangeTorioUranio = rangeTorioUranio;
	}

	public List<ClassRangeColour> getRangePotassioUranio() {
		return rangePotassioUranio;
	}

	public void setRangePotassioUranio(List<ClassRangeColour> rangePotassioUranio) {
		this.rangePotassioUranio = rangePotassioUranio;
	}

	public List<ClassRangeColour> getRangeChiQuadro() {
		return rangeChiQuadro;
	}

	public void setRangeChiQuadro(List<ClassRangeColour> rangeChiQuadro) {
		this.rangeChiQuadro = rangeChiQuadro;
	}

	public List<ClassRangeColour> getRangeAllarmi() {
		return rangeAllarmi;
	}

	public void setRangeAllarmi(List<ClassRangeColour> rangeAllarmi) {
		this.rangeAllarmi = rangeAllarmi;
	}	
}
