package afcr.sd.cs.connection;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.Observable;
import java.util.logging.Logger;

import afcr.sd.cs.utilities.ConfigVariables;

/**
 * 
 * @author Andrés Felipe Chaparro Rosas
 * @date 7/11/2020
 */
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
	
	/**
	 * Hace una petición al servidor por medio de comando
	 */
	public void request(String request) throws IOException {
		this.dos.writeUTF(request);
		Logger.getGlobal().info("Client new request: " + request);
	}
	
	/**
	 * Recibe un string desde el servidor
	 * @return string
	 * @throws IOException
	 */
	public String receiveString() throws IOException {
		return this.dis.readUTF();
	}
	
	/**
	 * Recibe un grupo de bytes desde el servidor
	 * @return grupo de bytes
	 * @throws IOException
	 */
	public byte[] receiveBytes() throws IOException {
		byte[] data = new byte[this.dis.readInt()];
		this.dis.readFully(data);
		return data;
	}
	
	/**
	 * Recibe un entero desde el servidor
	 * @return entero
	 * @throws IOException
	 */
	public int receiveInt() throws IOException {
		return this.dis.readInt();
	}
	
	/**
	 * Retorna una estancia estatica para toda la aplicación
	 * @return Cliente a conectarse
	 * @throws UnknownHostException No existe conexión
	 * @throws IOException Error de entrada o salida
	 */
	public static Client getInstance() throws UnknownHostException, IOException {
		if(client == null)
			client = new Client(ConfigVariables.host, ConfigVariables.port);
		return client;
	}
	
	public Observable getObservable() {
		return observable;
	}
}
