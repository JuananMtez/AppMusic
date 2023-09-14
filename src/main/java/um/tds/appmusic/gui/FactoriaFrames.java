package um.tds.appmusic.gui;

import javax.swing.JFrame;

public enum FactoriaFrames {

	INSTANCE;

	private FactoriaFrames() {
	}

	public Panel crearPanel(String panel, JFrame frm) {

		if (panel.equals("Explora"))
			return new PanelExplora(frm);
		else if (panel.equals("Nueva Lista"))
			return new PanelNuevaLista(frm);
		else if (panel.equals("En Bucle"))
			return new PanelEnBucle(frm);
		else if (panel.equals("Recientes"))
			return new PanelRecientes(frm);
		else if (panel.equals("Top Exitos"))
			return new PanelTopExitos(frm);

		else
			return null;

	}

}
