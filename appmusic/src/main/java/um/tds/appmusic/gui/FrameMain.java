package um.tds.appmusic.gui;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JSeparator;

import java.awt.BorderLayout;
import java.awt.Dimension;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;

import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.Insets;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.BoxLayout;
import java.awt.Color;
import java.awt.Component;
import javax.swing.SwingConstants;
import javax.swing.border.SoftBevelBorder;

import um.tds.appmusic.controlador.Controlador;
import umu.tds.componente.Cancion;
import umu.tds.componente.Canciones;
import umu.tds.componente.CargadorCanciones;
import umu.tds.componente.MapperCancionesXMLtoJava;

import javax.swing.border.BevelBorder;
import javax.swing.JFileChooser;

public class FrameMain {

	private JFrame frmVentanaPrincipal;

	private JPanel panelCentral;
	private JPanel panelOeste;
	private JPanel panelNorte;

	private PanelPlaylists frmPlaylist;

	private Panel panel;

	private JPanel panelExplorar;
	private JPanel panelPlaylists;
	private JPanel panelRecientes;
	private JPanel panelEnBucle;
	private JPanel panelNuevaLista;
	private JPanel panelTopExitos;
	private JPanel panelBotones;
	private JPanel panelLogo;
	private JPanel panelRelleno;

	private JButton btnExplorar;
	private JButton btnPlayLists;
	private JButton btnReciente;
	private JButton btnEnBucle;
	private JButton btnNuevaLista;
	private JButton btnTopExitos;

	private JPanel panelListPlayLists;

	private CargadorCanciones cargador;

	private Ventana ventana;
	private JLabel lblLogo;

	public FrameMain() {
		initialize();
	}

	public void mostrarVentana() {
		frmVentanaPrincipal.setLocationRelativeTo(null);
		frmVentanaPrincipal.setVisible(true);
	}

	private void initialize() {

		frmVentanaPrincipal = new JFrame("AppMusic");
		frmVentanaPrincipal.getContentPane().setBackground(new Color(135, 206, 250));
		frmVentanaPrincipal.setBounds(350, 125, 650, 625);
		frmVentanaPrincipal.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frmVentanaPrincipal.setResizable(false);

		anadirMenuBar();
		crearPanelNorte();
		crearPanelOeste();

		panel = FactoriaFrames.INSTANCE.crearPanel("Recientes", frmVentanaPrincipal);
		panel.mostrarComponentes();
		cambiarFondoBoton(panelRecientes, new Color(192, 192, 192));

		ventana = Ventana.RECIENTES;

	}

	private void crearPanelNorte() {

		panelNorte = new JPanel();
		panelNorte.setBorder(new SoftBevelBorder(BevelBorder.LOWERED, null, null, null, null));
		panelNorte.setBackground(new Color(135, 206, 250));
		FlowLayout flowLayout = (FlowLayout) panelNorte.getLayout();
		flowLayout.setHgap(15);
		flowLayout.setAlignment(FlowLayout.LEFT);

		frmVentanaPrincipal.getContentPane().add(panelNorte, BorderLayout.NORTH);

		crearPanelLogo();
		panelNorte.add(panelLogo);

		panelRelleno = new JPanel();
		panelRelleno.setBackground(new Color(135, 206, 250));
		setSizePanel(panelRelleno, 180, 30);
		panelNorte.add(panelRelleno);

	}

	private void crearPanelLogo() {

		panelLogo = new JPanel();
		panelLogo.setBackground(new Color(135, 206, 250));
		FlowLayout flowLayout_1 = (FlowLayout) panelLogo.getLayout();
		flowLayout_1.setAlignment(FlowLayout.LEFT);

		lblLogo = new JLabel(" AppMusic");
		lblLogo.setForeground(new Color(0, 0, 0));
		lblLogo.setFont(new Font("Courier New", Font.BOLD, 21));
		lblLogo.setHorizontalAlignment(SwingConstants.CENTER);
		lblLogo.setIcon(new ImageIcon("src\\main\\java\\um\\tds\\appmusic\\gui\\imagenes\\LogoIco.png"));

		Dimension d = new Dimension(170, 44);
		lblLogo.setMinimumSize(d);
		lblLogo.setMaximumSize(d);
		lblLogo.setPreferredSize(d);
		panelLogo.add(lblLogo);

	}

