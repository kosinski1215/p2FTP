package pl.kosinski.p2FTP;

import java.io.IOException;
import java.net.ServerSocket;

public class Nasluch extends Thread{
	private int port;
	private ServerSocket ssocket;
	
	public Nasluch(int port){
		this.port=port;
	}
	
	public void run(){
		while(true){
			System.out.println("Nowe gniazdo");
		try {
			ssocket = new ServerSocket(port);
			new Polaczenie(ssocket.accept()).start();
			System.out.println("Nowy socket jest");

			ssocket.close();
		} catch (IOException e) {
			System.err.println("B³¹d portu");
			e.printStackTrace();
		}
		
		
		
		
		
		}
		}
		

	
	public static void startNew(int port){
		new Nasluch(port).start();

	}
}
