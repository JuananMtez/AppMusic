package um.tds.appmusic;

import java.awt.EventQueue;

import um.tds.appmusic.gui.FrameLogin;

public class AppMusic {
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					FrameLogin ventana = new FrameLogin();
					ventana.mostrarVentana();
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}
}
