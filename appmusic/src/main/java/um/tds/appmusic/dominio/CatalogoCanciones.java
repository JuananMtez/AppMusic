package um.tds.appmusic.dominio;

import java.util.*;
import java.util.stream.Collectors;
import um.tds.appmusic.dao.DAOException;
import um.tds.appmusic.dao.FactoriaDAO;

public enum CatalogoCanciones {

	INSTANCE;

	private FactoriaDAO factoria;
	private HashMap<Integer, Cancion> cancionesPorID;
	private HashMap<String, Cancion> cancionesPorTitulo;

	private CatalogoCanciones() {
		cancionesPorID = new HashMap<Integer, Cancion>();
		cancionesPorTitulo = new HashMap<String, Cancion>();

		try {
			factoria = FactoriaDAO.getInstancia();

			List<Cancion> listaCanciones = factoria.getCancionDAO().recuperarTodasCanciones();
			for (Cancion cancion : listaCanciones) {
				cancionesPorID.put(cancion.getCodigo(), cancion);
				cancionesPorTitulo.put(cancion.getTitulo(), cancion);
			}

		} catch (DAOException eDAO) {
			eDAO.printStackTrace();
		}
	}

	public Cancion getCancion(String titulo) {
		return cancionesPorTitulo.get(titulo);
	}
	

	public Cancion getCancion(int codigo) {
		return cancionesPorID.get(codigo);
	}

	public void addCancion(Cancion cancion) {
		cancionesPorID.put(cancion.getCodigo(), cancion);
		cancionesPorTitulo.put(cancion.getTitulo(), cancion);
	}

	public void removeCancion(Cancion cancion) {
		cancionesPorID.remove(cancion.getCodigo());
		cancionesPorTitulo.remove(cancion.getTitulo());
	}

	public Set<String> getAllStyles() {
		return cancionesPorID.values().stream()
				.map(c -> c.getEstilo())
				.collect(Collectors.toSet());
	}

	public List<Cancion> getMasReproducidas() {
		return cancionesPorTitulo.values().stream()
				.sorted(Comparator.comparingInt(Cancion::getNumReproducciones).reversed())
				.limit(10)
				.collect(Collectors.toList());
	}

	public List<Cancion> getMasReproducidasUsuario() {
		return cancionesPorTitulo.values().stream()
				.sorted(Comparator.comparingInt(Cancion::getNumReproducciones).reversed())
				.limit(10)
				.collect(Collectors.toList());
	}

	public List<Cancion> aplicarFiltro(String titulo, String artista, String estilo) {

		List<Cancion> lc = cancionesPorTitulo.values().stream()
				.filter(c -> titulo == null || c.getTitulo().toLowerCase().contains(titulo.toLowerCase()))
				.filter(c -> artista == null || c.getInterprete().toLowerCase().contains(artista.toLowerCase()))
				.filter(c -> estilo == null || c.getEstilo().contentEquals(estilo)).collect(Collectors.toList());

		return lc;
	}

}
