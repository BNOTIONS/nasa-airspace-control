package com.bnotions.airspacecontrol.api;

public class RequestInfo {
	
	private static final int DEFAULT_READ_TIMEOUT = 7000;
	private static final int DEFAULT_CONNECT_TIMEOUT = 15000;
	
	public int id;
	public String server;
	public String endpoint; 
	public String method;
	public String[][] headers;
	public String[] get_params;
	public String[] post_params;
	public String body;
	public int read_timeout = DEFAULT_READ_TIMEOUT;
	public int connect_timeout = DEFAULT_CONNECT_TIMEOUT;

}
