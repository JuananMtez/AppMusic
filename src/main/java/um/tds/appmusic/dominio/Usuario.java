package um.tds.appmusic.dominio;


import java.time.LocalDate;
import java.time.Period;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Locale;
import java.util.function.Supplier;

public class Usuario {

	private int codigo;
	private String nombre;
	private String apellidos;
	private String email;
	private String login;
	private String password;
	private String fechaNacimiento;
	private boolean premium;
	private Supplier<Descuento> supplierDescuento;

	private List<Playlist> playlists;

	// En bucle
	private List<Reproduccion> reproducciones;

	// Recientes
	private List<Cancion> recientes;

	public Usuario(String nombre, String apellidos, String email, String login, String password, String fechaNacimiento,
			boolean premium) {
		this.codigo = 0;
		this.nombre = nombre;
		this.apellidos = apellidos;
		this.email = email;
		this.login = login;
		this.password = password;
		this.fechaNacimiento = fechaNacimiento;
		this.playlists = new ArrayList<Playlist>();
		this.reproducciones = new ArrayList<Reproduccion>();
		this.recientes = new LinkedList<Cancion>();
		this.premium = premium;
	}

	public void setSupplierDescuento(Supplier<Descuento> supplier) {
		this.supplierDescuento = supplier;
	}

	public void realizarPago() {

		System.out.println("El usuario dispone del descuento " + supplierDescuento.get().getClass().getSimpleName()
				+ " -> " + "Se reduce del precio total un " + supplierDescuento.get().calcDescuento());

	}

	public boolean esPlaylistRegistrada(String nombrePlaylist) {
		boolean registrada = playlists.stream()
				.map(p -> p.getNombre().toLowerCase())
				.anyMatch(p -> p.equals(nombrePlaylist.toLowerCase()));
		return registrada;
	}

	public Playlist crearNuevaPlaylist(String nombrePlaylist) {

		if (esPlaylistRegistrada(nombrePlaylist))
			return null;

		Playlist nuevaPlaylist = new Playlist(nombrePlaylist);
		playlists.add(nuevaPlaylist);
		return nuevaPlaylist;
	}

	public int getCodigo() {
		return codigo;
	}

	public void setCodigo(int codigo) {
		this.codigo = codigo;
	}

	public String getNombre() {
		return nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	public String getApellidos() {
		return apellidos;
	}

	public void setApellidos(String apellidos) {
		this.apellidos = apellidos;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getLogin() {
		return login;
	}

	public void setLogin(String login) {
		this.login = login;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getFechaNacimiento() {
		return fechaNacimiento;
	}

	public void setFechaNacimiento(String fechaNacimiento) {
		this.fechaNacimiento = fechaNacimiento;
	}

	public List<Playlist> getPlaylists() {
		return new ArrayList<Playlist>(playlists);
	}

	public Playlist getPlaylist(String nombre) {
		Playlist plist = playlists.stream()
				.filter(p -> p.getNombre().toLowerCase().equals(nombre.toLowerCase()))
				.findAny().orElse(null);
		
		return plist;
	}

	public Playlist eliminarPlaylist(Playlist playlist) {

		Playlist borrar = playlists.stream()
				.filter(p -> p.getCodigo() == playlist.getCodigo())
				.findAny()
				.orElse(null);

		playlists.remove(borrar);
		return borrar;
	}

	public void setPlaylists(List<Playlist> playlists) {
		this.playlists = playlists;
	}

	public List<Reproduccion> getReproducciones() {
		return new ArrayList<Reproduccion>(reproducciones);
	}

	public void addPlaylist(Playlist p) {
		playlists.add(p);
	}

	public void addReproduccion(Reproduccion r) {
		reproducciones.add(r);
	}

	public List<Cancion> getRecientes() {
		return recientes;
	}

	public boolean isPremium() {
		return premium;
	}

	public void setPremium(boolean premium) {
		this.premium = premium;
	}

	public boolean isCancionInRecientes(Cancion cancion) {
		return recientes.stream()
				.anyMatch(c -> c.getCodigo() == cancion.getCodigo());
	}

	public void addReciente(Cancion cancion) {

		if (!isCancionInRecientes(cancion)) {
			if (recientes.size() == 10)
				recientes.remove(0);
			recientes.add(cancion);
		}
	}

	public Playlist addSongToPlaylist(Playlist plist, Cancion cancion) {
		Playlist auxList = playlists.get(playlists.indexOf(plist));
		auxList.addCancion(cancion);
		return auxList;
	}

	public Playlist removeSongFromPlaylist(Playlist plist, Cancion cancion) {
		Playlist auxList = playlists.get(playlists.indexOf(plist));
		auxList.removeCancion(cancion);
		return auxList;
	}

	public List<Cancion> getCancionesPlaylist(Playlist playlist) {
		if (playlists.contains(playlist)) {
			return new ArrayList<Cancion>(playlist.getCanciones());
		}
		return null;
	}

	public Reproduccion addReproduccion(Cancion cancion) {

		Reproduccion repr = null;

		if (existeReproduccion(cancion)) {
			repr = getReproduccion(cancion);
			repr.aumentarReproduccion();

		} else {

			repr = new Reproduccion(cancion);
			repr.aumentarReproduccion();
			reproducciones.add(repr);
		}

		addReciente(cancion);

		return repr;
	}

	public boolean existeReproduccion(Cancion cancion) {
		return reproducciones.stream()
				.map(r -> r.getCancion())
				.anyMatch(c -> c.getCodigo() == cancion.getCodigo());
	}

	public Reproduccion getReproduccion(Cancion cancion) {
		return reproducciones.stream()
				.filter(r -> r.getCancion().getCodigo() == cancion.getCodigo())
				.findAny()
				.orElse(null);
	}

	public void cambiarPremium() {
		realizarPago();
		premium = true;
	}

	public void cancelarPremium() {
		premium = false;
	}

	public int calcularEdad() {

		DateTimeFormatter fmt = DateTimeFormatter.ofPattern("EEE MMM dd HH:mm:ss zzz yyyy", Locale.ENGLISH);
		LocalDate fechaNac = LocalDate.parse(fechaNacimiento, fmt);
		LocalDate ahora = LocalDate.now();

		Period periodo = Period.between(fechaNac, ahora);
		return periodo.getYears();

	}
}
