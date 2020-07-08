package samorad.serviceMap;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.math.BigDecimal;
import java.sql.Time;
import java.sql.Timestamp;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Random;

import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionManagement;
import javax.ejb.TransactionAttributeType;
import javax.ejb.TransactionManagementType;
import javax.faces.model.SelectItem;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaDelete;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.CriteriaUpdate;
import javax.persistence.criteria.ParameterExpression;
import javax.persistence.criteria.Root;
import javax.xml.rpc.ServiceException;

import org.apache.log4j.Logger;

import com.sun.xml.rpc.processor.modeler.j2ee.xml.string;

import samorad.bean.mappe.ClassMappe;
import samorad.bean.mappe.ClassMappeChartInfo;
import samorad.bean.mappe.ClassRangeColour;
import samorad.bean.mappe.CoordinateLnLtQuadrato;
import samorad.bean.mappe.CoordinateLtLn;
import samorad.exception.ControllerException;
import samorad.exception.ServiceEjbException;
import samorad.file.db.FileVolo;
import samorad.file.db.FileVoloRighe;
import samorad.serviceLogin.client.ServiceLoginLocal;
import samorad.serviceLogin.client.ServiceLoginRemote;
import samorad.serviceMap.client.ServiceMapLocal;
import samorad.serviceMap.client.ServiceMapRemote;
import samorad.user.db.User;
import samorad.util.common.Constant;
import samorad.util.common.Message;
import samorad.util.common.SamUtil;

@SuppressWarnings("serial")
@Stateless(name = ServiceMapRemote.SERVICE_ID)
@TransactionManagement(value = TransactionManagementType.CONTAINER)
@TransactionAttribute(TransactionAttributeType.REQUIRES_NEW)
public class ServiceMap implements ServiceMapLocal, ServiceMapRemote {
	
	final Logger log = Logger.getLogger("SAMORADlogger");
	
	@PersistenceContext
	private EntityManager em;
	