	private void anadirMenuBar() {

		JMenuBar mb = new JMenuBar();

		JMenu menInicio = new JMenu("Inicio");

		JMenuItem premium = new JMenuItem("Premium");
		JMenuItem noPremium = new JMenuItem("Cancelar Premium");
		JMenuItem pdf = new JMenuItem("Exportar PDF");
		JSeparator sep = new JSeparator();

		premium.addActionListener(ev -> {
			Controlador.INSTANCE.setUsuarioPremium();
			premium.setVisible(false);
			noPremium.setVisible(true);
			pdf.setVisible(true);
			sep.setVisible(true);
			JOptionPane.showMessageDialog(frmVentanaPrincipal, "Usuario ya es premium");

			if (ventana == Ventana.TOP_EXITOS) {

				panel.eliminarComponentes();
				panel = FactoriaFrames.INSTANCE.crearPanel("Top Exitos", frmVentanaPrincipal);
				panel.mostrarComponentes();
			}

		});

		noPremium.addActionListener(ev -> {
			Controlador.INSTANCE.cancelarPremium();
			noPremium.setVisible(false);
			premium.setVisible(true);
			pdf.setVisible(false);
			sep.setVisible(false);
			JOptionPane.showMessageDialog(frmVentanaPrincipal, "Premium cancelado");

			if (ventana == Ventana.TOP_EXITOS) {
				panel.eliminarComponentes();
				panel = FactoriaFrames.INSTANCE.crearPanel("Top Exitos", frmVentanaPrincipal);
				panel.mostrarComponentes();
			}

		});

		pdf.addActionListener(ev -> {

			if (Controlador.INSTANCE.crearPdf())
				JOptionPane.showMessageDialog(frmVentanaPrincipal, "PDF creado correctamente");
			else
				JOptionPane.showMessageDialog(frmVentanaPrincipal, "PDF no se ha creado");

		});

		cargador = new CargadorCanciones(" Añadir Canciones");
		cargador.getJMenuItem()
				.setIcon(new ImageIcon("src\\main\\java\\um\\\\tds\\appmusic\\gui\\imagenes\\addCancionesIco.png"));
		menInicio.add(cargador.getJMenuItem());
		menInicio.add(new JSeparator());

		cargador.addCancionesListeners(ev -> {

			JFileChooser jFileChooser = new JFileChooser();

			int result = jFileChooser.showOpenDialog(new JFrame());

			if (result == JFileChooser.APPROVE_OPTION) {

				System.out.println(jFileChooser.getSelectedFile().toString());

				Canciones canciones = MapperCancionesXMLtoJava
						.cargarCanciones(jFileChooser.getSelectedFile().toString());

				for (Cancion cancion : canciones.getCancion()) {

					boolean registrado = false;
					registrado = Controlador.INSTANCE.registrarCancion(cancion.getTitulo(), cancion.getInterprete(),
							cancion.getEstilo().toLowerCase(), cancion.getURL());

					if (registrado) {
						System.out.println("Cancion: " + cancion.getTitulo() + " registrada correctamente");
					} else {
						System.out.println("No se pudo registrar la cancion: " + cancion.getTitulo());
					}

				}
			}
		});

		JMenuItem desc = new JMenuItem("Desconectarse");

		premium.setIcon(new ImageIcon("src\\main\\java\\um\\tds\\appmusic\\gui\\imagenes\\PremiumIco.png"));
		noPremium.setIcon(new ImageIcon("src\\main\\java\\um\\tds\\appmusic\\gui\\imagenes\\NoPremiumIco.png"));
		pdf.setIcon(new ImageIcon("src\\main\\java\\um\\tds\\appmusic\\gui\\imagenes\\PDFIco.png"));
		desc.addActionListener(ev -> {
			frmVentanaPrincipal.dispose();

		});
		desc.setIcon(new ImageIcon("src\\main\\java\\um\\tds\\appmusic\\gui\\imagenes\\LogoutIco.png"));

		menInicio.add(noPremium);
		menInicio.add(premium);
		menInicio.add(sep);
		menInicio.add(pdf);

		if (Controlador.INSTANCE.isUsuarioPremium()) {
			noPremium.setVisible(true);
			premium.setVisible(false);
			pdf.setVisible(true);
			sep.setVisible(true);

		} else {
			noPremium.setVisible(false);
			premium.setVisible(true);
			pdf.setVisible(false);
			sep.setVisible(false);

		}

		menInicio.add(new JSeparator());
		menInicio.add(desc);

		JMenu menHelp = new JMenu("Help");
		JMenuItem menAcercaDe = new JMenuItem("Acerca de");

		menHelp.add(menAcercaDe);

		menAcercaDe.addActionListener(ev -> {
			AcercaDeDialog acercadeDialogo = new AcercaDeDialog(frmVentanaPrincipal);
			acercadeDialogo.setVisible(true);

		});

		mb.add(menInicio);
		mb.add(menHelp);

		frmVentanaPrincipal.setJMenuBar(mb);
	}

