package samorad.mappeInterattive;

import java.io.Serializable;

import samorad.util.common.Constant;
import samorad.util.common.Message;
import samorad.util.common.SamUtil;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;

import javax.ejb.EJB;
import javax.faces.model.SelectItem;

import org.apache.log4j.Logger;
import org.primefaces.context.RequestContext;
import org.primefaces.event.ItemSelectEvent;
import org.primefaces.model.chart.CartesianChartModel;
import org.primefaces.model.chart.ChartSeries;
import org.primefaces.model.chart.LineChartSeries;

import com.google.gson.Gson;

import samorad.bean.mappe.ClassMappe;
import samorad.bean.mappe.ClassMappeChartInfo;
import samorad.bean.mappe.ClassMappeInfoReturn;
import samorad.bean.mappe.CoordinateLtLn;
import samorad.bean.mappe.CoordinateLtLnMappa;
import samorad.exception.ControllerException;
import samorad.serviceMap.client.ServiceMapRemote;

public abstract class BaseController implements Serializable {
	
	final Logger log = Logger.getLogger("SAMORADlogger");
	
	@EJB
    private ServiceMapRemote serviceMapObj;
	
	//info frame destro
	private String fileVolo;
	private String dataVolo;
	private String oraVolo;
	private String durataVolo;
	private String numCoordGeo;
	private String numEventi;
	
	private String timeChartLnLtPoint;
	private String coorGeoLt;
	private String coorGeoLn;
	private String kPotassio;
	private String dtKpotassio;
	private String euUranio;
	private String deuUranio;
	private String ethTorio;
	private String dtEthTorio;
	private String csCesio;
	private String dtCsCesio;
	private String torioUranio;
	private String potassioUranio;
	private String chiQuadro;
	private String valAllarmi;
	private String timePointHhMmSsTabDestra;
	private String temperaturaTabDestra;
	private String pressioneTabDestra;
	private String velocitaTabDestra;
	private String altitudineTabDestra;
	private String demTabDestra;
	private String altitudinedemTabDestra;
	
	
	private String positionlegend; 
	private double maxY;
	private double minY;
	private CartesianChartModel combinedModel; 
	private LineChartSeries potassio;
	private LineChartSeries uranio;
	private LineChartSeries torio;
	private LineChartSeries cesio;
	private LineChartSeries lineTorioUranio;
	private LineChartSeries linePotassioUranio;
	private LineChartSeries lineChiQuadro;
	private LineChartSeries temp;
	private LineChartSeries pressione;
	private LineChartSeries velocita;
	private LineChartSeries altitudine;
	private LineChartSeries dem;
	private LineChartSeries altitudineDem;
	private LineChartSeries allarmeValore;
	private LineChartSeries allarmeSoglia;
	private LineChartSeries livello; 
	private boolean chartPotassio;
	private boolean chartUranio;
	private boolean chartTorio;
	private boolean chartCesio;
	private boolean chartTorioUranio;
	private boolean chartPotassioUranio;
	private boolean chartChiQuadro;
	private boolean chartTemp;
	
	private boolean chartPressione;
	private boolean chartVelocita;
	private boolean chartAltitudine;
	private boolean chartDem;
	private boolean chartAltitudineDem;
	private boolean chartAllarmeValore;
	
	private boolean typeGeneraleController;
	private boolean typeConcentrazioniController;
	private boolean typeAllarmeController;
	
	private boolean disableFrecciaDestraChart;
	private boolean disableFrecciaSinistraChart;
	private String firstOrario;
	private String lastOrario;
	private HashMap<String, String> singleOrarioDesc;
	private String singleOraioStamp = "";
	private String singleOraio = "";
	private List<SelectItem> listOrari;
	private String singleElemento;
	private List<SelectItem> listElementi;	  
	//obj js vs controller
	private ClassMappe clm;
	private ClassMappeChartInfo clmChartInfo;
	private String fromObjectToJsonMapStr;
	private String singlePointMarker;
	
	private Integer coordinataSelectKey;
	private String fromJsonToObjectInfoStr;
	private ClassMappeInfoReturn fromJsonToObjectInfoObj;
	
	public void init(){
		log.debug("START-BaseController:init");
		try{
			setClmChartInfo(getServiceMapObj().ritornaMappa(SamUtil.getContesto().getIdUser(), SamUtil.getContesto().getIdFile()));
			setClm(getClmChartInfo().getClmMappa());
			setFromObjectToJsonMapStr(getClm().getFromObjectToJsonMap(getClm()));
			
			//oggetto utilizzato per lo scambio di info da js a controller per le mappe
			setFromJsonToObjectInfoObj(new ClassMappeInfoReturn());
			setFromJsonToObjectInfoStr(getFromJsonToObjectInfoObj().getFromObjectToJsonInfo(getFromJsonToObjectInfoObj()));						
			
			setFileVolo(getClmChartInfo().getFileVolo());
			setDataVolo(getClmChartInfo().getDataVolo());
			setOraVolo(getClmChartInfo().getOraVolo());
			setDurataVolo(getClmChartInfo().getDurataVolo());
			setNumCoordGeo(getClmChartInfo().getNumCoordGeo());
			setNumEventi(getClmChartInfo().getNumEventi());			
			
			listElementi  = new ArrayList<>();
			
			SelectItem ElementoObj3 = new SelectItem();
			ElementoObj3.setLabel(Constant.FrontEnd.Elementi.POTASSIO);
			ElementoObj3.setValue(Constant.FrontEnd.Elementi.POTASSIO);
			SelectItem ElementoObj0 = new SelectItem();
			ElementoObj0.setLabel(Constant.FrontEnd.Elementi.URANIO);
			ElementoObj0.setValue(Constant.FrontEnd.Elementi.URANIO);
			SelectItem ElementoObj1 = new SelectItem();
			ElementoObj1.setLabel(Constant.FrontEnd.Elementi.TORIO);
			ElementoObj1.setValue(Constant.FrontEnd.Elementi.TORIO);
			SelectItem ElementoObj2 = new SelectItem();
			ElementoObj2.setLabel(Constant.FrontEnd.Elementi.CESIO);
			ElementoObj2.setValue(Constant.FrontEnd.Elementi.CESIO);
			SelectItem ElementoObj4 = new SelectItem();
			ElementoObj4.setLabel(Constant.FrontEnd.Elementi.TORIO_URANIO);
			ElementoObj4.setValue(Constant.FrontEnd.Elementi.TORIO_URANIO);
			SelectItem ElementoObj5 = new SelectItem();
			ElementoObj5.setLabel(Constant.FrontEnd.Elementi.POTASSIO_URANIO);
			ElementoObj5.setValue(Constant.FrontEnd.Elementi.POTASSIO_URANIO);
			SelectItem ElementoObj6 = new SelectItem();
			ElementoObj6.setLabel(Constant.FrontEnd.Elementi.CHIQUADRO);
			ElementoObj6.setValue(Constant.FrontEnd.Elementi.CHIQUADRO);	
			
			getListElementi().add(ElementoObj3);
			getListElementi().add(ElementoObj0);
			getListElementi().add(ElementoObj1);
			getListElementi().add(ElementoObj2);
			getListElementi().add(ElementoObj4);
			getListElementi().add(ElementoObj5);
			getListElementi().add(ElementoObj6);
			
			setMaxY(0);
			setMinY(0);
			setTimePointHhMmSsTabDestra("HH:MM:SS");
			setChartPotassio(false);
			setChartUranio(false);
			setChartTorio(false);
			setChartCesio(false);
			setChartTorioUranio(false);
			setChartPotassioUranio(false);
			setChartChiQuadro(false);
			setChartTemp(false);
			setChartPressione(false);
			setChartVelocita(false);
			setChartAltitudine(true);
			setChartDem(false);
			setChartAltitudineDem(false);
			setChartAllarmeValore(false);
			setCombinedModel(new CartesianChartModel());
			//chartLivelloFun();
			
			riempiComboOrari();
			//setCoordinataSelectKey(-1);
			//setTypeController(false);
			
			//setChartAltitudine(true);
			actionSetFromJsonToObjectInfoObjImpl();
			chooseChart();
			log.debug("END-BaseController:init");
		}catch(ControllerException contrEx){
			log.debug(contrEx.getgetMessageExceptionLog());
			contrEx.getMessageExceptionVideo();
		}catch(Exception e){
			log.debug(Message.FrontEnd.MessageFatal.FATAL_ERROR_READ_FILE_VOLO_RIGHE);
	    	Message.addError(Message.FrontEnd.MessageFatal.FATAL_ERROR_READ_FILE_VOLO_RIGHE);				    	
	    }
	}   
    
