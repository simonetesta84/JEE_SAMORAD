package samorad.bean.mappe;

import java.io.Serializable;

public class CoordinateLnLtQuadrato implements Serializable {

	CoordinateLtLn nordOvest;
	CoordinateLtLn nordEst;
	CoordinateLtLn sudOvest;
	CoordinateLtLn sudEst;
	
	String color;
	String descrQuadrato;	
	
	public CoordinateLnLtQuadrato(CoordinateLtLn nordOvest, CoordinateLtLn nordEst, CoordinateLtLn sudOvest, CoordinateLtLn sudEst, String color, String descrQuadrato) {
		this.nordOvest = nordOvest;
		this.nordEst = nordEst;
		this.sudOvest = sudOvest;
		this.sudEst = sudEst;
		this.color = color;
		this.descrQuadrato = descrQuadrato;
	}
	
	//---getter e setter--
	public CoordinateLtLn getNordOvest() {
		return nordOvest;
	}
	public void setNordOvest(CoordinateLtLn nordOvest) {
		this.nordOvest = nordOvest;
	}
	public CoordinateLtLn getNordEst() {
		return nordEst;
	}
	public void setNordEst(CoordinateLtLn nordEst) {
		this.nordEst = nordEst;
	}
	public CoordinateLtLn getSudOvest() {
		return sudOvest;
	}
	public void setSudOvest(CoordinateLtLn sudOvest) {
		this.sudOvest = sudOvest;
	}
	public CoordinateLtLn getSudEst() {
		return sudEst;
	}
	public void setSudEst(CoordinateLtLn sudEst) {
		this.sudEst = sudEst;
	}
	public String getColor() {
		return color;
	}
	public void setColor(String color) {
		this.color = color;
	}
	public String getDescrQuadrato() {
		return descrQuadrato;
	}
	public void setDescrQuadrato(String descrQuadrato) {
		this.descrQuadrato = descrQuadrato;
	}	
}