	public ClassMappeChartInfo ritornaMappa(Integer idUser, Integer idIntestazioneFile) throws ControllerException{
		log.debug("START-ServiceMap:ritornaMappa");
		ClassMappeChartInfo clmChartInfo = new ClassMappeChartInfo();
		
		Integer numAllarmiSupSoglia = 0;
		List<Double> soglieElementi;
		Integer index0Perc = null;
		Integer index5Perc = null;
		Integer index25Perc = null;
		Integer index50Perc = null;
		Integer index75Perc = null;
		Integer index95Perc = null;
		Integer index100Perc = null;
		
		/*
		Double maxValPotassio = 0.0;
		Double minValPotassio = 999999999999999999999999999999999999999999999999999999999999.0;
		
		Double maxValUranio = 0.0;
		Double minValUranio = 999999999999999999999999999999999999999999999999999999999999.0;
		
		Double maxValTorio = 0.0;
		Double minValTorio = 999999999999999999999999999999999999999999999999999999999999.0;
		
		Double maxValCesio = 0.0;
		Double minValCesio = 999999999999999999999999999999999999999999999999999999999999.0;
		
		Double maxValTorioUranio = 0.0;
		Double minValTorioUranio = 999999999999999999999999999999999999999999999999999999999999.0;
		
		Double maxValPotassioUranio = 0.0;
		Double minValPotassioUranio = 999999999999999999999999999999999999999999999999999999999999.0;
		
		Double maxValChiQuadro = 0.0;
		Double minValChiQuadro = 999999999999999999999999999999999999999999999999999999999999.0;
		
		*/
		Double maxValAllarme = 0.0;
		Double minValAllarme = 999999999999999999999999999999999999999999999999999999999999.0;
		
		
		try{
			CriteriaBuilder criteria = em.getCriteriaBuilder();
			CriteriaQuery mapper = criteria.createQuery(FileVolo.class);
			Root<FileVolo> example = mapper.from(FileVolo.class);
			mapper.select(example);
			mapper.where(
					criteria.equal(example.get("idKsUser"), idUser),
					criteria.equal(example.get("idKpFileVolo"), idIntestazioneFile)
					);
			List<FileVolo> fileVolo = em.createQuery(mapper).getResultList();
			FileVolo intestazioneFileVolo = fileVolo.get(0);
			
			CriteriaBuilder criteria2 = em.getCriteriaBuilder();
			CriteriaQuery mapper2 = criteria2.createQuery(FileVoloRighe.class);
			Root<FileVoloRighe> example2 = mapper2.from(FileVoloRighe.class);
			mapper2.select(example2);
			mapper2.where(
					criteria2.equal(example2.get("idKsFileVoloRighe"), idIntestazioneFile)
					);
			List<FileVoloRighe> fileVoloRighe = em.createQuery(mapper2).getResultList();
			
			//indici file array
			index0Perc = 0;
			index5Perc = (fileVoloRighe.size()* 5/100)-1;
			index25Perc = (fileVoloRighe.size()* 25/100)-1;
			index50Perc = (fileVoloRighe.size()* 50/100)-1;
			index75Perc = (fileVoloRighe.size()* 75/100)-1;
			index95Perc = (fileVoloRighe.size()* 95/100)-1;
			index100Perc = fileVoloRighe.size()-1;
			
			//for soglia allarmi
			for (Iterator iterator = fileVoloRighe.iterator(); iterator.hasNext();) {
				FileVoloRighe fileVoloRighe2 = (FileVoloRighe) iterator.next();
				/*
				if(maxValPotassio<fileVoloRighe2.getK()){maxValPotassio = fileVoloRighe2.getK();}
				if(minValPotassio>fileVoloRighe2.getK()){minValPotassio = fileVoloRighe2.getK();}
				
				if(maxValUranio<fileVoloRighe2.getEu()){maxValUranio = fileVoloRighe2.getEu();}
				if(minValUranio>fileVoloRighe2.getEu()){minValUranio = fileVoloRighe2.getEu();}
				
				if(maxValTorio<fileVoloRighe2.getEth()){maxValTorio = fileVoloRighe2.getEth();}
				if(minValTorio>fileVoloRighe2.getEth()){minValTorio = fileVoloRighe2.getEth();}
				
				if(maxValCesio<fileVoloRighe2.getCs()){maxValCesio = fileVoloRighe2.getCs();}
				if(minValCesio>fileVoloRighe2.getCs()){minValCesio = fileVoloRighe2.getCs();}
				
				if(maxValTorioUranio<(fileVoloRighe2.getEth()/fileVoloRighe2.getEu())){maxValTorioUranio = (fileVoloRighe2.getEth()/fileVoloRighe2.getEu());}
				if(minValTorioUranio>(fileVoloRighe2.getEth()/fileVoloRighe2.getEu())){minValTorioUranio = (fileVoloRighe2.getEth()/fileVoloRighe2.getEu());}
				
				if(maxValPotassioUranio<(fileVoloRighe2.getK()/(fileVoloRighe2.getEu()/10000))){maxValPotassioUranio = (fileVoloRighe2.getK()/(fileVoloRighe2.getEu()/10000));} //converto l uranio da ppm a perc
				if(minValPotassioUranio>(fileVoloRighe2.getK()/(fileVoloRighe2.getEu()/10000))){minValPotassioUranio = (fileVoloRighe2.getK()/(fileVoloRighe2.getEu()/10000));} //converto l uranio da ppm a perc
				
				if(maxValChiQuadro<fileVoloRighe2.getChiSq()){maxValChiQuadro = fileVoloRighe2.getChiSq();}
				if(minValChiQuadro>fileVoloRighe2.getChiSq()){minValChiQuadro = fileVoloRighe2.getChiSq();}
				*/
				
				if(maxValAllarme<fileVoloRighe2.getValoreSogliaAllarme()){maxValAllarme = fileVoloRighe2.getValoreSogliaAllarme();}
				
				if(SamUtil.getContesto().getSogliaAllarme()<fileVoloRighe2.getValoreSogliaAllarme()){
					numAllarmiSupSoglia++;
				}
			}
			
			clmChartInfo.setInfoGeneraleVolo(fileVoloRighe.get(0).getVoloDesc(), SamUtil.getFromDateToString(intestazioneFileVolo.getDataVoloStart()), SamUtil.getFromTimeToString(intestazioneFileVolo.getDataVoloStart())+" - "+SamUtil.getFromTimeToString(intestazioneFileVolo.getDataVoloEnd()), intestazioneFileVolo.getDurataVolo(), new Integer(intestazioneFileVolo.getNCoorGeo()).toString(), new Integer(intestazioneFileVolo.getNEventi()).toString(), numAllarmiSupSoglia);

			
			//for soglie potassio
			soglieElementi = new ArrayList<Double>();
			for (Iterator iterator = fileVoloRighe.iterator(); iterator.hasNext();) {
				FileVoloRighe fileVoloRighe2 = (FileVoloRighe) iterator.next();
				soglieElementi.add(fileVoloRighe2.getK());
				Collections.sort(soglieElementi);
			}
			double potassioM0 = SamUtil.trunkDueCifreDecimali(soglieElementi.get(index0Perc));
			double potassioM1 = SamUtil.trunkDueCifreDecimali(soglieElementi.get(index5Perc));
			double potassioM2 = SamUtil.trunkDueCifreDecimali(soglieElementi.get(index25Perc));
			double potassioM3 = SamUtil.trunkDueCifreDecimali(soglieElementi.get(index50Perc));
			double potassioM4 = SamUtil.trunkDueCifreDecimali(soglieElementi.get(index75Perc));
			double potassioM5 = SamUtil.trunkDueCifreDecimali(soglieElementi.get(index95Perc));
			double potassioM6 = SamUtil.trunkDueCifreDecimali(soglieElementi.get(index100Perc));	
			ClassRangeColour rangePotassio0 = new ClassRangeColour(Constant.BackEnd.Colour.AZZURRO, potassioM0, potassioM1, Constant.BackEnd.UnitMis.PERC);
			ClassRangeColour rangePotassio1 = new ClassRangeColour(Constant.BackEnd.Colour.VERDE, potassioM1, potassioM2, Constant.BackEnd.UnitMis.PERC);
			ClassRangeColour rangePotassio2 = new ClassRangeColour(Constant.BackEnd.Colour.GIALLO, potassioM2, potassioM3, Constant.BackEnd.UnitMis.PERC);
			ClassRangeColour rangePotassio3 = new ClassRangeColour(Constant.BackEnd.Colour.ARANCIONE,potassioM3, potassioM4, Constant.BackEnd.UnitMis.PERC);
			ClassRangeColour rangePotassio4 = new ClassRangeColour(Constant.BackEnd.Colour.ROSSO, potassioM4, potassioM5, Constant.BackEnd.UnitMis.PERC);
			ClassRangeColour rangePotassio5 = new ClassRangeColour(Constant.BackEnd.Colour.VIOLA, potassioM5, potassioM6, Constant.BackEnd.UnitMis.PERC);
			
			//for soglie uranio
			soglieElementi = new ArrayList<Double>();
			for (Iterator iterator = fileVoloRighe.iterator(); iterator.hasNext();) {
				FileVoloRighe fileVoloRighe2 = (FileVoloRighe) iterator.next();
				soglieElementi.add(fileVoloRighe2.getEu());
				Collections.sort(soglieElementi);
			}
			double uranioM0 = SamUtil.trunkDueCifreDecimali(soglieElementi.get(index0Perc));
			double uranioM1 = SamUtil.trunkDueCifreDecimali(soglieElementi.get(index5Perc));
			double uranioM2 = SamUtil.trunkDueCifreDecimali(soglieElementi.get(index25Perc));
			double uranioM3 = SamUtil.trunkDueCifreDecimali(soglieElementi.get(index50Perc));
			double uranioM4 = SamUtil.trunkDueCifreDecimali(soglieElementi.get(index75Perc));
			double uranioM5 = SamUtil.trunkDueCifreDecimali(soglieElementi.get(index95Perc));
			double uranioM6 = SamUtil.trunkDueCifreDecimali(soglieElementi.get(index100Perc));
			ClassRangeColour rangeUranio0 = new ClassRangeColour(Constant.BackEnd.Colour.AZZURRO, uranioM0, uranioM1, Constant.BackEnd.UnitMis.PPM);
			ClassRangeColour rangeUranio1 = new ClassRangeColour(Constant.BackEnd.Colour.VERDE, uranioM1, uranioM2, Constant.BackEnd.UnitMis.PPM);
			ClassRangeColour rangeUranio2 = new ClassRangeColour(Constant.BackEnd.Colour.GIALLO, uranioM2, uranioM3, Constant.BackEnd.UnitMis.PPM);
			ClassRangeColour rangeUranio3 = new ClassRangeColour(Constant.BackEnd.Colour.ARANCIONE, uranioM3, uranioM4, Constant.BackEnd.UnitMis.PPM);
			ClassRangeColour rangeUranio4 = new ClassRangeColour(Constant.BackEnd.Colour.ROSSO, uranioM4, uranioM5, Constant.BackEnd.UnitMis.PPM);
			ClassRangeColour rangeUranio5 = new ClassRangeColour(Constant.BackEnd.Colour.VIOLA, uranioM5, uranioM6, Constant.BackEnd.UnitMis.PPM);
			
			//for soglie torio
			soglieElementi = new ArrayList<Double>();
			for (Iterator iterator = fileVoloRighe.iterator(); iterator.hasNext();) {
				FileVoloRighe fileVoloRighe2 = (FileVoloRighe) iterator.next();
				soglieElementi.add(fileVoloRighe2.getEth());
				Collections.sort(soglieElementi);
			}
			double torioM0 = SamUtil.trunkDueCifreDecimali(soglieElementi.get(index0Perc));
			double torioM1 = SamUtil.trunkDueCifreDecimali(soglieElementi.get(index5Perc));
			double torioM2 = SamUtil.trunkDueCifreDecimali(soglieElementi.get(index25Perc));
			double torioM3 = SamUtil.trunkDueCifreDecimali(soglieElementi.get(index50Perc));
			double torioM4 = SamUtil.trunkDueCifreDecimali(soglieElementi.get(index75Perc));
			double torioM5 = SamUtil.trunkDueCifreDecimali(soglieElementi.get(index95Perc));	
			double torioM6 = SamUtil.trunkDueCifreDecimali(soglieElementi.get(index100Perc));	
			ClassRangeColour rangeTorio0 = new ClassRangeColour(Constant.BackEnd.Colour.AZZURRO, torioM0, torioM1, Constant.BackEnd.UnitMis.PPM);
			ClassRangeColour rangeTorio1 = new ClassRangeColour(Constant.BackEnd.Colour.VERDE, torioM1, torioM2, Constant.BackEnd.UnitMis.PPM);
			ClassRangeColour rangeTorio2 = new ClassRangeColour(Constant.BackEnd.Colour.GIALLO, torioM2, torioM3, Constant.BackEnd.UnitMis.PPM);
			ClassRangeColour rangeTorio3 = new ClassRangeColour(Constant.BackEnd.Colour.ARANCIONE, torioM3, torioM4, Constant.BackEnd.UnitMis.PPM);
			ClassRangeColour rangeTorio4 = new ClassRangeColour(Constant.BackEnd.Colour.ROSSO, torioM4, torioM5, Constant.BackEnd.UnitMis.PPM);		
			ClassRangeColour rangeTorio5 = new ClassRangeColour(Constant.BackEnd.Colour.VIOLA, torioM5, torioM6, Constant.BackEnd.UnitMis.PPM);		

			//for soglie cesio
			soglieElementi = new ArrayList<Double>();
			for (Iterator iterator = fileVoloRighe.iterator(); iterator.hasNext();) {
				FileVoloRighe fileVoloRighe2 = (FileVoloRighe) iterator.next();
				soglieElementi.add(fileVoloRighe2.getCs());
				Collections.sort(soglieElementi);
			}
			double cesioM0 = SamUtil.trunkDueCifreDecimali(soglieElementi.get(index0Perc));
			double cesioM1 = SamUtil.trunkDueCifreDecimali(soglieElementi.get(index5Perc));
			double cesioM2 = SamUtil.trunkDueCifreDecimali(soglieElementi.get(index25Perc));
			double cesioM3 = SamUtil.trunkDueCifreDecimali(soglieElementi.get(index50Perc));
			double cesioM4 = SamUtil.trunkDueCifreDecimali(soglieElementi.get(index75Perc));
			double cesioM5 = SamUtil.trunkDueCifreDecimali(soglieElementi.get(index95Perc));	
			double cesioM6 = SamUtil.trunkDueCifreDecimali(soglieElementi.get(index100Perc));	
			ClassRangeColour rangeCesio0 = new ClassRangeColour(Constant.BackEnd.Colour.AZZURRO, cesioM0, cesioM1, Constant.BackEnd.UnitMis.CPS);
			ClassRangeColour rangeCesio1 = new ClassRangeColour(Constant.BackEnd.Colour.VERDE, cesioM1, cesioM2, Constant.BackEnd.UnitMis.CPS);
			ClassRangeColour rangeCesio2 = new ClassRangeColour(Constant.BackEnd.Colour.GIALLO, cesioM2, cesioM3, Constant.BackEnd.UnitMis.CPS);
			ClassRangeColour rangeCesio3 = new ClassRangeColour(Constant.BackEnd.Colour.ARANCIONE, cesioM3, cesioM4, Constant.BackEnd.UnitMis.CPS);
			ClassRangeColour rangeCesio4 = new ClassRangeColour(Constant.BackEnd.Colour.ROSSO, cesioM4, cesioM5, Constant.BackEnd.UnitMis.CPS);			
			ClassRangeColour rangeCesio5 = new ClassRangeColour(Constant.BackEnd.Colour.VIOLA, cesioM5, cesioM6, Constant.BackEnd.UnitMis.CPS);	
			
			//for soglie torioUranio
			soglieElementi = new ArrayList<Double>();
			for (Iterator iterator = fileVoloRighe.iterator(); iterator.hasNext();) {
				FileVoloRighe fileVoloRighe2 = (FileVoloRighe) iterator.next();
				soglieElementi.add(fileVoloRighe2.getEth()/fileVoloRighe2.getEu());
				Collections.sort(soglieElementi);
			}
			double torioUranioM0 = SamUtil.trunkDueCifreDecimali(soglieElementi.get(index0Perc));
			double torioUranioM1 = SamUtil.trunkDueCifreDecimali(soglieElementi.get(index5Perc));
			double torioUranioM2 = SamUtil.trunkDueCifreDecimali(soglieElementi.get(index25Perc));
			double torioUranioM3 = SamUtil.trunkDueCifreDecimali(soglieElementi.get(index50Perc));
			double torioUranioM4 = SamUtil.trunkDueCifreDecimali(soglieElementi.get(index75Perc));
			double torioUranioM5 = SamUtil.trunkDueCifreDecimali(soglieElementi.get(index95Perc));
			double torioUranioM6 = SamUtil.trunkDueCifreDecimali(soglieElementi.get(index100Perc));
			ClassRangeColour rangeTorioUranio0 = new ClassRangeColour(Constant.BackEnd.Colour.AZZURRO, torioUranioM0, torioUranioM1, "");
			ClassRangeColour rangeTorioUranio1 = new ClassRangeColour(Constant.BackEnd.Colour.VERDE, torioUranioM1, torioUranioM2, "");
			ClassRangeColour rangeTorioUranio2 = new ClassRangeColour(Constant.BackEnd.Colour.GIALLO, torioUranioM2, torioUranioM3, "");
			ClassRangeColour rangeTorioUranio3 = new ClassRangeColour(Constant.BackEnd.Colour.ARANCIONE, torioUranioM3, torioUranioM4, "");
			ClassRangeColour rangeTorioUranio4 = new ClassRangeColour(Constant.BackEnd.Colour.ROSSO, torioUranioM4, torioUranioM5, "");
			ClassRangeColour rangeTorioUranio5 = new ClassRangeColour(Constant.BackEnd.Colour.VIOLA, torioUranioM5, torioUranioM6, "");

			//for soglie potassioUranio
			soglieElementi = new ArrayList<Double>();
			for (Iterator iterator = fileVoloRighe.iterator(); iterator.hasNext();) {
				FileVoloRighe fileVoloRighe2 = (FileVoloRighe) iterator.next();
				soglieElementi.add(fileVoloRighe2.getK()/(fileVoloRighe2.getEu()/10000));
				Collections.sort(soglieElementi);
			}
			double potassioUranioM0 = SamUtil.trunkDueCifreDecimali(soglieElementi.get(index0Perc));
			double potassioUranioM1 = SamUtil.trunkDueCifreDecimali(soglieElementi.get(index5Perc));
			double potassioUranioM2 = SamUtil.trunkDueCifreDecimali(soglieElementi.get(index25Perc));
			double potassioUranioM3 = SamUtil.trunkDueCifreDecimali(soglieElementi.get(index50Perc));
			double potassioUranioM4 = SamUtil.trunkDueCifreDecimali(soglieElementi.get(index75Perc));
			double potassioUranioM5 = SamUtil.trunkDueCifreDecimali(soglieElementi.get(index95Perc));
			double potassioUranioM6 = SamUtil.trunkDueCifreDecimali(soglieElementi.get(index100Perc));
			ClassRangeColour rangePotassioUranio0 = new ClassRangeColour(Constant.BackEnd.Colour.AZZURRO, potassioUranioM0, potassioUranioM1, "");
			ClassRangeColour rangePotassioUranio1 = new ClassRangeColour(Constant.BackEnd.Colour.VERDE, potassioUranioM1, potassioUranioM2, "");
			ClassRangeColour rangePotassioUranio2 = new ClassRangeColour(Constant.BackEnd.Colour.GIALLO, potassioUranioM2, potassioUranioM3, "");
			ClassRangeColour rangePotassioUranio3 = new ClassRangeColour(Constant.BackEnd.Colour.ARANCIONE, potassioUranioM3, potassioUranioM4, "");
			ClassRangeColour rangePotassioUranio4 = new ClassRangeColour(Constant.BackEnd.Colour.ROSSO, potassioUranioM4, potassioUranioM5, "");	
			ClassRangeColour rangePotassioUranio5 = new ClassRangeColour(Constant.BackEnd.Colour.VIOLA, potassioUranioM5, potassioUranioM6, "");	

			//for soglie potassioUranio
			soglieElementi = new ArrayList<Double>();
			for (Iterator iterator = fileVoloRighe.iterator(); iterator.hasNext();) {
				FileVoloRighe fileVoloRighe2 = (FileVoloRighe) iterator.next();
				soglieElementi.add(fileVoloRighe2.getChiSq());
				Collections.sort(soglieElementi);
			}
			double chiQuadroM0 = SamUtil.trunkDueCifreDecimali(soglieElementi.get(index0Perc));
			double chiQuadroM1 = SamUtil.trunkDueCifreDecimali(soglieElementi.get(index5Perc));
			double chiQuadroM2 = SamUtil.trunkDueCifreDecimali(soglieElementi.get(index25Perc));
			double chiQuadroM3 = SamUtil.trunkDueCifreDecimali(soglieElementi.get(index50Perc));
			double chiQuadroM4 = SamUtil.trunkDueCifreDecimali(soglieElementi.get(index75Perc));
			double chiQuadroM5 = SamUtil.trunkDueCifreDecimali(soglieElementi.get(index95Perc));
			double chiQuadroM6 = SamUtil.trunkDueCifreDecimali(soglieElementi.get(index100Perc));
			ClassRangeColour rangeChiQuadro0 = new ClassRangeColour(Constant.BackEnd.Colour.AZZURRO, chiQuadroM0, chiQuadroM1, "");
			ClassRangeColour rangeChiQuadro1 = new ClassRangeColour(Constant.BackEnd.Colour.VERDE, chiQuadroM1, chiQuadroM2, "");
			ClassRangeColour rangeChiQuadro2 = new ClassRangeColour(Constant.BackEnd.Colour.GIALLO, chiQuadroM2, chiQuadroM3, "");
			ClassRangeColour rangeChiQuadro3 = new ClassRangeColour(Constant.BackEnd.Colour.ARANCIONE, chiQuadroM3, chiQuadroM4, "");
			ClassRangeColour rangeChiQuadro4 = new ClassRangeColour(Constant.BackEnd.Colour.ROSSO, chiQuadroM4, chiQuadroM5, "");
			ClassRangeColour rangeChiQuadro5 = new ClassRangeColour(Constant.BackEnd.Colour.VIOLA, chiQuadroM5, chiQuadroM6, "");

			double percNUmAllarmi = 100*numAllarmiSupSoglia.doubleValue()/Double.parseDouble(clmChartInfo.getNumCoordGeo());
			Double percNUmAllarmiTrunk = SamUtil.trunkTreCifreDecimali(percNUmAllarmi);			
			
			List<ClassRangeColour> allarmiSoglieVal= new ArrayList<ClassRangeColour>();
			
			if(SamUtil.getContesto().getSogliaAllarme() > maxValAllarme){
				ClassRangeColour rangeAllarme0 = new ClassRangeColour(Constant.BackEnd.Colour.VERDE, 0.0, 100.00, "");
				ClassRangeColour rangeAllarme0Val = new ClassRangeColour(Constant.BackEnd.Colour.VERDE, 0.0, SamUtil.getContesto().getSogliaAllarme() , "");
				clmChartInfo.getClmMappa().getRangeAllarmi().add(rangeAllarme0);
				allarmiSoglieVal.add(rangeAllarme0Val);
			}else{
				ClassRangeColour rangeAllarme0 = new ClassRangeColour(Constant.BackEnd.Colour.VERDE, 0.0, 100.00-percNUmAllarmiTrunk, "");
				ClassRangeColour rangeAllarme1 = new ClassRangeColour(Constant.BackEnd.Colour.ROSSO, 100.00-percNUmAllarmiTrunk, 100.00, "");
				ClassRangeColour rangeAllarme0Val = new ClassRangeColour(Constant.BackEnd.Colour.VERDE, 0.0, SamUtil.getContesto().getSogliaAllarme() , "");
				ClassRangeColour rangeAllarme1Val = new ClassRangeColour(Constant.BackEnd.Colour.ROSSO, SamUtil.getContesto().getSogliaAllarme(), maxValAllarme , "");
				clmChartInfo.getClmMappa().getRangeAllarmi().add(rangeAllarme0);
				clmChartInfo.getClmMappa().getRangeAllarmi().add(rangeAllarme1);
				allarmiSoglieVal.add(rangeAllarme0Val);
				allarmiSoglieVal.add(rangeAllarme1Val);
			}								
			
			clmChartInfo.getClmMappa().getRangePotassio().add(rangePotassio0);
			clmChartInfo.getClmMappa().getRangePotassio().add(rangePotassio1);
			clmChartInfo.getClmMappa().getRangePotassio().add(rangePotassio2);
			clmChartInfo.getClmMappa().getRangePotassio().add(rangePotassio3);
			clmChartInfo.getClmMappa().getRangePotassio().add(rangePotassio4);
			clmChartInfo.getClmMappa().getRangePotassio().add(rangePotassio5);
			
			clmChartInfo.getClmMappa().getRangeUranio().add(rangeUranio0);
			clmChartInfo.getClmMappa().getRangeUranio().add(rangeUranio1);
			clmChartInfo.getClmMappa().getRangeUranio().add(rangeUranio2);
			clmChartInfo.getClmMappa().getRangeUranio().add(rangeUranio3);
			clmChartInfo.getClmMappa().getRangeUranio().add(rangeUranio4);
			clmChartInfo.getClmMappa().getRangeUranio().add(rangeUranio5);
			
			clmChartInfo.getClmMappa().getRangeTorio().add(rangeTorio0);
			clmChartInfo.getClmMappa().getRangeTorio().add(rangeTorio1);
			clmChartInfo.getClmMappa().getRangeTorio().add(rangeTorio2);
			clmChartInfo.getClmMappa().getRangeTorio().add(rangeTorio3);
			clmChartInfo.getClmMappa().getRangeTorio().add(rangeTorio4);
			clmChartInfo.getClmMappa().getRangeTorio().add(rangeTorio5);
			
			clmChartInfo.getClmMappa().getRangeCesio().add(rangeCesio0);
			clmChartInfo.getClmMappa().getRangeCesio().add(rangeCesio1);
			clmChartInfo.getClmMappa().getRangeCesio().add(rangeCesio2);
			clmChartInfo.getClmMappa().getRangeCesio().add(rangeCesio3);
			clmChartInfo.getClmMappa().getRangeCesio().add(rangeCesio4);
			clmChartInfo.getClmMappa().getRangeCesio().add(rangeCesio5);
			
			clmChartInfo.getClmMappa().getRangeTorioUranio().add(rangeTorioUranio0);
			clmChartInfo.getClmMappa().getRangeTorioUranio().add(rangeTorioUranio1);
			clmChartInfo.getClmMappa().getRangeTorioUranio().add(rangeTorioUranio2);
			clmChartInfo.getClmMappa().getRangeTorioUranio().add(rangeTorioUranio3);
			clmChartInfo.getClmMappa().getRangeTorioUranio().add(rangeTorioUranio4);
			clmChartInfo.getClmMappa().getRangeTorioUranio().add(rangeTorioUranio5);
			
			clmChartInfo.getClmMappa().getRangePotassioUranio().add(rangePotassioUranio0);
			clmChartInfo.getClmMappa().getRangePotassioUranio().add(rangePotassioUranio1);
			clmChartInfo.getClmMappa().getRangePotassioUranio().add(rangePotassioUranio2);
			clmChartInfo.getClmMappa().getRangePotassioUranio().add(rangePotassioUranio3);
			clmChartInfo.getClmMappa().getRangePotassioUranio().add(rangePotassioUranio4);
			clmChartInfo.getClmMappa().getRangePotassioUranio().add(rangePotassioUranio5);
			
			clmChartInfo.getClmMappa().getRangeChiQuadro().add(rangeChiQuadro0);
			clmChartInfo.getClmMappa().getRangeChiQuadro().add(rangeChiQuadro1);
			clmChartInfo.getClmMappa().getRangeChiQuadro().add(rangeChiQuadro2);
			clmChartInfo.getClmMappa().getRangeChiQuadro().add(rangeChiQuadro3);
			clmChartInfo.getClmMappa().getRangeChiQuadro().add(rangeChiQuadro4);
			clmChartInfo.getClmMappa().getRangeChiQuadro().add(rangeChiQuadro5);
			
			for (Iterator iterator = fileVoloRighe.iterator(); iterator.hasNext();) {
				FileVoloRighe fileVoloRighe2 = (FileVoloRighe) iterator.next();
				clmChartInfo.getClmMappa().addLnLtTracciatoVoloMappe(fileVoloRighe2.getIdKpFileVoloRighe(), fileVoloRighe2.getIdKsFileVoloRighe(), fileVoloRighe2.getDataVolo(), fileVoloRighe2.getCoordGeoLt(), fileVoloRighe2.getCoordGeoLn(), clmChartInfo.getClmMappa().returnColourRange(clmChartInfo.getClmMappa().getRangePotassio(), fileVoloRighe2.getK()),  clmChartInfo.getClmMappa().returnColourRange(clmChartInfo.getClmMappa().getRangeUranio(),fileVoloRighe2.getEu()), clmChartInfo.getClmMappa().returnColourRange(clmChartInfo.getClmMappa().getRangeTorio(), fileVoloRighe2.getEth()), clmChartInfo.getClmMappa().returnColourRange(clmChartInfo.getClmMappa().getRangeCesio(),fileVoloRighe2.getCs()), fileVoloRighe2.getIndexDataVolo(), clmChartInfo.getClmMappa().returnColourRange(clmChartInfo.getClmMappa().getRangeTorioUranio(), (fileVoloRighe2.getEth()/fileVoloRighe2.getEu())), clmChartInfo.getClmMappa().returnColourRange(clmChartInfo.getClmMappa().getRangePotassioUranio(), (fileVoloRighe2.getK()/(fileVoloRighe2.getEu()/10000))),  clmChartInfo.getClmMappa().returnColourRange(clmChartInfo.getClmMappa().getRangeChiQuadro(), fileVoloRighe2.getChiSq()), fileVoloRighe2.getRaggioCerchioCoordinata(), clmChartInfo.getClmMappa().returnColourRange(allarmiSoglieVal, fileVoloRighe2.getValoreSogliaAllarme()));
				clmChartInfo.getInfoChart().add(new CoordinateLtLn(fileVoloRighe2.getIdKpFileVoloRighe(), fileVoloRighe2.getIdKsFileVoloRighe(), new Date(fileVoloRighe2.getDataVolo().getTime()), fileVoloRighe2.getCoordGeoLt(), fileVoloRighe2.getCoordGeoLn(),"", fileVoloRighe2.getK(), fileVoloRighe2.getDk(), clmChartInfo.getClmMappa().returnColourRange(clmChartInfo.getClmMappa().getRangePotassio(), fileVoloRighe2.getK()),clmChartInfo.getClmMappa().returnColourRange(clmChartInfo.getClmMappa().getRangeUranio(), fileVoloRighe2.getEu()), fileVoloRighe2.getEu(), fileVoloRighe2.getDeu(), clmChartInfo.getClmMappa().returnColourRange(clmChartInfo.getClmMappa().getRangeTorio(), fileVoloRighe2.getEth()), fileVoloRighe2.getEth(), fileVoloRighe2.getDeth(), clmChartInfo.getClmMappa().returnColourRange(clmChartInfo.getClmMappa().getRangeCesio(), fileVoloRighe2.getCs()), fileVoloRighe2.getCs(), fileVoloRighe2.getDcs(), fileVoloRighe2.getDem(), fileVoloRighe2.getHAltezza(), fileVoloRighe2.getVel(), fileVoloRighe2.getTemp(),fileVoloRighe2.getPres(), fileVoloRighe2.getIndexDataVolo(), clmChartInfo.getClmMappa().returnColourRange(clmChartInfo.getClmMappa().getRangeTorioUranio(), (fileVoloRighe2.getEth()/fileVoloRighe2.getEu())), clmChartInfo.getClmMappa().returnColourRange(clmChartInfo.getClmMappa().getRangePotassioUranio(), (fileVoloRighe2.getK()/fileVoloRighe2.getEu())),  clmChartInfo.getClmMappa().returnColourRange(clmChartInfo.getClmMappa().getRangeChiQuadro(), fileVoloRighe2.getChiSq()), (fileVoloRighe2.getEth()/fileVoloRighe2.getEu()),  (fileVoloRighe2.getK()/(fileVoloRighe2.getEu()/10000)), fileVoloRighe2.getChiSq(),  fileVoloRighe2.getRaggioCerchioCoordinata(), clmChartInfo.getClmMappa().returnColourRange(clmChartInfo.getClmMappa().getRangeAllarmi(), fileVoloRighe2.getValoreSogliaAllarme()), fileVoloRighe2.getValoreSogliaAllarme(), SamUtil.trunkDueCifreDecimali(SamUtil.getContesto().getSogliaAllarme())));						
			}
			
		}catch(Exception e){
			log.debug(Message.BackEnd.MessageFatal.FATAL_ERR_READ_FILE_RIGHE);
			throw new ControllerException(Message.BackEnd.MessageFatal.FATAL_ERR_READ_FILE_RIGHE,Constant.FrontEnd.TipoMsg.FATAL_ERROR);
		}

		log.debug("END-ServiceMap:ritornaMappa");
		return clmChartInfo;
	}
	
	
	public CoordinateLtLn ritornaMappaChartInfo(Integer idVoloRigaKS, Integer indexDataVolo,  ClassMappe clm) throws ControllerException{
		log.debug("START-ServiceMap:ritornaMappaChartInfo");
		CoordinateLtLn coorLnLt = new CoordinateLtLn();
		try{
			CriteriaBuilder criteria = em.getCriteriaBuilder();
			CriteriaQuery mapper = criteria.createQuery(FileVoloRighe.class);
			Root<FileVoloRighe> example = mapper.from(FileVoloRighe.class);
			mapper.select(example);
			mapper.where(
					criteria.equal(example.get("idKsFileVoloRighe"), idVoloRigaKS),
					criteria.equal(example.get("indexDataVolo"), indexDataVolo)
					);
			List<FileVoloRighe> fileVoloRighe = em.createQuery(mapper).getResultList();
			coorLnLt = new CoordinateLtLn(fileVoloRighe.get(0).getIdKpFileVoloRighe(), fileVoloRighe.get(0).getIdKsFileVoloRighe(), new Date(fileVoloRighe.get(0).getDataVolo().getTime()), fileVoloRighe.get(0).getCoordGeoLt(), fileVoloRighe.get(0).getCoordGeoLn(),"", fileVoloRighe.get(0).getK(), fileVoloRighe.get(0).getDk(), clm.returnColourRange(clm.getRangePotassio(), fileVoloRighe.get(0).getK()),clm.returnColourRange(clm.getRangeUranio(), fileVoloRighe.get(0).getEu()), fileVoloRighe.get(0).getEu(), fileVoloRighe.get(0).getDeu(), clm.returnColourRange(clm.getRangeTorio(), fileVoloRighe.get(0).getEth()), fileVoloRighe.get(0).getEth(), fileVoloRighe.get(0).getDeth(), clm.returnColourRange(clm.getRangeCesio(), fileVoloRighe.get(0).getCs()), fileVoloRighe.get(0).getCs(), fileVoloRighe.get(0).getDcs(), fileVoloRighe.get(0).getDem(), fileVoloRighe.get(0).getHAltezza(), fileVoloRighe.get(0).getVel(), fileVoloRighe.get(0).getTemp(),fileVoloRighe.get(0).getPres(), fileVoloRighe.get(0).getIndexDataVolo(), clm.returnColourRange(clm.getRangeTorioUranio(), (fileVoloRighe.get(0).getEth()/fileVoloRighe.get(0).getEu())), clm.returnColourRange(clm.getRangePotassioUranio(), (fileVoloRighe.get(0).getK()/(fileVoloRighe.get(0).getEu()/10000))), clm.returnColourRange(clm.getRangeChiQuadro(), fileVoloRighe.get(0).getChiSq()), (fileVoloRighe.get(0).getEth()/fileVoloRighe.get(0).getEu()),  (fileVoloRighe.get(0).getK()/(fileVoloRighe.get(0).getEu()/10000)), fileVoloRighe.get(0).getChiSq(),  fileVoloRighe.get(0).getRaggioCerchioCoordinata(), clm.returnColourRange(clm.getRangeAllarmi(), fileVoloRighe.get(0).getValoreSogliaAllarme()), fileVoloRighe.get(0).getValoreSogliaAllarme(), null);
		}catch(Exception e){
			log.debug(Message.BackEnd.MessageFatal.FATAL_ERR_READ_FILE_RIGHE);
			throw new ControllerException(Message.BackEnd.MessageFatal.FATAL_ERR_READ_FILE_RIGHE,Constant.FrontEnd.TipoMsg.FATAL_ERROR);
		}
		log.debug("END-ServiceMap:ritornaMappaChartInfo");
		return coorLnLt;
	}
	
