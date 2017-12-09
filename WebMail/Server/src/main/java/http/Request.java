package http;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.HashMap;
import java.util.Map;

public class Request {
private InputStream is;
	
	private BufferedReader br; 
	
	private String httpMethod;
	
	private String path;
	
	private String servicePath;
	
	protected String actionPath;
	
	private Map<String, String> headers = new HashMap<String, String>();
	
	protected byte[] body;

	public Request(InputStream is) {
		this.is = is;
		br = new BufferedReader(new InputStreamReader(is));
		
		String firstLine;		
		
		try {
			firstLine = br.readLine();
			httpMethod = firstLine.substring(0, firstLine.indexOf(' '));
			path = firstLine.substring(httpMethod.length()+1, firstLine.indexOf(' ', httpMethod.length()+1));
		} catch (IOException e) {
			e.printStackTrace();
		}

		servicePath = path.substring(0,path.indexOf('/', 1));
		actionPath = path.substring(servicePath.length());
		
		String line;
		try {
			while((line = br.readLine()).length() > 0) {
				String key = line.substring(0, line.indexOf(':'));
				String value = line.substring(line.indexOf(':') + 1);
				headers.put(key.toLowerCase(), value.trim());
			}
		} catch (IOException e) {
			e.printStackTrace();
		}

		String strContentLength = headers.get("content-length");
		
		int contentLength;
		if (strContentLength == null) {
			contentLength = 0;
		} else {
			contentLength = Integer.parseInt(strContentLength);
		}

		//body = br.read(contentLength);
	}

	public byte[] getBody() {
		return body;
	}

	public String getBodyAsString() {
		return new String(body);
	}

	public String getHttpMethod() {
		return httpMethod;
	}

	public String getPath() {
		return path;
	}

	public String getServicePath() {
		return servicePath;
	}

	public String getActionPath() {
		return actionPath;
	}

	public Map<String, String> getHeaders() {
		return headers;
	}

	@Override
	public String toString() {
		return "Request [httpMethod=" + httpMethod + ", path=" + path + "]";
	}

}
