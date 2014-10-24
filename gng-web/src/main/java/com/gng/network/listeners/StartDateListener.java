package com.gng.network.listeners;

import java.util.Date;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;

public class StartDateListener implements ServletContextListener {

	public void contextInitialized(ServletContextEvent sce) {
		sce.getServletContext().setAttribute("startDate", new Date());
	}

	public void contextDestroyed(ServletContextEvent sce) {
		sce.getServletContext().removeAttribute("startDate");
	}


}
