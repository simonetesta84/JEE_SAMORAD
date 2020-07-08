package samorad.bean.mappe;

public class ClassRangeColour {

	private String labelColour;
	private Double rangeColourDa;
	private Double rangeColourA;
	private String unitMis;
	
	public ClassRangeColour(String labelColour, Double rangeColourDa,
			Double rangeColourA, String unitMis) {
		super();
		this.labelColour = labelColour;
		this.rangeColourDa = rangeColourDa;
		this.rangeColourA = rangeColourA;
		this.unitMis = unitMis;
	}
	
	public String getLabelColour() {
		return labelColour;
	}
	public void setLabelColour(String labelColour) {
		this.labelColour = labelColour;
	}
	public Double getRangeColourDa() {
		return rangeColourDa;
	}
	public void setRangeColourDa(Double rangeColourDa) {
		this.rangeColourDa = rangeColourDa;
	}
	public Double getRangeColourA() {
		return rangeColourA;
	}
	public void setRangeColourA(Double rangeColourA) {
		this.rangeColourA = rangeColourA;
	}

	public String getUnitMis() {
		return unitMis;
	}

	public void setUnitMis(String unitMis) {
		this.unitMis = unitMis;
	}
	
}