	public void loginAccess(String userName, String passWord) throws ControllerException{
		log.debug("START-ServiceMap:loginAccess");
		try{
			CriteriaBuilder criteria = em.getCriteriaBuilder();
			CriteriaQuery mapper = criteria.createQuery(User.class);
			Root<User> example = mapper.from(User.class);
			mapper.select(example);
			mapper.where(
					criteria.equal(example.get("username"), userName),
					criteria.equal(example.get("password"), passWord)
					);
			List<User> userList = em.createQuery(mapper).getResultList();
			if(userList.size()>0){
				SamUtil.getContesto().setIdUser(userList.get(0).getIdKpUser());
				SamUtil.getContesto().setNomeUser(userList.get(0).getNomeUser());
				SamUtil.getContesto().setCognomeUser(userList.get(0).getCognomeUser());
				SamUtil.getContesto().setSogliaAllarme(userList.get(0).getSogliaAllarme());
				log.debug("ID: " + userList.get(0).getIdKpUser() + "Nome user: " + userList.get(0).getNomeUser() + "Cognome User: " + userList.get(0).getCognomeUser() + "Soglia allarme: " +  userList.get(0).getSogliaAllarme());
			}else{
				throw new ServiceEjbException(Message.FrontEnd.MessageWarm.WARM_ERROR_LOGIN_FILE);	
			};
		}catch(ServiceEjbException serEjbEx){
			log.debug(serEjbEx.getMessageException());
			throw new ControllerException(serEjbEx.getMessageException(),Constant.FrontEnd.TipoMsg.WARM);
		}catch(Exception ePersisten){
			log.debug(Message.BackEnd.MessageError.ERR_LOGIN_ACCESS);
			throw new ControllerException(Message.BackEnd.MessageError.ERR_LOGIN_ACCESS,Constant.FrontEnd.TipoMsg.ERROR);
		}
		log.debug("END-ServiceMap:loginAccess");
	}
	
