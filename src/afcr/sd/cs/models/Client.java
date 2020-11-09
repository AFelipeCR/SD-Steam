package afcr.sd.cs.models;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.Observable;
import java.util.logging.Logger;

import afcr.sd.cs.utilities.ConfigVariables;

public class Client extends Socket {
	private Observable observable;
	private static Client client = null;
	private DataInputStream dis;
	private DataOutputStream dos;
	
	private Client(String host, int port ) throws UnknownHostException, IOException {
		super(host, port);
		this.dis = new DataInputStream(this.getInputStream());
		this.dos = new DataOutputStream(this.getOutputStream());
		Logger.getGlobal().info("New client");
	}
	
	public void request(String request) throws IOException {
		this.dos.writeUTF(request);
		Logger.getGlobal().info("Client new request: " + request);
	}
	
	public String receiveString() throws IOException {
		return this.dis.readUTF();
	}
	
	public byte[] receiveBytes() throws IOException {
		byte[] data = new byte[this.dis.readInt()];
		this.dis.read(data);
		return data;
	}
	
	public int receiveInt() throws IOException {
		return this.dis.readInt();
	}
	
	public static Client getInstance() throws UnknownHostException, IOException {
		if(client == null)
			client = new Client(ConfigVariables.host, ConfigVariables.port);
		return client;
	}
	
	public Observable getObservable() {
		return observable;
	}
}
