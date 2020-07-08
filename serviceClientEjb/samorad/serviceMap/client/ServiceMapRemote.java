package samorad.serviceMap.client;

import java.io.BufferedReader;
import java.io.InputStream;
import java.util.Date;
import java.util.List;

import javax.ejb.Remote;
import javax.faces.model.SelectItem;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.xml.rpc.ServiceException;

import samorad.bean.mappe.ClassMappe;
import samorad.bean.mappe.ClassMappeChartInfo;
import samorad.bean.mappe.CoordinateLtLn;
import samorad.exception.ControllerException;
import samorad.util.common.SamUtil;


@Remote
public interface ServiceMapRemote {
    
	public static final String SERVICE_ID = "serviceMapRemote";	
	
	public void loginAccess(String userName, String passWord) throws ControllerException;
	
	public List<SelectItem> retunIntestazioneFileStoredInDB(Integer idUser) throws ControllerException;
	public Integer insertIntestazioneFileToDb(Integer  idKsUser, String fileName) throws ControllerException;
    public void removeIntestazioneFileToDb(Integer iDFile) throws ControllerException;    
    public void updateIntestazioneFile(Integer iDFile) throws ControllerException;    
    public void insertLineFileToDb(String line, Integer IdFile, Integer indexDataVolo) throws ControllerException;
    
    public ClassMappeChartInfo ritornaMappa(Integer idUser, Integer idIntestazioneFile)throws ControllerException;
	public CoordinateLtLn ritornaMappaChartInfo(Integer idVoloRigaKS, Integer indexDataVolo, ClassMappe clm)throws ControllerException;  
	
	public void updateSogliaAllarmi(String sogliaAllarmi) throws ControllerException;  
}
