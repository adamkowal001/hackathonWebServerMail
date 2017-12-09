package http;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

import org.json.JSONObject;

import DB.DBsupport;
import DB.User;
import email.Email;

public class Request {
	
	private InputStream is;
	private BufferedReader br; 
	private String method;
	private String author;
	
	public String getMethod() {
		return method;
	}

	public String getAuthor() {
		return author;
	}

	public Email getEmail() {
		return email;
	}

	public User getUser() {
		return user;
	}

	private JSONObject data; 
	private Email email;
	private User user;
	private JSONObject json;
	
	public Request(InputStream is) {
		this.is = is;
		br = new BufferedReader(new InputStreamReader(is));
		
		try {
			System.out.println("przed readline");
			
			this.json = new JSONObject(br.readLine());
			System.out.println(this.json.toString());
			this.method = json.getString("method");
			if (this.method.equals("read")) {
				author = json.getString("author");								
			}else if(this.method.equals("write")) {
				JsonToEmail();
				
			}else if(this.method.equals("login")) {
				JsonToUser();
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public void JsonToEmail(){
		this.data = json.getJSONObject("data");	
		email = new Email(json.getString("author"), json.getString("to"), json.getString("text"));
	}
	
	public void JsonToUser(){
		this.data = json.getJSONObject("data");	
		user = new User(json.getString("author"), data.getString("password"));		
	}
	
}