	public List<SelectItem> retunIntestazioneFileStoredInDB(Integer idUser) throws ControllerException{
		log.debug("START-ServiceMap:retunIntestazioneFileStoredInDB");
		List<SelectItem> listItem = new ArrayList<SelectItem>();
		try{		
			CriteriaBuilder criteria = em.getCriteriaBuilder();
			CriteriaQuery mapper = criteria.createQuery(FileVolo.class);
			Root<FileVolo> example = mapper.from(FileVolo.class);
			mapper.select(example);
			mapper.where(
					criteria.equal(example.get("idKsUser"), idUser)
					);
			List<FileVolo> fileVolo = em.createQuery(mapper).getResultList();
			
			for (Iterator iterator = fileVolo.iterator(); iterator.hasNext();) {
				FileVolo fileVolo2 = (FileVolo) iterator.next();
				SelectItem selTmp = new SelectItem();
				selTmp.setLabel(fileVolo2.getNomeFile());
				selTmp.setValue(fileVolo2.getIdKpFileVolo());
				listItem.add(selTmp);
			}
		}catch(Exception ePersisten){
			log.debug(Message.BackEnd.MessageError.ERR_SELECT_DB);
			throw new ControllerException(Message.FrontEnd.MessageError.ERR_SELECT_FILE,Constant.FrontEnd.TipoMsg.ERROR);
		}		
		log.debug("END-ServiceMap:retunIntestazioneFileStoredInDB");
		return listItem;
	}
	
