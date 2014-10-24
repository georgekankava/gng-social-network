package com.gng.network.servlet.atmosphere;

import java.io.IOException;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.atmosphere.config.service.MeteorService;
import org.atmosphere.cpr.AtmosphereResource;
import org.atmosphere.cpr.AtmosphereResourceEventListenerAdapter;
import org.atmosphere.cpr.Broadcaster;
import org.atmosphere.cpr.BroadcasterFactory;
import org.atmosphere.cpr.DefaultBroadcaster;
import org.atmosphere.cpr.Meteor;
import org.atmosphere.interceptor.AtmosphereResourceLifecycleInterceptor;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.context.support.WebApplicationContextUtils;

import com.gng.network.service.MessageService;


@SuppressWarnings("serial")
@MeteorService(path = "/*", interceptors = {AtmosphereResourceLifecycleInterceptor.class})
public class CommentBroadcaster extends HttpServlet {
	 
	/**
     * Create a {@link Meteor} and use it to suspend the response.
     *
     * @param req An {@link HttpServletRequest}
     * @param res An {@link HttpServletResponse}
     */
    @SuppressWarnings("unused")
	@Override
    public void doGet(HttpServletRequest req, HttpServletResponse res) throws IOException {
        // Set the logger level to TRACE to see what's happening.
    	String username = req.getParameter("comment");
        Meteor meteor = Meteor.build(req).addListener(new AtmosphereResourceEventListenerAdapter());
        AtmosphereResource resource = meteor.getAtmosphereResource();
        
    }

    /**
     * Re-use the {@link Meteor} created on the first GET for broadcasting message.
     *
     * @param req An {@link HttpServletRequest}
     * @param res An {@link HttpServletResponse}
     */
    @SuppressWarnings("unused")
	@Override
    public void doPost(HttpServletRequest req, HttpServletResponse res) throws IOException {
    	WebApplicationContext wac = WebApplicationContextUtils.getRequiredWebApplicationContext(getServletContext());
    	MessageService messageService = wac.getBean(MessageService.class);
        String body = req.getReader().readLine().trim();
        Broadcaster broadcaster = BroadcasterFactory.getDefault().lookup(DefaultBroadcaster.class, "/*");
    }
}
