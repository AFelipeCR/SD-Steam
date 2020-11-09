package afcr.sd.cs.views;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.net.UnknownHostException;
import java.util.logging.Logger;

import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;

import afcr.sd.cs.connection.Client;
import afcr.sd.cs.utilities.AppConstants;
import afcr.sd.cs.utilities.ConfigVariables;
import afcr.sd.cs.utilities.ImageReader;

/**
 * 
 * @author Andrés Felipe Chaparro Rosas
 * @date 7/11/2020
 */
public class MenuBar extends JMenuBar implements ActionListener, AppConstants {
	private static final long serialVersionUID = 4L;
	private static final String REGISTER_COMMAND = "Register";
	private static final String COVER_COMMAND = "Cover";
	private static final String CONNECT_COMMAND = "Connect";
	private static final String CONFIG_CONN_COMMAND = "ConfigConn";

	private MainPanel mainPanel;
	private BottomStatusPanel statusPanel;

	private JMenu servicesMenu;
	private JMenu connectionMenu;

	public MenuBar(MainPanel mainPanel) {
		this.mainPanel = mainPanel;
		this.init();
	}

	private void init() {
		this.servicesMenu = new JMenu("Servicios");
		JMenuItem registerGame = new JMenuItem("Registrar juego");
		JMenuItem coverGame = new JMenuItem("Ver caratula de juego");

		this.servicesMenu.add(registerGame);
		this.servicesMenu.add(coverGame);

		this.connectionMenu = new JMenu("Conexión");
		JMenuItem connect = new JMenuItem("Conectar");
		JMenuItem configConnection = new JMenuItem("Configurar");

		this.connectionMenu.add(connect);
		this.connectionMenu.add(configConnection);

		registerGame.setActionCommand(REGISTER_COMMAND);
		registerGame.addActionListener(this);

		coverGame.setActionCommand(COVER_COMMAND);
		coverGame.addActionListener(this);

		connect.setActionCommand(CONNECT_COMMAND);
		connect.addActionListener(this);

		configConnection.setActionCommand(CONFIG_CONN_COMMAND);
		configConnection.addActionListener(this);

	}

	@Override
	public void actionPerformed(ActionEvent e) {
		String game;

		switch (e.getActionCommand()) {
		case REGISTER_COMMAND:
			game = JOptionPane.showInputDialog(this.mainPanel, "Ingresa el nombre del juego a registrar", "Registrar",
					JOptionPane.QUESTION_MESSAGE);

			if (game != null) {
				if (!game.isEmpty())
					try {
						Client.getInstance().request("steam/games/" + game);

						if (Client.getInstance().receiveInt() == 1)
							JOptionPane.showMessageDialog(this.mainPanel, "Ese juego ya ha sido registrado",
									"Error S102", JOptionPane.ERROR_MESSAGE);

						Logger.getGlobal().info(Client.getInstance().receiveString());
						;
					} catch (UnknownHostException e1) {
						this.statusPanel.setStatus(DISCONNECTED_STATUS);
					} catch (IOException e1) {
						this.statusPanel.setStatus(DISCONNECTED_STATUS);
					}
				else
					JOptionPane.showMessageDialog(this.mainPanel, "No puedes registrar un juego sin nombre.",
							"Error S100", JOptionPane.ERROR_MESSAGE);
			}
			break;

		case COVER_COMMAND:
			game = JOptionPane.showInputDialog(this.mainPanel, "Ingresa el nombre del juego a mostrar", "Caratula",
					JOptionPane.QUESTION_MESSAGE);

			if (game != null) {
				if (!game.isEmpty())
					try {
						Client.getInstance().request("steam/covers/?id=" + game);
						Logger.getGlobal().info(Client.getInstance().receiveString());
						
						if (Client.getInstance().receiveInt() == 1) {
							JOptionPane.showMessageDialog(this.mainPanel, "No existe un juego con ese nombre",
									"Error S106", JOptionPane.ERROR_MESSAGE);
							Logger.getGlobal().info(Client.getInstance().receiveString());
						} else {
							Logger.getGlobal().info(Client.getInstance().receiveString());
							ImageReader ir = new ImageReader(Client.getInstance().receiveBytes());
							Logger.getGlobal().info(ir.toString());
							this.mainPanel.setImage(ir.getBufferedImage());
						}
					} catch (UnknownHostException e1) {
						this.statusPanel.setStatus(DISCONNECTED_STATUS);
					} catch (IOException e1) {
						this.statusPanel.setStatus(DISCONNECTED_STATUS);
					}
				else
					JOptionPane.showMessageDialog(this.mainPanel, "No puedes pedir un juego sin nombre.", "Error S101",
							JOptionPane.ERROR_MESSAGE);
			}
			break;

		case CONNECT_COMMAND:
			try {
				Client.getInstance();
				this.statusPanel.setStatus(CONNECTED_STATUS);
			} catch (UnknownHostException e1) {
				this.statusPanel.setStatus(DISCONNECTED_STATUS);
				JOptionPane.showMessageDialog(this.mainPanel, "No se ha podido establecer la conexión.", "Error S105", JOptionPane.ERROR_MESSAGE);
			} catch (IOException e1) {
				this.statusPanel.setStatus(DISCONNECTED_STATUS);
				JOptionPane.showMessageDialog(this.mainPanel, "No se ha podido establecer la conexión.", "Error", JOptionPane.ERROR_MESSAGE);
			}
			break;

		case CONFIG_CONN_COMMAND:
			String conn = JOptionPane.showInputDialog(this.mainPanel,
					"Introduce el host y el puerto de la forma: HOST:PORT",
					ConfigVariables.host + ":" + ConfigVariables.port);

			if (conn != null) {
				String[] conns = conn.split(":");
				ConfigVariables.host = conns[0];
				ConfigVariables.port = Integer.parseInt(conns[1]);
				this.statusPanel.updateHostPort();
			}
			break;
		}
	}

	public void setStatus(int status) {
		if (status == CONNECTED_STATUS) {
			this.add(servicesMenu);
			this.remove(connectionMenu);
		} else if (status == DISCONNECTED_STATUS) {
			this.add(connectionMenu);
			this.remove(servicesMenu);
		}
	}

	public void setStatusPanel(BottomStatusPanel statusPanel) {
		this.statusPanel = statusPanel;
	}
}
