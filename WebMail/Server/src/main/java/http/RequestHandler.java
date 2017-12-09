package http;

import java.net.Socket;
import java.util.ArrayList;
import java.util.List;
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
		db.delete("users");
		db.delete("emails");
		db.createTables();
		db.insertUsers("AK@gmail.com", "zaq");
		db.insertUsers("KB@gmail.com", "wsx");
		db.insertUsers("MK@gmail.com", "cde");
		db.insertMails("fr", "AK@gmail.com" , "elo elo ssAK");
		db.insertMails("fr", "KB@gmail.com" , "elo elo KB");
		db.insertMails("fr", "MK@gmail.com" , "elo elo MK");
//		
//		   List<Email> listaEmail1 = db.selectMails("AK@gmail.com");
//		   List<Email> listaEmail2 = db.selectMails("KB@gmail.com");
//		   
//		   System.out.println("size " + listaEmail1.size() + " " + listaEmail1.get(0).getContent());
//		   System.out.println("size " + listaEmail2.size() + " " + listaEmail2.get(0).getContent());
//		   
	}
	
	public Boolean call() throws Exception{
		Request req = new Request(s.getInputStream());
		boolean status = false;
		//Response res = new Response(s.getOutputStream());
		System.out.println("w call");
		System.out.println("Metoda: " + req.getMethod());
		if (req.getMethod().equals("read")) {
			System.out.println("w readzie");
			ArrayList<Email> emailList = db.selectMails(req.getAuthor());
			status = true;
			new Response(s.getOutputStream(), status, emailList);
			
		}else if(req.getMethod().equals("write")) {
			System.out.println("w write");
			db.insertMails(req.getEmail().getFrom(), req.getEmail().getTo(), req.getEmail().getContent());	
			status = true;
			new Response(s.getOutputStream(), status);
			
		}else if(req.getMethod().equals("login")) {
			System.out.println("w login");
			String login = req.getUser().getLogin();
			String password = req.getUser().getPassword();
			User user = db.selectUser(login);
			if(login.equals(user.getLogin()) && password.equals(user.getPassword()) ) {
				status = true;	
				System.out.println("login ok");
				Response res = new Response(s.getOutputStream(), status);
				res.send();
			}
			else {
				System.out.println("login not ok");
				status = false;
				new Response(s.getOutputStream(), status);
			}	
		}else {
			System.out.println("w pozostalych");
			status = false;
			new Response(s.getOutputStream(), status);
		}
		db.closeConnection();
		return status;
	}

}
