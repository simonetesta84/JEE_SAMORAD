package samorad.bean.mappe;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import com.google.gson.Gson;

public class ClassMappeChartInfo implements Serializable{

	private List<CoordinateLtLn> infoChart;
	private ClassMappe clmMappa;

	//info frame destro
	private String fileVolo;
	private String dataVolo;
	private String oraVolo;
	private String durataVolo;
	private String numCoordGeo;
	private String numEventi;
	private Integer numAllarmiSupSoglia;
		
	public ClassMappeChartInfo() {
		setInfoChart(new ArrayList<CoordinateLtLn>());
		setClmMappa(new ClassMappe());
	}
	
	public void addLnLtVoloChartInfo(Integer idVoloRigaKP, Integer idVoloRigaKS,
			Date date, Integer orarioH, Integer orarioM, Integer orarioS,
			Double latitudine, Double longitudine, String descrCoordinata,
			Double potassio, Double dtPotassio, String colourPotassio,
			String colourUranio, Double uranio, Double dtUranio,
			String colourTorio, Double torio, Double dtTorio,
			String colourCesio, Double cesio, Double dtCesio, Double dem,
			Double altitudine, Double velocita, Double temperatura,
			Double pressione, Integer indexDataVolo,
			String colourTorioUranio, String colourPotassioUranio, String colourChiQuadro, Double torioUranio, Double potassioUranio, Double chiQuadro, Double raggioCerhioCoordinata, String colourAllarmi,  Double allarmi, Double sogliaAllarme){

		//definisco il tracciato percorso dall'alicottero
		CoordinateLtLn ltLnChartInfo = new CoordinateLtLn(
				idVoloRigaKP, idVoloRigaKS, date,
				latitudine, longitudine, descrCoordinata,
				potassio, dtPotassio, colourPotassio,
				colourUranio, uranio, dtUranio,
				colourTorio, torio, dtTorio,
				colourCesio, cesio, dtCesio, dem,
				altitudine, velocita, temperatura,
				pressione, indexDataVolo,
				colourTorioUranio, colourPotassioUranio, colourChiQuadro, torioUranio, potassioUranio, chiQuadro, raggioCerhioCoordinata, colourAllarmi, allarmi, sogliaAllarme);		
		getInfoChart().add(ltLnChartInfo);	
	}
	
	public void setInfoGeneraleVolo(String fileVolo, String dataVolo, String oraVolo, String durataVolo, String numCoordGeo, String numEventi, Integer numAllarmiSupSoglia){
		setFileVolo(fileVolo);
		setDataVolo(dataVolo);
		setOraVolo(oraVolo);
		setDurataVolo(durataVolo);
		setNumCoordGeo(numCoordGeo);
		setNumEventi(numEventi);
		setNumAllarmiSupSoglia(numAllarmiSupSoglia);
		
	}
	public List<CoordinateLtLn> getInfoChart() {
		return infoChart;
	}
	public void setInfoChart(List<CoordinateLtLn> infoChart) {
		this.infoChart = infoChart;
	}

	public ClassMappe getClmMappa() {
		return clmMappa;
	}

	public void setClmMappa(ClassMappe clmMappa) {
		this.clmMappa = clmMappa;
	}

	public String getFileVolo() {
		return fileVolo;
	}

	public void setFileVolo(String fileVolo) {
		this.fileVolo = fileVolo;
	}

	public String getDataVolo() {
		return dataVolo;
	}

	public void setDataVolo(String dataVolo) {
		this.dataVolo = dataVolo;
	}

	public String getOraVolo() {
		return oraVolo;
	}

	public void setOraVolo(String oraVolo) {
		this.oraVolo = oraVolo;
	}

	public String getDurataVolo() {
		return durataVolo;
	}

	public void setDurataVolo(String durataVolo) {
		this.durataVolo = durataVolo;
	}

	public String getNumCoordGeo() {
		return numCoordGeo;
	}

	public void setNumCoordGeo(String numCoordGeo) {
		this.numCoordGeo = numCoordGeo;
	}

	public String getNumEventi() {
		return numEventi;
	}

	public void setNumEventi(String numEventi) {
		this.numEventi = numEventi;
	}

	public Integer getNumAllarmiSupSoglia() {
		return numAllarmiSupSoglia;
	}

	public void setNumAllarmiSupSoglia(Integer numAllarmiSupSoglia) {
		this.numAllarmiSupSoglia = numAllarmiSupSoglia;
	}
}
