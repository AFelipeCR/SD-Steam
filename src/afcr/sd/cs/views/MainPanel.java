package afcr.sd.cs.views;

import java.awt.Graphics;
import java.awt.Image;

import javax.swing.JPanel;

import afcr.sd.cs.utilities.AppConstants;

public class MainPanel extends JPanel {
	private static final long serialVersionUID = 3L;
	private Image image;

	public void setImage(Image image) {
		this.image = image;
		this.repaint();
	}

	@Override
	public void paint(Graphics g) {
		super.paint(g);
		
		if (this.image != null) {
			g.drawImage(this.image, (AppConstants.APP_SIZE.width - this.image.getWidth(this)) / 2, 10, this);
		}
	}
}
