package um.tds.appmusic.dao;

import java.util.List;
import um.tds.appmusic.dominio.Cancion;

public interface InterfaceCancionDAO {

	void registrarCancion(Cancion cancion);
	boolean borrarCancion(Cancion cancion);
	void modificarCancion(Cancion cancion);
	Cancion recuperarCancion(int id);
	List<Cancion> recuperarTodasCanciones();

}
