package http;

import java.net.Socket;
import java.util.ArrayList;
import java.util.concurrent.Callable;

import DB.DBsupport;
import DB.User;
import email.Email;

public class RequestHandler implements Callable<Boolean>{
	private Socket s;
	private DBsupport db;
	
	public RequestHandler(Socket s) {
		this.s = s;
		db = new DBsupport();
	}
	
	public Boolean call() throws Exception{
		Request req = new Request(s.getInputStream());
		boolean status = false;
		//Response res = new Response(s.getOutputStream());
		
		if (req.getMethod().equals("read")) {
			ArrayList<Email> emailList = db.selectMails(req.getAuthor());
			status = true;
			new Response(s.getOutputStream(), status, emailList);
			
		}else if(req.getMethod().equals("write")) {
			db.insertMails(req.getEmail().getFrom(), req.getEmail().getTo(), req.getEmail().getContent());	
			status = true;
			new Response(s.getOutputStream(), status);
			
		}else if(req.getMethod().equals("login")) {
			String login = req.getUser().getLogin();
			String password = req.getUser().getPassword();
			User user = db.selectUser(login);
			if(login.equals(user.getLogin()) && password.equals(user.getPassword()) ) {
				status = true;	
				new Response(s.getOutputStream(), status);
			}
			else {
				status = false;
				new Response(s.getOutputStream(), status);
			}	
		}else {
			status = false;
			new Response(s.getOutputStream(), status);
		}
		db.closeConnection();
		return status;
	}

}
