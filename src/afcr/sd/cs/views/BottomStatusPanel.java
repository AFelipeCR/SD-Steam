package afcr.sd.cs.views;

import java.io.IOException;
import java.net.UnknownHostException;

import javax.swing.JLabel;
import javax.swing.JPanel;

import afcr.sd.cs.models.Client;
import afcr.sd.cs.utilities.AppConstants;
import afcr.sd.cs.utilities.ConfigVariables;

public class BottomStatusPanel extends JPanel implements AppConstants{
	private static final long serialVersionUID = 2L;
	
	private MenuBar menuBar;
	
	private JLabel connectionStatusLabel;
	private JLabel hostPortStatusLabel;

	public BottomStatusPanel(MenuBar menuBar) {
		this.connectionStatusLabel = new JLabel();
		this.hostPortStatusLabel = new JLabel(ConfigVariables.host + ":" + ConfigVariables.port);
		this.menuBar = menuBar;
		this.menuBar.setStatusPanel(this);
		this.init();
	}
	
	private void init() {
		this.add(this.connectionStatusLabel);
		this.add(this.hostPortStatusLabel);
		
		try {
			Client.getInstance().getObservable();
			this.setStatus(CONNECTED_STATUS);
		} catch (UnknownHostException e) {
			this.setStatus(DISCONNECTED_STATUS);
		} catch (IOException e) {
			this.setStatus(DISCONNECTED_STATUS);
		}
	}
	
	public void setStatus(int status){
		this.menuBar.setStatus(status);
		
		if(status == CONNECTED_STATUS)
			this.connectionStatusLabel.setText("Conectado");
		else
			this.connectionStatusLabel.setText("Desconectado");
		
		updateHostPort();
	}
	
	public void updateHostPort() {
		this.hostPortStatusLabel.setText(ConfigVariables.host + ":" + ConfigVariables.port);
	}
}
