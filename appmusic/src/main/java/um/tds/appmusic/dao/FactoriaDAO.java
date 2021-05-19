package um.tds.appmusic.dao;

import um.tds.appmusic.dao.FactoriaDAO;

public abstract class FactoriaDAO {
	private static FactoriaDAO unicaInstancia;

	public static final String DAO_TDS = "um.tds.appmusic.dao.TDSFactoriaDAO";

	/**
	 * Crea un tipo de factoria DAO. Solo existe el tipo TDSFactoriaDAO
	 */
	@SuppressWarnings("deprecation")
	public static FactoriaDAO getInstancia(String tipo) throws DAOException {
		if (unicaInstancia == null)
			try {
				unicaInstancia = (FactoriaDAO) Class.forName(tipo).newInstance();
			} catch (Exception e) {
				throw new DAOException(e.getMessage());
			}
		return unicaInstancia;
	}

	public static FactoriaDAO getInstancia() throws DAOException {
		return getInstancia(FactoriaDAO.DAO_TDS);
	}

	/* Constructor */
	protected FactoriaDAO() {}

	public abstract InterfaceUsuarioDAO getUsuarioDAO();
	public abstract InterfaceCancionDAO getCancionDAO();
	public abstract InterfacePlaylistDAO getPlaylistDAO();
	public abstract InterfaceReproduccionDAO getReproduccionDAO();

}