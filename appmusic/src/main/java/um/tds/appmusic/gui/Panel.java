package um.tds.appmusic.gui;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Insets;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.*;
import javax.swing.Timer;
import javax.swing.BoxLayout;
import javax.swing.DefaultComboBoxModel;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.JSlider;
import javax.swing.border.BevelBorder;
import javax.swing.border.SoftBevelBorder;
import um.tds.appmusic.controlador.Controlador;
import um.tds.appmusic.dominio.Cancion;
import java.awt.Component;


public abstract class Panel {

	protected JFrame miFrame;
	protected JPanel panelCentral;

	protected JPanel panelTable;
	protected JTable tableCanciones;
	protected JScrollPane scrollPane;

	protected JPanel panelReproductor;
	protected JPanel panelSliderReproduccion;
	protected JSlider sliderReproduccion;

	protected JSlider sliderVolumen;

	protected JPanel panelBotones;
	protected JButton btnSiguiente;
	protected JButton btnAnterior;
	protected JButton btnPlay;

	private boolean playing;
	protected List<Cancion> canciones;

	private int posSlider;
	private Timer timer;

	public Panel(JFrame miFrame, Ventana ventana) {

		this.miFrame = miFrame;

		panelCentral = new JPanel();
		panelCentral.setAlignmentX(Component.RIGHT_ALIGNMENT);
		panelCentral.setBorder(new SoftBevelBorder(BevelBorder.LOWERED, null, null, null, null));
		panelCentral.setBackground(new Color(135, 206, 250));
		panelCentral.setLayout(new BoxLayout(panelCentral, BoxLayout.Y_AXIS));

		if (ventana != Ventana.NUEVA_LISTA && ventana != Ventana.EXPLORAR_CANCIONES) {

			JPanel panelRelleno = new JPanel();

			panelRelleno.setBackground(new Color(135, 206, 250));

			setSizePanel(panelRelleno, 200, 20);
			panelCentral.add(panelRelleno);
			playing = false;
			posSlider = 0;

		}

		timer = new Timer(1000, ev -> {

			sliderReproduccion.setValue(posSlider);
			posSlider += 1;

			if (posSlider >= sliderReproduccion.getMaximum()) {

				int row = tableCanciones.getSelectedRow();

				if (row < canciones.size() - 1) {
					System.out.println();
					int auxRow = (row + 1) % canciones.size();
					tableCanciones.changeSelection(auxRow, 0, false, false);
					seleccionarCancion(auxRow);
					sliderReproduccion.setValue(posSlider);
				}
			}
		});
	}

	public void mostrarComponentes() {

		miFrame.getContentPane().add(panelCentral, BorderLayout.CENTER);
		miFrame.revalidate();
		miFrame.validate();
		miFrame.repaint();

	}

	public void eliminarComponentes() {

		miFrame.getContentPane().remove(panelCentral);
		miFrame.revalidate();
		miFrame.validate();
		miFrame.repaint();
	}

	public void addPanelCentral() {

		crearPanelTabla();
		panelCentral.add(panelTable);

		crearPanelReproductor();
		panelCentral.add(panelReproductor);
	}

	public void crearPanelRelleno() {

		JPanel panelRelleno = new JPanel();

		panelRelleno.setBackground(new Color(135, 206, 250));

		setSizePanel(panelRelleno, 200, 20);
		panelCentral.add(panelRelleno);
	}

	private void crearPanelTabla() {

		panelTable = new JPanel();
		panelTable.setBorder(null);
		panelTable.setBackground(new Color(135, 206, 250));
		panelTable.setLayout(new FlowLayout(FlowLayout.CENTER, 5, 5));

		canciones = new LinkedList<Cancion>();
		tableCanciones = new JTable(new CancionesTableModel(canciones));

		tableCanciones.addMouseListener(new MouseAdapter() {

			@Override
			public void mouseClicked(MouseEvent ev) {

				int clicks = ev.getClickCount();
				if (clicks == 2) {
					panelReproductor.setVisible(true);
					miFrame.revalidate();
					miFrame.repaint();

					if (!playing) {

						btnPlay.setIcon(
								new ImageIcon("src\\main\\java\\um\\tds\\appmusic\\gui\\imagenes\\PauseIco.png"));
						panelBotones.revalidate();
						panelBotones.repaint();
					}

					seleccionarCancion(tableCanciones.getSelectedRow());
					sliderReproduccion.setValue(posSlider);
					timer.start();
				}

			}
		});

		scrollPane = new JScrollPane(tableCanciones);
		tableCanciones.setPreferredScrollableViewportSize(new Dimension(300, 250));
		panelTable.add(scrollPane);
		panelTable.setVisible(true);

	}

