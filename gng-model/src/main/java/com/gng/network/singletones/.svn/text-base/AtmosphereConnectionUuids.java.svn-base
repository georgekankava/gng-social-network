package com.gng.network.singletones;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import org.atmosphere.cpr.AtmosphereResource;

public class AtmosphereConnectionUuids {
	
	private static AtmosphereConnectionUuids INSTANCE = null;
	
	private static Map<Integer, AtmosphereResource> resources = new ConcurrentHashMap<Integer, AtmosphereResource>();
	
	private AtmosphereConnectionUuids() {
		
	}
	
	public static AtmosphereConnectionUuids getInstance() {
		if(INSTANCE == null) {
			INSTANCE  = new AtmosphereConnectionUuids();
		} 
		return INSTANCE;
	}
	
	public Map<Integer, AtmosphereResource> getResourceUuids() {
		return resources;
	}
	
	public void addResource(Integer userId, AtmosphereResource resource) {
		resources.put(userId, resource);
	}
	
	public AtmosphereResource getResource(Integer userId) {
		return resources.get(userId);
	}
	
}
