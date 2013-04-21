package com.bnotions.airspacecontrol.api;

import java.net.HttpURLConnection;

public class RequestResponse<T> {

    public static final int ERROR_NO_CONNECTION = 5;
    public static final int ERROR_TIMEOUT = 6;

    public static final String MSG_DEVELOPER_ERROR = "Oops, we seem to have a bug in our app, please let us know!";
    public static final String MSG_TIMEOUT_ERROR = "The network is slow right now, please try again later!";
    public static final String MSG_API_ERROR = "The server is currently experiencing issues, please let us know or try again later!";
    public static final String MSG_UNAUTHORIZED = "We encountered an issue with your login credentials, please try signing in again!";
    public static final String MSG_GENERAL_ERROR = "We could not complete your request, please let us know or try again later";
    public static final String MSG_NO_CONNECTION = "No Network Connection!";
    public static final String MSG_TIMEOUT = "Request took too long, please retry.";

	public int id;
	public boolean successful;
	public int http_response_code = 0;
    public String http_cookie;
	public String http_response_msg;
	public long last_modified = 0;
	public T data;
	
	public String getUserErrorMessage() {
		
		switch (http_response_code) {
		case HttpURLConnection.HTTP_BAD_REQUEST:
			return MSG_DEVELOPER_ERROR;	
		case HttpURLConnection.HTTP_CLIENT_TIMEOUT:
			return MSG_TIMEOUT_ERROR;
		case HttpURLConnection.HTTP_FORBIDDEN:
        case HttpURLConnection.HTTP_NO_CONTENT:
			return MSG_API_ERROR;
		case HttpURLConnection.HTTP_GATEWAY_TIMEOUT:
			return MSG_TIMEOUT_ERROR;
		case HttpURLConnection.HTTP_INTERNAL_ERROR:
			return MSG_API_ERROR;
		case HttpURLConnection.HTTP_UNAUTHORIZED:
			return MSG_UNAUTHORIZED;
        case ERROR_NO_CONNECTION:
            return MSG_NO_CONNECTION;
        case ERROR_TIMEOUT:
            return MSG_TIMEOUT;
		default:
			return MSG_GENERAL_ERROR;
		}
		
	}
	
}