	private void crearPanelReproductor() {

		panelReproductor = new JPanel();
		panelReproductor.setForeground(new Color(0, 0, 0));
		panelReproductor.setLayout(new BoxLayout(panelReproductor, BoxLayout.Y_AXIS));
		panelReproductor.setBorder(
				new SoftBevelBorder(BevelBorder.LOWERED, new Color(0, 0, 0), new Color(0, 0, 0), null, null));
		panelReproductor.setBackground(new Color(240, 240, 240));

		setSizePanel(panelReproductor, 500, 80);

		crearPanelSliderReproduccion();
		panelReproductor.add(panelSliderReproduccion);

		crearPanelBotones();
		panelReproductor.add(panelBotones);

		panelReproductor.setVisible(true);

	}

	private JPanel crearPanelSliderReproduccion() {

		panelSliderReproduccion = new JPanel();
		panelSliderReproduccion.setBackground(new Color(192, 192, 192));

		Dimension d = new Dimension(500, 30);
		panelSliderReproduccion.setMinimumSize(d);
		panelSliderReproduccion.setMaximumSize(d);
		panelSliderReproduccion.setPreferredSize(d);

		sliderReproduccion = new JSlider();
		sliderReproduccion.setValue(0);
		sliderReproduccion.setBackground(new Color(192, 192, 192));
		sliderReproduccion.setForeground(new Color(192, 192, 192));

		Dimension d1 = new Dimension(440, 20);
		sliderReproduccion.setMinimumSize(d1);
		sliderReproduccion.setMaximumSize(d1);
		sliderReproduccion.setPreferredSize(d1);

		sliderReproduccion.addMouseListener(new MouseListener() {

			@Override
			public void mouseReleased(MouseEvent arg0) {
				posSlider = sliderReproduccion.getValue();
				Controlador.INSTANCE.cambiarPosicionCancion(posSlider);
			}

			@Override
			public void mousePressed(MouseEvent arg0) {

			}

			@Override
			public void mouseExited(MouseEvent arg0) {
				// TODO Auto-generated method stub

			}

			@Override
			public void mouseEntered(MouseEvent arg0) {
				// TODO Auto-generated method stub

			}

			@Override
			public void mouseClicked(MouseEvent arg0) {
				// TODO Auto-generated method stub

			}
		});

		panelSliderReproduccion.add(sliderReproduccion);

		return panelSliderReproduccion;

	}