	private void crearPanelOeste() {

		panelOeste = new JPanel();
		panelOeste.setBorder(new BevelBorder(BevelBorder.LOWERED, null, null, null, null));
		panelOeste.setBackground(new Color(255, 160, 122));
		frmVentanaPrincipal.getContentPane().add(panelOeste, BorderLayout.WEST);
		panelOeste.setLayout(new BoxLayout(panelOeste, BoxLayout.Y_AXIS));
		setSizePanel(panelOeste, 150, 300);

		crearPanelBotones();
		panelOeste.add(panelBotones);

		crearPanelListPlayLists();
		panelOeste.add(panelListPlayLists);

	}

	public void crearPanelCentral() {

		panelCentral = new JPanel();
		panelCentral.setBackground(new Color(135, 206, 250));
		frmVentanaPrincipal.getContentPane().add(panelCentral, BorderLayout.CENTER);
		panelCentral.setLayout(new BoxLayout(panelCentral, BoxLayout.Y_AXIS));
	}

	private void crearPanelBotones() {

		panelBotones = new JPanel();
		panelBotones.setBackground(new Color(255, 160, 122));
		panelBotones.setLayout(new BoxLayout(panelBotones, BoxLayout.Y_AXIS));

		crearPanelExplora();
		panelBotones.add(panelExplorar);

		crearPanelTopExitos();
		panelBotones.add(panelTopExitos);

		crearPanelRecientes();
		panelBotones.add(panelRecientes);

		crearPanelEnBucle();
		panelBotones.add(panelEnBucle);

		crearPanelPlaylists();
		panelBotones.add(panelPlaylists);

		crearPanelNuevaPlaylist();
		panelBotones.add(panelNuevaLista);

	}

	private void crearPanelExplora() {

		panelExplorar = new JPanel();
		FlowLayout flowLayout = (FlowLayout) panelExplorar.getLayout();
		flowLayout.setAlignment(FlowLayout.LEFT);
		panelExplorar.setBackground(new Color(255, 160, 122));
		btnExplorar = new JButton("Explorar");
		btnExplorar.setHorizontalAlignment(SwingConstants.LEFT);

		btnExplorar.setIcon(new ImageIcon("src\\main\\java\\um\\tds\\appmusic\\gui\\imagenes\\ExplorarIco.png"));
		btnExplorar.setAlignmentX(Component.LEFT_ALIGNMENT);
		btnExplorar.setMargin(new Insets(0, 0, 0, 0));
		btnExplorar.setBorderPainted(false);
		btnExplorar.setFocusPainted(false);
		btnExplorar.setContentAreaFilled(false);
		setSizeButton(btnExplorar, 145, 40);

		panelExplorar.add(btnExplorar);

		// EVENTO DE BOTON EXPLORAR
		btnExplorar.addActionListener(ev -> {
			eventoBtnExplora();
		});

	}

	private void crearPanelPlaylists() {

		panelPlaylists = new JPanel();
		FlowLayout flowLayout = (FlowLayout) panelPlaylists.getLayout();
		flowLayout.setAlignment(FlowLayout.LEFT);
		panelPlaylists.setBackground(new Color(255, 160, 122));

		btnPlayLists = new JButton("Playlists");
		btnPlayLists.setHorizontalAlignment(SwingConstants.LEFT);

		btnPlayLists.setIcon(new ImageIcon("src\\main\\java\\um\\tds\\appmusic\\gui\\imagenes\\PlaylistsIco.png"));
		btnPlayLists.setAlignmentX(Component.LEFT_ALIGNMENT);
		btnPlayLists.setMargin(new Insets(0, 0, 0, 0));
		btnPlayLists.setBorderPainted(false);
		btnPlayLists.setFocusPainted(false);
		btnPlayLists.setContentAreaFilled(false);

		setSizeButton(btnPlayLists, 145, 40);
		panelPlaylists.add(btnPlayLists);

		// EVENTO DE BOTON PLAYLIST
		btnPlayLists.addActionListener(ev -> {
			eventoBtnPlaylist();
		});

	}

