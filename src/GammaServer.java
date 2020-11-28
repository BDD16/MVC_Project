import java.io.BufferedReader;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.PrintWriter;
import java.net.*;
import java.util.HashSet;
public class GammaServer {
	DataInputStream In1;
	DataOutputStream Out1;
	Socket connect;
	
	private static final int PORT = 8170;
	
	private static HashSet<String> screennames = new HashSet<String>();
	
	private static HashSet<PrintWriter> writers = new HashSet<PrintWriter>();
	
	
	
	public GammaServer() throws IOException{
		this.connect = new Socket();
		this.In1 = new DataInputStream(connect.getInputStream());
		this.Out1 = new DataOutputStream(connect.getOutputStream());
	}
	
	private static class Handler extends Thread{
		private String name;
		private Socket socket;
		private BufferedReader in;
		private PrintWriter out;
		
		
		public void run(){
			
			
		}
		
	
	}
	
	
}
