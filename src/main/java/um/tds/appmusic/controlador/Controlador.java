package um.tds.appmusic.controlador;

import um.tds.appmusic.dao.DAOException;
import um.tds.appmusic.dao.FactoriaDAO;
import um.tds.appmusic.dao.InterfaceCancionDAO;
import um.tds.appmusic.dao.InterfacePlaylistDAO;
import um.tds.appmusic.dao.InterfaceReproduccionDAO;
import um.tds.appmusic.dao.InterfaceUsuarioDAO;
import um.tds.appmusic.dominio.Cancion;
import um.tds.appmusic.dominio.CatalogoCanciones;
import um.tds.appmusic.dominio.CatalogoUsuarios;
import um.tds.appmusic.dominio.Descuento;
import um.tds.appmusic.dominio.DescuentoAncianos;
import um.tds.appmusic.dominio.DescuentoGeneral;
import um.tds.appmusic.dominio.Playlist;
import um.tds.appmusic.dominio.Reproduccion;
import um.tds.appmusic.dominio.Reproductor;
import um.tds.appmusic.dominio.Usuario;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.util.*;
import java.util.function.Supplier;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

import javax.swing.JFileChooser;
import javax.swing.JFrame;

import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.pdf.PdfWriter;

import javafx.util.Duration;

public enum Controlador {

	INSTANCE;

	private Usuario usuarioActual;
	private FactoriaDAO factoria;
	private Reproductor reproductor;

	private static final File RAIZ = new File("C:\\tds\\canciones");

	private Controlador() {
		usuarioActual = null;

		try {
			factoria = FactoriaDAO.getInstancia();
			reproductor = Reproductor.INSTANCE;
		} catch (DAOException e) {
			e.printStackTrace();
		}
	}

	public Usuario getUsuarioActual() {
		return usuarioActual;
	}

	public boolean esUsuarioRegistrado(String login) {
		return CatalogoUsuarios.INSTANCE.getUsuario(login) != null;
	}

	public boolean esCancionRegistrada(String titulo) {
		return CatalogoCanciones.INSTANCE.getCancion(titulo) != null;
	}

	public boolean loginUsuario(String nombre, String password) {
		Usuario usuario = CatalogoUsuarios.INSTANCE.getUsuario(nombre);
		if (usuario != null && usuario.getPassword().equals(password)) {
			this.usuarioActual = usuario;

			Arrays.asList(RAIZ.listFiles()).stream().filter(f -> f.isDirectory())
					.forEach(f -> cargarCanciones(f.getName(), f));

			return true;
		}
		return false;
	}

	private void cargarCanciones(String estilo, File fichero) {

		String regex = "(.+)( - )(.+)(\\.mp3)";
		Pattern pat = Pattern.compile(regex);

		for (File ficheroEntrada : fichero.listFiles()) {
			if (!ficheroEntrada.isDirectory()) {

				Matcher mat = pat.matcher(ficheroEntrada.getName());
				if (mat.find()) {
					registrarCancion(mat.group(3), mat.group(1), fichero.getName().toLowerCase(),
							ficheroEntrada.toString());
				}
			}
		}

	}

	public List<Cancion> obtenerCancionesMasEscuchadas() {
		return CatalogoCanciones.INSTANCE.getMasReproducidas();
	}

	public List<Cancion> obtenerCancionesMasEscuchadasUsuario() {
		return usuarioActual.getReproducciones().stream()
				.sorted(Comparator.comparingInt(Reproduccion::getNumReproducidas).reversed()).map(r -> r.getCancion())
				.limit(10).collect(Collectors.toList());

	}

	public Duration playSong(Cancion cancion, double volumen) {

		InterfaceReproduccionDAO reproduccionDAO = factoria.getReproduccionDAO();

		Reproduccion repr = usuarioActual.addReproduccion(cancion);

		if (repr.getNumReproducidas() > 1)
			reproduccionDAO.modificarReproduccion(repr);

		else
			reproduccionDAO.registrarReproduccion(repr);

		InterfaceUsuarioDAO usuarioDAO = factoria.getUsuarioDAO();
		usuarioDAO.modificarUsuario(usuarioActual);

		InterfaceCancionDAO cancionDAO = factoria.getCancionDAO();
		cancionDAO.modificarCancion(cancion);

		return reproductor.reproducirCancion(cancion, volumen);

	}

	public void pauseSong() {
		reproductor.pausarCancion();
	}

	public void continueSong() {
		reproductor.continuarCancion();
	}

	public void finishSong() {
		reproductor.terminarCancion();
	}

	public void cambiarPosicionCancion(int posicion) {
		reproductor.cambiarPosicionCancion(posicion);
	}

	public void cambiarVolumenCancion(Double volumen) {
		reproductor.cambiarVolumenCancion(volumen);
	}

	public boolean registrarUsuario(String nombre, String apellidos, String email, String login, String password,
			String fechaNacimiento) {

		if (esUsuarioRegistrado(login))
			return false;

		Usuario usuario = new Usuario(nombre, apellidos, email, login, password, fechaNacimiento, false);

		InterfaceUsuarioDAO usuarioDAO = factoria.getUsuarioDAO();
		usuarioDAO.registrarUsuario(usuario);

		CatalogoUsuarios.INSTANCE.addUsuario(usuario);
		return true;
	}

	public Playlist crearPlaylist(String nombrePlaylist) {

		Playlist nuevaLista;
		if ((nuevaLista = usuarioActual.crearNuevaPlaylist(nombrePlaylist)) != null) {

			InterfacePlaylistDAO playlistDAO = factoria.getPlaylistDAO();
			playlistDAO.registrarPlaylist(nuevaLista);

			InterfaceUsuarioDAO usuarioDAO = factoria.getUsuarioDAO();
			usuarioDAO.modificarUsuario(usuarioActual);
			return nuevaLista;
		}
		return null;
	}

