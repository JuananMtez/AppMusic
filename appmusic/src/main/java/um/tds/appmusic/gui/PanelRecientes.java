package um.tds.appmusic.gui;

import javax.swing.JFrame;

import um.tds.appmusic.controlador.Controlador;

public class PanelRecientes extends Panel {

	public PanelRecientes(JFrame miFrame) {

		super(miFrame, Ventana.RECIENTES);
		super.addPanelCentral();
		mostrarContenidoTabla();
		panelTable.setVisible(true);

	}

	private void mostrarContenidoTabla() {

		canciones.addAll(Controlador.INSTANCE.obtenerCancionesRecientes());

		tableCanciones.revalidate();
		tableCanciones.repaint();

	}

}