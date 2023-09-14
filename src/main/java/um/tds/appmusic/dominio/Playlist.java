package um.tds.appmusic.dominio;

import java.util.ArrayList;
import java.util.List;

public class Playlist {

	private int codigo;
	private String nombre;
	private List<Cancion> canciones;

	public Playlist(String nombre) {
		this.nombre = nombre;
		this.canciones = new ArrayList<Cancion>();
		codigo = 0;
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

	public List<Cancion> getCanciones() {
		return new ArrayList<Cancion>(canciones);
	}

	public void addCancion(Cancion cancion) {
		this.canciones.add(cancion);
	}

	public void removeCancion(Cancion cancion) {
		canciones.remove(canciones.indexOf(cancion));
	}
}
