package http;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.Socket;
import java.net.UnknownHostException;

public class Client {
	
	private Socket socket;
	private InputStream is;
	private OutputStream os;
	
	public Client(){
		try {
			this.socket = new Socket("localhost", 3000);
			this.is = socket.getInputStream();
			this.os = socket.getOutputStream();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	
	public InputStream getIs() {
		return is;
	}


	public OutputStream getOs() {
		return os;
	}

	public static void main(String[] args){
		Client client = new Client();
		
		Request req = new Request(client.getOs(), "AK@gmail.com", "zaq");
		System.out.println("wszedlem");
		
		client = new Client();
		req.read();
		System.out.println("wszedlem2");
		// req.write("some text", "user2");
		
		Response res = new Response(client.getIs());
		System.out.println("The status was successful: " + res.getStatus());
	}
}
