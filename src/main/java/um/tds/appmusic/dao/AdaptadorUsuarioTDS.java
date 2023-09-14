package um.tds.appmusic.dao;


import java.util.*;

import beans.Entidad;
import beans.Propiedad;
import um.tds.appmusic.dominio.Playlist;
import um.tds.appmusic.dominio.Reproduccion;
import um.tds.appmusic.dominio.Usuario;
import um.tds.appmusic.dominio.Cancion;
import tds.driver.FactoriaServicioPersistencia;
import tds.driver.ServicioPersistencia;


public enum AdaptadorUsuarioTDS implements InterfaceUsuarioDAO {

	INSTANCE;
	
	private ServicioPersistencia servPersistencia;

	private static final String USUARIO = "Usuario";

	private static final String NOMBRE = "nombre";
	private static final String APELLIDOS = "apellidos";
	private static final String EMAIL = "email";
	private static final String LOGIN = "login";
	private static final String PASSWORD = "password";
	private static final String FECHA_NACIMIENTO = "fechaNacimiento";
	private static final String PREMIUM = "premium";

	private static final String PLAYLISTS = "playlists";
	private static final String REPRODUCCIONES = "reproducciones";
	private static final String RECIENTES = "recientes";

	private AdaptadorUsuarioTDS() {
		servPersistencia = FactoriaServicioPersistencia.getInstance().getServicioPersistencia();
	}

	private Usuario entidadToUsuario(Entidad eUsuario) {

		String nombre = servPersistencia.recuperarPropiedadEntidad(eUsuario, NOMBRE);
		String apellidos = servPersistencia.recuperarPropiedadEntidad(eUsuario, APELLIDOS);
		String email = servPersistencia.recuperarPropiedadEntidad(eUsuario, EMAIL);
		String login = servPersistencia.recuperarPropiedadEntidad(eUsuario, LOGIN);
		String password = servPersistencia.recuperarPropiedadEntidad(eUsuario, PASSWORD);
		String fechaNacimiento = servPersistencia.recuperarPropiedadEntidad(eUsuario, FECHA_NACIMIENTO);

		boolean premium = Boolean.parseBoolean(servPersistencia.recuperarPropiedadEntidad(eUsuario, PREMIUM));

		List<Playlist> playlists = obtenerPlaylistsDesdeCodigos(
				servPersistencia.recuperarPropiedadEntidad(eUsuario, PLAYLISTS));

		List<Reproduccion> reproducciones = obtenerReproduccionesDesdeCodigos(
				servPersistencia.recuperarPropiedadEntidad(eUsuario, REPRODUCCIONES));

		List<Cancion> recientes = obtenerRecientesDesdeCodigos(
				servPersistencia.recuperarPropiedadEntidad(eUsuario, RECIENTES));

		Usuario usuario = new Usuario(nombre, apellidos, email, login, password, fechaNacimiento, premium);
		usuario.setCodigo(eUsuario.getId());

		for (Playlist playlist : playlists) {
			usuario.addPlaylist(playlist);
		}

		for (Reproduccion reproduccion : reproducciones)
			usuario.addReproduccion(reproduccion);

		for (Cancion cancion : recientes)
			usuario.addReciente(cancion);

		return usuario;
	}

	private Entidad usuarioToEntidad(Usuario usuario) {
		Entidad eUsuario = new Entidad();
		eUsuario.setNombre(USUARIO);

		eUsuario.setPropiedades(new ArrayList<Propiedad>(Arrays.asList(new Propiedad(NOMBRE, usuario.getNombre()),
				new Propiedad(APELLIDOS, usuario.getApellidos()), new Propiedad(EMAIL, usuario.getEmail()),
				new Propiedad(LOGIN, usuario.getLogin()), new Propiedad(PASSWORD, usuario.getPassword()),
				new Propiedad(FECHA_NACIMIENTO, usuario.getFechaNacimiento()),
				new Propiedad(PREMIUM, String.valueOf(usuario.isPremium())),
				new Propiedad(PLAYLISTS, obtenerCodigosPlaylist(usuario.getPlaylists())),
				new Propiedad(REPRODUCCIONES, obtenerCodigosReproduccion(usuario.getReproducciones())),
				new Propiedad(RECIENTES, obtenerCodigosRecientes(usuario.getRecientes())))));

		return eUsuario;
	}

	@Override
	public void registrarUsuario(Usuario usuario) {
		Entidad eUsuario = this.usuarioToEntidad(usuario);
		eUsuario = servPersistencia.registrarEntidad(eUsuario);
		usuario.setCodigo(eUsuario.getId());
	}

