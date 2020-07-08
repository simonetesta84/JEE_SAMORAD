package samorad.serviceLogin.client;

import javax.ejb.Remote;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

@Remote
public interface ServiceLoginRemote {
    
	public static final String SERVICE_ID = "DatiUtenteLocalname";
    
	public String returnName();
	
	public String returnSurname();
	
	public String returnConcut(String str);
}
