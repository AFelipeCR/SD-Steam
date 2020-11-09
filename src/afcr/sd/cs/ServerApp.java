package afcr.sd.cs;

import java.io.IOException;

import afcr.sd.cs.connection.Server;

/**
 * 
 * @author Andrés Felipe Chaparro Rosas
 * @date 7/11/2020
 */
public class ServerApp {
	
	public static void main(String[] args) {
		try {
			new Server(65355);
		} catch (IOException e) {}
	}

}
