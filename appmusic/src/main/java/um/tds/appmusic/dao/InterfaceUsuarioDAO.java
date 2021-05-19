package um.tds.appmusic.dao;

import java.util.List;

import um.tds.appmusic.dominio.Usuario;

public interface InterfaceUsuarioDAO {

	void registrarUsuario(Usuario usuario);
	boolean borrarUsuario(Usuario usuario);
	void modificarUsuario(Usuario usuario);
	Usuario recuperarUsuario(int id);
	List<Usuario> recuperarTodosUsuario();
	
	
	

}