import java.net.ServerSocket;
import java.net.Socket;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

import http.RequestHandler;

public class Server {
	
	public static void main(String[] args){
		ExecutorService es = Executors.newFixedThreadPool(5);
		
		try{
			ServerSocket ss = new ServerSocket(3000);
			boolean finish = false;
			while(!finish) {
				System.out.println("Server start");
				Socket s = ss.accept();
				System.out.println("someone connect");
				Future<Boolean> val = es.submit(new RequestHandler(s));

				System.out.println(val.get());
			}
			ss.close();
		} catch(Exception e){
			e.printStackTrace();
		}
	}
}