	public Playlist playlistExistente(String nombrePlaylist) {
		return usuarioActual.getPlaylist(nombrePlaylist);
	}

	public List<Playlist> obtenerPlaylistUsuario() {
		return usuarioActual.getPlaylists();
	}

	public List<Cancion> obtenerCancionesPlaylistUsuario(Playlist playlist) {
		return usuarioActual.getCancionesPlaylist(playlist);
	}

	public List<Cancion> obtenerCancionesRecientes() {
		return usuarioActual.getRecientes();
	}

	public void registrarCancionPlaylistUsuario(Playlist plist, Cancion cancion) {

		Playlist plaux = usuarioActual.addSongToPlaylist(plist, cancion);

		InterfacePlaylistDAO playlistDAO = factoria.getPlaylistDAO();
		playlistDAO.modificarPlaylist(plaux);
	}

	public void removeCancionPlaylistUsuario(Playlist plist, Cancion cancion) {

		Playlist plaux = usuarioActual.removeSongFromPlaylist(plist, cancion);

		InterfacePlaylistDAO playlistDAO = factoria.getPlaylistDAO();
		playlistDAO.modificarPlaylist(plaux);
	}

	public boolean registrarCancion(String titulo, String interprete, String estilo, String url) {
		if (esCancionRegistrada(titulo)) {
			System.out.println("Cancion: " + titulo + " ya esta registrada");
			return false;
		}

		System.out.println("Registrando: " + titulo);
		Cancion cancion = new Cancion(titulo, interprete, estilo, url);

		InterfaceCancionDAO cancionDAO = factoria.getCancionDAO();
		cancionDAO.registrarCancion(cancion);

		CatalogoCanciones.INSTANCE.addCancion(cancion);
		return true;
	}

	public boolean borrarUsuario(Usuario usuario) {
		if (!esUsuarioRegistrado(usuario.getLogin()))
			return false;

		InterfaceUsuarioDAO usuarioDAO = factoria.getUsuarioDAO();
		usuarioDAO.borrarUsuario(usuario);

		CatalogoUsuarios.INSTANCE.removeUsuario(usuario);
		return true;
	}

	public List<Cancion> mostrarCancionesTabla() {

		InterfaceCancionDAO cancionDAO = factoria.getCancionDAO();
		return cancionDAO.recuperarTodasCanciones();
	}

	public Set<String> obtenerEstilos() {
		return CatalogoCanciones.INSTANCE.getAllStyles();
	}

	public List<Cancion> filtrarCanciones(String titulo, String interprete, String estilo) {
		return CatalogoCanciones.INSTANCE.aplicarFiltro(titulo, interprete, estilo);
	}

	public void eliminarPlaylist(Playlist playlist) {

		Playlist plist = usuarioActual.eliminarPlaylist(playlist);

		InterfacePlaylistDAO playlistDAO = factoria.getPlaylistDAO();
		playlistDAO.borrarPlaylist(plist);

		InterfaceUsuarioDAO usuarioDAO = factoria.getUsuarioDAO();
		usuarioDAO.modificarUsuario(usuarioActual);

	}

	public void setUsuarioPremium() {

		Supplier<Descuento> descuentosSupplier;

		if (usuarioActual.calcularEdad() >= 65)
			descuentosSupplier = () -> new DescuentoAncianos();
		else
			descuentosSupplier = () -> new DescuentoGeneral();

		usuarioActual.setSupplierDescuento(descuentosSupplier);

		usuarioActual.cambiarPremium();

		InterfaceUsuarioDAO usuarioDAO = factoria.getUsuarioDAO();
		usuarioDAO.modificarUsuario(usuarioActual);
	}

	public void cancelarPremium() {
		usuarioActual.cancelarPremium();
		InterfaceUsuarioDAO usuarioDAO = factoria.getUsuarioDAO();
		usuarioDAO.modificarUsuario(usuarioActual);
	}

	public boolean isUsuarioPremium() {
		return usuarioActual.isPremium();
	}

	public boolean crearPdf() {

		JFileChooser jFileChooser = new JFileChooser();

		jFileChooser.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);

		int result = jFileChooser.showOpenDialog(new JFrame());

		if (result == JFileChooser.APPROVE_OPTION) {

			FileOutputStream archivo;
			try {

				archivo = new FileOutputStream(
						jFileChooser.getSelectedFile().toString() + "\\Playlists del usuario.pdf");

				Document documento = new Document();
				PdfWriter.getInstance(documento, archivo);
				documento.open();

				usuarioActual.getPlaylists().stream().forEach(p -> {

					try {

						documento.add(new Paragraph("NOMBRE DE LA LISTA: " + p.getNombre()
								+ "\n---------------------------------------------------------"));
						p.getCanciones().stream().forEach(c -> {

							try {
								documento.add(new Paragraph("Título: " + c.getTitulo() + "\nIntérprete: "
										+ c.getInterprete() + "\nEstilo: " + c.getEstilo() + "\n\n"));
							} catch (DocumentException e) {
								e.printStackTrace();
							}
						}

						);

						documento.add(new Paragraph("---------------------------------------------------------\n\n"));
					} catch (DocumentException e) {
						e.printStackTrace();
					}

				});

				documento.close();
			} catch (FileNotFoundException | DocumentException e) {
				e.printStackTrace();
			}
			return true;
		}
		return false;
	}
}