	public Integer insertIntestazioneFileToDb(Integer idKsUser, String fileName)  throws ControllerException{
		log.debug("START-ServiceMap:insertIntestazioneFileToDb");
		Integer idKpFile = null;
		try{
			//controllo che per l utente 'idKsUser' il file 'fileName' non sia già nel DB
			//in caso positivo lancio eccezione di WARN e non inserisco
			CriteriaBuilder criteria = em.getCriteriaBuilder();
			CriteriaQuery<Long> mapper = criteria.createQuery(Long.class);
			Root<FileVolo> example = mapper.from(FileVolo.class);
			mapper.select(criteria.count(example));
			mapper.where(
			    criteria.equal(example.get("idKsUser"), idKsUser),
			    criteria.equal(example.get("nomeFile"), fileName)
			);
			if(em.createQuery(mapper).getSingleResult().intValue()>0){
				throw new ServiceEjbException(Message.BackEnd.MessageWarm.WARM_FILE_UPLOAD_ESISTENTE);	
			};
			
			//inserisco nuova riga sul DataBase
			FileVolo intestazioneFile = new FileVolo();
        	intestazioneFile.setIdKsUser(idKsUser);
        	intestazioneFile.setNomeFile(fileName);
        	intestazioneFile.setDataInsVoloInDataBase(new Timestamp((new Date()).getTime()));
			em.persist(intestazioneFile);			
			CriteriaBuilder criteria2 = em.getCriteriaBuilder();
			CriteriaQuery mapper2 = criteria2.createQuery(FileVolo.class);
			Root<FileVolo> example2 = mapper2.from(FileVolo.class);
			mapper2.select(example2);
			mapper2.where(
					criteria2.equal(example2.get("idKsUser"), idKsUser),
					criteria2.equal(example2.get("nomeFile"), fileName)
					);
			List<FileVolo> fileVolo = em.createQuery(mapper2).getResultList();
			idKpFile = fileVolo.get(0).getIdKpFileVolo();
		}catch(ServiceEjbException serEjbEx){
			log.debug(serEjbEx.getMessageException());
			throw new ControllerException(serEjbEx.getMessageException(),Constant.FrontEnd.TipoMsg.WARM);
		}catch(Exception ePersisten){
			log.debug(Message.BackEnd.MessageError.ERR_INSERT_DB);
			throw new ControllerException(Message.FrontEnd.MessageError.ERR_UPLOAD_FILE,Constant.FrontEnd.TipoMsg.ERROR);
		}		
		log.debug("END-ServiceMap:insertIntestazioneFileToDb");
		return idKpFile;
	}
	
