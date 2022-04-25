package it.unibo.ai.didattica.competition.tablut.whatTheFork;

import java.io.IOException;
import java.net.UnknownHostException;

public class TablutWTFBlackClient {
	public static void main(String[] args) throws UnknownHostException, ClassNotFoundException, IOException {
	       String[] array = new String[]{"BLACK", "60", "localhost"};
	       if (args.length>0){
	           array = new String[]{"BLACK", args[0]};
	       }
	       TablutWTFClient.main(array);
	   }	
}
