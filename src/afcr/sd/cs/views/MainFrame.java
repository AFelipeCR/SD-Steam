package afcr.sd.cs.views;

import java.awt.BorderLayout;

import javax.swing.JFrame;

import afcr.sd.cs.utilities.AppConstants;

/**
 * 
 * @author Andrés Felipe Chaparro Rosas
 * @date 7/11/2020
 */
public class MainFrame extends JFrame implements AppConstants {
	private static final long serialVersionUID = 1L;
	
	private MenuBar menuBar;
	
	private MainPanel mainPanel;
	private BottomStatusPanel statusPanel;
	
	public MainFrame() {
		this.mainPanel = new MainPanel();
		this.menuBar = new MenuBar(this.mainPanel);
		this.statusPanel = new BottomStatusPanel(this.menuBar);
		this.init();
	}
	
	private void init() {
		this.setTitle(APP_NAME);
		this.setSize(APP_SIZE);
		this.setLocationRelativeTo(null);
		this.setDefaultCloseOperation(EXIT_ON_CLOSE);
		
		this.setLayout(new BorderLayout());
		
		this.setJMenuBar(this.menuBar);
		
		this.add(this.mainPanel, BorderLayout.CENTER);
		this.add(this.statusPanel, BorderLayout.SOUTH);
		
		this.setVisible(true);
	}

}
