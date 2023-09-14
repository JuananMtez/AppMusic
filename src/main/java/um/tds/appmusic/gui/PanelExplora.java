package um.tds.appmusic.gui;

import javax.swing.JPanel;
import java.awt.FlowLayout;
import java.awt.Font;
import javax.swing.JButton;
import javax.swing.SwingConstants;
import java.awt.Color;
import javax.swing.JTextField;
import javax.swing.JComboBox;
import javax.swing.JFrame;

public class PanelExplora extends Panel {

	private JTextField txtInterprete;
	private JTextField txtTitulo;
	private JPanel panelFiltro;
	private JPanel panelBuscar;
	JComboBox cmEstilo;
	

	public PanelExplora(JFrame miFrame) {
		super(miFrame, Ventana.EXPLORAR_CANCIONES);
		addPanelCentral();
		panelReproductor.setVisible(false);

	}

	@Override
	public void addPanelCentral() {
//s
		crearPanelFiltro();
		panelCentral.add(panelFiltro);

		crearPanelBuscar();
		panelCentral.add(panelBuscar);

		super.addPanelCentral();

	}

	private void crearPanelFiltro() {

		panelFiltro = new JPanel();
		panelFiltro.setBackground(new Color(135, 206, 250));

		panelFiltro.setLayout(new FlowLayout(FlowLayout.CENTER, 5, 5));
		setSizePanel(panelFiltro, 500, 40);

		txtInterprete = new JTextField();
		txtInterprete.setHorizontalAlignment(SwingConstants.LEFT);
		panelFiltro.add(txtInterprete);
		txtInterprete.setColumns(15);
		TextPrompt placeholderInterprete = new TextPrompt("Intérprete", txtInterprete);
		placeholderInterprete.changeAlpha(0.75f);
		placeholderInterprete.changeStyle(Font.BOLD);

		txtTitulo = new JTextField();
		txtTitulo.setHorizontalAlignment(SwingConstants.LEFT);
		panelFiltro.add(txtTitulo);
		txtTitulo.setColumns(15);

		TextPrompt placeholderTitulo = new TextPrompt("Título", txtTitulo);
		placeholderTitulo.changeAlpha(0.75f);
		placeholderTitulo.changeStyle(Font.BOLD);

		cmEstilo = crearComboBoxEstilo();
		panelFiltro.add(cmEstilo);

	}

	private void crearPanelBuscar() {

		panelBuscar = new JPanel();
		panelBuscar.setBackground(new Color(135, 206, 250));

		setSizePanel(panelBuscar, 500, 40);
		JButton btnBuscar = new JButton("Buscar");

		btnBuscar.setFocusPainted(false);
		panelBuscar.add(btnBuscar);

		btnBuscar.addActionListener(ev -> {
			panelTable.setVisible(true);
			eventoFiltrarCanciones(tableCanciones, txtTitulo, txtInterprete, cmEstilo, canciones);
		});

		JButton btnLimpiar = new JButton("Limpiar Filtro");

		btnLimpiar.setFocusPainted(false);
		
		btnLimpiar.addActionListener(ev -> {
			canciones.clear();
			tableCanciones.revalidate();
			tableCanciones.repaint();
			
			cmEstilo.setSelectedItem("cualquier estilo");
			txtInterprete.setText(null);
			txtTitulo.setText(null);
			
			TextPrompt placeholderInterprete = new TextPrompt("Intérprete", txtInterprete);
			placeholderInterprete.changeAlpha(0.75f);
			placeholderInterprete.changeStyle(Font.BOLD);
			
			TextPrompt placeholderTitulo = new TextPrompt("Título", txtTitulo);
			placeholderTitulo.changeAlpha(0.75f);
			placeholderTitulo.changeStyle(Font.BOLD);
			
		});

		panelBuscar.add(btnLimpiar);

	}

}