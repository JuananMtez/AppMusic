package um.tds.appmusic.dao;

public class TDSFactoriaDAO extends FactoriaDAO {

	public TDSFactoriaDAO() {}
	
	@Override
	public InterfaceUsuarioDAO getUsuarioDAO() {
		return AdaptadorUsuarioTDS.INSTANCE;
	}
	
	@Override
	public InterfaceCancionDAO getCancionDAO() {
		return AdaptadorCancionTDS.INSTANCE;
	}
	
	@Override
	public InterfacePlaylistDAO getPlaylistDAO() {
		return AdaptadorPlaylistTDS.INSTANCE;
	}

	@Override
	public InterfaceReproduccionDAO getReproduccionDAO() {
		return AdaptadorReproduccionTDS.INSTANCE;
	}
	
	
}