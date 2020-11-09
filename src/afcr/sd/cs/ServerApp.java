package afcr.sd.cs;

import java.io.IOException;

import afcr.sd.cs.models.Server;

public class ServerApp {
	
	public static void main(String[] args) {
		try {
			new Server(65355);
		} catch (IOException e) {}
	}

}