    public void removeIntestazioneFileToDb(Integer iDFile) throws ControllerException{
		log.debug("START-ServiceMap:removeIntestazioneFileToDb");
		try{		
			//elimino le righe della tabella "file_volo"
			CriteriaBuilder criteria = this.em.getCriteriaBuilder();
			CriteriaDelete<FileVolo> mapping = criteria.createCriteriaDelete(FileVolo.class);			  
			Root example = mapping.from(FileVolo.class);			  
			mapping.where(criteria.equal(example.get("idKpFileVolo"), iDFile));			  
			em.createQuery(mapping).executeUpdate();

			//elimino le righe della tabella "file_volo_righe"
			CriteriaBuilder criteria2 = this.em.getCriteriaBuilder();
			CriteriaDelete<FileVoloRighe> mapping2 = criteria2.createCriteriaDelete(FileVoloRighe.class);			  
			Root example2 = mapping2.from(FileVoloRighe.class);			  
			mapping2.where(criteria2.equal(example2.get("idKsFileVoloRighe"), iDFile));			  
			em.createQuery(mapping2).executeUpdate();
			
		}catch(Exception ePersisten){
			log.debug(Message.BackEnd.MessageError.ERR_REMOVE_DB);
			throw new ControllerException(Message.FrontEnd.MessageError.ERR_UPLOAD_FILE,Constant.FrontEnd.TipoMsg.ERROR);
		}		
		log.debug("END-ServiceMap:removeIntestazioneFileToDb");
	}
    
