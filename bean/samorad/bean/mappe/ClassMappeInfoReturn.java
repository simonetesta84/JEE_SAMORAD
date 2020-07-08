package samorad.bean.mappe;

import java.io.Serializable;

import com.google.gson.Gson;

public class ClassMappeInfoReturn implements Serializable {

	CoordinateLnLtQuadrato infoQuadrato;
	CoordinateLtLnMappa infoCoordinateLtLn;
		
	public ClassMappeInfoReturn() {
		this.infoQuadrato = null;
		this.infoCoordinateLtLn = null;
	}

	public String getFromObjectToJsonInfo(ClassMappeInfoReturn classMappeInfoReturn){
		Gson gson = new Gson();
		return gson.toJson(classMappeInfoReturn);
	}
	
	public ClassMappeInfoReturn getFromJsonToObject(String stringMappeInfoReturn){
		Gson gson = new Gson();
		return gson.fromJson(stringMappeInfoReturn, ClassMappeInfoReturn.class);
	}
	
	//--getter and setter--
	public CoordinateLnLtQuadrato getInfoQuadrato() {
		return infoQuadrato;
	}
	public void setInfoQuadrato(CoordinateLnLtQuadrato infoQuadrato) {
		this.infoQuadrato = infoQuadrato;
	}
	public CoordinateLtLnMappa getInfoCoordinateLtLn() {
		return infoCoordinateLtLn;
	}
	public void setInfoCoordinateLtLn(CoordinateLtLnMappa infoCoordinateLtLn) {
		this.infoCoordinateLtLn = infoCoordinateLtLn;
	}	
}
