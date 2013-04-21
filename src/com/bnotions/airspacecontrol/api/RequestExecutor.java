package com.bnotions.airspacecontrol.api;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;
import android.os.Build;
import android.util.Log;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.*;
import java.net.*;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.zip.GZIPInputStream;

public class RequestExecutor<T> {

    public static final String HEADER_COOKIE = "Cookie";
    private static final String HEADER_SET_COOKIE = "Set-Cookie";

    private RequestTask task;

	public void execute(Context context, RequestInfo request, Class<?> clazz, RequestCallback<T> callback) {

        if (Retrofit.DEBUG) logRequest(request);
        task = new RequestTask(context, request, callback, clazz);
        task.executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR);

	}

    public void cancel() {

        if (task != null) {
            task.cancel(true);
        }

    }

    private void logRequest(RequestInfo request) {

        Log.d("RequestExecutor", "Logging HTTP Request:");
        Log.d("RequestExecutor", "ID = " + request.id);
        Log.d("RequestExecutor", "SERVER = " + request.server);
        Log.d("RequestExecutor", "ENDPOINT = " + request.endpoint);
        Log.d("RequestExecutor", "METHOD = " + request.method);

    }

	private class RequestTask extends AsyncTask<Void, Integer, Void> {

		private Context context;
		private RequestInfo request;
		private RequestCallback<T> callback;
		private RequestResponse<T> response;
        private Class<?> clazz;

		public RequestTask(Context context, RequestInfo request, RequestCallback<T> callback, Class<?> clazz) {

			this.context = context;
			this.request = request;
			this.callback = callback;
            this.clazz = clazz;
			response = new RequestResponse<T>();

		}

		@Override
		protected Void doInBackground(Void... params) {

			HttpURLConnection connection = null;
            InputStream stream = null;
            response.id = request.id;
			try {
                if (context == null) return null;
				checkNetworkConnection(context);
				verifyRequest(request);
				disableConnectionReuseIfNecessary();
				setCookieManager();
	
				String str_url = request.server + request.endpoint;
				if (request.get_params != null) str_url = addGetParams(request, str_url);
				URL url = new URL(str_url);
				connection = (HttpURLConnection) url.openConnection();
	
				connection.setReadTimeout(request.read_timeout);
				connection.setConnectTimeout(request.connect_timeout);
                if (request.headers != null) setHeaders(request, connection);
				connection.addRequestProperty("Cache-Control", "max-stale=" + 10);
				connection.setUseCaches(true);
				connection.setDoInput(true);
				connection.setRequestMethod(request.method);
	
				if (request.post_params != null || request.body != null){
					connection.setDoOutput(true);
					OutputStream out = connection.getOutputStream();
					if (request.body != null) {
						out.write(request.body.getBytes());
					} else if (request.post_params != null) {
						out.write(convertPostParamsToBody(request).getBytes());
					}
					out.close();
				}

				response.http_response_code = connection.getResponseCode();
				response.http_response_msg = connection.getResponseMessage();
				response.last_modified = connection.getLastModified();
                response.http_cookie = connection.getHeaderField(HEADER_SET_COOKIE);

                if (false) logAllResponseHeaders(connection);

                if (Retrofit.DEBUG) logResponse(response);

                //check for http error codes
				if (response.http_response_code >= 400) {
					response.successful = false;
                } else {
                    response.successful = true;
                }

                //check for redirects
				if (!url.getHost().equals(connection.getURL().getHost())) {
					response.successful = false;
                    response.http_response_msg = "Was redirected to foreign host.";
					return null;
				}

                //get the input stream
                try {
                    stream = connection.getInputStream();
                } catch (FileNotFoundException e) {
                    //if we get a FNFE we check the error stream
                    response.successful = false;
                    stream = connection.getErrorStream();
                }
                if (stream != null) {
                    if ("gzip".equals(connection.getContentEncoding())) {
                        stream = new GZIPInputStream(stream);
                    }

                    try {
                        response.data = decodeStream(stream, clazz);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
				return null;

            } catch (SocketTimeoutException e) {
                e.printStackTrace();
                response.successful = false;
                response.http_response_code = RequestResponse.ERROR_TIMEOUT;
			} catch (IllegalArgumentException e) {
				e.printStackTrace();
                response.successful = false;
                response.http_response_code = HttpURLConnection.HTTP_BAD_REQUEST;
            } catch (IllegalStateException e) {
                e.printStackTrace();
                response.successful = false;
                response.http_response_code = RequestResponse.ERROR_NO_CONNECTION;
			} catch (MalformedURLException e) {
                response.successful = false;
				e.printStackTrace();
			} catch (IOException e) {
                response.successful = false;
				e.printStackTrace();
			} finally {
                if (stream != null) {
                    try { stream.close(); } catch (IOException e) {}
                }
                if (connection != null) {
					connection.disconnect();
				}
			}

            response.successful = false;

			return null;
		}

        private void logAllResponseHeaders(HttpURLConnection connection) {

            Map<String, List<String>> headers = connection.getHeaderFields();
            Iterator<String> itr = headers.keySet().iterator();
            while (itr.hasNext()) {
                String key = itr.next();
                String value = headers.get(key).get(0);
                Log.d("RequestExecutor", "HEADER = " + key + " : " + value);
            }

        }

        private void logResponse(RequestResponse response) {

            Log.d("RequestExecutor", "Logging HTTP Response:");
            Log.d("RequestExecutor", "ID = " + response.id);
            Log.d("RequestExecutor", "CODE = " + response.http_response_code);
            Log.d("RequestExecutor", "MSG = " + response.http_response_msg);

        }

		@Override
		protected void onProgressUpdate(Integer... progress) {
			//TODO: Figure out a way to notify caller on download progress.
		}

		@Override
		protected void onPostExecute(Void result) {

            if (callback != null) {
                if (response != null && response.successful) {
                    callback.onComplete(response);
                } else {
                    callback.onFailure(response);
                }
            }
			
		}



	}

	private void verifyRequest(RequestInfo request) throws IllegalStateException {

		if (request.server == null) throw new IllegalArgumentException("server can not be null");
		if (!request.server.endsWith("/")) throw new IllegalArgumentException("server must end with '/'");
		if (request.endpoint == null) throw new IllegalArgumentException("endpoint can not be null");
		if (request.method == null) throw new IllegalArgumentException("method can not be null");

	}

	private void disableConnectionReuseIfNecessary() {

		if (Build.VERSION.SDK_INT < Build.VERSION_CODES.FROYO) {
			System.setProperty("http.keepAlive", "false");
		}

	}

	private void setCookieManager() {

		CookieManager cookieManager = new CookieManager();
		CookieHandler.setDefault(cookieManager);

	}

	public static boolean checkNetworkConnection(Context context) throws IllegalStateException {

		ConnectivityManager connection_manager = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
		NetworkInfo network_info = connection_manager.getActiveNetworkInfo();
		if (network_info == null || !network_info.isConnected()) {
            throw new IllegalStateException("No network connection");
        } else {
            return true;
        }

	}

	private void setHeaders(RequestInfo request, HttpURLConnection connection) {

		int count = request.headers.length;
		for (int i = 0; i < count; i++) {
			String name = request.headers[i][0];
			String value = request.headers[i][1];
            if (Retrofit.DEBUG) Log.d("RequestExecutor", "HEADER = " + name + ":" + value);
			connection.addRequestProperty(name, value);
		}

	}	

	private String addGetParams(RequestInfo request, String str_url) throws UnsupportedEncodingException {

		int count = request.get_params.length;
		str_url += "?";
		for (int i = 0; i < count; i++) {
			if (i > 0) str_url += "&";
			String param = request.get_params[i];
			str_url += param;
		}

        if (Retrofit.DEBUG) Log.d("RequestExecutor", "GET PARAMS = " + str_url);

		return str_url;
	}

	private String convertPostParamsToBody(RequestInfo request) throws UnsupportedEncodingException {

		int count = request.post_params.length;
		StringBuilder body_builder = new StringBuilder("");
		for (int i = 0; i < count; i++) {
			if (i > 0) body_builder.append("&");
			String param = request.post_params[i];
			body_builder.append(param);
		}

        if (Retrofit.DEBUG) Log.d("RequestExecutor", "POST BODY = " + body_builder.toString());

		return body_builder.toString();
	}

    private T decodeStream(InputStream stream, Class<?> clazz) throws IOException, JSONException, InstantiationException, IllegalAccessException {

        if (clazz == null) {
            return null;
        } else if (String.class.isAssignableFrom(clazz)) {
            return (T) readStreamAsString(stream);
        } else if (JSONObject.class.isAssignableFrom(clazz)) {
            return (T) new JSONObject(readStreamAsString(stream));
        } else if (JSONArray.class.isAssignableFrom(clazz)) {
            return (T) new JSONArray(readStreamAsString(stream));
        }

        return null;
    }

	public static String readStreamAsString(InputStream stream) throws IOException, UnsupportedEncodingException {

		BufferedReader reader = new BufferedReader(new InputStreamReader(stream));
		StringBuilder builder = new StringBuilder();
		String line;
		while ((line = reader.readLine()) != null) {
			builder.append(line);
		} 

		return new String(builder.toString());
	}

    public static byte[] readStreamAsByteArray(InputStream stream) throws IOException, UnsupportedEncodingException {

		ByteArrayOutputStream buffer = new ByteArrayOutputStream();

		int read;
		byte[] data = new byte[1024];
		while ((read = stream.read(data, 0, data.length)) != -1) {
			buffer.write(data, 0, read);
		}
		buffer.flush();
		stream.close();

		return buffer.toByteArray();
	}

}
