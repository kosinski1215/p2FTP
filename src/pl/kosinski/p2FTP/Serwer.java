package pl.kosinski.p2FTP;

public class Serwer {

	public static void main(String[] args) {
		
		User.add("admin", "1234");
		User.add("marian", "4321");
		Nasluch.startNew(8620);
		
	}

}
