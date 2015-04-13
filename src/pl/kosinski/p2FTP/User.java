package pl.kosinski.p2FTP;

import java.util.HashMap;

public class User {
	public boolean zalogowany = false;
	private static HashMap<String,User> users = new HashMap<String,User>();
	private String haslo;
	private String login;
	private String ip;
	public User(String haslo){
		this.haslo=haslo;
	}
	
	public static boolean loguj(String login,String haslo){
		if(users.containsKey(login) && users.get(login).haslo.equals(haslo)){
			get(login).zalogowany=true;
			get(login).login=login;
			return true;
		}else{
			return false;
		}
		
	}
	public static void add(String login,String haslo){
		users.put(login, new User(haslo));
	}
	
	public static User get(String s){
		if(users.containsKey(s)){
		return users.get(s);
		}else{
			return null;
		}
	}
	public String nazwa(){
		return this.login;
	}
	public void setIP(String ip){
		this.ip=ip;
	}
	public String getIP(){
		return ip;
	}
}
