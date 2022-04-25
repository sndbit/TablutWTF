package it.unibo.ai.didattica.competition.tablut.whatTheFork;

import java.io.IOException;
import java.net.UnknownHostException;

public class TablutWTFWhiteClient {
	
	public static void main(String[] args) throws UnknownHostException, ClassNotFoundException, IOException {
	       String[] array = new String[]{"WHITE", "60", "localhost"};
	       if (args.length>0){
	            array = new String[]{"WHITE", args[0]};
	       }
	       TablutWTFClient.main(array);
	   }
}