    private void chartPotassioFun(){
    	setPotassio(new LineChartSeries());  
    	getPotassio().setLabel(SamUtil.getBundle().getString("chart.potassio"));
    	boolean isModify = false;
    	for (Iterator iterator = getClmChartInfo().getInfoChart().iterator(); iterator.hasNext();) {
			CoordinateLtLn coordinateLtLn = (CoordinateLtLn) iterator.next();
			getPotassio().set(coordinateLtLn.getIndexDataVolo(), coordinateLtLn.getPotassio());
			if(coordinateLtLn.getPotassio()>getMaxY()){
				setMaxY(coordinateLtLn.getPotassio());
				isModify = true;
			}
		}
    	if(isModify){
    		setMaxY(getMaxY()+2);
    	}
    	getCombinedModel().addSeries(getPotassio());
    }
    
    private void chartUranioFun(){    	
        setUranio(new LineChartSeries());  
        getUranio().setLabel(SamUtil.getBundle().getString("chart.uranio"));
    	boolean isModify = false;
    	for (Iterator iterator = getClmChartInfo().getInfoChart().iterator(); iterator.hasNext();) {
			CoordinateLtLn coordinateLtLn = (CoordinateLtLn) iterator.next();
			getUranio().set(coordinateLtLn.getIndexDataVolo(), coordinateLtLn.getUranio());
			if(coordinateLtLn.getUranio()>getMaxY()){
				setMaxY(coordinateLtLn.getUranio());
				isModify = true;
			}
		}
    	if(isModify){
    		setMaxY(getMaxY()+2);
    	}
    	getCombinedModel().addSeries(getUranio());
    }
    
    private void chartTorioFun(){
    	setTorio(new LineChartSeries());  
    	getTorio().setLabel(SamUtil.getBundle().getString("chart.torio"));
    	boolean isModify = false;
    	for (Iterator iterator = getClmChartInfo().getInfoChart().iterator(); iterator.hasNext();) {
			CoordinateLtLn coordinateLtLn = (CoordinateLtLn) iterator.next();
			getTorio().set(coordinateLtLn.getIndexDataVolo(), coordinateLtLn.getTorio());
			if(coordinateLtLn.getTorio()>getMaxY()){
				setMaxY(coordinateLtLn.getTorio());
				isModify = true;
			}
		}
    	if(isModify){
    		setMaxY(getMaxY()+2);
    	}
    	getCombinedModel().addSeries(getTorio());
    }
    
    private void chartCesioFun(){
    	setCesio(new LineChartSeries());  
    	getCesio().setLabel(SamUtil.getBundle().getString("chart.cesio"));
    	boolean isModify = false;    	
    	for (Iterator iterator = getClmChartInfo().getInfoChart().iterator(); iterator.hasNext();) {
			CoordinateLtLn coordinateLtLn = (CoordinateLtLn) iterator.next();
			getCesio().set(coordinateLtLn.getIndexDataVolo(), coordinateLtLn.getCesio());
			if(coordinateLtLn.getCesio()>getMaxY()){
				setMaxY(coordinateLtLn.getCesio());
				isModify = true;
			}
		}
    	if(isModify){
    		setMaxY(getMaxY()+2);
    	}
    	getCombinedModel().addSeries(getCesio());
    }
 
    private void chartTorioUranioFun(){
    	setLineTorioUranio(new LineChartSeries());  
    	getLineTorioUranio().setLabel(SamUtil.getBundle().getString("chart.torio.uranio"));
    	boolean isModify = false;
    	for (Iterator iterator = getClmChartInfo().getInfoChart().iterator(); iterator.hasNext();) {
			CoordinateLtLn coordinateLtLn = (CoordinateLtLn) iterator.next();
			getLineTorioUranio().set(coordinateLtLn.getIndexDataVolo(), coordinateLtLn.getTorioUranio());
			if(coordinateLtLn.getTorioUranio()>getMaxY()){
				setMaxY(coordinateLtLn.getTorioUranio());
				isModify = true;
			}
		}
    	if(isModify){
    		setMaxY(getMaxY()+2);
    	}
    	getCombinedModel().addSeries(getLineTorioUranio());
    }
    
