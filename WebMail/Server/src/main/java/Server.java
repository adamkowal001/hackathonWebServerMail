import java.net.ServerSocket;
import java.net.Socket;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

public class Server {
	
	public static void main(String[] args){
		ExecutorService es = Executors.newFixedThreadPool(5);
		
		try{
			ServerSocket ss = new ServerSocket(3000);
			boolean finish = false;
			while(!finish) {
				Socket s = ss.accept();
				// Future<Integer> val = es.submit(new RequestHandler(s));

				//System.out.println(val.get());
			}
			ss.close();
		} catch(Exception e){
			e.printStackTrace();
		}
	}
}