	@Override
	public boolean borrarUsuario(Usuario usuario) {
		Entidad eUsuario;
		eUsuario = servPersistencia.recuperarEntidad(usuario.getCodigo());

		return servPersistencia.borrarEntidad(eUsuario);
	}

	@Override
	public Usuario recuperarUsuario(int id) {
		Entidad eUsuario = servPersistencia.recuperarEntidad(id);
		return entidadToUsuario(eUsuario);
	}

	@Override
	public void modificarUsuario(Usuario usuario) {
		Entidad eUsuario = servPersistencia.recuperarEntidad(usuario.getCodigo());

		servPersistencia.eliminarPropiedadEntidad(eUsuario, RECIENTES);
		servPersistencia.anadirPropiedadEntidad(eUsuario, RECIENTES, obtenerCodigosRecientes(usuario.getRecientes()));

		servPersistencia.eliminarPropiedadEntidad(eUsuario, PREMIUM);
		servPersistencia.anadirPropiedadEntidad(eUsuario, PREMIUM, String.valueOf(usuario.isPremium()));

		servPersistencia.eliminarPropiedadEntidad(eUsuario, REPRODUCCIONES);
		servPersistencia.anadirPropiedadEntidad(eUsuario, REPRODUCCIONES,
				obtenerCodigosReproduccion(usuario.getReproducciones()));

		servPersistencia.eliminarPropiedadEntidad(eUsuario, PLAYLISTS);
		servPersistencia.anadirPropiedadEntidad(eUsuario, PLAYLISTS, obtenerCodigosPlaylist(usuario.getPlaylists()));

	}

	@Override
	public List<Usuario> recuperarTodosUsuario() {
		List<Entidad> entidades = servPersistencia.recuperarEntidades(USUARIO);

		List<Usuario> usuarios = new LinkedList<Usuario>();
		for (Entidad eUsuario : entidades) {
			usuarios.add(recuperarUsuario(eUsuario.getId()));
		}

		return usuarios;
	}

	private String obtenerCodigosPlaylist(List<Playlist> playlists) {

		String lineas = "";

		for (Playlist playlist : playlists)
			lineas += playlist.getCodigo() + " ";

		return lineas.trim();

	}

	private String obtenerCodigosReproduccion(List<Reproduccion> reproducciones) {

		String lineas = "";

		for (Reproduccion reproduccion : reproducciones)
			lineas += reproduccion.getCodigo() + " ";

		return lineas.trim();

	}

	private String obtenerCodigosRecientes(List<Cancion> recientes) {

		String lineas = "";

		for (Cancion cancion : recientes)
			lineas += cancion.getCodigo() + " ";
		return lineas.trim();

	}

	private List<Playlist> obtenerPlaylistsDesdeCodigos(String lineas) {

		List<Playlist> playlists = new ArrayList<Playlist>();
		if (lineas != null) {
			StringTokenizer strTok = new StringTokenizer(lineas, " ");
			AdaptadorPlaylistTDS adaptadorPlaylist = AdaptadorPlaylistTDS.INSTANCE;
			while (strTok.hasMoreTokens()) {
				playlists.add(adaptadorPlaylist.recuperarPlaylist(Integer.valueOf((String) strTok.nextElement())));
			}
		}
		return playlists;
	}

	private List<Cancion> obtenerRecientesDesdeCodigos(String lineas) {

		List<Cancion> recientes = new ArrayList<Cancion>();
		if (lineas != null) {
			StringTokenizer strTok = new StringTokenizer(lineas, " ");
			AdaptadorCancionTDS adaptadorCancion = AdaptadorCancionTDS.INSTANCE;
			while (strTok.hasMoreTokens()) {
				recientes.add(adaptadorCancion.recuperarCancion((Integer.valueOf((String) strTok.nextElement()))));
			}
		}
		return recientes;
	}

	private List<Reproduccion> obtenerReproduccionesDesdeCodigos(String lineas) {

		List<Reproduccion> reproducciones = new ArrayList<Reproduccion>();

		if (lineas != null) {
			StringTokenizer strTok = new StringTokenizer(lineas, " ");
			AdaptadorReproduccionTDS adaptadorP = AdaptadorReproduccionTDS.INSTANCE;
			while (strTok.hasMoreTokens()) {
				reproducciones.add(adaptadorP.recuperarReproduccion(Integer.valueOf((String) strTok.nextElement())));
			}
		}
		return reproducciones;
	}
}