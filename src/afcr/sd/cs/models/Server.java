package afcr.sd.cs.models;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.File;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.SocketException;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.logging.Logger;

import afcr.sd.cs.utilities.ImageReader;

public class Server extends ServerSocket{
	private static final String Game_Command = "steam/games/";
	private static final String Cover_Command = "steam/covers/?id=";
	private List<Game> games;
	private File[] images;

	public Server(int port) throws IOException {
		super(port);
		this.games = new ArrayList<>();
		Logger.getGlobal().info("Server run on " + port);
		this.images = new File("images").listFiles();
		
		this.init();
	}
	
	private void init() {
		new Thread(() -> {
			while(true) {
				try {
					Socket connection = this.accept();
					
					new Thread(() -> {
						Logger.getGlobal().info("New connection");
						
						try {
							DataInputStream dis = new DataInputStream(connection.getInputStream());
							DataOutputStream dos = new DataOutputStream(connection.getOutputStream());
							String request;
							String[] data;
							Game game;
							ImageReader imageReader;
							
							while(dis.available() > -1) {
								try {
									request = dis.readUTF();
									
									data = extractInfo(request);
									
									if(data != null) {
										Logger.getGlobal().info("New request " + data[1]);
										
										switch(data[1]) {
											case Game_Command:
												if(findGame(data[0]) == null) {
													games.add(new Game(data[0], this.images[new Random().nextInt(this.images.length)].toString()));
													Logger.getGlobal().info("Registed game: " + data[0]);
													dos.writeInt(0);
													dos.writeUTF("Registed game: " + data[0]);
												}else {
													Logger.getGlobal().info("Already exists: " + data[0]);
													dos.writeInt(1);
													dos.writeUTF("Already exists: " + data[0]);
												}
												
												break;
											case Cover_Command:
												Logger.getGlobal().info("Searching: " + data[0]);
												dos.writeUTF("Searching game: " + data[0]);
												game = findGame(data[0]);
												
												if(game != null) {
													dos.writeInt(0);
													dos.writeUTF("Found: " + game.getName());
													Logger.getGlobal().info("Found: " + data[0]);
													imageReader = new ImageReader(game.getImageURL());
													Logger.getGlobal().info("Sending image: " + game.getImageURL());
													Logger.getGlobal().info("Image bytes: " + imageReader.getImageBytes().length);
													dos.writeInt(imageReader.getImageBytes().length);
													dos.write(imageReader.getImageBytes());
													Logger.getGlobal().info("Image sended: " + game.getImageURL());
												} else {
													dos.writeInt(1);
													dos.writeUTF(data[0] + " not found");
													Logger.getGlobal().info(data[0] + " not found");
												}
												break;
											default:
												Logger.getGlobal().warning("Server response Error");
												dos.writeUTF("Error 404: " + request + " not found");
												break;
										}
									}
								}catch(SocketException e) {
									break;
								}
							}
						} catch (IOException e1) {
						}
					}).start();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}).start();;
	}
	
	private String[] extractInfo(String request) {
		String data;
		if(request.contains(Game_Command)) {
			data = request.replace(Game_Command, "");
			return new String[] {data,  request.replace(data, "")};
		} else if(request.contains(Cover_Command)) {
			data =request.replace(Cover_Command, "");
			return new String[] {data,  request.replace(data, "")};
		}
		
		return null;
	}
	
	Game findGame(String name) {
		for (Game game : this.games) {
			if(game.getName().contentEquals(name))
				return game;
		}
		
		return null;
	}

}
