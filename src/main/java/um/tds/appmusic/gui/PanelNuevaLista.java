package um.tds.appmusic.gui;

import java.awt.Color;
import java.awt.Dimension;

import javax.swing.BoxLayout;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import um.tds.appmusic.controlador.Controlador;
import um.tds.appmusic.dominio.Cancion;
import um.tds.appmusic.dominio.Playlist;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.Insets;
import java.util.ArrayList;
import java.util.List;

public class PanelNuevaLista extends Panel {

	private JTextField txtNombreLista;
	private JButton btnCrearLista;
	private JPanel panelCrearLista;

	private JPanel panelTablas;
	private JPanel panelBuscador;
	private JTextField txtInterprete;
	private JTextField txtTitulo;
	private JComboBox comboEstilo;
	private JButton btnBuscar;

	private List<Cancion> cancionesPosibles;
	private JTable tablaCanciones;
	JScrollPane scrollPane;

	private List<Cancion> cancionesMiPlaylist;
	private JTable tablaMiPlaylist;
	JScrollPane scrollPane2;

	private Playlist playlistActual;

	private JButton btnMeter;
	private JButton btnSacar;

	private boolean ampliada;

	public PanelNuevaLista(JFrame miFrame) {
		super(miFrame, Ventana.NUEVA_LISTA);
		ampliada = false;
		cancionesMiPlaylist = new ArrayList<Cancion>();
		addPanelCentral();
	}

	@Override
	public void addPanelCentral() {

		crearPanelCrearLista();
		panelCrearLista.add(txtNombreLista);
		panelCrearLista.add(btnCrearLista);
		panelCentral.add(panelCrearLista);
	}

	private void crearPanelCrearLista() {

		panelCrearLista = new JPanel();
		setSizePanel(panelCrearLista, 850, 70);
		panelCrearLista.setBackground(new Color(135, 206, 250));
		FlowLayout flowLayout = (FlowLayout) panelCrearLista.getLayout();
		flowLayout.setHgap(40);
		flowLayout.setVgap(20);
		txtNombreLista = new JTextField();
		txtNombreLista.setColumns(20);
		TextPrompt placeholderNombreLista = new TextPrompt("Nombre de la lista", txtNombreLista);
		placeholderNombreLista.changeAlpha(0.75f);
		placeholderNombreLista.changeStyle(Font.BOLD);

		btnCrearLista = new JButton("Crear");
		btnCrearLista.setMargin(new Insets(0, 0, 0, 0));
		btnCrearLista.setFocusPainted(false);

		setSizeButton(btnCrearLista, 100, 25);
		
		btnCrearLista.addActionListener(ev -> {

			int res = JOptionPane.showConfirmDialog(miFrame, "Deseas crear una nueva Lista?", "Nueva Playlist",
					JOptionPane.YES_NO_OPTION);

			if (res == JOptionPane.NO_OPTION) {
				JOptionPane.showMessageDialog(miFrame, "Operacion cancelada");
			}

			if (res == JOptionPane.YES_OPTION) {
				if (txtNombreLista.getText().equals("")) {
					JOptionPane.showMessageDialog(miFrame, "Introduce un nombre para la Playlist");
				} else {

					if ((playlistActual = Controlador.INSTANCE
							.crearPlaylist(txtNombreLista.getText())) != null) {
						JOptionPane.showMessageDialog(miFrame, "Lista creada");
						actualizarFrame();
					} else {
						JOptionPane.showMessageDialog(miFrame,
								"Playlist: *" + txtNombreLista.getText() + "* ya existe, puede modificarla.");
						playlistActual = Controlador.INSTANCE.playlistExistente(txtNombreLista.getText());
						cancionesMiPlaylist.addAll(playlistActual.getCanciones());
						actualizarFrame();
					}
				}
			}
		});
	}

	private void actualizarFrame() {

		addNombrePlaylist();
		miFrame.setBounds(miFrame.getX() - 200, miFrame.getY(), 1200, 625);
		crearPanelBuscador();
		panelCentral.add(panelBuscador);
		crearPanelTablas();
		panelCentral.add(panelTablas);

		ampliada = true;

	}

	private void addNombrePlaylist() {

		txtNombreLista.setVisible(false);
		btnCrearLista.setVisible(false);

		txtNombreLista.getText();
		JLabel lblNombreLista = new JLabel(txtNombreLista.getText());
		lblNombreLista.setFont(new Font("Tahoma", Font.BOLD, 25));
		panelCrearLista.add(lblNombreLista);

		miFrame.revalidate();
		miFrame.repaint();

	}

