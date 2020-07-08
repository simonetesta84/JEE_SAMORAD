package samorad.loginPage;

import java.io.Serializable;

import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;
import javax.faces.event.ActionEvent;

import org.apache.log4j.Logger;

import com.sun.xml.registry.uddi.bindings_v2_2.GetServiceDetail;

import samorad.bean.contesto.Contesto;
import samorad.exception.ControllerException;
import samorad.serviceLogin.client.ServiceLoginRemote;
import samorad.serviceMap.client.ServiceMapRemote;
import samorad.util.common.Constant;
import samorad.util.common.Message;
import samorad.util.common.SamUtil;


@ManagedBean (name = "loginPageController")
@SessionScoped
public class LoginPageController implements Serializable{

	@EJB
    private ServiceMapRemote serviceMapObj;
	
	private static final Logger log = Logger.getLogger("SAMORADlogger");
	
	public static final String NAVIGATION_RULE_OK = "/pages/fileVolo/selezionaFileVolo.xhtml"+"?faces-redirect=true";
	
	private String nomeUtente;		
	private String password;
	
	
	@PostConstruct
	public void init(){
		log.debug("START-LoginPageController:init");	
		//dichiaro l'oggetto contesto condiviso da tutti i bean
		Contesto contex = new Contesto();
		SamUtil.setSessionMapValue("getContesto", contex);
		
		setNomeUtente("");
		setPassword("");
		log.debug("END-LoginPageController:init");			
	}	
	
	public String entra(){
		log.debug("START-LoginPageController:entra");		
		String returnPage = null;		
		try{	
			getServiceMapObj().loginAccess(getNomeUtente(), getPassword());
			returnPage = NAVIGATION_RULE_OK;
		}catch(ControllerException contrEx){
			log.debug(contrEx.getgetMessageExceptionLog());
			returnPage = null;
			setNomeUtente("");
			setPassword("");
			contrEx.getMessageExceptionVideo();
		}catch(Exception e){
			log.debug(Message.FrontEnd.MessageError.ERR_NON_GESTITO_FILE_LOG);
	    	returnPage = null;
	    	setNomeUtente("");
			setPassword("");
	    	Message.addError(Message.FrontEnd.MessageError.ERR_NON_GESTITO_FILE_VIDEO);				    	
	    }
		log.debug("END-LoginPageController:entra");	
		return returnPage;
				
	}

	//-----getter setter------
	public String getNomeUtente() {
		return nomeUtente;
	}

	public void setNomeUtente(String nomeUtente) {
		this.nomeUtente = nomeUtente;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public ServiceMapRemote getServiceMapObj() {
		return serviceMapObj;
	}

	public void setServiceMapObj(ServiceMapRemote serviceMapObj) {
		this.serviceMapObj = serviceMapObj;
	}
}
