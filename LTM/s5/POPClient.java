import java.io.*; 
import java.util.*; 
import java.net.*; 
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class POPClient {
	public static void main(String[] args) {
		if(args.length < 4) {System.out.println("Enter <server> <port> <user> <password>"); return;}
		 try {
             
			 String pop3ServerName = args[0];
			 int port = Integer.valueOf(args[1]).intValue();
			 
			 Socket pop3ServerSocket = new Socket(pop3ServerName, port);
			 
			 BufferedReader brPop3ServerSocket = new BufferedReader(new InputStreamReader(pop3ServerSocket.getInputStream()));
			 PrintWriter pw = new PrintWriter(pop3ServerSocket.getOutputStream());
             BufferedReader keyboard = new BufferedReader(new InputStreamReader(System.in));
             
			 // login
			 pw.println("user " + args[2]);
			 pw.flush();
			 String dataRecive = brPop3ServerSocket.readLine();
			 System.out.println("Server Response : " + dataRecive);
			 
			 // password
			 pw.println("pass " + args[3]);
			 pw.flush();
			 dataRecive = brPop3ServerSocket.readLine();
			 System.out.println("Server Response: " + dataRecive);
			 
             // recive a email with number
             String noMail = "1";
			 while(true) {
                System.out.println("Enter message no, 0 for the end: ");
				noMail = keyboard.readLine();
                
                if(noMail.equals("quit")) {
                    pw.println("quit");
                    pw.println("quit");
					pw.flush();
					break;
                }
                if(noMail.equals("0")) {
                    pw.println("stat");
                    pw.flush();
                    dataRecive = brPop3ServerSocket.readLine();
                    Pattern p = Pattern.compile("\\d+");
                    Matcher numberOfMail = p.matcher(dataRecive);
                    if(numberOfMail.find()) {
                        pw.println("retr "  + numberOfMail.group());
                        pw.flush();
                    }
                } else {
                    pw.println("retr "  + noMail);
                    pw.flush();
                }
                
                // read email
                while(true) {
                    dataRecive = brPop3ServerSocket.readLine();
                    if(dataRecive.equals("-ERR There's no message " + noMail + ".")) {
                        System.out.println("-ERR There's no message " + noMail + ".");
                        break;
                    }
                    System.out.println(dataRecive);
                    if(dataRecive.equals(".")) {
                        break;
                    }
                }
			}
			 dataRecive = brPop3ServerSocket.readLine();
			 System.out.println("Server Response : " + dataRecive);
			 pop3ServerSocket.close();
		 } catch(IOException e) {
			 System.out.println(e);
		 }
	}
}