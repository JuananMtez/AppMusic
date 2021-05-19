package um.tds.appmusic.dao;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;

import beans.Entidad;
import beans.Propiedad;
import tds.driver.FactoriaServicioPersistencia;
import tds.driver.ServicioPersistencia;
import um.tds.appmusic.dominio.Cancion;
import um.tds.appmusic.dominio.Reproduccion;

public enum AdaptadorReproduccionTDS implements InterfaceReproduccionDAO {

	INSTANCE;

	private ServicioPersistencia servPersistencia;

	private static final String REPRODUCCION = "Reproduccion";

	private static final String CANCION = "cancion";
	private static final String NUMREPRODUCCIONES = "numReproducciones";

	private AdaptadorReproduccionTDS() {
		servPersistencia = FactoriaServicioPersistencia.getInstance().getServicioPersistencia();
	}

	private Reproduccion entidadToReproduccion(Entidad eReproduccion) {

		AdaptadorCancionTDS adaptadorCancion = AdaptadorCancionTDS.INSTANCE;
		int codigoCancion = Integer.parseInt(servPersistencia.recuperarPropiedadEntidad(eReproduccion, CANCION));
		Cancion cancion = adaptadorCancion.recuperarCancion(codigoCancion);

		String numReproducciones = servPersistencia.recuperarPropiedadEntidad(eReproduccion, NUMREPRODUCCIONES);

		Reproduccion reproduccion = new Reproduccion(cancion);
		reproduccion.setCodigo(eReproduccion.getId());
		reproduccion.setNumReproducidas(Integer.parseInt(numReproducciones));

		return reproduccion;
	}

	private Entidad reproduccionToEntidad(Reproduccion reproduccion) {
		Entidad eReproduccion = new Entidad();
		eReproduccion.setNombre(REPRODUCCION);

		eReproduccion.setPropiedades(new ArrayList<Propiedad>(
				Arrays.asList(new Propiedad(CANCION, String.valueOf(reproduccion.getCancion().getCodigo())),
						new Propiedad(NUMREPRODUCCIONES, String.valueOf(reproduccion.getNumReproducidas())))));

		return eReproduccion;
	}

	@Override
	public void registrarReproduccion(Reproduccion reproduccion) {
		Entidad eReproduccion = this.reproduccionToEntidad(reproduccion);
		eReproduccion = servPersistencia.registrarEntidad(eReproduccion);
		reproduccion.setCodigo(eReproduccion.getId());
	}

	@Override
	public boolean borrarReproduccion(Reproduccion reproduccion) {
		Entidad eReproduccion;
		eReproduccion = servPersistencia.recuperarEntidad(reproduccion.getCodigo());

		return servPersistencia.borrarEntidad(eReproduccion);
	}

	@Override
	public void modificarReproduccion(Reproduccion reproduccion) {

		Entidad eReproduccion = servPersistencia.recuperarEntidad(reproduccion.getCodigo());
		servPersistencia.eliminarPropiedadEntidad(eReproduccion, NUMREPRODUCCIONES);
		servPersistencia.anadirPropiedadEntidad(eReproduccion, NUMREPRODUCCIONES,
				String.valueOf(reproduccion.getNumReproducidas()));

	}

	@Override
	public Reproduccion recuperarReproduccion(int id) {
		Entidad eReproduccion = servPersistencia.recuperarEntidad(id);
		return entidadToReproduccion(eReproduccion);
	}

	@Override
	public List<Reproduccion> recuperarTodasReproducciones() {

		List<Entidad> entidades = servPersistencia.recuperarEntidades(REPRODUCCION);

		List<Reproduccion> reproducciones = new LinkedList<Reproduccion>();
		for (Entidad eReproduccion : entidades) {
			reproducciones.add(recuperarReproduccion((eReproduccion.getId())));
		}

		return reproducciones;
	}

}
