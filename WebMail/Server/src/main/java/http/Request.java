package http;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.HashMap;
import java.util.Map;

import org.json.JSONObject;

public class Request {
private InputStream is;
	
	private BufferedReader br; 
	
	private String method;
	
	private String path;
	
	private String servicePath;
	
	protected String actionPath;
	
	private Map<String, String> headers = new HashMap<String, String>();
	
	protected byte[] body;
	protected JSONObject bodyAsObject;	

	public Request(InputStream is) {
		this.is = is;
		br = new BufferedReader(new InputStreamReader(is));
		
		String firstLine;		
		
		try {
			firstLine = br.readLine();
			method = firstLine.substring(0, firstLine.indexOf(' '));
			path = firstLine.substring(method.length()+1, firstLine.indexOf(' ', method.length()+1));
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

		body = readBody(contentLength);
	}

	public byte[] getBody() {
		return body;
	}

	public String getBodyAsString() {
		return new String(body);
	}

	public String getHttpMethod() {
		return method;
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
		return "Request [httpMethod=" + method + ", path=" + path + "]";
	}
	
	private byte[] readBody(int size){
		byte[] buffer = new byte[1024*1024*10]; // 10Mb
		int readContent = 0;
		int pointer = 0;
		byte[] res = new byte[size];
		
		while(readContent + size > pointer) {
			try {
				while(is.available()==0) {
					Thread.sleep(500);
				}
			} catch (Exception e) {
				e.printStackTrace();
			try {
				while(is.available()>0) {
					buffer[pointer++] = (byte)is.read();
				}
			} catch (IOException e1) {
				e1.printStackTrace();
			}
		}
		System.arraycopy(buffer, readContent, res, 0, size);
		readContent += size;
		}
		return res;
	}
}
