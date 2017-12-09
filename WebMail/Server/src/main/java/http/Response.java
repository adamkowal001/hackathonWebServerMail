package http;

import java.io.OutputStream;
import java.io.PrintStream;
import java.util.ArrayList;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONObject;

import email.Email;

public class Response{
	
	private boolean status;
	private List<Email> list;
	private OutputStream os;
	private PrintStream ps;
	
	public Response(OutputStream os, boolean status) {
		this.os = os;
		ps = new PrintStream(os);
		this.status = status;
		list = new ArrayList<Email>();
	}
	
	public Response(OutputStream os, boolean status, List<Email> list) {
		this.os = os;
		ps = new PrintStream(os);
		this.status = status;	
		this.list = list;
	}
	
	public void send() {
		JSONObject json = new JSONObject();
		json.put("status", this.status);
		JSONArray jarray = new JSONArray();
		for (Email email : this.list) {
			JSONObject json2 = new JSONObject(email.toString());
			jarray.put(json2);
			
		}
		json.put("emails", jarray);
		System.out.println(json.toString());
		ps.println(json.toString());
		ps.flush();
	}
	
}