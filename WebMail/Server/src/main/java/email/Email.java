package email;

import org.json.JSONObject;

public class Email {
	
	private String from;
	private String to;
	private String content;
	private int id = -1;
	
	public Email(int id, String from, String to, String content){
		this.from = from;
		this.to = to;
		this.content = content;
		this.id = id;
	}
	
	public Email(JSONObject obj){
		this.from = obj.getString("from");
		this.to = obj.getString("to");
		this.content = obj.getString("content");
	}
	
	public String getFrom() {
		return from;
	}
	public void setFrom(String from) {
		this.from = from;
	}
	public String getTo() {
		return to;
	}
	public void setTo(String to) {
		this.to = to;
	}
	public String getContent() {
		return content;
	}
	public void setContent(String content) {
		this.content = content;
	}
	
	
}
