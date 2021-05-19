package um.tds.appmusic.gui;

import javax.swing.JFrame;

import um.tds.appmusic.controlador.Controlador;

public class PanelEnBucle extends Panel {

	public PanelEnBucle(JFrame miFrame) {
		super(miFrame, Ventana.EN_BUCLE);
		super.addPanelCentral();
		mostrarContenidoTabla();
		panelTable.setVisible(true);
	}

	protected void mostrarContenidoTabla() {

		canciones.addAll(Controlador.INSTANCE.obtenerCancionesMasEscuchadasUsuario());

		tableCanciones.revalidate();
		tableCanciones.repaint();

	}
}
