package http;

import java.io.IOException;
import java.io.OutputStream;
import java.io.PrintStream;
import org.json.JSONObject;

public class Request {
        private OutputStream out;
        private String login;
        private String to;
        private String text;
        private String password;
        // private JSONObject data;
        

        public Request(OutputStream out, String login, String password) {
            this.out = out;
            login(login, password);
        }

        private void login(String login, String pass){
        	this.login = login;
        	this.password = pass;
        	send(toJson(this.login, this.password));
        }
        
        public void write(String text, String to){
        	send(toJson(text, to));
        }
        
        public void read(){
        	send(toJson());
        }
        
        private JSONObject toJson(){
        	// for read
        	JSONObject json = new JSONObject();
        	json.put("method", "read");
        	json.put("author", this.login);
        	JSONObject data = new JSONObject();
        	json.put("data", data);
        	return json;
        }
        
        private JSONObject toJson(String password){
        	// for login
        	this.password = password;
        	JSONObject json = new JSONObject();
        	json.put("method", "login");
        	json.put("author", this.login);
        	JSONObject data = new JSONObject();
        	data.put("password", this.password);
        	json.put("data", data);
        	return json;
        }
        
        private JSONObject toJson(String text, String to){
        	// for write
        	this.to = to;
        	this.text = text;
        	JSONObject json = new JSONObject();
        	json.put("method", "write");
        	json.put("author", this.login);
        	JSONObject data = new JSONObject();
        	data.put("to", to);
        	data.put("text", text);
        	json.put("data", data);
        	
        	return json;
        }
        
		public void send(JSONObject json) {
            PrintStream ps = new PrintStream(out);
            ps.print(json.toString());
        }
}
