package samorad.fileVolo;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;
import javax.faces.event.ActionEvent;
import javax.faces.model.SelectItem;
import javax.persistence.criteria.CommonAbstractCriteria;
import javax.xml.rpc.ServiceException;

import org.apache.log4j.Logger;
import org.primefaces.event.FileUploadEvent;
import org.primefaces.model.UploadedFile;

import samorad.exception.ControllerException;
import samorad.file.db.FileVolo;
import samorad.serviceMap.client.ServiceMapRemote;
import samorad.util.common.Constant;
import samorad.util.common.Message;
import samorad.util.common.SamUtil;


@ManagedBean (name = "fileVoloController")
@ViewScoped
public class FileVoloController implements Serializable{

	private static final Logger log = Logger.getLogger("SAMORADlogger");
	
	@EJB
    private ServiceMapRemote serviceMapObj;
	
	public static final String NAVIGATION_RULE_OK = "/pages/mappeInterattive/mappaGenerale.xhtml"+"?faces-redirect=true";
	public static final String NAVIGATION_RULE_LOGOUT = "/pages/login/loginPage.xhtml"+"?faces-redirect=true";
	
	private String optionRadio;
	private UploadedFile file; 
	private Integer fileVoloDataBaseSingolo;
	private List<SelectItem> fileVoloDataBaseList  = new ArrayList<>();	
	private Integer progress;	
	
	private boolean disableAnnulla;
	
	@PostConstruct
	public void init(){
		log.debug("START-FileVoloController:init");		
		setOptionRadio(Constant.FrontEnd.FileVoloConstant.RADIO_FILE_UPLOAD_SELECT);
		setFileVoloDataBaseSingolo(Constant.FrontEnd.Vario.MINUS_UNO);
		setDisableAnnulla(SamUtil.getContesto().isDisableBottonAnnulla());
		setProgress(null);		
		try{
			setFileVoloDataBaseList(getServiceMapObj().retunIntestazioneFileStoredInDB(SamUtil.getContesto().getIdUser()));
			log.debug("END-FileVoloController:init");
		}catch(ControllerException contrEx){
			log.debug(contrEx.getgetMessageExceptionLog());
			contrEx.getMessageExceptionVideo();
		}catch(Exception e){
			log.debug(Message.FrontEnd.MessageError.ERR_NON_GESTITO_FILE_LOG);
	    	Message.addError(Message.FrontEnd.MessageError.ERR_NON_GESTITO_FILE_VIDEO);				    	
	    }

	} 
	
	public void enableButtonAnnulla(){
		log.debug("START-FileVoloController:enableButtonAnnulla");	
		setDisableAnnulla(SamUtil.getContesto().isDisableBottonAnnulla());
		log.debug("END-FileVoloController:enableButtonAnnulla");
	}
	