    public void updateIntestazioneFile(Integer idFile) throws ControllerException{
    	log.debug("START-ServiceMap:updateIntestazioneFile");
		try{
	    	CriteriaBuilder criteria = em.getCriteriaBuilder();
			CriteriaQuery mapper = criteria.createQuery(FileVoloRighe.class);
			Root<FileVoloRighe> example = mapper.from(FileVoloRighe.class);
			mapper.select(example);
			mapper.where(criteria.equal(example.get("idKsFileVoloRighe"), idFile));
			mapper.orderBy(criteria.asc(example.get("dataVolo")));
			List<FileVoloRighe> fileVoloRighe = em.createQuery(mapper).getResultList();
						
			Integer indexEnd = fileVoloRighe.size()-1;
			long millisDiff = fileVoloRighe.get(indexEnd).getDataVolo().getTime() - fileVoloRighe.get(0).getDataVolo().getTime();
			Integer seconds = (int) (millisDiff / 1000 % 60);
			Integer minutes = (int) (millisDiff / 60000 % 60);
			Integer hours = (int) (millisDiff / 3600000 % 24);
			
			CriteriaBuilder criteria2 = this.em.getCriteriaBuilder();
			CriteriaUpdate<FileVolo> update = criteria2.createCriteriaUpdate(FileVolo.class);
			Root example2 = update.from(FileVolo.class);
			update.set("dataVoloStart", fileVoloRighe.get(0).getDataVolo());
			update.set("dataVoloEnd", fileVoloRighe.get(indexEnd).getDataVolo());
			update.set("durataVolo", hours.toString()+"h"+minutes.toString()+"m"+seconds.toString()+"s");
			update.set("nCoorGeo", indexEnd+1);
			update.set("nEventi", 0);
			update.where(criteria2.equal(example2.get("idKpFileVolo"), idFile));
			em.createQuery(update).executeUpdate();
		}catch(Exception ePersisten){
			log.debug(Message.BackEnd.MessageError.ERR_UPDATE_INTESTAZIONE_FILE_DB);
			throw new ControllerException(Message.FrontEnd.MessageError.ERR_UPLOAD_FILE,Constant.FrontEnd.TipoMsg.ERROR);
		}		
		log.debug("END-ServiceMap:updateIntestazioneFile");
    }
    
