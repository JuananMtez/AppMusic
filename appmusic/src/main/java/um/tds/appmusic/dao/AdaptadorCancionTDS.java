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

public enum AdaptadorCancionTDS implements InterfaceCancionDAO {

	INSTANCE;
	
	private static final String CANCION = "Cancion";

	private static final String TITULO = "titulo";
	private static final String INTERPRETE = "interprete";
	private static final String ESTILO = "estilo";
	private static final String RUTADISCO = "rutadisco";
	private static final String NUMREPRODUCCIONES = "numReproducciones";

	private ServicioPersistencia servPersistencia;

	private AdaptadorCancionTDS() {
		servPersistencia = FactoriaServicioPersistencia.getInstance().getServicioPersistencia();
	}


	private Cancion entidadToCancion(Entidad eCancion) {

		String titulo = servPersistencia.recuperarPropiedadEntidad(eCancion, TITULO);
		String interprete = servPersistencia.recuperarPropiedadEntidad(eCancion, INTERPRETE);
		String estilo = servPersistencia.recuperarPropiedadEntidad(eCancion, ESTILO);
		String rutaDisco = servPersistencia.recuperarPropiedadEntidad(eCancion, RUTADISCO);
		String numReproducciones = servPersistencia.recuperarPropiedadEntidad(eCancion, NUMREPRODUCCIONES);

		Cancion cancion = new Cancion(titulo, interprete, estilo, rutaDisco);
		cancion.setNumReproducciones(Integer.parseInt(numReproducciones));
		cancion.setCodigo(eCancion.getId());

		return cancion;

	}

	private Entidad cancionToEntidad(Cancion cancion) {

		Entidad eCancion = new Entidad();
		eCancion.setNombre(CANCION);

		eCancion.setPropiedades(new ArrayList<Propiedad>(Arrays.asList(new Propiedad(TITULO, cancion.getTitulo()),
				new Propiedad(INTERPRETE, cancion.getInterprete()), new Propiedad(ESTILO, cancion.getEstilo()),
				new Propiedad(RUTADISCO, cancion.getRutaDisco()),
				new Propiedad(NUMREPRODUCCIONES, Integer.toString(cancion.getNumReproducciones())))));

		return eCancion;
	}

	@Override
	public void registrarCancion(Cancion cancion) {
		Entidad eCancion = this.cancionToEntidad(cancion);
		eCancion = servPersistencia.registrarEntidad(eCancion);
		cancion.setCodigo(eCancion.getId());
	}
	
	@Override
	public boolean borrarCancion(Cancion cancion) {
		Entidad eCancion;
		eCancion = servPersistencia.recuperarEntidad(cancion.getCodigo());

		return servPersistencia.borrarEntidad(eCancion);
	}

	@Override
	public void modificarCancion(Cancion cancion) {

		Entidad eCancion = servPersistencia.recuperarEntidad(cancion.getCodigo());
		servPersistencia.eliminarPropiedadEntidad(eCancion, NUMREPRODUCCIONES);
		servPersistencia.anadirPropiedadEntidad(eCancion, NUMREPRODUCCIONES,
				String.valueOf(cancion.getNumReproducciones()));

	}

	@Override
	public Cancion recuperarCancion(int codigo) {
		Entidad eCancion = servPersistencia.recuperarEntidad(codigo);
		return entidadToCancion(eCancion);
	}

	@Override
	public List<Cancion> recuperarTodasCanciones() {

		List<Entidad> entidades = servPersistencia.recuperarEntidades(CANCION);

		List<Cancion> canciones = new LinkedList<Cancion>();
		for (Entidad eCancion : entidades) {
			canciones.add(recuperarCancion(eCancion.getId()));
		}

		return canciones;
	}

}
