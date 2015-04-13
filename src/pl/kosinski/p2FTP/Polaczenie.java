package pl.kosinski.p2FTP;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.concurrent.TimeoutException;

public class Polaczenie extends Thread {
	private static final int timeout = 60;
	private Socket socket;
	private PrintWriter out;
	private BufferedReader in;
	private String polecenie;
	private User user;
	private boolean wyjscie = false;
	private User target;
	
	public Polaczenie(Socket socket) {
		this.socket=socket;
	}
	
	public void run() {
		
		try {
			info("po³¹czono");
			info("client socket jest");
			out = new PrintWriter(socket.getOutputStream(), true);
			info("wyjscie poleceñ jest");
			in = new BufferedReader(new InputStreamReader(
					socket.getInputStream()));
			info("Wejscie polecen jest");
		}
		catch (IOException e) {
			System.err.println("B³¹d portu/po³¹czenia");
			e.printStackTrace();
		}
		
		out.println("identify");
		
		while (!wyjscie) {
			polecenie = czytajNext();
			
			switch (polecenie) {
				case "user":
					String login = czytajNext();
					String haslo = czytajNext();
					if (User.loguj(login, haslo)) {
						info("Zalogowano " + login);
						out.println("Zalogowano " + login);
						user = User.get(login);
						user.zalogowany = true;
						user.setIP(socket.getInetAddress().getHostAddress());
						poZalogowaniu();
					}
					break;
				case "exit":
					return;
			}
			
		}
		
	}
	
	private String czytajNext() {
		try {
			int licznik = 0;
			while (!in.ready() && !wyjscie) {
				Thread.sleep(10);
				licznik++;
				if (licznik > timeout * 100) throw new TimeoutException();
				
			}
			return in.readLine();
		}
		catch (Exception e) {
			System.err.println("B³¹d komunikacji");
			return "";
		}
		
	}
	
	private void info(String s) {
		System.out.println("[klient] " + s);
	}
	
	private void poZalogowaniu() {
		while(!wyjscie){
			polecenie = "exit";
			polecenie = czytajNext();
			if(polecenie.equals("exit")){
				wyloguj();
			}else if(polecenie.equals("mojeip")){
				out.println(user.getIP());
			}else if(polecenie.equals("target")){
				target = User.get(czytajNext());
				if(target != null && target.zalogowany){
				out.println(target.getIP());
				}else{
					out.println("brak");
					info("user niezalogowany/brak usera");
				}
			}else if(polecenie.equals("")){
				
			}
			
			
			
		}
	}
		private void wyloguj(){
			info("Wylogowano " + user.nazwa());
			user.zalogowany=false;
			wyjscie = true;
			try {
				socket.close();
			}
			catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	
}
