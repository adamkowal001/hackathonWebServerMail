package http;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import email.Email;


public class Response {
	
	private BufferedReader br;
	private boolean status;
	private List<Email> emails;
	
	
	
	public boolean getStatus() {
		return status;
	}

	public List<Email> getEmails() {
		return emails;
	}

	public Response(InputStream is){
		System.out.println("frf");
		this.br = new BufferedReader(new InputStreamReader(is));
		JSONObject json = toJson();
		this.status = json.getBoolean("status");
		System.out.println("frf");
		JSONArray emailsAsJsons = json.getJSONArray("emails");
//		System.out.println(emailsAsJsons.get(0).getClass());
		System.out.println("frf" + emailsAsJsons.length());
		for (int i = 0; i < emailsAsJsons.length(); i++){
			emails.add(new Email(emailsAsJsons.getJSONObject(i)));
		}
	}
	
	private JSONObject toJson(){
		try {
			System.out.println("przezd readline");
			System.out.println(br.readLine());
			return new JSONObject(br.readLine());
		} catch (JSONException e) {
			e.printStackTrace();
			return null;
		} catch (IOException e) {
			e.printStackTrace();
			return null;
		}
	}
	
}
