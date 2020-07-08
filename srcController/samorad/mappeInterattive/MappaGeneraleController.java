package samorad.mappeInterattive;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.ejb.PostActivate;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;
import javax.faces.event.ActionEvent;

import org.primefaces.context.RequestContext;
import org.primefaces.model.chart.CartesianChartModel;
import org.primefaces.model.chart.LineChartSeries;

import samorad.bean.mappe.ClassMappe;
import samorad.bean.mappe.ClassMappeInfoReturn;
import samorad.bean.mappe.CoordinateLtLn;
import samorad.serviceLogin.client.ServiceLoginRemote;
import samorad.util.common.Message;
import samorad.util.common.SamUtil;

import org.primefaces.model.chart.MeterGaugeChartModel;

import com.google.gson.Gson;

@ManagedBean (name = "mappaGeneraleController")
@ViewScoped
public class MappaGeneraleController extends BaseController{
	
	@PostConstruct
	public void init(){
		log.debug("START-MappaGeneraleController:init");
		super.init();
		SamUtil.getContesto().setDisableBottonAnnulla(false);
		setTypeGeneraleController(true);
		setTypeConcentrazioniController(false);
		setTypeAllarmeController(false);
		log.debug("END-MappaGeneraleController:init");				
	}
}
