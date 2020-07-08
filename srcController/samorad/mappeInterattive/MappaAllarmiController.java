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
import samorad.exception.ControllerException;
import samorad.serviceLogin.client.ServiceLoginRemote;
import samorad.util.common.Constant;
import samorad.util.common.Message;
import samorad.util.common.SamUtil;


@ManagedBean (name = "mappaAllarmiController")
@ViewScoped
public class MappaAllarmiController extends BaseController{
	
	private String NAVIGATION_UPLOAD_SOGLIA = "/pages/mappeInterattive/mappaAllarmi.xhtml+?faces-redirect=true";
	private MeterGaugeChartModel chartSoglieConcentrazioni;
	private List<ClassRangeColour> rangeColourLegend;
	private String sogliaAllarmeLabel;
	private String elencoColourChart;
	private String unitMisura;
	private String numAllarmiSupSoglia;
	
	@PostConstruct
	public void init(){
		log.debug("START-MappaAllarmiController:init");	
		super.init();
		setSogliaAllarmeLabel(getClmChartInfo().getInfoChart().get(0).getSogliaAllarme().toString());
		
		double percNUmAllarmi = 100*getClmChartInfo().getNumAllarmiSupSoglia().doubleValue()/Double.parseDouble(getClmChartInfo().getNumCoordGeo());
		Double percNUmAllarmiTrunk = SamUtil.trunkTreCifreDecimali(percNUmAllarmi);			
		setNumAllarmiSupSoglia(percNUmAllarmiTrunk.toString()+"%");
		
		setSingleElemento(Constant.FrontEnd.Elementi.ALLARMI);
		createMeterGaugeModel();
		setTypeGeneraleController(false);
		setTypeConcentrazioniController(false);
		setTypeAllarmeController(true);
		log.debug("END-MappaAllarmiController:init");				
	}
	
	private void createMeterGaugeModel() {
		List<Number> intervals = new ArrayList<Number>();
		String[] colurInteval={""};
		Double singlePointElement[] = {0.0};
		setRangeColourLegend(new ArrayList<ClassRangeColour>());
		double percVerde = 0.0;
	   	for (Iterator iterator = getClm().getRangeAllarmi().iterator(); iterator.hasNext();) {
	       	 ClassRangeColour classRangeColour = (ClassRangeColour) iterator.next();
	    	 
	       	 intervals.add(classRangeColour.getRangeColourA());
	       	 
	       	 if(colurInteval[0].equals("")){
		         colurInteval[0] = classRangeColour.getLabelColour().substring(1);
		         setUnitMisura(classRangeColour.getUnitMis());
	       	 }else{
		         colurInteval[0] = colurInteval[0] +","+ classRangeColour.getLabelColour().substring(1); 
	       	 }
	       	 if(classRangeColour.getLabelColour().equals(Constant.BackEnd.Colour.VERDE)){
	       		percVerde = classRangeColour.getRangeColourA();
	       		ClassRangeColour clRangeColour = new ClassRangeColour(classRangeColour.getLabelColour(), SamUtil.trunkTreCifreDecimali(classRangeColour.getRangeColourA()),null, null);
	       		getRangeColourLegend().add(clRangeColour);
	       	 }else{
	       		ClassRangeColour clRangeColour = new ClassRangeColour(classRangeColour.getLabelColour(), SamUtil.trunkTreCifreDecimali(100.00-percVerde),null, null);
	       		getRangeColourLegend().add(clRangeColour);
	       	 }

	 	}
	    
	   // if(!getValAllarme().equals(Constant.FrontEnd.Vario.NON_DEFINITO)){
	    	singlePointElement[0] = Double.parseDouble(getValAllarmi());
	   // }else{
	    //	singlePointElement[0] = 0.0;
	    //}
	    
		setElencoColourChart(colurInteval[0]);	
		chartSoglieConcentrazioni = new MeterGaugeChartModel(singlePointElement[0], intervals);
	}
	 
	public void actionSetFromJsonToObjectInfoObjAllarmi(){
		actionSetFromJsonToObjectInfoObj();
		createMeterGaugeModel();
	}
	
	public String updateSogliaAllarmi(){
		log.debug("START-MappaAllarmiController:updateSogliaAllarmi");	
		try{
			if(getSogliaAllarmeLabel().equals("")){
				throw new ControllerException(Message.FrontEnd.MessageError.ERR_INSERT_SOGLIA_ALLARME,Constant.FrontEnd.TipoMsg.ERROR);
			}else{
				getServiceMapObj().updateSogliaAllarmi(getSogliaAllarmeLabel());
				init();
				RequestContext.getCurrentInstance().execute("initialize()");
			}
		}catch(ControllerException contrEx){
			log.debug(contrEx.getgetMessageExceptionLog());
			contrEx.getMessageExceptionVideo();
		}catch(Exception e){
			log.debug(Message.FrontEnd.MessageError.ERR_UPDATE_SOGLIA_ALLARME);
	    	Message.addError(Message.FrontEnd.MessageError.ERR_UPDATE_SOGLIA_ALLARME);				    	
	    }
		log.debug("END-MappaAllarmiController:updateSogliaAllarmi");
		return NAVIGATION_UPLOAD_SOGLIA;
	}
	
	//getter and setter
	public MeterGaugeChartModel getChartSoglieConcentrazioni() {
		return chartSoglieConcentrazioni;
	}

	public void setChartSoglieConcentrazioni(
			MeterGaugeChartModel chartSoglieConcentrazioni) {
		this.chartSoglieConcentrazioni = chartSoglieConcentrazioni;
	}

	public List<ClassRangeColour> getRangeColourLegend() {
		return rangeColourLegend;
	}

	public void setRangeColourLegend(List<ClassRangeColour> rangeColourLegend) {
		this.rangeColourLegend = rangeColourLegend;
	}

	public String getElencoColourChart() {
		return elencoColourChart;
	}

	public void setElencoColourChart(String elencoColourChart) {
		this.elencoColourChart = elencoColourChart;
	}

	public String getUnitMisura() {
		return unitMisura;
	}

	public void setUnitMisura(String unitMisura) {
		this.unitMisura = unitMisura;
	}

	public String getSogliaAllarmeLabel() {
		return sogliaAllarmeLabel;
	}

	public void setSogliaAllarmeLabel(String sogliaAllarmeLabel) {
		this.sogliaAllarmeLabel = sogliaAllarmeLabel;
	}

	public String getNumAllarmiSupSoglia() {
		return numAllarmiSupSoglia;
	}

	public void setNumAllarmiSupSoglia(String numAllarmiSupSoglia) {
		this.numAllarmiSupSoglia = numAllarmiSupSoglia;
	}
}