    private void chartPotassioUranioFun(){
    	setLinePotassioUranio(new LineChartSeries());  
    	getLinePotassioUranio().setLabel(SamUtil.getBundle().getString("chart.potassio.uranio"));
    	boolean isModify = false;
    	for (Iterator iterator = getClmChartInfo().getInfoChart().iterator(); iterator.hasNext();) {
			CoordinateLtLn coordinateLtLn = (CoordinateLtLn) iterator.next();
			getLinePotassioUranio().set(coordinateLtLn.getIndexDataVolo(), coordinateLtLn.getPotassioUranio());
			if(coordinateLtLn.getPotassioUranio()>getMaxY()){
				setMaxY(coordinateLtLn.getPotassioUranio());
				isModify = true;
			}
		}
    	if(isModify){
    		setMaxY(getMaxY()+2);
    	}
    	getCombinedModel().addSeries(getLinePotassioUranio());
    }
    
    private void chartChiQuadroFun(){
    	setLineChiQuadro(new LineChartSeries());  
    	getLineChiQuadro().setLabel(SamUtil.getBundle().getString("chart.chiquadro"));
    	boolean isModify = false;
    	for (Iterator iterator = getClmChartInfo().getInfoChart().iterator(); iterator.hasNext();) {
			CoordinateLtLn coordinateLtLn = (CoordinateLtLn) iterator.next();
			getLineChiQuadro().set(coordinateLtLn.getIndexDataVolo(), coordinateLtLn.getChiQuadro());
			if(coordinateLtLn.getChiQuadro()>getMaxY()){
				setMaxY(coordinateLtLn.getChiQuadro());
				isModify = true;
			}
		}
    	if(isModify){
    		setMaxY(getMaxY()+2);
    	}
    	getCombinedModel().addSeries(getLineChiQuadro());
    }
    
    private void chartTempFun(){
    	setTemp(new LineChartSeries());  
    	getTemp().setLabel(SamUtil.getBundle().getString("chart.temp"));
    	boolean isModify = false;
    	for (Iterator iterator = getClmChartInfo().getInfoChart().iterator(); iterator.hasNext();) {
			CoordinateLtLn coordinateLtLn = (CoordinateLtLn) iterator.next();
			getTemp().set(coordinateLtLn.getIndexDataVolo(), coordinateLtLn.getTemperatura());
			if(coordinateLtLn.getTemperatura()>getMaxY()){
				setMaxY(coordinateLtLn.getTemperatura());
				isModify = true;
			}
		}
    	if(isModify){
    		setMaxY(getMaxY()+2);
    	}
    	getCombinedModel().addSeries(getTemp());
    }
    
    private void chartPressioneFun(){
    	setPressione(new LineChartSeries());  
    	getPressione().setLabel(SamUtil.getBundle().getString("chart.pressione"));
    	boolean isModify = false;
    	for (Iterator iterator = getClmChartInfo().getInfoChart().iterator(); iterator.hasNext();) {
			CoordinateLtLn coordinateLtLn = (CoordinateLtLn) iterator.next();
			getPressione().set(coordinateLtLn.getIndexDataVolo(), coordinateLtLn.getPressione());
			if(coordinateLtLn.getPressione()>getMaxY()){
				setMaxY(coordinateLtLn.getPressione());
				isModify = true;
			}
		}
    	if(isModify){
    		setMaxY(getMaxY()+10);
    	}
    	getCombinedModel().addSeries(getPressione());
    }
    
    private void chartVelocitaFun(){
    	setVelocita(new LineChartSeries());  
    	getVelocita().setLabel(SamUtil.getBundle().getString("chart.velocita"));
    	boolean isModify = false;
    	for (Iterator iterator = getClmChartInfo().getInfoChart().iterator(); iterator.hasNext();) {
			CoordinateLtLn coordinateLtLn = (CoordinateLtLn) iterator.next();
			getVelocita().set(coordinateLtLn.getIndexDataVolo(), coordinateLtLn.getVelocita());
			if(coordinateLtLn.getVelocita()>getMaxY()){
				setMaxY(coordinateLtLn.getVelocita());
				isModify = true;
			}
		}
    	if(isModify){
    		setMaxY(getMaxY()+2);
    	}
    	getCombinedModel().addSeries(getVelocita());
    }
    
    private void chartAltitudineFun(){
    	setAltitudine(new LineChartSeries());  
    	getAltitudine().setLabel(SamUtil.getBundle().getString("chart.altitudine"));
    	boolean isModify = false;
    	for (Iterator iterator = getClmChartInfo().getInfoChart().iterator(); iterator.hasNext();) {
			CoordinateLtLn coordinateLtLn = (CoordinateLtLn) iterator.next();
			getAltitudine().set(coordinateLtLn.getIndexDataVolo(), coordinateLtLn.getAltitudine());
			if(coordinateLtLn.getAltitudine()>getMaxY()){
				setMaxY(coordinateLtLn.getAltitudine());
				isModify = true;
			}
		}
    	if(isModify){
    		setMaxY(getMaxY()+2);
    	}
    	getAltitudine().setFill(true);
    	getCombinedModel().addSeries(getAltitudine());
    }
    
    private void chartDemFun(){
    	setDem(new LineChartSeries());  
    	getDem().setLabel(SamUtil.getBundle().getString("chart.dem"));
    	boolean isModify = false;
    	for (Iterator iterator = getClmChartInfo().getInfoChart().iterator(); iterator.hasNext();) {
			CoordinateLtLn coordinateLtLn = (CoordinateLtLn) iterator.next();
	    	getDem().set(coordinateLtLn.getIndexDataVolo(), coordinateLtLn.getDem());
	    	if(coordinateLtLn.getDem()>getMaxY()){
				setMaxY(coordinateLtLn.getDem());
				isModify = true;
	    	}
		}
    	if(isModify){
    		setMaxY(getMaxY()+2);
    	}
    	getDem().setFill(true);
    	getCombinedModel().addSeries(getDem());
       
    }
    
    private void chartAltitudineDemFun(){
    	setAltitudineDem(new LineChartSeries());  
    	getAltitudineDem().setLabel(SamUtil.getBundle().getString("chart.altitudine.dem"));
    	boolean isModify = false;
    	for (Iterator iterator = getClmChartInfo().getInfoChart().iterator(); iterator.hasNext();) {
			CoordinateLtLn coordinateLtLn = (CoordinateLtLn) iterator.next();
			getAltitudineDem().set(coordinateLtLn.getIndexDataVolo(), coordinateLtLn.getAltitudineDem());
			if(coordinateLtLn.getAltitudineDem()>getMaxY()){
				setMaxY(coordinateLtLn.getAltitudineDem());
				isModify = true;
			}
		}
    	if(isModify){
    		setMaxY(getMaxY()+2);
    	}
    	getCombinedModel().addSeries(getAltitudineDem());
    }
    
