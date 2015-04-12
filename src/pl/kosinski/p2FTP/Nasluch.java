package pl.kosinski.p2FTP;

import java.io.IOException;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;

public class Nasluch extends Thread{
	private int port;
	private ServerSocket ssocket;
	private Socket socket;
	private PrintWriter out;

	
	public Nasluch(int port){
		this.port=port;
	}
	
	public void run(){
		while(true){
			System.out.println("Nowe gniazdo");
		try {
			ssocket = new ServerSocket(port);
			System.out.println("Server socket jest");
			socket = ssocket.accept();
			System.out.println("client socket jest");
			out = new PrintWriter(socket.getOutputStream(),true);
			System.out.println("wyjscie poleceñ jest");
		} catch (IOException e) {
			System.err.println("B³¹d portu/po³¹czenia");
			e.printStackTrace();
		}
		
		out.println("port");
		new Polaczenie(2048).start();
		out.println(2048);
		out.close();
		try {
			socket.close();
			ssocket.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		System.out.println("Przekierowuje na port 2048 \n");
		
		}
		}
		

	
	public static void startNew(int port){
		new Nasluch(port).start();

	}
}