	private void crearPanelRecientes() {

		panelRecientes = new JPanel();
		FlowLayout flowLayout = (FlowLayout) panelRecientes.getLayout();
		flowLayout.setAlignment(FlowLayout.LEFT);
		panelRecientes.setBackground(new Color(255, 160, 122));
		btnReciente = new JButton("Recientes");
		btnReciente.setHorizontalAlignment(SwingConstants.LEFT);
		btnReciente.setIcon(new ImageIcon("src\\main\\java\\um\\tds\\appmusic\\gui\\imagenes\\RecientesIco.png"));
		btnReciente.setAlignmentX(Component.LEFT_ALIGNMENT);
		btnReciente.setMargin(new Insets(0, 0, 0, 0));
		btnReciente.setBorderPainted(false);
		btnReciente.setContentAreaFilled(false);
		btnReciente.setFocusPainted(false);
		setSizeButton(btnReciente, 145, 40);
		panelRecientes.add(btnReciente);

		// EVENTO DE BOTON RECIENTES
		btnReciente.addActionListener(ev -> {
			eventoBtnRecientes();
		});

	}

	private void crearPanelEnBucle() {

		panelEnBucle = new JPanel();
		FlowLayout flowLayout = (FlowLayout) panelEnBucle.getLayout();
		flowLayout.setAlignment(FlowLayout.LEFT);
		panelEnBucle.setBackground(new Color(255, 160, 122));

		btnEnBucle = new JButton("En Bucle");
		btnEnBucle.setHorizontalAlignment(SwingConstants.LEFT);
		btnEnBucle.setIcon(new ImageIcon("src\\main\\java\\um\\tds\\appmusic\\gui\\imagenes\\BucleIco.png"));
		btnEnBucle.setAlignmentX(Component.LEFT_ALIGNMENT);

		btnEnBucle.setMargin(new Insets(0, 0, 0, 0));
		btnEnBucle.setBorderPainted(false);
		btnEnBucle.setContentAreaFilled(false);
		btnEnBucle.setFocusPainted(false);
		setSizeButton(btnEnBucle, 145, 40);
		panelEnBucle.add(btnEnBucle);

		btnEnBucle.addActionListener(ev -> {
			eventoBtnEnBucle();
		});

	}

	private void crearPanelNuevaPlaylist() {

		panelNuevaLista = new JPanel();
		FlowLayout flowLayout = (FlowLayout) panelNuevaLista.getLayout();
		flowLayout.setAlignment(FlowLayout.LEFT);
		panelNuevaLista.setBackground(new Color(255, 160, 122));

		btnNuevaLista = new JButton("Nueva Playlist");
		btnNuevaLista.setHorizontalAlignment(SwingConstants.LEFT);
		btnNuevaLista.setIcon(new ImageIcon("src\\main\\java\\um\\tds\\appmusic\\gui\\imagenes\\NuevaListaIco.png"));
		btnNuevaLista.setAlignmentX(Component.LEFT_ALIGNMENT);

		btnNuevaLista.setMargin(new Insets(0, 0, 0, 0));
		btnNuevaLista.setBorderPainted(false);
		btnNuevaLista.setFocusPainted(false);
		btnNuevaLista.setContentAreaFilled(false);
		setSizeButton(btnNuevaLista, 145, 40);
		panelNuevaLista.add(btnNuevaLista);

		// EVENTO DE BOTON NUEVA LISTA

		btnNuevaLista.addActionListener(ev -> {
			eventoBtnNuevaLista();
		});
	}