    private void chartAllarmeFun(){
    	setAllarmeValore(new LineChartSeries());  
    	getAllarmeValore().setLabel(SamUtil.getBundle().getString("chart.allarme.valore"));
    	boolean isModify = false;
    	for (Iterator iterator = getClmChartInfo().getInfoChart().iterator(); iterator.hasNext();) {
			CoordinateLtLn coordinateLtLn = (CoordinateLtLn) iterator.next();
			getAllarmeValore().set(coordinateLtLn.getIndexDataVolo(), coordinateLtLn.getAllarmi());
			if(coordinateLtLn.getAllarmi()>getMaxY()){
				setMaxY(coordinateLtLn.getAllarmi());
				isModify = true;
			}
		}
    	if(isModify){
    		setMaxY(getMaxY()+2);
    	}
    	getCombinedModel().addSeries(getAllarmeValore());
    	chartAllarmeSogliaFun();
    }
    
    private void chartAllarmeSogliaFun(){
    	setAllarmeSoglia(new LineChartSeries());  
    	getAllarmeSoglia().setLabel(SamUtil.getBundle().getString("chart.allarme.soglia"));
    	boolean isModify = false;
    	for (Iterator iterator = getClmChartInfo().getInfoChart().iterator(); iterator.hasNext();) {
			CoordinateLtLn coordinateLtLn = (CoordinateLtLn) iterator.next();
			getAllarmeSoglia().set(coordinateLtLn.getIndexDataVolo(), coordinateLtLn.getSogliaAllarme());
			if(coordinateLtLn.getSogliaAllarme()>getMaxY()){
				setMaxY(coordinateLtLn.getSogliaAllarme());
				isModify = true;
			}
		}
    	if(isModify){
    		setMaxY(getMaxY()+2);
    	}
    	getCombinedModel().addSeries(getAllarmeSoglia());
    }
    
    private void chartLivelloFun(){
    	setLivello(new LineChartSeries());
    	if(isOneChartSelected()){
    		getLivello().setLabel(SamUtil.getBundle().getString("chart.point"));
    	    getLivello().set(Integer.parseInt(getSingleOraio())+0.000000000000000000001, getMinY());  
    	    getLivello().set(Integer.parseInt(getSingleOraio()), getMaxY());
    		getCombinedModel().addSeries(getLivello());
    	}else{
    		getLivello().setLabel(SamUtil.getBundle().getString("chart.point"));
    		getLivello().set(50, getMinY()); //livello fasullo utile all init
        	getCombinedModel().addSeries(getLivello());    		
    	}    	
    }
    
    private boolean isOneChartSelected(){
    	boolean returnBool = isChartAltitudine() || isChartDem() || isChartAltitudineDem() || isChartAllarmeValore() || isChartPotassio() || isChartUranio() || isChartTorio() || isChartCesio() || isChartTorioUranio() || isChartPotassioUranio() || isChartChiQuadro() || isChartTemp() || isChartPressione() || isChartVelocita();
    	if(returnBool){
    		setPositionlegend("e");
    	}else{
    		setPositionlegend("");
    	}
    	return returnBool;
    }
    
    private void riempiComboOrari(){
		listOrari  = new ArrayList<>();
		String timeTmp_1 = "";
		String timeTmp = "";
		setSingleOrarioDesc(new HashMap<String, String>());
    	for (Iterator iterator = getClm().getTracciatoVolo().iterator(); iterator.hasNext();) {
    		CoordinateLtLnMappa coordinateLtLn = (CoordinateLtLnMappa) iterator.next();
    		timeTmp = SamUtil.getFromTimeToString(new Timestamp(coordinateLtLn.getDataOraPoint().getTime()));
    		if(timeTmp.compareTo(timeTmp_1)!=0){
	    		timeTmp_1 = timeTmp;
	   			SelectItem elementoOrario = new SelectItem();
	   			elementoOrario.setLabel(timeTmp);
	   			elementoOrario.setValue(coordinateLtLn.getIndexDataVolo().toString());
	   			getSingleOrarioDesc().put(elementoOrario.getValue().toString(), elementoOrario.getLabel());
	   			getListOrari().add(elementoOrario);
    		}
   		}
    	setFirstOrario(getListOrari().get(0).getValue().toString());
    	setLastOrario(getListOrari().get(getListOrari().size()-1).getValue().toString());
    	setDisableFrecciaSinistraChart(true);
    	setDisableFrecciaDestraChart((getListOrari().size()>1)?false:true);    	
    	setSingleOraio(getListOrari().get(0).getValue().toString());
    	//selectOraioChart();
	}
    
    public void chooseChart(){
    	log.debug("START-BaseController:chooseChart");
    	getCombinedModel().clear();
    	setMaxY(0);
    	if(isChartAltitudine()){chartAltitudineFun();}
    	if(isChartDem()){chartDemFun();}
    	if(isChartAltitudineDem()){chartAltitudineDemFun();}
    	if(isChartAllarmeValore()){chartAllarmeFun();}
    	if(isChartPotassio()){chartPotassioFun();}	
    	if(isChartUranio()){chartUranioFun();}
    	if(isChartTorio()){chartTorioFun();}
    	if(isChartCesio()){chartCesioFun();}
    	if(isChartTorioUranio()){chartTorioUranioFun();}
    	if(isChartPotassioUranio()){chartPotassioUranioFun();}
    	if(isChartChiQuadro()){chartChiQuadroFun();}
    	if(isChartTemp()){chartTempFun();}
    	if(isChartPressione()){chartPressioneFun();}
    	if(isChartVelocita()){chartVelocitaFun();}    	
    	chartLivelloFun();    	
    	actionSetFromJsonToObjectInfoObjImpl();
    	if(isOneChartSelected()){
    		RequestContext.getCurrentInstance().execute("singlePointMarker()");
    	}
    	log.debug("END-BaseController:chooseChart");
    }   
     
