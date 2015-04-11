package pl.kosinski.p2FTP;

import java.util.HashMap;

public class User {
	private static String aktualnyUser = null;
	private static HashMap<String,User> users = new HashMap<String,User>();
	private String haslo;
	
	public User(String haslo){
		this.haslo=haslo;
	}
	
	public static boolean loguj(String login,String haslo){
		if(users.containsKey(login) && users.get(login).haslo.equals(haslo)){
			aktualnyUser=login;
			return true;
		}else{
			return false;
		}
		
	}
	public static void add(String login,String haslo){
		users.put(login, new User(haslo));
	}
	public static String aktualny(){
		return aktualnyUser;
	}
}
