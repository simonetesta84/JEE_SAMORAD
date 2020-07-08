package samorad.serviceLogin;

import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionManagement;
import javax.ejb.TransactionAttributeType;
import javax.ejb.TransactionManagementType;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import samorad.serviceLogin.client.ServiceLoginLocal;
import samorad.serviceLogin.client.ServiceLoginRemote;




@SuppressWarnings("serial")
@Stateless(name = ServiceLoginRemote.SERVICE_ID)
@TransactionManagement(value = TransactionManagementType.CONTAINER)
@TransactionAttribute(TransactionAttributeType.REQUIRES_NEW)
public class ServiceLogin implements ServiceLoginLocal, ServiceLoginRemote {
	
	@PersistenceContext
	private EntityManager em;
	
	public String returnName(){		
		return "simone";
	}
	
	public String returnSurname(){		
		return "tes";
	}
	
	public String  returnConcut(String str){	
		return str + "EJBService";
	}
}