	private void crearPanelBotones() {

		panelBotones = new JPanel();
		FlowLayout flowLayout_1 = (FlowLayout) panelBotones.getLayout();
		flowLayout_1.setHgap(50);
		flowLayout_1.setVgap(-3);
		panelBotones.setBackground(new Color(192, 192, 192));

		btnSiguiente = new JButton();
		btnSiguiente.setIcon(new ImageIcon("src\\main\\java\\um\\tds\\appmusic\\gui\\imagenes\\NextIco.png"));
		btnSiguiente.setMargin(new Insets(0, 0, 0, 0));
		btnSiguiente.setBorderPainted(false);
		btnSiguiente.setOpaque(true);
		btnSiguiente.setFocusPainted(false);
		btnSiguiente.setContentAreaFilled(false);

		btnSiguiente.addActionListener(ev -> {

			if (playing) {
				Controlador.INSTANCE.pauseSong();
				int row = tableCanciones.getSelectedRow();
				int auxRow = (row + 1) % canciones.size();
				tableCanciones.changeSelection(auxRow, 0, false, false);
				seleccionarCancion(auxRow);
			}

		});

		btnAnterior = new JButton();
		btnAnterior.setIcon(new ImageIcon("src\\main\\java\\um\\tds\\appmusic\\gui\\imagenes\\BeforeIco.png"));
		btnAnterior.setMargin(new Insets(0, 0, 0, 0));
		btnAnterior.setBorderPainted(false);
		btnAnterior.setOpaque(true);
		btnAnterior.setFocusPainted(false);
		btnAnterior.setContentAreaFilled(false);

		btnAnterior.addActionListener(ev -> {

			if (playing) {
				int row = tableCanciones.getSelectedRow();
				int auxRow = row - 1;
				if (auxRow < 0) {
					auxRow = canciones.size() - 1;
				}
				tableCanciones.changeSelection(auxRow, 0, false, false);
				seleccionarCancion(auxRow);
			}

		});

		btnPlay = new JButton();
		btnPlay.setIcon(new ImageIcon("src\\main\\java\\um\\tds\\appmusic\\gui\\imagenes\\PlayIco.png"));
		btnPlay.setMargin(new Insets(0, 0, 0, 0));
		btnPlay.setBorderPainted(false);
		btnPlay.setOpaque(true);
		btnPlay.setFocusPainted(false);
		btnPlay.setContentAreaFilled(false);

		btnPlay.addActionListener(e -> {

			if (playing == false) {
				btnPlay.setIcon(new ImageIcon("src\\main\\java\\um\\tds\\appmusic\\gui\\imagenes\\PauseIco.png"));
				playing = true;
				Controlador.INSTANCE.continueSong();
				timer.start();

			} else {
				btnPlay.setIcon(new ImageIcon("src\\main\\java\\um\\tds\\appmusic\\gui\\imagenes\\PlayIco.png"));
				playing = false;
				Controlador.INSTANCE.pauseSong();
				timer.stop();
			}

			panelBotones.repaint();
			panelBotones.revalidate();

		});

		sliderVolumen = new JSlider();
		sliderVolumen.setMaximum(100);
		sliderVolumen.setMinimum(0);
		sliderVolumen.setValue(sliderVolumen.getMaximum());
		sliderVolumen.setBackground(new Color(192, 192, 192));
		sliderVolumen.setForeground(new Color(192, 192, 192));

		Dimension d = new Dimension(100, 20);
		sliderVolumen.setMinimumSize(d);
		sliderVolumen.setMaximumSize(d);
		sliderVolumen.setPreferredSize(d);

		sliderVolumen.addChangeListener(e -> {

			Controlador.INSTANCE.cambiarVolumenCancion((double) sliderVolumen.getValue() / 100);

		});

		panelBotones.add(btnAnterior);
		panelBotones.add(btnPlay);
		panelBotones.add(btnSiguiente);
		panelBotones.add(sliderVolumen);

	}

	public void seleccionarCancion(int row) {
		Controlador.INSTANCE.finishSong();
		Cancion song = canciones.get(row);
		playing = true;
		posSlider = 0;

		sliderReproduccion.setMaximum(
				(int) Controlador.INSTANCE.playSong(song, (double) sliderVolumen.getValue() / 100).toSeconds());

	}

	public void setSizePanel(JPanel panel, int ancho, int alto) {

		Dimension d = new Dimension(ancho, alto);
		panel.setMinimumSize(d);
		panel.setMaximumSize(d);
		panel.setPreferredSize(d);
	}

	public void setSizeButton(JButton btn, int ancho, int alto) {

		Dimension d = new Dimension(ancho, alto);
		btn.setMinimumSize(d);
		btn.setMaximumSize(d);
		btn.setPreferredSize(d);
	}

	public void eventoFiltrarCanciones(JTable tabla, JTextField txtTit, JTextField txtInt, JComboBox comboEstilo,
			List<Cancion> songs) {
		String titulo = txtTit.getText();
		String interprete = txtInt.getText();
		if (interprete.isEmpty()) {
			interprete = null;
		}

		String estilo = comboEstilo.getSelectedItem().toString();
		if (estilo == "cualquier estilo")
			estilo = null;
		songs.clear();
		songs.addAll(Controlador.INSTANCE.filtrarCanciones(titulo, interprete, estilo));
		tabla.revalidate();
		tabla.repaint();
	}

	public JComboBox crearComboBoxEstilo() {
		JComboBox cmEstilo = new JComboBox();
		Set<String> estilos = Controlador.INSTANCE.obtenerEstilos();
		cmEstilo.setModel(new DefaultComboBoxModel<>(Controlador.INSTANCE.obtenerEstilos().toArray()));
		cmEstilo.addItem("cualquier estilo");
		cmEstilo.setSelectedItem("cualquier estilo");
		return cmEstilo;
	}

}
