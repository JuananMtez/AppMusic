package um.tds.appmusic.gui;

import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.Font;
import javax.swing.BoxLayout;
import javax.swing.ImageIcon;
import javax.swing.border.TitledBorder;
import javax.swing.JTextPane;
import javax.swing.border.EtchedBorder;

public class AcercaDeDialog extends JDialog {

	private static final long serialVersionUID = 1L;

	public AcercaDeDialog(JFrame owner) {
		super(owner, "Acerca De", true);

		this.setBounds(600, 250, 300, 350);
		this.setResizable(false);

		crearPanelTitulo();
		crearPanelTexto();

	}

	private void crearPanelTitulo() {

		JPanel panelNorte = new JPanel();
		panelNorte.setPreferredSize(new Dimension(10, 100));
		panelNorte.setMinimumSize(new Dimension(10, 68));
		panelNorte.setMaximumSize(new Dimension(10, 68));
		panelNorte.setBorder(null);
		panelNorte.setBackground(new Color(135, 206, 250));

		getContentPane().add(panelNorte, BorderLayout.NORTH);
		panelNorte.setLayout(new BoxLayout(panelNorte, BoxLayout.Y_AXIS));

		JLabel lblTitulo = new JLabel();
		lblTitulo.setIcon(new ImageIcon("src\\main\\java\\um\\tds\\appmusic\\gui\\imagenes\\LogoIco.png"));
		lblTitulo.setPreferredSize(new Dimension(57, 105));
		lblTitulo.setAlignmentX(Component.CENTER_ALIGNMENT);
		lblTitulo.setForeground(new Color(0, 51, 255));
		lblTitulo.setFont(new Font("Segoe Script", Font.BOLD | Font.ITALIC, 35));
		panelNorte.add(lblTitulo);
	}

	private void crearPanelTexto() {

		JPanel panelCentral = new JPanel();
		panelCentral.setMinimumSize(new Dimension(10, 50));
		panelCentral.setPreferredSize(new Dimension(10, 200));
		panelCentral.setBackground(new Color(135, 206, 250));
		panelCentral.setForeground(new Color(0, 0, 0));
		panelCentral
				.setBorder(new TitledBorder(
						new TitledBorder(
								new EtchedBorder(EtchedBorder.LOWERED, new Color(255, 255, 255),
										new Color(160, 160, 160)),
								"", TitledBorder.LEADING, TitledBorder.TOP, null, new Color(0, 0, 0)),
						"Créditos", TitledBorder.LEFT, TitledBorder.TOP, null, new Color(0, 0, 0)));

		getContentPane().add(panelCentral, BorderLayout.CENTER);
		panelCentral.setLayout(new BoxLayout(panelCentral, BoxLayout.Y_AXIS));

		JTextPane txtCreditos = new JTextPane();
		txtCreditos.setPreferredSize(new Dimension(7, 40));
		txtCreditos.setBackground(new Color(135, 206, 250));
		txtCreditos.setText(
				"Esta aplicación ha sido desarrollada para la asignatura de 3º Tecnología de Software por alumnos del Grupo 3.1\r\n\r\n\t"
						+ "Desarrolladores\r\n\r\n" + "Juan Antonio Martínez López\r\n" + "Jose Piñera Moreno\r\n");

		txtCreditos.setForeground(Color.BLACK);
		txtCreditos.setFont(new Font("Tahoma", Font.BOLD | Font.ITALIC, 13));
		txtCreditos.setEditable(false);
		panelCentral.add(txtCreditos);
	}

}
