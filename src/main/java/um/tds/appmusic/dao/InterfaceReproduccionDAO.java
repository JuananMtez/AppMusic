package um.tds.appmusic.dao;

import java.util.List;

import um.tds.appmusic.dominio.Reproduccion;

public interface InterfaceReproduccionDAO {

	void registrarReproduccion(Reproduccion reproduccion);
	boolean borrarReproduccion(Reproduccion reproduccion);
	void modificarReproduccion(Reproduccion reproduccion);
	Reproduccion recuperarReproduccion(int id);
	List<Reproduccion> recuperarTodasReproducciones();

}
