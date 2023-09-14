package um.tds.appmusic.gui;

import java.awt.Color;
import java.awt.Component;
import java.awt.Font;

import javax.swing.BoxLayout;
import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingConstants;

import um.tds.appmusic.controlador.Controlador;

public class PanelTopExitos extends Panel {

	public PanelTopExitos(JFrame miFrame) {

		super(miFrame, Ventana.TOP_EXITOS);

		if (Controlador.INSTANCE.isUsuarioPremium()) {

			super.addPanelCentral();
			panelTable.setVisible(true);
			mostrarContenidoTabla();
		} else {

			mostrarErrorNoPremium();

		}

	}

	private void mostrarContenidoTabla() {

		canciones.addAll(Controlador.INSTANCE.obtenerCancionesMasEscuchadas());

		tableCanciones.revalidate();
		tableCanciones.repaint();

	}

	public void mostrarErrorNoPremium() {

		JPanel panelEquis = new JPanel();
		panelEquis.setAlignmentX(Component.RIGHT_ALIGNMENT);
		panelEquis.setBackground(new Color(135, 206, 250));
		setSizePanel(panelEquis, 500, 200);
		panelCentral.add(panelEquis);
		panelEquis.setLayout(new BoxLayout(panelEquis, BoxLayout.Y_AXIS));

		JLabel imageError = new JLabel();
		imageError.setHorizontalTextPosition(SwingConstants.CENTER);
		imageError.setIcon(new ImageIcon("src\\main\\java\\um\\tds\\appmusic\\gui\\imagenes\\ErrorIco.png"));
		imageError.setAlignmentX(Component.RIGHT_ALIGNMENT);
		imageError.setAlignmentY(Component.TOP_ALIGNMENT);
		imageError.setHorizontalAlignment(SwingConstants.CENTER);
		panelEquis.add(imageError);

		JPanel panelRelleno = new JPanel();
		panelRelleno.setBackground(new Color(135, 206, 250));
		setSizePanel(panelRelleno, 30, 30);
		panelCentral.add(panelRelleno);

		JPanel panelEquisTexto = new JPanel();
		panelEquisTexto.setBackground(new Color(135, 206, 250));
		panelCentral.add(panelEquisTexto);

		JLabel textoError = new JLabel("Active su cuenta Premium para poder disfrutar de mayores ventajas");
		textoError.setFont(new Font("Tahoma", Font.BOLD, 12));
		panelEquisTexto.add(textoError);

	}
}