    public void selectOraioChart(){
    	log.debug("START-BaseController:selectOraioChart");    	 	    		
    	try{
    		chooseChart();
	    	disableEnableChartButton();	
	    	if(getFromJsonToObjectInfoObj().getInfoCoordinateLtLn()!=null || isOneChartSelected()){
		    	actionSetFromJsonToObjectInfoObjImpl();
		    	RequestContext.getCurrentInstance().execute("singlePointMarker()");
	    	}
		}catch(Exception e){
			log.debug(Message.FrontEnd.MessageFatal.FATAL_ERROR_READ_FILE_VOLO_RIGHE);
	    	Message.addError(Message.FrontEnd.MessageFatal.FATAL_ERROR_READ_FILE_VOLO_RIGHE);				    	
	    }	
    	log.debug("END-BaseController:selectOraioChart");
    }
    
    public void itemSelect(ItemSelectEvent event) {    	
    	log.debug("START-BaseController:itemSelect");    	
    	setSingleOraio(((Integer)event.getItemIndex()).toString()); 
    	selectOraioChart();
    	log.debug("END-BaseController:itemSelect");
    }
    
    public void actionSetFromJsonToObjectInfoObj() {
    	log.debug("START-BaseController:actionSetFromJsonToObjectInfoObj");
    	setFromJsonToObjectInfoObj(getFromJsonToObjectInfoObj().getFromJsonToObject(getFromJsonToObjectInfoStr()));
    	setSingleOraio(getFromJsonToObjectInfoObj().getInfoCoordinateLtLn().getIndexDataVolo().toString());
    	actionSetFromJsonToObjectInfoObjImpl();
    	chooseChart();
    	RequestContext.getCurrentInstance().execute("singlePointMarker()");
    	log.debug("END-BaseController:actionSetFromJsonToObjectInfoObj");
    }
    
    public void actionSetFromJsonToObjectInfoObjImpl() {
		log.debug("START-BaseController:actionSetFromJsonToObjectInfoObjImpl");
		try{
			//if(getFromJsonToObjectInfoObj().getInfoCoordinateLtLn()!=null  || isOneChartSelected() || !getCoordinataSelectKey().equals(-1)){
				CoordinateLtLn coorLtLn = serviceMapObj.ritornaMappaChartInfo(getClm().getTracciatoVolo().get(0).getIdVoloRigaKS(), Integer.parseInt(getSingleOraio()), getClm());
				
				CoordinateLtLnMappa lnlt = new CoordinateLtLnMappa();		
				lnlt.setIdVoloRigaKP(coorLtLn.getIdVoloRigaKP());
				lnlt.setLatitudine(coorLtLn.getLatitudine());
				lnlt.setLongitudine(coorLtLn.getLongitudine());
				lnlt.setRaggioCerhioCoordinata(coorLtLn.getRaggioCerhioCoordinata());
				lnlt.setColourPotassio(coorLtLn.getColourPotassio());
				lnlt.setColourUranio(coorLtLn.getColourUranio());
				lnlt.setColourTorio(coorLtLn.getColourTorio());
				lnlt.setColourCesio(coorLtLn.getColourCesio());
				lnlt.setColourTorioUranio(coorLtLn.getColourTorioUranio());
				lnlt.setColourPotassioUranio(coorLtLn.getColourPotassioUranio());
				lnlt.setColourChiQuadro(coorLtLn.getColourChiQuadro());				
		    	setSinglePointMarker(getSinglePointMarkerFromObjtoStr(lnlt));
		    	
				setCoordinataSelectKey(coorLtLn.getIdVoloRigaKP());
				setCoorGeoLt(coorLtLn.getLatitudine().toString());
				setCoorGeoLn(coorLtLn.getLongitudine().toString());
				setkPotassio(coorLtLn.getPotassio().toString());
				setTimeChartLnLtPoint(SamUtil.getFromTimeToString(new Timestamp(coorLtLn.getDataOraPoint().getTime())));
				setDtKpotassio(coorLtLn.getDtPotassio().toString());
				setEuUranio(coorLtLn.getUranio().toString());
				setDeuUranio(coorLtLn.getDtUranio().toString());
				setEthTorio(coorLtLn.getTorio().toString());
				setDtEthTorio(coorLtLn.getDtTorio().toString());
				setCsCesio(coorLtLn.getCesio().toString());
				setDtCsCesio(coorLtLn.getDtCesio().toString());
				setTorioUranio(coorLtLn.getTorioUranio().toString());
				setPotassioUranio(coorLtLn.getPotassioUranio().toString());
				setChiQuadro(coorLtLn.getChiQuadro().toString());
				setValAllarmi(coorLtLn.getAllarmi().toString());
				setTimePointHhMmSsTabDestra(SamUtil.getFromTimeToString(new Timestamp(coorLtLn.getDataOraPoint().getTime())));
				setTemperaturaTabDestra(coorLtLn.getTemperatura().toString());
				setPressioneTabDestra(coorLtLn.getPressione().toString());
				setVelocitaTabDestra(coorLtLn.getVelocita().toString());
				setAltitudineTabDestra(coorLtLn.getAltitudine().toString());
				setDemTabDestra(coorLtLn.getDem().toString());
				setAltitudinedemTabDestra(coorLtLn.getAltitudineDem().toString());
				setSingleOraio(coorLtLn.getIndexDataVolo().toString());							
				disableEnableChartButton();
			/*}else{
				setCoorGeoLt(Constant.FrontEnd.Vario.NON_DEFINITO);
				setCoorGeoLn(Constant.FrontEnd.Vario.NON_DEFINITO);
				setkPotassio(Constant.FrontEnd.Vario.NON_DEFINITO);
				setDtKpotassio(Constant.FrontEnd.Vario.NON_DEFINITO);
				setEuUranio(Constant.FrontEnd.Vario.NON_DEFINITO);
				setDeuUranio(Constant.FrontEnd.Vario.NON_DEFINITO);
				setEthTorio(Constant.FrontEnd.Vario.NON_DEFINITO);
				setDtEthTorio(Constant.FrontEnd.Vario.NON_DEFINITO);
				setCsCesio(Constant.FrontEnd.Vario.NON_DEFINITO);
				setDtCsCesio(Constant.FrontEnd.Vario.NON_DEFINITO);
				setTorioUranio(Constant.FrontEnd.Vario.NON_DEFINITO);
				setPotassioUranio(Constant.FrontEnd.Vario.NON_DEFINITO);
				setChiQuadro(Constant.FrontEnd.Vario.NON_DEFINITO);
				setTimePointHhMmSsTabDestra(Constant.FrontEnd.Vario.NON_DEFINITO);
				setTemperaturaTabDestra(Constant.FrontEnd.Vario.NON_DEFINITO);
				setPressioneTabDestra(Constant.FrontEnd.Vario.NON_DEFINITO);
				setVelocitaTabDestra(Constant.FrontEnd.Vario.NON_DEFINITO);
				setAltitudineTabDestra(Constant.FrontEnd.Vario.NON_DEFINITO);
				setDemTabDestra(Constant.FrontEnd.Vario.NON_DEFINITO);
				setAltitudinedemTabDestra(Constant.FrontEnd.Vario.NON_DEFINITO);
			}*/
			if(getFromJsonToObjectInfoObj().getInfoQuadrato()!=null){	
				log.debug("fromJsonToObject: " + getFromJsonToObjectInfoObj().getInfoQuadrato().getDescrQuadrato());
			}
		}catch(ControllerException contrEx){
			log.debug(contrEx.getgetMessageExceptionLog());
			contrEx.getMessageExceptionVideo();
		}catch(Exception e){
			log.debug(Message.FrontEnd.MessageFatal.FATAL_ERROR_READ_FILE_VOLO_RIGHE);
	    	Message.addError(Message.FrontEnd.MessageFatal.FATAL_ERROR_READ_FILE_VOLO_RIGHE);				    	
	    }
		log.debug("END-BaseController:actionSetFromJsonToObjectInfoObjImpl");
	}
    