	private void crearPanelTopExitos() {

		panelTopExitos = new JPanel();
		FlowLayout flowLayout_1 = (FlowLayout) panelTopExitos.getLayout();
		flowLayout_1.setAlignment(FlowLayout.LEFT);
		panelTopExitos.setBackground(new Color(255, 160, 122));
		btnTopExitos = new JButton("Top Éxitos");
		btnTopExitos.setHorizontalAlignment(SwingConstants.LEFT);
		btnTopExitos.setIcon(new ImageIcon("src\\main\\java\\um\\tds\\appmusic\\gui\\imagenes\\TopIco.png"));
		btnTopExitos.setAlignmentX(Component.LEFT_ALIGNMENT);
		btnTopExitos.setMargin(new Insets(0, 0, 0, 0));
		btnTopExitos.setBorderPainted(false);
		btnTopExitos.setContentAreaFilled(false);
		btnTopExitos.setFocusPainted(false);
		setSizeButton(btnTopExitos, 145, 40);
		panelTopExitos.add(btnTopExitos);

		// EVENTO DE BOTON RECIENTES
		btnTopExitos.addActionListener(ev -> {
			eventoBtnTopExitos();
		});
	}

	private void eventoBtnExplora() {

		Controlador.INSTANCE.finishSong();
		switch (ventana) {
		case EXPLORAR_CANCIONES:
			return;
		case MIS_LISTAS:
			frmPlaylist.ocultarLista();
			frmPlaylist.eliminarComponentes();
			cambiarFondoBoton(panelPlaylists, new Color(255, 160, 122));
			break;
		case RECIENTES:
			cambiarFondoBoton(panelRecientes, new Color(255, 160, 122));
			break;
		case EN_BUCLE:
			cambiarFondoBoton(panelEnBucle, new Color(255, 160, 122));
			break;
		case NUEVA_LISTA:
			cambiarFondoBoton(panelNuevaLista, new Color(255, 160, 122));
			break;
		case TOP_EXITOS:
			cambiarFondoBoton(panelTopExitos, new Color(255, 160, 122));
			break;
		}

		panel.eliminarComponentes();

		panel = FactoriaFrames.INSTANCE.crearPanel("Explora", frmVentanaPrincipal);
		panel.mostrarComponentes();

		cambiarFondoBoton(panelExplorar, new Color(192, 192, 192));
		ventana = Ventana.EXPLORAR_CANCIONES;

	}

	private void eventoBtnPlaylist() {

		Controlador.INSTANCE.finishSong();

		switch (ventana) {
		case EXPLORAR_CANCIONES:
			cambiarFondoBoton(panelExplorar, new Color(255, 160, 122));
			break;
		case MIS_LISTAS:
			return;
		case RECIENTES:
			cambiarFondoBoton(panelRecientes, new Color(255, 160, 122));
			break;
		case EN_BUCLE:
			cambiarFondoBoton(panelEnBucle, new Color(255, 160, 122));
			break;
		case NUEVA_LISTA:
			cambiarFondoBoton(panelNuevaLista, new Color(255, 160, 122));
			break;
		case TOP_EXITOS:
			cambiarFondoBoton(panelTopExitos, new Color(255, 160, 122));
			break;
		}

		panel.eliminarComponentes();

		frmPlaylist = new PanelPlaylists(frmVentanaPrincipal, panelListPlayLists);
		frmPlaylist.mostrarLista();

		frmPlaylist.mostrarComponentes();
		cambiarFondoBoton(panelPlaylists, new Color(192, 192, 192));

		ventana = Ventana.MIS_LISTAS;

	}

	private void eventoBtnRecientes() {

		Controlador.INSTANCE.finishSong();

		switch (ventana) {
		case EXPLORAR_CANCIONES:
			cambiarFondoBoton(panelExplorar, new Color(255, 160, 122));
			break;
		case MIS_LISTAS:
			frmPlaylist.ocultarLista();
			frmPlaylist.eliminarComponentes();
			cambiarFondoBoton(panelPlaylists, new Color(255, 160, 122));
			break;
		case RECIENTES:
			return;
		case EN_BUCLE:
			cambiarFondoBoton(panelEnBucle, new Color(255, 160, 122));
			break;
		case NUEVA_LISTA:
			cambiarFondoBoton(panelNuevaLista, new Color(255, 160, 122));
			break;
		case TOP_EXITOS:
			cambiarFondoBoton(panelTopExitos, new Color(255, 160, 122));
			break;
		}
		panel.eliminarComponentes();

		panel = FactoriaFrames.INSTANCE.crearPanel("Recientes", frmVentanaPrincipal);
		panel.mostrarComponentes();
		cambiarFondoBoton(panelRecientes, new Color(192, 192, 192));

		ventana = Ventana.RECIENTES;

	}

