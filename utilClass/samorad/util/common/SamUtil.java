package samorad.util.common;

import java.math.BigDecimal;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.*;

import javax.faces.context.FacesContext;
import javax.xml.rpc.ServiceException;

import org.apache.log4j.Logger;

import com.sun.xml.rpc.processor.modeler.j2ee.xml.string;

import samorad.bean.contesto.Contesto;

public class SamUtil {
	
	final static Logger log = Logger.getLogger("SAMORADlogger");
	
	//for bundle to bean
	public static ResourceBundle getBundle(){
		FacesContext context = FacesContext.getCurrentInstance();
		return context.getApplication().getResourceBundle(context, "msg");
	}
	
	//for static variable
	public static void setSessionMapValue(String key, Object value) {
        FacesContext.getCurrentInstance().getExternalContext().getSessionMap().put(key, value);
    }
    public static Object getSessionMapValue(String key) {
        return FacesContext.getCurrentInstance().getExternalContext().getSessionMap().get(key);
    }
    
    //for static variable specific
    public static Contesto getContesto() {
    	  return (Contesto) FacesContext.getCurrentInstance().getExternalContext().getSessionMap().get("getContesto");
    }
    
    //from string to Date
    public static Timestamp getFromStringToDate(String dataString, String timeString) throws Exception {
    	
    	SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy hh:mm:ss");    	
    	String[] strDate = dataString.split("/");
    	Timestamp dateTime = null;
    	try {
	    	switch (strDate[1]) {
				case "Jan":
					strDate[1]="01";
					break;
				case "Feb":
					strDate[1]="02";
					break;
				case "Mar":
					strDate[1]="03";
					break;
				case "Apr":
					strDate[1]="04";
					break;
				case "May":
					strDate[1]="05";
					break;
				case "Jun":
					strDate[1]="06";
					break;
				case "Jul":
					strDate[1]="07";
					break;
				case "Aug":
					strDate[1]="08";
					break;
				case "Sep":
					strDate[1]="09";
					break;
				case "Oct":
					strDate[1]="10";
					break;
				case "Nov":
					strDate[1]="11";
					break;
				case "Dec":
					strDate[1]="12";
					break;
			}
	    	
	    	dataString = strDate[0]+"/"+strDate[1]+"/"+strDate[2]+" "+timeString;    	
    		Date date = formatter.parse(dataString);
    		dateTime = new Timestamp(date.getTime());
    	} catch (Exception e) {
    		log.debug(Message.BackEnd.MessageError.ERR_FROM_STRING_TO_DATE);
    		throw new Exception(Message.BackEnd.MessageError.ERR_FROM_STRING_TO_DATE);
    	}
    	return dateTime;
    }

    //from string to Date
    public static Timestamp getFromStringToDate(String dataString) throws Exception {
    	
    	SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy hh:mm:ss");    	
    	String[] strDate = dataString.split("/");
    	Timestamp dateTime = null;
    	try {
	    	switch (strDate[1]) {
				case "Jan":
					strDate[1]="01";
					break;
				case "Feb":
					strDate[1]="02";
					break;
				case "Mar":
					strDate[1]="03";
					break;
				case "Apr":
					strDate[1]="04";
					break;
				case "May":
					strDate[1]="05";
					break;
				case "Jun":
					strDate[1]="06";
					break;
				case "Jul":
					strDate[1]="07";
					break;
				case "Aug":
					strDate[1]="08";
					break;
				case "Sep":
					strDate[1]="09";
					break;
				case "Oct":
					strDate[1]="10";
					break;
				case "Nov":
					strDate[1]="11";
					break;
				case "Dec":
					strDate[1]="12";
					break;
			}
	    	
	    	dataString = strDate[0]+"/"+strDate[1]+"/"+strDate[2];    	
    		Date date = formatter.parse(dataString);
    		dateTime = new Timestamp(date.getTime());
    	} catch (Exception e) {
    		log.debug(Message.BackEnd.MessageError.ERR_FROM_STRING_TO_DATE);
    		throw new Exception(Message.BackEnd.MessageError.ERR_FROM_STRING_TO_DATE);
    	}
    	return dateTime;
    }
    
    //from date to string
    public static String getFromDateToString(Timestamp dataTime){    	
    	Date data = new Date(dataTime.getTime());
    	SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy");
    	String dateInString = formatter.format(data);
    	return dateInString;
    }
    
  //from date Time to string
    public static String getFromDateTimeToString(Timestamp dataTime){    	
    	Date data = new Date(dataTime.getTime());
    	SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
    	String dateInString = formatter.format(data);
    	return dateInString;
    }
    
    //from date to time
    public static String getFromTimeToString(Timestamp dataTime){
    	Date data = new Date(dataTime.getTime());
    	SimpleDateFormat formatter = new SimpleDateFormat("HH:mm:ss");
    	String timeInString = formatter.format(data);
    	return timeInString;
    }
    
    //get coordinata double
    public static double getCoordinata(String coordinataGeo){
    	Double returnDoubleCoor = null;
    	if(coordinataGeo.startsWith(Constant.BackEnd.CoordinateGeo.NORD)){
    		returnDoubleCoor = Double.parseDouble(coordinataGeo.substring(1));
    	}else if(coordinataGeo.startsWith(Constant.BackEnd.CoordinateGeo.SUD)){
    		returnDoubleCoor = - Double.parseDouble(coordinataGeo.substring(1));
    	}else if(coordinataGeo.startsWith(Constant.BackEnd.CoordinateGeo.EST)){
    		returnDoubleCoor = Double.parseDouble(coordinataGeo.substring(1));
    	}else if(coordinataGeo.startsWith(Constant.BackEnd.CoordinateGeo.OVEST)){
    		returnDoubleCoor = - Double.parseDouble(coordinataGeo.substring(1));
    	}
    	return returnDoubleCoor;
    }
    
    public static String getHourInString(Integer ora, Integer minuti, Integer secondi){
    	
    	return ((ora<10)?("0" + ora.toString()):ora.toString()) +":"+ ((minuti<10)?("0" + minuti.toString()):minuti.toString()) +":"+ ((secondi<10)?("0" + secondi.toString()):secondi.toString());
    }
    
	 public static String getHourInString(Integer ora, Integer minuti){
	    	
	   	return ((ora<10)?("0" + ora.toString()):ora.toString()) +":"+ ((minuti<10)?("0" + minuti.toString()):minuti.toString());
	 }
	 
	 public static Double trunkDueCifreDecimali(Double ValNonTrunk){
		 return (new BigDecimal(ValNonTrunk)).setScale(2, BigDecimal.ROUND_HALF_UP).doubleValue();
	 }
	 
	 public static Double trunkTreCifreDecimali(Double ValNonTrunk){
		 return (new BigDecimal(ValNonTrunk)).setScale(3, BigDecimal.ROUND_HALF_UP).doubleValue();
	 }

}
