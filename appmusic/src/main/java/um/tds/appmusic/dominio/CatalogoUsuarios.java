package um.tds.appmusic.dominio;

import java.util.*;

import um.tds.appmusic.dao.DAOException;
import um.tds.appmusic.dao.FactoriaDAO;

public enum CatalogoUsuarios {

	INSTANCE;

	private FactoriaDAO factoria;
	private HashMap<Integer, Usuario> asistentesPorID;
	private HashMap<String, Usuario> asistentesPorLogin;

	private CatalogoUsuarios() {
		asistentesPorID = new HashMap<Integer, Usuario>();
		asistentesPorLogin = new HashMap<String, Usuario>();

		try {

			factoria = FactoriaDAO.getInstancia();

			List<Usuario> listaAsistentes = factoria.getUsuarioDAO().recuperarTodosUsuario();
			for (Usuario usuario : listaAsistentes) {
				asistentesPorID.put(usuario.getCodigo(), usuario);
				asistentesPorLogin.put(usuario.getLogin(), usuario);
			}
		} catch (DAOException eDAO) {
			eDAO.printStackTrace();
		}
	}

	public List<Usuario> getUsuarios() throws DAOException {
		return new LinkedList<Usuario>(asistentesPorLogin.values());
	}

	public Usuario getUsuario(String login) {
		return asistentesPorLogin.get(login);
	}

	public Usuario getUsuario(int codigo) {
		return asistentesPorID.get(codigo);
	}

	public void addUsuario(Usuario usuario) {
		asistentesPorID.put(usuario.getCodigo(), usuario);
		asistentesPorLogin.put(usuario.getLogin(), usuario);
	}

	public void removeUsuario(Usuario usuario) {
		asistentesPorID.remove(usuario.getCodigo());
		asistentesPorLogin.remove(usuario.getLogin());
	}

}
