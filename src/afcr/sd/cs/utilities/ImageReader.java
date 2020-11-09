package afcr.sd.cs.utilities;

import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

/**
 * 
 * @author Andrés Felipe Chaparro Rosas
 * @date 7/11/2020
 */
public class ImageReader {
	private String path;
	private BufferedImage bi;
	private ByteArrayOutputStream bos;
	private ByteArrayInputStream bis;
	private byte[] imageBytes;
	
	
	public ImageReader(String path) throws IOException {
		this.path = path;
		this.bos = new ByteArrayOutputStream();
		this.bi = ImageIO.read(new File(this.path));
		ImageIO.write(bi, "jpg", bos);
		this.imageBytes = bos.toByteArray();
	}
	
	public ImageReader(byte[] data) throws IOException {
		this.imageBytes = data;
		this.bis = new ByteArrayInputStream(data);
		this.bi = ImageIO.read(bis);
	}
	
	public byte[] getImageBytes() {
		return imageBytes;
	}
	
	public BufferedImage getBufferedImage() {
		return bi;
	}
}