    public void scorriChartSinistra(){
    	log.debug("START-BaseController:scorriChartSinistra");
    	if(!isDisableFrecciaSinistraChart()){
    		for (int i = 0; i < getListOrari().size(); i++) {
				if(getSingleOraio().equals(getListOrari().get(i).getValue().toString())){
					setSingleOraio(getListOrari().get(i-1).getValue().toString());
					setDisableFrecciaDestraChart(false);
					break;
				}
			}
    		selectOraioChart();
    	}    	
    	if(getSingleOraio().equals(getFirstOrario())){
    		setDisableFrecciaSinistraChart(true);
    	}
    	log.debug("END-BaseController:scorriChartSinistra");    	
    }
	
    public void scorriChartDestra(){
    	log.debug("START-BaseController:scorriChartDestra");
    	if(!isDisableFrecciaDestraChart()){
    		for (int i = 0; i < getListOrari().size(); i++) {
				if(getSingleOraio().equals(getListOrari().get(i).getValue().toString())){
					setSingleOraio(getListOrari().get(i+1).getValue().toString());
					setDisableFrecciaSinistraChart(false);
					break;
				}
			}
    		selectOraioChart();
    	}    	
    	if(getSingleOraio().equals(getLastOrario())){
    		setDisableFrecciaDestraChart(true);
    	}
    	log.debug("END-BaseController:scorriChartDestra");
    }
    
    public void disableEnableChartButton(){
    	log.debug("START-BaseController:disableEnableChartButton");
    	if(getSingleOraio().equals(getFirstOrario())){
    		setDisableFrecciaSinistraChart(true);
    		if(getListOrari().size()>1){
	    		setDisableFrecciaDestraChart(false);
    		}
    	}else{
    		if(getSingleOraio().equals(getLastOrario())){
	    		setDisableFrecciaDestraChart(true);
	    		if(getListOrari().size()>1){
		    		setDisableFrecciaSinistraChart(false);
	    	  }
    		}else{
    			if(getListOrari().size()>1){
	    			setDisableFrecciaSinistraChart(false);
		    		setDisableFrecciaDestraChart(false);
	    		}	
    		}    		
    	}
    	log.debug("END-BaseController:disableEnableChartButton");
    }
    
	//setter and getter
	public String getSingleElemento() {
		return singleElemento;
	}

	public void setSingleElemento(String singleElemento) {
		this.singleElemento = singleElemento;
	}

	public List<SelectItem> getListElementi() {
		return listElementi;
	}

	public void setListElementi(List<SelectItem> listElementi) {
		this.listElementi = listElementi;
	}

	public String getFromObjectToJsonMapStr() {
		return fromObjectToJsonMapStr;
	}

	public void setFromObjectToJsonMapStr(String fromObjectToJsonMapStr) {
		this.fromObjectToJsonMapStr = fromObjectToJsonMapStr;
	}

	public String getFromJsonToObjectInfoStr() {
		return fromJsonToObjectInfoStr;
	}

	public void setFromJsonToObjectInfoStr(String fromJsonToObjectInfoStr) {
		this.fromJsonToObjectInfoStr = fromJsonToObjectInfoStr;
	}

	public ClassMappeInfoReturn getFromJsonToObjectInfoObj() {
		return fromJsonToObjectInfoObj;
	}

	public void setFromJsonToObjectInfoObj(
			ClassMappeInfoReturn fromJsonToObjectInfoObj) {
		this.fromJsonToObjectInfoObj = fromJsonToObjectInfoObj;
	}

	public ServiceMapRemote getServiceMapObj() {
		return serviceMapObj;
	}

	public void setServiceMapObj(ServiceMapRemote serviceMapObj) {
		this.serviceMapObj = serviceMapObj;
	}

	public String getkPotassio() {
		return kPotassio;
	}

	public void setkPotassio(String kPotassio) {
		this.kPotassio = kPotassio;
	}

	public String getDtKpotassio() {
		return dtKpotassio;
	}

	public void setDtKpotassio(String dtKpotassio) {
		this.dtKpotassio = dtKpotassio;
	}

	public String getEuUranio() {
		return euUranio;
	}

	public void setEuUranio(String euUranio) {
		this.euUranio = euUranio;
	}

	public String getDeuUranio() {
		return deuUranio;
	}

	public void setDeuUranio(String deuUranio) {
		this.deuUranio = deuUranio;
	}

	public String getEthTorio() {
		return ethTorio;
	}

	public void setEthTorio(String ethTorio) {
		this.ethTorio = ethTorio;
	}

	public String getDtEthTorio() {
		return dtEthTorio;
	}

	public void setDtEthTorio(String dtEthTorio) {
		this.dtEthTorio = dtEthTorio;
	}

	public String getCsCesio() {
		return csCesio;
	}

	public void setCsCesio(String csCesio) {
		this.csCesio = csCesio;
	}

	public String getDtCsCesio() {
		return dtCsCesio;
	}