	private void crearPanelBuscador() {

		panelBuscador = new JPanel();
		FlowLayout flowLayout_1 = (FlowLayout) panelBuscador.getLayout();
		flowLayout_1.setHgap(40);
		panelBuscador.setBackground(new Color(135, 206, 250));
		setSizePanel(panelBuscador, 850, 80);

		txtInterprete = new JTextField();
		txtInterprete.setColumns(10);
		TextPrompt placeholderInterprete = new TextPrompt("Intérprete", txtInterprete);
		placeholderInterprete.changeAlpha(0.75f);
		placeholderInterprete.changeStyle(Font.BOLD);

		panelBuscador.add(txtInterprete);

		txtTitulo = new JTextField();
		txtTitulo.setColumns(10);
		TextPrompt placeholderTitulo = new TextPrompt("Título", txtTitulo);
		placeholderTitulo.changeAlpha(0.75f);
		placeholderTitulo.changeStyle(Font.BOLD);
		panelBuscador.add(txtTitulo);

		comboEstilo = crearComboBoxEstilo();

		panelBuscador.add(comboEstilo);

		btnBuscar = new JButton("Buscar");
		btnBuscar.addActionListener(ev -> {
			eventoFiltrarCanciones(tablaCanciones, txtTitulo, txtInterprete, comboEstilo, cancionesPosibles);
		});
		
		panelBuscador.add(btnBuscar);

	}

	private void crearPanelTablas() {

		panelTablas = new JPanel();
		panelTablas.setBorder(null);
		panelTablas.setBackground(new Color(135, 206, 250));
		panelTablas.setLayout(new FlowLayout(FlowLayout.CENTER, 5, 5));
		FlowLayout flLayout = (FlowLayout) panelTablas.getLayout();
		flLayout.setHgap(50);

		crearTablaCanciones();
		panelTablas.add(scrollPane);

		crearPanelBotones();
		panelTablas.add(panelBotones);

		crearTablaMiPlaylists();
		panelTablas.add(scrollPane2);

	}

	private void crearTablaCanciones() {

		cancionesPosibles = new ArrayList<Cancion>();
		cancionesPosibles = Controlador.INSTANCE.mostrarCancionesTabla();
		tablaCanciones = new JTable(new CancionesTableModel(cancionesPosibles));
		scrollPane = new JScrollPane(tablaCanciones);
		tablaCanciones.setPreferredScrollableViewportSize(new Dimension(300, 300));
	}

	private void crearTablaMiPlaylists() {

		tablaMiPlaylist = new JTable(new CancionesTableModel(cancionesMiPlaylist));
		scrollPane2 = new JScrollPane(tablaMiPlaylist);
		tablaMiPlaylist.setPreferredScrollableViewportSize(new Dimension(300, 300));

	}

	private void crearPanelBotones() {

		panelBotones = new JPanel();

		panelBotones.setLayout(new BoxLayout(panelBotones, BoxLayout.Y_AXIS));
		panelBotones.setBackground(new Color(135, 206, 250));

		btnMeter = new JButton();
		btnMeter.setIcon(new ImageIcon("src\\main\\java\\um\\tds\\appmusic\\gui\\imagenes\\masIco.png"));
		btnMeter.setMargin(new Insets(0, 0, 0, 0));
		btnMeter.setFocusPainted(false);
		btnMeter.setBackground(new Color(135, 206, 250));

		btnMeter.addActionListener(ev -> {
			int row = tablaCanciones.getSelectedRow();
			if (row >= 0) {
				cancionesMiPlaylist.add(cancionesPosibles.get(row));
				Controlador.INSTANCE.registrarCancionPlaylistUsuario(playlistActual,
						cancionesPosibles.get(row));
				cancionesPosibles.remove(row);

				tablaMiPlaylist.repaint();
				tablaMiPlaylist.revalidate();
				tablaCanciones.repaint();
				tablaCanciones.revalidate();

			}

		});

		btnSacar = new JButton();
		btnSacar.setIcon(new ImageIcon("src\\main\\java\\um\\tds\\appmusic\\gui\\imagenes\\menosIco.png"));
		btnSacar.setMargin(new Insets(0, 0, 0, 0));
		btnSacar.setFocusPainted(false);
		btnSacar.setBackground(new Color(135, 206, 250));

		setSizeButton(btnMeter, 70, 50);
		setSizeButton(btnSacar, 70, 50);

		btnSacar.addActionListener(ev -> {
			int row = tablaMiPlaylist.getSelectedRow();

			if (row >= 0) {
				try {
					cancionesPosibles.add(cancionesMiPlaylist.get(row));
					Controlador.INSTANCE.removeCancionPlaylistUsuario(playlistActual,
							cancionesMiPlaylist.get(row));
					cancionesMiPlaylist.remove(row);
					tablaMiPlaylist.repaint();
					tablaMiPlaylist.revalidate();
					tablaCanciones.repaint();
					tablaCanciones.revalidate();
				} catch (IndexOutOfBoundsException e) {
				}

			}
		});

		panelBotones.add(btnMeter);
		panelBotones.add(btnSacar);

	}

	@Override
	public void eliminarComponentes() {

		if (ampliada)
			miFrame.setBounds(miFrame.getX() + 200, miFrame.getY(), 650, 625);

		super.eliminarComponentes();

	}

}