	private void eventoBtnEnBucle() {

		Controlador.INSTANCE.finishSong();

		switch (ventana) {
		case EXPLORAR_CANCIONES:
			cambiarFondoBoton(panelExplorar, new Color(255, 160, 122));
			break;
		case MIS_LISTAS:
			frmPlaylist.ocultarLista();
			frmPlaylist.eliminarComponentes();
			cambiarFondoBoton(panelPlaylists, new Color(255, 160, 122));
			break;
		case RECIENTES:
			cambiarFondoBoton(panelRecientes, new Color(255, 160, 122));
			break;
		case EN_BUCLE:
			return;
		case NUEVA_LISTA:
			cambiarFondoBoton(panelNuevaLista, new Color(255, 160, 122));
			break;
		case TOP_EXITOS:
			cambiarFondoBoton(panelTopExitos, new Color(255, 160, 122));
			break;

		}
		panel.eliminarComponentes();

		panel = FactoriaFrames.INSTANCE.crearPanel("En Bucle", frmVentanaPrincipal);
		panel.mostrarComponentes();

		cambiarFondoBoton(panelEnBucle, new Color(192, 192, 192));
		ventana = Ventana.EN_BUCLE;

	}

	private void eventoBtnNuevaLista() {

		Controlador.INSTANCE.finishSong();

		switch (ventana) {
		case EXPLORAR_CANCIONES:
			cambiarFondoBoton(panelExplorar, new Color(255, 160, 122));
			break;
		case MIS_LISTAS:
			frmPlaylist.ocultarLista();
			frmPlaylist.eliminarComponentes();
			cambiarFondoBoton(panelPlaylists, new Color(255, 160, 122));
			break;
		case RECIENTES:
			cambiarFondoBoton(panelRecientes, new Color(255, 160, 122));
			break;
		case EN_BUCLE:
			cambiarFondoBoton(panelEnBucle, new Color(255, 160, 122));
			break;
		case NUEVA_LISTA:
			return;
		case TOP_EXITOS:
			cambiarFondoBoton(panelTopExitos, new Color(255, 160, 122));
			break;
		}

		panel.eliminarComponentes();

		panel = FactoriaFrames.INSTANCE.crearPanel("Nueva Lista", frmVentanaPrincipal);
		panel.mostrarComponentes();

		cambiarFondoBoton(panelNuevaLista, new Color(192, 192, 192));
		ventana = Ventana.NUEVA_LISTA;
	}

	private void eventoBtnTopExitos() {

		Controlador.INSTANCE.finishSong();

		switch (ventana) {
		case EXPLORAR_CANCIONES:
			cambiarFondoBoton(panelExplorar, new Color(255, 160, 122));
			break;
		case MIS_LISTAS:
			frmPlaylist.ocultarLista();
			frmPlaylist.eliminarComponentes();
			cambiarFondoBoton(panelPlaylists, new Color(255, 160, 122));
			break;
		case RECIENTES:
			cambiarFondoBoton(panelRecientes, new Color(255, 160, 122));
			break;
		case EN_BUCLE:
			cambiarFondoBoton(panelEnBucle, new Color(255, 160, 122));
			break;
		case NUEVA_LISTA:
			cambiarFondoBoton(panelNuevaLista, new Color(255, 160, 122));
			break;
		case TOP_EXITOS:
			return;

		}

		panel.eliminarComponentes();

		panel = FactoriaFrames.INSTANCE.crearPanel("Top Exitos", frmVentanaPrincipal);
		panel.mostrarComponentes();

		cambiarFondoBoton(panelTopExitos, new Color(192, 192, 192));
		ventana = Ventana.TOP_EXITOS;

	}

	private void crearPanelListPlayLists() {

		panelListPlayLists = new JPanel();
		setSizePanel(panelListPlayLists, 180, 300);
		panelListPlayLists.setBackground(new Color(255, 160, 122));
	}

	private void cambiarFondoBoton(JPanel panelBtn, Color color) {

		panelBtn.setBackground(color);

	}

	private void setSizePanel(JPanel panel, int ancho, int alto) {

		Dimension d = new Dimension(ancho, alto);
		panel.setMinimumSize(d);
		panel.setMaximumSize(d);
		panel.setPreferredSize(d);
	}

	private void setSizeButton(JButton btn, int ancho, int alto) {

		Dimension d = new Dimension(ancho, alto);
		btn.setMinimumSize(d);
		btn.setMaximumSize(d);
		btn.setPreferredSize(d);
	}

}
