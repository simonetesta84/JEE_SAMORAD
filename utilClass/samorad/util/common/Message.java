package samorad.util.common;

import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;

public class Message {

	public static class BackEnd{
		
		public static class MessageError{			
			public static final String ERR_FROM_STRING_TO_DATE = "Errore durante la conversione da string a Data";
			public static final String ERR_INSERT_DB = "Errore durante inserimento DataBase";
			public static final String ERR_REMOVE_DB = "Errore durante il remove DataBase";
			public static final String ERR_SELECT_DB = "Errore durante il select da DataBase";
			public static final String ERR_UPDATE_INTESTAZIONE_FILE_DB = "Errore durante l'update dell'intestazione file";
			public static final String ERR_UPDATE_UPDATE_SOGLIA_ALLARME_DB = "Errore durante l'update della soglia allarme";
			public static final String ERR_LOGIN_ACCESS = "Errore durante il login";
		}
		
		public static class MessageWarm{
			public static final String WARM_FILE_UPLOAD_ESISTENTE = "Attenzione: hai già caricato il file nel DB"; 
		}
		
		public static class MessageFatal{
			public static final String FATAL_ERR_READ_FILE_RIGHE = "Errore durante la query select file_volo_righe"; 
		}
	}
	
	public static class FrontEnd{		
		
		public static class MessageWarm{
			public static final String WARM_NO_FILE_SELECT = "Attenzione: nessun File Selezionato"; 
			public static final String WARM_NO_FILE_UPLOAD = "Attenzione: nessun File Caricato";
			public static final String WARM_ERROR_LOGIN_FILE = "Username o Password non corretti!"; 
		}
		
		public static class MessageError{
			public static final String ERR_UPLOAD_FILE = "Errore durante l'upload del file";
			public static final String ERR_SELECT_FILE = "Errore durante il caricamento dei file volo disponibili";
			public static final String ERR_NON_GESTITO_FILE_LOG = "Errore non gestito durante la gestione file";
			public static final String ERR_NON_GESTITO_FILE_VIDEO = "Errore durante la gestione file: Ripetere l'operazione";
			public static final String ERR_CARICAMENTO_PAGINA = "Errore durante il caricamento ";
			public static final String ERR_UPDATE_SOGLIA_ALLARME = "Errore durante l'aggiornamento della soglia allarme";
			public static final String ERR_INSERT_SOGLIA_ALLARME = "Attenzione: inserire la Soglia Allarme nel seguente formato 0.000 (ex 0.400)";
		}
		
		public static class MessageFatal{
			public static final String FATAL_ERROR_READ_FILE_VOLO_RIGHE = "Errore si è verificato un errore potrebbe essere necessario rieseguire il login";
		}
	}
	
	
	//function
	public static void addInfo(String info) {  
	    FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO,"INFO: ", info));  
	}  
	 
	public static void addWarn(String info) {  
	    FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_WARN,"WARM: ", info));  
	}  
	 
	public static void addError(String info) {  
	    FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR,"ERROR: ", info));  
	}  
	 
	public static void addFatal(String info) {  
	    FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_FATAL,"FATAL ERROR: ", info));  
	}
}
