package um.tds.appmusic.dao;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;
import java.util.StringTokenizer;

import beans.Entidad;
import beans.Propiedad;
import tds.driver.FactoriaServicioPersistencia;
import tds.driver.ServicioPersistencia;
import um.tds.appmusic.dominio.Cancion;
import um.tds.appmusic.dominio.Playlist;

public enum AdaptadorPlaylistTDS implements InterfacePlaylistDAO {

	INSTANCE;

	private static final String PLAYLIST = "Playlist";
	private static final String NOMBRE = "nombre";
	private static final String CANCIONES = "canciones";

	private ServicioPersistencia servPersistencia;

	private AdaptadorPlaylistTDS() {
		servPersistencia = FactoriaServicioPersistencia.getInstance().getServicioPersistencia();
	}

	private Entidad playlistToEntidad(Playlist playlist) {
		Entidad ePlaylist = new Entidad();
		ePlaylist.setNombre(PLAYLIST);

		ePlaylist.setPropiedades(new ArrayList<Propiedad>(Arrays.asList(new Propiedad(NOMBRE, playlist.getNombre()),
				new Propiedad(CANCIONES, obtenerCodigosCanciones(playlist.getCanciones())))));

		return ePlaylist;
	}

	private Playlist entidadToPlaylist(Entidad ePlaylist) {

		String nombre = servPersistencia.recuperarPropiedadEntidad(ePlaylist, NOMBRE);

		List<Cancion> canciones = obtenerCancionesDesdeCodigos(
				servPersistencia.recuperarPropiedadEntidad(ePlaylist, CANCIONES));

		Playlist playlist = new Playlist(nombre);
		playlist.setCodigo(ePlaylist.getId());

		for (Cancion cancion : canciones)
			playlist.addCancion(cancion);

		return playlist;
	}

	@Override
	public void registrarPlaylist(Playlist playlist) {
		Entidad ePlaylist = this.playlistToEntidad(playlist);
		ePlaylist = servPersistencia.registrarEntidad(ePlaylist);
		playlist.setCodigo(ePlaylist.getId());
	}

	@Override
	public boolean borrarPlaylist(Playlist playlist) {
		Entidad ePlaylist;
		ePlaylist = servPersistencia.recuperarEntidad(playlist.getCodigo());

		return servPersistencia.borrarEntidad(ePlaylist);
	}

	@Override
	public void modificarPlaylist(Playlist playlist) {
		Entidad ePlaylist = servPersistencia.recuperarEntidad(playlist.getCodigo());
		servPersistencia.eliminarPropiedadEntidad(ePlaylist, CANCIONES);
		servPersistencia.anadirPropiedadEntidad(ePlaylist, CANCIONES, obtenerCodigosCanciones(playlist.getCanciones()));
	}

	@Override
	public Playlist recuperarPlaylist(int id) {
		Entidad ePlaylist = servPersistencia.recuperarEntidad(id);
		return entidadToPlaylist(ePlaylist);
	}

	@Override
	public List<Playlist> recuperarTodasPlaylist() {
		List<Entidad> entidades = servPersistencia.recuperarEntidades(PLAYLIST);

		List<Playlist> playlists = new LinkedList<Playlist>();
		for (Entidad ePlaylist : entidades) {
			playlists.add(recuperarPlaylist(ePlaylist.getId()));
		}

		return playlists;
	}

	private List<Cancion> obtenerCancionesDesdeCodigos(String lineas) {

		List<Cancion> canciones = new ArrayList<Cancion>();

		if (lineas != null) {
			StringTokenizer strTok = new StringTokenizer(lineas, " ");
			AdaptadorCancionTDS adaptadorCancion = AdaptadorCancionTDS.INSTANCE;
			while (strTok.hasMoreTokens()) {
				canciones.add(adaptadorCancion.recuperarCancion(Integer.valueOf((String) strTok.nextElement())));
			}
		}
		return canciones;
	}

	private String obtenerCodigosCanciones(List<Cancion> canciones) {

		String lineas = "";

		for (Cancion cancion : canciones)
			lineas += cancion.getCodigo() + " ";

		return lineas.trim();

	}

}