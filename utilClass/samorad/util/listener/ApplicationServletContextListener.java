package samorad.util.listener;

import javax.servlet.ServletContext;
import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;

import org.apache.log4j.PropertyConfigurator;

import samorad.bean.contesto.Contesto;
import samorad.util.common.SamUtil;

public class ApplicationServletContextListener implements ServletContextListener{
 
	public void contextInitialized(ServletContextEvent event) 
	{ 
		//configuro il logger per poter dichiarare la variabile in tutto l applicativo
		configureLog4j(event);		
	}
 
	public void contextDestroyed(ServletContextEvent event) 
	{
   
	}
 
	private void configureLog4j(ServletContextEvent event){	 
		ServletContext ctx = event.getServletContext();
		String prefix =  ctx.getRealPath("/");     
		String file = "WEB-INF" + System.getProperty("file.separator") + "log4j.properties";		
		if(file != null) {
		  PropertyConfigurator.configure(prefix+file);
		  System.out.println("Log4J Logging started for application: " + prefix+file);
		}else{
		 System.out.println("Log4J Is not configured for application Application: " + prefix+file);
		} 
	}
		
}
