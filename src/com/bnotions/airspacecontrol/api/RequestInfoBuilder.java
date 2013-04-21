package com.bnotions.airspacecontrol.api;

import android.util.Pair;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;


public class RequestInfoBuilder {
	
	private static final int RANDOM_ID_MAX = 100000;
	
	public static final String GET = "GET";
	public static final String POST = "POST";
	public static final String PUT = "PUT";
	public static final String DELETE = "DELETE";
	
	private static final String EQUALS = "=";
	
	private RequestInfo request;
	
	public RequestInfoBuilder() {
		
		request = new RequestInfo();
		request.id = getRandomId();
		
	}
	
	private int getRandomId() {
		
		return (int) (Math.random() * RANDOM_ID_MAX);
	}
	
	public RequestInfoBuilder setServer(String server) {
		
		request.server = server;
		
		return this;
	}
	
	public RequestInfoBuilder setEndpoint(String endpoint) {
		
		request.endpoint = endpoint;
		
		return this;
	}
	
	public RequestInfoBuilder setMethod(String method) {
		
		request.method = method;
		
		return this;
	}
	
	public RequestInfoBuilder setGetParams(Pair<String, String>... params) {
		
		int count = params.length;
		request.get_params = new String[count];
		for (int i = 0; i < count; i++)	{
			Pair<String, String> pair = params[i];
            try {
                request.get_params[i] = URLEncoder.encode(pair.first, "UTF-8") + EQUALS + URLEncoder.encode(pair.second, "UTF-8");
            } catch (UnsupportedEncodingException e) {
                e.printStackTrace();
            }
        }
		
		return this;
	}
	
	public RequestInfoBuilder setPostParams(Pair<String, String>... params) {
		
		int count = params.length;
		request.post_params = new String[count];
		for (int i = 0; i < count; i++)	{
			Pair<String, String> pair = params[i];
            try {
                request.post_params[i] = URLEncoder.encode(pair.first, "UTF-8") + EQUALS + URLEncoder.encode(pair.second, "UTF-8");
            } catch (UnsupportedEncodingException e) {
                e.printStackTrace();
            }
        }
		
		return this;
	}
	
	public RequestInfoBuilder setHeaders(Pair<String, String>... params) {
		
		int count = params.length;
		request.headers = new String[count][2];
		for (int i = 0; i < count; i++)	{
			Pair<String, String> pair = params[i];
            request.headers[i][0] = pair.first;
            request.headers[i][1] = pair.second;
		}
		
		return this;
	}
	
	public RequestInfoBuilder setBody(String body) {
		
		request.body = body;
		
		return this;
	}
	

	public RequestInfoBuilder setReadTimeout(int read_timeout) {
		
		request.read_timeout = read_timeout;
		
		return this;
	}
	

	public RequestInfoBuilder setConnectTimeout(int connect_timeout) {
		
		request.connect_timeout = connect_timeout;
		
		return this;
	}

    public RequestInfo build() {

        return request;
    }
	
}
