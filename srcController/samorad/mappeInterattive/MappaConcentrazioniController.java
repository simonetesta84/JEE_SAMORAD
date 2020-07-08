package samorad.mappeInterattive;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.bean.ViewScoped;

import org.primefaces.context.RequestContext;
import org.primefaces.model.chart.CartesianChartModel;
import org.primefaces.model.chart.LineChartSeries;
import org.primefaces.model.chart.MeterGaugeChartModel;

import samorad.bean.mappe.ClassMappe;
import samorad.bean.mappe.ClassMappeInfoReturn;
import samorad.bean.mappe.ClassRangeColour;
import samorad.serviceLogin.client.ServiceLoginRemote;
import samorad.util.common.Constant;

import org.primefaces.model.chart.MeterGaugeChartModel;

@ManagedBean (name = "mappaConcentrazioniController")
@ViewScoped
public class MappaConcentrazioniController extends BaseController{
	
	private MeterGaugeChartModel chartSoglieConcentrazioni;
	private String elencoColourChart;
	private String unitMisura;
	private List<ClassRangeColour> rangeColourLegend;
	
	@PostConstruct
	public void init(){
		log.debug("START-MappaConcentrazioniController:init");
		super.init();
		setSingleElemento(Constant.FrontEnd.Elementi.POTASSIO);
		createMeterGaugeModel();
		setTypeGeneraleController(false);
		setTypeConcentrazioniController(true);
		setTypeAllarmeController(false);
		log.debug("END-MappaConcentrazioniController:init");				
	}	
	 
	private void switchCaseFun(List<ClassRangeColour> rangeElement, String valElement, List<Number> intervals, String[] colurInteval, Double[] singlePointElement){
		   
	   	for (Iterator iterator = rangeElement.iterator(); iterator.hasNext();) {
	       	 ClassRangeColour classRangeColour = (ClassRangeColour) iterator.next();
	    	 
	       	 intervals.add(classRangeColour.getRangeColourA());
	       	 
	       	 if(colurInteval[0].equals("")){
		         colurInteval[0] = classRangeColour.getLabelColour().substring(1);
		         setUnitMisura(classRangeColour.getUnitMis());
	       	 }else{
		         colurInteval[0] = colurInteval[0] +","+ classRangeColour.getLabelColour().substring(1); 
	       	 }
	       	 getRangeColourLegend().add(classRangeColour);
	 	}
	    
	  //  if(!valElement.equals(Constant.FrontEnd.Vario.NON_DEFINITO)){
	    	singlePointElement[0] = Double.parseDouble(valElement);
	  // }else{
	  //  	singlePointElement[0] = 0.0;
	  //  }
	    
	}
	
	private void createMeterGaugeModel() {
		List<Number> intervals = new ArrayList<Number>();
		String[] colurInteval={""};
		Double singlePointElement[] = {0.0};
		setRangeColourLegend(new ArrayList<ClassRangeColour>());

		switch (getSingleElemento()) {
			case Constant.FrontEnd.Elementi.POTASSIO:{
				switchCaseFun(getClm().getRangePotassio(), getkPotassio(), intervals, colurInteval, singlePointElement);
		    }
		    break;
		  
	        case Constant.FrontEnd.Elementi.URANIO:{
	        	switchCaseFun(getClm().getRangeUranio(), getEuUranio(), intervals, colurInteval, singlePointElement);
	        }
	        break;
	         
	        case Constant.FrontEnd.Elementi.TORIO:{
	        	switchCaseFun(getClm().getRangeTorio(), getEthTorio(), intervals, colurInteval, singlePointElement);
	        }
	        break;
	         
	        case Constant.FrontEnd.Elementi.CESIO:{
	        	switchCaseFun(getClm().getRangeCesio(), getCsCesio(), intervals, colurInteval, singlePointElement);
	        }
	        break;
	        
	        case Constant.FrontEnd.Elementi.TORIO_URANIO:{
	        	switchCaseFun(getClm().getRangeTorioUranio(), getTorioUranio(), intervals, colurInteval, singlePointElement);
	        }
	        break;
	        
	        case Constant.FrontEnd.Elementi.POTASSIO_URANIO:{
	        	switchCaseFun(getClm().getRangePotassioUranio(), getPotassioUranio(), intervals, colurInteval, singlePointElement);
	        }
	        break;
	        
	        case Constant.FrontEnd.Elementi.CHIQUADRO:{
	        	switchCaseFun(getClm().getRangeChiQuadro(), getChiQuadro(), intervals, colurInteval, singlePointElement);
	        }
	        break;
		}
		setElencoColourChart(colurInteval[0]);	
		chartSoglieConcentrazioni = new MeterGaugeChartModel(singlePointElement[0], intervals);
	}

	public void changeColourElementController(){
		RequestContext.getCurrentInstance().execute("changeColourElement();");
		createMeterGaugeModel();
	}
	
	public void actionSetFromJsonToObjectInfoObjConcentrazioni(){
		actionSetFromJsonToObjectInfoObj();
		createMeterGaugeModel();
		RequestContext.getCurrentInstance().execute("gestioneRaggioCerchioCoordinata();");
	}
	 
	//getter and setter
	public MeterGaugeChartModel getChartSoglieConcentrazioni() {
		return chartSoglieConcentrazioni;
	}

	public void setChartSoglieConcentrazioni(
			MeterGaugeChartModel chartSoglieConcentrazioni) {
		this.chartSoglieConcentrazioni = chartSoglieConcentrazioni;
	}

	public String getElencoColourChart() {
		return elencoColourChart;
	}

	public void setElencoColourChart(String elencoColourChart) {
		this.elencoColourChart = elencoColourChart;
	}

	public List<ClassRangeColour> getRangeColourLegend() {
		return rangeColourLegend;
	}

	public void setRangeColourLegend(List<ClassRangeColour> rangeColourLegend) {
		this.rangeColourLegend = rangeColourLegend;
	}

	public String getUnitMisura() {
		return unitMisura;
	}

	public void setUnitMisura(String unitMisura) {
		this.unitMisura = unitMisura;
	}	
}
