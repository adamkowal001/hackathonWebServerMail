package http;

import java.io.IOException;
import java.io.OutputStream;
import java.io.PrintStream;
import java.util.HashMap;
import java.util.Map;


public class Response {
	public final static String NL = "\r\n";

	private OutputStream os;

	private PrintStream ps;

	private int responseCode = 200;
	
	private String responseMsg = "OK";
	
	private Map<String, String> headers = new HashMap<String, String>();
	
	private String contentType = "text/html;charset=UTF-8";
	
	public Response(OutputStream os) {
		this.os = os;
		ps = new PrintStream(os);
	}
	
	private void printHeaders() {
		for(String key : headers.keySet()) {
			ps.print(key + ": " + headers.get(key) + NL);
		}
	}
	
	public void setResponseCode(int code, String msg) {
		this.responseCode = code;
		this.responseMsg = msg;
	}
	
	public int getResponseCode() {
		return responseCode;
	}
	
	
	public void setContentType(String contentType) {
		this.contentType = contentType;
	}

	public void send(String body) {
		ps.print("HTTP/1.1 " + responseCode + ' ' + responseMsg + NL);
		
		headers.put("Content-Type", contentType);
		headers.put("Content-Length", "" + body.getBytes().length);

		printHeaders();
		
		ps.print(NL);
		
		ps.println(body);
		ps.flush();
	}

	public void send(byte[] data, String contentType) {
		ps.print("HTTP/1.1 " + responseCode + ' ' + responseMsg + NL);
		
		headers.put("Content-Type", contentType);
		headers.put("Content-Length", "" + data.length);

		printHeaders();
		
		ps.print(NL);
		
		try {
			ps.write(data);
		} catch (IOException e) {
			e.printStackTrace();
		}

		ps.flush();
	}

}