	public void insertLineFileToDb(String line, Integer idFile, Integer indexDataVolo)  throws ControllerException{
		log.debug("START-ServiceMap:insertLineFileToDb");
		String[] vettoreStringheFile = line.split(Constant.BackEnd.Vario.DELIMITATORE_LINE_FILE);
    	List<String> vettoreStringheFileNotSpace = new ArrayList<String>();
		try{
        	for (int i = 0; i < vettoreStringheFile.length; i++) {		            		
        		if(vettoreStringheFile[i].length()>0){		            					            		
        			vettoreStringheFileNotSpace.add(vettoreStringheFile[i]);        			        			
        		}
			}      		
        	FileVoloRighe rigaFile = new FileVoloRighe();
        	rigaFile.setIndexDataVolo(indexDataVolo);
			rigaFile.setIdKsFileVoloRighe(idFile); 
			rigaFile.setDataVolo(SamUtil.getFromStringToDate(vettoreStringheFileNotSpace.get(1),vettoreStringheFileNotSpace.get(2)));
			rigaFile.setOraGVolo(Double.parseDouble(vettoreStringheFileNotSpace.get(3)));
			String[] ltln = vettoreStringheFileNotSpace.get(4).split(",");
			rigaFile.setCoordGeoLt(SamUtil.getCoordinata(ltln[1]));
			rigaFile.setCoordGeoLn(SamUtil.getCoordinata(ltln[0]));
			rigaFile.setXumt(Double.parseDouble(vettoreStringheFileNotSpace.get(5)));
			rigaFile.setYumt(Double.parseDouble(vettoreStringheFileNotSpace.get(6)));
			rigaFile.setDem(Double.parseDouble(vettoreStringheFileNotSpace.get(7)));
			rigaFile.setHAltezza(Double.parseDouble(vettoreStringheFileNotSpace.get(8)));
			rigaFile.setTrTot(Double.parseDouble(vettoreStringheFileNotSpace.get(9)));
			rigaFile.setChiSq(Double.parseDouble(vettoreStringheFileNotSpace.get(10)));
			rigaFile.setK(Double.parseDouble(vettoreStringheFileNotSpace.get(11)));
			rigaFile.setDk(Double.parseDouble(vettoreStringheFileNotSpace.get(12)));
			rigaFile.setEu(Double.parseDouble(vettoreStringheFileNotSpace.get(13)));
			rigaFile.setDeu(Double.parseDouble(vettoreStringheFileNotSpace.get(14)));
			rigaFile.setEth(Double.parseDouble(vettoreStringheFileNotSpace.get(15)));
			rigaFile.setDeth(Double.parseDouble(vettoreStringheFileNotSpace.get(16)));
			rigaFile.setCs(Double.parseDouble(vettoreStringheFileNotSpace.get(17)));
			rigaFile.setDcs(Double.parseDouble(vettoreStringheFileNotSpace.get(18)));
			rigaFile.setCr(Double.parseDouble(vettoreStringheFileNotSpace.get(19)));
			rigaFile.setDcr(Double.parseDouble(vettoreStringheFileNotSpace.get(20)));
			rigaFile.setRn(Double.parseDouble(vettoreStringheFileNotSpace.get(21)));
			rigaFile.setEuRn(Double.parseDouble(vettoreStringheFileNotSpace.get(22)));
			rigaFile.setDuRn(Double.parseDouble(vettoreStringheFileNotSpace.get(23)));
			rigaFile.setCt(Double.parseDouble(vettoreStringheFileNotSpace.get(24)));
			rigaFile.setSk(Double.parseDouble(vettoreStringheFileNotSpace.get(25)));
			rigaFile.setSdk(Double.parseDouble(vettoreStringheFileNotSpace.get(26)));
			rigaFile.setCtEu(Double.parseDouble(vettoreStringheFileNotSpace.get(27)));
			rigaFile.setSeu(Double.parseDouble(vettoreStringheFileNotSpace.get(28)));
			rigaFile.setSdeu(Double.parseDouble(vettoreStringheFileNotSpace.get(29)));
			rigaFile.setCtEth(Double.parseDouble(vettoreStringheFileNotSpace.get(30)));
			rigaFile.setSeth(Double.parseDouble(vettoreStringheFileNotSpace.get(31)));
			rigaFile.setSedth(Double.parseDouble(vettoreStringheFileNotSpace.get(32)));
			rigaFile.setCtCs(Double.parseDouble(vettoreStringheFileNotSpace.get(33)));
			rigaFile.setScs(Double.parseDouble(vettoreStringheFileNotSpace.get(34)));
			rigaFile.setSdcs(Double.parseDouble(vettoreStringheFileNotSpace.get(35)));
			rigaFile.setVoloDesc(vettoreStringheFileNotSpace.get(36));
			rigaFile.setHStp(Double.parseDouble(vettoreStringheFileNotSpace.get(37)));
			rigaFile.setVel(Double.parseDouble(vettoreStringheFileNotSpace.get(38)));
			rigaFile.setTemp(Double.parseDouble(vettoreStringheFileNotSpace.get(39)));
			rigaFile.setPres(Double.parseDouble(vettoreStringheFileNotSpace.get(40)));
			rigaFile.setRaggioCerchioCoordinata(25.00);

			//FORMULA DEL => D(nGy/h) = 0.0417 * (Att di 40K [Bq/kg]) + 0.462 (Att di 238U [Bq/kg]) + 0.604 *(Att di 232Th [Bq/kg])
			double potassioK_Bq_kg = (rigaFile.getK() * 10000) / 32.3; //converto da percent to ppm to Bq/Kg
			double uranioU_Bq_Kg = ((rigaFile.getEu() / 1000) / 81); //converto da ppm to ppb to Bq/Kg
			double torioEth_Bq_Kg = ((rigaFile.getEth() / 1000) / 546); //converto da ppm to ppb to Bq/Kg
			
			double d = 0.0417*(potassioK_Bq_kg) + 0.462*(uranioU_Bq_Kg) + 0.604*(torioEth_Bq_Kg);
			double ed = 0.0012264*d;
			rigaFile.setValoreSogliaAllarme(SamUtil.trunkDueCifreDecimali(ed));
			log.debug("ED: " + ed);
			
//			Random random = new Random();
//			rigaFile.setValoreSogliaAllarme(SamUtil.trunkDueCifreDecimali(random.nextInt(4)*0.2));
			
			try{
				if(rigaFile.getVel()>Constant.BackEnd.Vario.VELOCITA_MINIMA_CAMPIONAMENTO){
					em.persist(rigaFile);
				}
			}catch(Exception ePersisten){
				log.debug(Message.BackEnd.MessageError.ERR_INSERT_DB);
				throw new Exception(Message.BackEnd.MessageError.ERR_INSERT_DB);
			}

		 }catch(Exception eTotalUpdate){
		 	if(!eTotalUpdate.getMessage().isEmpty()){
			   	log.debug(eTotalUpdate.getMessage());
		 	}
		   	log.debug(Message.FrontEnd.MessageError.ERR_UPLOAD_FILE);
		   	throw new ControllerException(Message.FrontEnd.MessageError.ERR_UPLOAD_FILE,Constant.FrontEnd.TipoMsg.ERROR);
		 }		
		log.debug("END-ServiceMap:insertLineFileToDb");
	}
	
	public void updateSogliaAllarmi(String sogliaAllarmi) throws ControllerException{
		log.debug("START-ServiceMap:updateSogliaAllarmi");
		log.debug("SOGLIA ALLARME: " + sogliaAllarmi);
		try{
			CriteriaBuilder criteria = this.em.getCriteriaBuilder();
			CriteriaUpdate<User> update = criteria.createCriteriaUpdate(User.class);
			Root example = update.from(User.class);
			update.set("sogliaAllarme", Double.parseDouble(sogliaAllarmi));
			update.where(criteria.equal(example.get("idKpUser"), SamUtil.getContesto().getIdUser()));
			em.createQuery(update).executeUpdate();
			
			SamUtil.getContesto().setSogliaAllarme(Double.parseDouble(sogliaAllarmi));

		}catch(Exception ePersisten){
			log.debug(Message.BackEnd.MessageError.ERR_UPDATE_UPDATE_SOGLIA_ALLARME_DB);
			throw new ControllerException(Message.FrontEnd.MessageError.ERR_INSERT_SOGLIA_ALLARME,Constant.FrontEnd.TipoMsg.ERROR);
		}
		log.debug("END-ServiceMap:updateSogliaAllarmi");
	}
	
}
