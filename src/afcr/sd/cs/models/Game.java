package afcr.sd.cs.models;

public class Game {
	private String name;
	private String imageURL;
	
	public Game(String name, String imageURL) {
		this.name = name;
		this.imageURL = imageURL;
	}
	
	public String getName() {
		return name;
	}
	
	public String getImageURL() {
		return imageURL;
	}
}
