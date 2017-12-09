package http;

import java.net.Socket;
import java.util.concurrent.Callable;

public class RequestHandler implements Callable<Boolean>{
	private Socket s;
	
	public RequestHandler(Socket s) {
		this.s = s;
	}
	
	public Boolean call() throws Exception{
		Request req = new Request(s.getInputStream());
		Response res = new Response(s.getOutputStream());
		
		return res.getResponseCode();
	}

}