	public String esploraMappa(){
		log.debug("START-FileVoloController:esploraMappa");		
		String returnPage = null;
		Integer idFile = Constant.BackEnd.Vario.MINUS_UNO;
		try{
			if(getOptionRadio().equals(Constant.FrontEnd.FileVoloConstant.RADIO_FILE_COMBO_SELECT)){
				if(!(getFileVoloDataBaseSingolo().equals(Constant.FrontEnd.Vario.MINUS_UNO))){
					returnPage = NAVIGATION_RULE_OK;					
					SamUtil.getContesto().setIdFile(getFileVoloDataBaseSingolo());
				}else{
					throw new ControllerException(Message.FrontEnd.MessageWarm.WARM_NO_FILE_SELECT,Constant.FrontEnd.TipoMsg.WARM);
				}
			}
			
			if(getOptionRadio().equals(Constant.FrontEnd.FileVoloConstant.RADIO_FILE_UPLOAD_SELECT)){
				if(getFile() != null && (getFile().getSize()>0)) {  
				       //****gestione file upload*****
					FileVolo intestazioneFile = new FileVolo();
					//inserisco l'intestazione del file nella tabella file_volo					
					idFile = getServiceMapObj().insertIntestazioneFileToDb(SamUtil.getContesto().getIdUser(), getFile().getFileName());
					if(idFile.equals(Constant.BackEnd.Vario.MINUS_UNO)){
						returnPage = NAVIGATION_RULE_OK;
					}else{
						BufferedReader br= new BufferedReader(new InputStreamReader(getFile().getInputstream()));
			            String line=br.readLine();			            		
			            Integer numRigheFilePasso = ((int)getFile().getSize()/Constant.FrontEnd.Vario.NUM_BYTE_APPROSS_LINE)/100; //print processBar ogni numRigheFilePasso			           
			            int j=0;
			            int k=0;
			            int p=1;
			            //while(line!=null){
			            while(j<3000){			            	
			            	if(j>2){
			            		if(k==numRigheFilePasso*p){
			            			progress = p;
			            			p++;
			            		}			            		
			            		returnPage = NAVIGATION_RULE_OK;
			            		getServiceMapObj().insertLineFileToDb(line, idFile, k);			            	
			            		k++;
			            	}
			            	line=br.readLine();
			            	j++;
			            }
			            if(j<2){
		            		getServiceMapObj().removeIntestazioneFileToDb(idFile);
			            }else{
				            SamUtil.getContesto().setIdFile(idFile);
				            getServiceMapObj().updateIntestazioneFile(idFile);
			            }
					}
				}else{
					throw new ControllerException(Message.FrontEnd.MessageWarm.WARM_NO_FILE_UPLOAD,Constant.FrontEnd.TipoMsg.WARM);
				}
			}
		}catch(ControllerException contrEx){
			log.debug(contrEx.getgetMessageExceptionLog());
			try{
				getServiceMapObj().removeIntestazioneFileToDb(idFile);
			}catch(Exception e1){}
			returnPage = null;
			contrEx.getMessageExceptionVideo();
		}catch(Exception e){
			log.debug(Message.FrontEnd.MessageError.ERR_NON_GESTITO_FILE_LOG);
	    	returnPage = null;
	    	try{
				getServiceMapObj().removeIntestazioneFileToDb(idFile);
			}catch(Exception e1){}
	    	Message.addError(Message.FrontEnd.MessageError.ERR_NON_GESTITO_FILE_VIDEO);				    	
	    }
		log.debug("END-FileVoloController:esploraMappa");
		return returnPage;	
	}
	
	public String returnMappa(){
		log.debug("START-FileVoloController:returnMappa");
		log.debug("END-FileVoloController:returnMappa");
		return NAVIGATION_RULE_OK;		
	}
	
	public void onChooseRadio(){
		log.debug("START-FileVoloController:onChoseRadio");
		setFileVoloDataBaseSingolo(Constant.FrontEnd.Vario.MINUS_UNO);
		log.debug("END-FileVoloController:onChoseRadio");
	}
	
	public Integer getProgress() {  
        if(progress == null)  
            progress = 0;  
        else {                         
            if(progress > 100)  
                progress = 100;  
        }  
          
        return progress;  
    }
  
    public void setProgress(Integer progress) {  
        this.progress = progress;  
    }

	//-----getter setter-----
	public String getOptionRadio() {
		return optionRadio;
	}

	public void setOptionRadio(String optionRadio) {
		this.optionRadio = optionRadio;
	}	
	
	public UploadedFile getFile() {  
        return file;  
    }  		  
    
	public void setFile(UploadedFile file) {  
        this.file = file;  
    }

	public Integer getFileVoloDataBaseSingolo() {
		return fileVoloDataBaseSingolo;
	}

	public void setFileVoloDataBaseSingolo(Integer fileVoloDataBaseSingolo) {
		this.fileVoloDataBaseSingolo = fileVoloDataBaseSingolo;
	}

	public List<SelectItem> getFileVoloDataBaseList() {
		return fileVoloDataBaseList;
	}

	public void setFileVoloDataBaseList(List<SelectItem> fileVoloDataBaseList) {
		this.fileVoloDataBaseList = fileVoloDataBaseList;
	}

	public ServiceMapRemote getServiceMapObj() {
		return serviceMapObj;
	}

	public void setServiceMapObj(ServiceMapRemote serviceMapObj) {
		this.serviceMapObj = serviceMapObj;
	}

	public boolean isDisableAnnulla() {
		return disableAnnulla;
	}

	public void setDisableAnnulla(boolean disableAnnulla) {
		this.disableAnnulla = disableAnnulla;
	}
}