	public void setDtCsCesio(String dtCsCesio) {
		this.dtCsCesio = dtCsCesio;
	}

	public String getCoorGeoLt() {
		return coorGeoLt;
	}

	public void setCoorGeoLt(String coorGeoLt) {
		this.coorGeoLt = coorGeoLt;
	}

	public String getCoorGeoLn() {
		return coorGeoLn;
	}

	public void setCoorGeoLn(String coorGeoLn) {
		this.coorGeoLn = coorGeoLn;
	}


	public CartesianChartModel getCombinedModel() {
		return combinedModel;
	}


	public void setCombinedModel(CartesianChartModel combinedModel) {
		this.combinedModel = combinedModel;
	}

	public boolean isChartUranio() {
		return chartUranio;
	}

	public void setChartUranio(boolean chartUranio) {
		this.chartUranio = chartUranio;
	}

	public boolean isChartTorio() {
		return chartTorio;
	}

	public void setChartTorio(boolean chartTorio) {
		this.chartTorio = chartTorio;
	}

	public boolean isChartCesio() {
		return chartCesio;
	}

	public void setChartCesio(boolean chartCesio) {
		this.chartCesio = chartCesio;
	}

	public boolean isChartTemp() {
		return chartTemp;
	}

	public void setChartTemp(boolean chartTemp) {
		this.chartTemp = chartTemp;
	}

	public boolean isChartPressione() {
		return chartPressione;
	}

	public void setChartPressione(boolean chartPressione) {
		this.chartPressione = chartPressione;
	}

	public boolean isChartVelocita() {
		return chartVelocita;
	}

	public void setChartVelocita(boolean chartVelocita) {
		this.chartVelocita = chartVelocita;
	}

	public boolean isChartAltitudine() {
		return chartAltitudine;
	}

	public void setChartAltitudine(boolean chartAltitudine) {
		this.chartAltitudine = chartAltitudine;
	}

	public boolean isChartDem() {
		return chartDem;
	}

	public void setChartDem(boolean chartDem) {
		this.chartDem = chartDem;
	}

	public boolean isChartAltitudineDem() {
		return chartAltitudineDem;
	}

	public void setChartAltitudineDem(boolean chartAltitudineDem) {
		this.chartAltitudineDem = chartAltitudineDem;
	}

	public boolean isChartPotassio() {
		return chartPotassio;
	}

	public void setChartPotassio(boolean chartPotassio) {
		this.chartPotassio = chartPotassio;
	}

	public LineChartSeries getPotassio() {
		return potassio;
	}

	public void setPotassio(LineChartSeries potassio) {
		this.potassio = potassio;
	}

	public ChartSeries getUranio() {
		return uranio;
	}

	public void setUranio(LineChartSeries uranio) {
		this.uranio = uranio;
	}

	public LineChartSeries getTorio() {
		return torio;
	}

	public void setTorio(LineChartSeries torio) {
		this.torio = torio;
	}

	public LineChartSeries getCesio() {
		return cesio;
	}

	public void setCesio(LineChartSeries cesio) {
		this.cesio = cesio;
	}

	public LineChartSeries getTemp() {
		return temp;
	}

	public void setTemp(LineChartSeries temp) {
		this.temp = temp;
	}

	public LineChartSeries getPressione() {
		return pressione;
	}

	public void setPressione(LineChartSeries pressione) {
		this.pressione = pressione;
	}

	public LineChartSeries getAltitudine() {
		return altitudine;
	}

	public void setAltitudine(LineChartSeries altitudine) {
		this.altitudine = altitudine;
	}

	public LineChartSeries getDem() {
		return dem;
	}

	public void setDem(LineChartSeries dem) {
		this.dem = dem;
	}

	public LineChartSeries getAltitudineDem() {
		return altitudineDem;
	}

	public void setAltitudineDem(LineChartSeries altitudineDem) {
		this.altitudineDem = altitudineDem;
	}

	public LineChartSeries getVelocita() {
		return velocita;
	}

	public void setVelocita(LineChartSeries velocita) {
		this.velocita = velocita;
	}

	public LineChartSeries getLivello() {
		return livello;
	}

	public void setLivello(LineChartSeries livello) {
		this.livello = livello;
	}

	public String getPositionlegend() {
		return positionlegend;
	}

	public void setPositionlegend(String positionlegend) {
		this.positionlegend = positionlegend;
	}

	public double getMaxY() {
		return maxY;
	}

	public void setMaxY(double maxY) {
		this.maxY = maxY;
	}

	public double getMinY() {
		return minY;
	}

	public void setMinY(double minY) {
		this.minY = minY;
	}

	public String getTimeChartLnLtPoint() {
		return timeChartLnLtPoint;
	}

	public void setTimeChartLnLtPoint(String timeChartLnLtPoint) {
		this.timeChartLnLtPoint = timeChartLnLtPoint;
	}

	public String getSingleOraio() {
		return singleOraio;
	}

	public void setSingleOraio(String singleOraio) {
		this.singleOraio = singleOraio;
	}

	public List<SelectItem> getListOrari() {
		return listOrari;
	}

	public void setListOrari(List<SelectItem> listOrari) {
		this.listOrari = listOrari;
	}

	public ClassMappe getClm() {
		return clm;
	}

	public void setClm(ClassMappe clm) {
		this.clm = clm;
	}

	public String getTemperaturaTabDestra() {
		return temperaturaTabDestra;
	}

	public void setTemperaturaTabDestra(String temperaturaTabDestra) {
		this.temperaturaTabDestra = temperaturaTabDestra;
	}

	public String getPressioneTabDestra() {
		return pressioneTabDestra;
	}

	public void setPressioneTabDestra(String pressioneTabDestra) {
		this.pressioneTabDestra = pressioneTabDestra;
	}

	public String getVelocitaTabDestra() {
		return velocitaTabDestra;
	}

	public void setVelocitaTabDestra(String velocitaTabDestra) {
		this.velocitaTabDestra = velocitaTabDestra;
	}

	public String getAltitudineTabDestra() {
		return altitudineTabDestra;
	}

	public void setAltitudineTabDestra(String altitudineTabDestra) {
		this.altitudineTabDestra = altitudineTabDestra;
	}

	public String getDemTabDestra() {
		return demTabDestra;
	}

	public void setDemTabDestra(String demTabDestra) {
		this.demTabDestra = demTabDestra;
	}

	public String getAltitudinedemTabDestra() {
		return altitudinedemTabDestra;
	}

