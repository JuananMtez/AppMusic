package um.tds.appmusic.gui;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.border.EtchedBorder;
import javax.swing.border.TitledBorder;

import java.util.*;

import um.tds.appmusic.controlador.Controlador;
import um.tds.appmusic.dominio.Playlist;
import um.tds.appmusic.dominio.Cancion;

public class PanelPlaylists extends Panel {

	private JPanel panelListPlayLists;
	private JList<String> listaPLists;
	List<Playlist> listas;
	JButton btnEliminar;
	JScrollPane scroller;
	JPanel panelEliminar;
	int listSelected;

	public PanelPlaylists(JFrame miFrame, JPanel panelListPlayLists) {

		super(miFrame, Ventana.MIS_LISTAS);
		this.panelListPlayLists = panelListPlayLists;

		addCompPanelListPlayLists();

		addPanelEliminar();
		super.addPanelCentral();

		btnEliminar.setVisible(false);
		panelTable.setVisible(true);

	}

	private void addPanelEliminar() {
		panelEliminar = new JPanel();
		panelEliminar.setBackground(new Color(135, 206, 250));
		panelEliminar.setLayout(new FlowLayout(FlowLayout.RIGHT, 5, 5));
		setSizePanel(panelEliminar, 500, 50);

		btnEliminar = new JButton("Eliminar");

		btnEliminar.addActionListener(ev -> {
			Controlador.INSTANCE.eliminarPlaylist(listas.get(listSelected));

			listas.remove(listSelected);
			listaPLists.revalidate();
			listaPLists.repaint();

			panelCentral.removeAll();
			crearPanelRelleno();
			addPanelEliminar();
			btnEliminar.setVisible(false);
			super.addPanelCentral();

			panelCentral.revalidate();
			panelCentral.repaint();

		});

		panelEliminar.add(btnEliminar);
		panelCentral.add(panelEliminar);
	}

	private void addCompPanelListPlayLists() {

		crearScrollLista();
		panelListPlayLists.add(scroller);

	}

	private void crearScrollLista() {

		listaPLists = new JList<String>();
		listaPLists.setFont(new Font("Californian FB", Font.BOLD, 14));
		listaPLists.setBorder(new TitledBorder(
				new EtchedBorder(EtchedBorder.LOWERED, new Color(255, 255, 255), new Color(160, 160, 160)),
				"Mis Playlists", TitledBorder.LEADING, TitledBorder.TOP, null, new Color(0, 0, 0)));
		listaPLists.setSelectedIndex(0);

		listas = Controlador.INSTANCE.obtenerPlaylistUsuario();
		listaPLists.setModel(new PlaylistListModel(listas));

		scroller = new JScrollPane(listaPLists);

		Dimension d = new Dimension(147, 186);
		scroller.setMinimumSize(d);
		scroller.setMaximumSize(d);
		scroller.setPreferredSize(d);
		scroller.setVisible(true);

		listaPLists.addMouseListener(new MouseAdapter() {

			public void mouseClicked(MouseEvent ev) {
				btnEliminar.setVisible(true);
				int clicks = ev.getClickCount();
				if (clicks == 1) {
					listSelected = listaPLists.getSelectedIndex();
					canciones.clear();
					canciones.addAll(Controlador.INSTANCE.obtenerCancionesPlaylistUsuario(listas.get(listSelected)));

					for (Cancion cancion : canciones) {
						System.out.println(cancion.getTitulo());
					}
					tableCanciones.repaint();
					tableCanciones.revalidate();
					panelCentral.revalidate();
					panelCentral.repaint();
				}

			}
		});

	}

	public void ocultarLista() {
		scroller.setVisible(false);
	}

	public void mostrarLista() {
		scroller.setVisible(true);
	}

}