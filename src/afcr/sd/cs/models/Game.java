package afcr.sd.cs.models;

/**
 * 
 * @author Andrés Felipe Chaparro Rosas
 * @date 7/11/2020
 */
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