	public void setAltitudinedemTabDestra(String altitudinedemTabDestra) {
		this.altitudinedemTabDestra = altitudinedemTabDestra;
	}

	public ClassMappeChartInfo getClmChartInfo() {
		return clmChartInfo;
	}

	public void setClmChartInfo(ClassMappeChartInfo clmChartInfo) {
		this.clmChartInfo = clmChartInfo;
	}

	public String getTimePointHhMmSsTabDestra() {
		return timePointHhMmSsTabDestra;
	}

	public void setTimePointHhMmSsTabDestra(String timePointHhMmSsTabDestra) {
		this.timePointHhMmSsTabDestra = timePointHhMmSsTabDestra;
	}

	public boolean isDisableFrecciaDestraChart() {
		return disableFrecciaDestraChart;
	}

	public void setDisableFrecciaDestraChart(boolean disableFrecciaDestraChart) {
		this.disableFrecciaDestraChart = disableFrecciaDestraChart;
	}

	public boolean isDisableFrecciaSinistraChart() {
		return disableFrecciaSinistraChart;
	}

	public void setDisableFrecciaSinistraChart(boolean disableFrecciaSinistraChart) {
		this.disableFrecciaSinistraChart = disableFrecciaSinistraChart;
	}

	public String getFirstOrario() {
		return firstOrario;
	}

	public void setFirstOrario(String firstOrario) {
		this.firstOrario = firstOrario;
	}

	public String getLastOrario() {
		return lastOrario;
	}

	public void setLastOrario(String lastOrario) {
		this.lastOrario = lastOrario;
	}
	public Integer getCoordinataSelectKey() {
		return coordinataSelectKey;
	}

	public void setCoordinataSelectKey(Integer coordinataSelectKey) {
		this.coordinataSelectKey = coordinataSelectKey;
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

	public Logger getLog() {
		return log;
	}
	
	public String getSingleOraioStamp() {
		try{
			setSingleOraioStamp(getSingleOrarioDesc().get(getSingleOraio()));			
		}catch(Exception e){
			singleOraioStamp="";
		}
		return singleOraioStamp;
	}

	public void setSingleOraioStamp(String singleOraioStamp) {
		this.singleOraioStamp = singleOraioStamp;
	}

	public HashMap<String, String> getSingleOrarioDesc() {
		return singleOrarioDesc;
	}

	public void setSingleOrarioDesc(HashMap<String, String> singleOrarioDesc) {
		this.singleOrarioDesc = singleOrarioDesc;
	}

	public String getSinglePointMarker() {
		return singlePointMarker;
	}

	public void setSinglePointMarker(String singlePointMarker) {
		this.singlePointMarker = singlePointMarker;
	}

	public String getSinglePointMarkerFromObjtoStr(CoordinateLtLnMappa lnlt) {
		Gson gson = new Gson();
		return gson.toJson(lnlt);
	}

	public boolean isTypeController() {
		return typeConcentrazioniController;
	}

	public void setTypeController(boolean typeController) {
		this.typeConcentrazioniController = typeController;
	}

	public boolean isTypeGeneraleController() {
		return typeGeneraleController;
	}

	public void setTypeGeneraleController(boolean typeGeneraleController) {
		this.typeGeneraleController = typeGeneraleController;
	}

	public boolean isTypeConcentrazioniController() {
		return typeConcentrazioniController;
	}

	public void setTypeConcentrazioniController(boolean typeConcentrazioniController) {
		this.typeConcentrazioniController = typeConcentrazioniController;
	}

	public boolean isTypeAllarmeController() {
		return typeAllarmeController;
	}

	public void setTypeAllarmeController(boolean typeAllarmeController) {
		this.typeAllarmeController = typeAllarmeController;
	}

	public String getTorioUranio() {
		return torioUranio;
	}

	public void setTorioUranio(String torioUranio) {
		this.torioUranio = torioUranio;
	}

	public String getPotassioUranio() {
		return potassioUranio;
	}

	public void setPotassioUranio(String potassioUranio) {
		this.potassioUranio = potassioUranio;
	}

	public String getChiQuadro() {
		return chiQuadro;
	}

	public void setChiQuadro(String chiQuadro) {
		this.chiQuadro = chiQuadro;
	}

	public LineChartSeries getLinePotassioUranio() {
		return linePotassioUranio;
	}

	public void setLinePotassioUranio(LineChartSeries linePotassioUranio) {
		this.linePotassioUranio = linePotassioUranio;
	}

	public LineChartSeries getLineChiQuadro() {
		return lineChiQuadro;
	}

	public void setLineChiQuadro(LineChartSeries lineChiQuadro) {
		this.lineChiQuadro = lineChiQuadro;
	}

	public LineChartSeries getLineTorioUranio() {
		return lineTorioUranio;
	}

	public void setLineTorioUranio(LineChartSeries lineTorioUranio) {
		this.lineTorioUranio = lineTorioUranio;
	}

	public boolean isChartTorioUranio() {
		return chartTorioUranio;
	}

	public void setChartTorioUranio(boolean chartTorioUranio) {
		this.chartTorioUranio = chartTorioUranio;
	}

	public boolean isChartPotassioUranio() {
		return chartPotassioUranio;
	}

	public void setChartPotassioUranio(boolean chartPotassioUranio) {
		this.chartPotassioUranio = chartPotassioUranio;
	}

	public boolean isChartChiQuadro() {
		return chartChiQuadro;
	}

	public void setChartChiQuadro(boolean chartChiQuadro) {
		this.chartChiQuadro = chartChiQuadro;
	}

	public String getValAllarmi() {
		return valAllarmi;
	}

	public void setValAllarmi(String valAllarmi) {
		this.valAllarmi = valAllarmi;
	}

	public LineChartSeries getAllarmeValore() {
		return allarmeValore;
	}

	public void setAllarmeValore(LineChartSeries allarmeValore) {
		this.allarmeValore = allarmeValore;
	}

	public LineChartSeries getAllarmeSoglia() {
		return allarmeSoglia;
	}

	public void setAllarmeSoglia(LineChartSeries allarmeSoglia) {
		this.allarmeSoglia = allarmeSoglia;
	}

	public boolean isChartAllarmeValore() {
		return chartAllarmeValore;
	}

	public void setChartAllarmeValore(boolean chartAllarmeValore) {
		this.chartAllarmeValore = chartAllarmeValore;
	}
}
