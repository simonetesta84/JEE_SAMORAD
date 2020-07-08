package samorad.bean.mappe;

import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

public class ClassRangeColourGroup {
	
	private List<ClassRangeColour> rangePotassio;
	private List<ClassRangeColour> rangeUranio;
	private List<ClassRangeColour> rangeTorio;
	private List<ClassRangeColour> rangeCesio;
	
	public ClassRangeColourGroup() {
		setRangePotassio(new ArrayList<ClassRangeColour>());
		setRangeUranio(new ArrayList<ClassRangeColour>());
		setRangeTorio(new ArrayList<ClassRangeColour>());
		setRangeCesio(new ArrayList<ClassRangeColour>());
	}
	
	public String returnColourRange(List<ClassRangeColour> element, Double valElemento){
		
		String colourReturn ="#000000";
		int j =1;
		for (Iterator iterator = element.iterator(); iterator.hasNext();) {
			ClassRangeColour classRangeColour = (ClassRangeColour) iterator.next();
			if(j == element.size() && classRangeColour.getRangeColourDa()<=valElemento && classRangeColour.getRangeColourA()>=valElemento){
				colourReturn = classRangeColour.getLabelColour();
			}else{
				if(classRangeColour.getRangeColourDa()<=valElemento && classRangeColour.getRangeColourA()>valElemento) {
					colourReturn = classRangeColour.getLabelColour();				
				}
			}
		}
		return colourReturn;
	}
	
	//setter getter
	public List<ClassRangeColour> getRangePotassio() {
		return rangePotassio;
	}

	public void setRangePotassio(List<ClassRangeColour> rangePotassio) {
		this.rangePotassio = rangePotassio;
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
	
}
