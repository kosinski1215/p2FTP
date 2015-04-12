package pl.kosinski.p2FTP;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.concurrent.TimeoutException;

public class Polaczenie extends Thread{
	private static final int timeout = 10;
	private ServerSocket ssocket;
	private Socket socket;
	private PrintWriter out;
	private BufferedReader in;
	private String polecenie;
	private int port;
	
	public Polaczenie(int port){
		this.port=port;
	}
	public void run(){
		
	try {
		ssocket = new ServerSocket(port);
		//System.out.println("Server socket jest");
		socket = ssocket.accept();
		info("po³¹czono");
		//System.out.println("client socket jest");
		out = new PrintWriter(socket.getOutputStream(),true);
		//System.out.println("wyjscie poleceñ jest");
		in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
		//System.out.println("Wejscie polecen jest");
	} catch (IOException e) {
		System.err.println("B³¹d portu/po³¹czenia");
		e.printStackTrace();
	}
	
	
	
	
	out.println("identify");

	

	while(true){
		polecenie=czytajNext();
		
		switch(polecenie){
			case "user":
				if(User.loguj(czytajNext(), czytajNext())){
					info("Zalogowano " + User.aktualny());
					out.println("Zalogowano " + User.aktualny());
				}
				break;
			case "exit":
				return;
		}
		
		}
	
	}
	
	private String czytajNext(){
		try{
			int licznik = 0;
		while(!in.ready()){
			Thread.sleep(10);
			licznik++;
			if(licznik>timeout*100 && User.aktualny()!=null)throw new TimeoutException();
				
		}
		return in.readLine();
		}catch(Exception e){
			System.err.println("B³¹d komunikacji");
			return "";
		}
		
	}
	private void info(String s){
		System.out.println("[port " + port +"] " +s);
	}
}
