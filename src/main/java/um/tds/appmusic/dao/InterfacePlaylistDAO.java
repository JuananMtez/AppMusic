package um.tds.appmusic.dao;

import java.util.List;

import um.tds.appmusic.dominio.Playlist;

public interface InterfacePlaylistDAO {

	void registrarPlaylist(Playlist playlist);
	boolean borrarPlaylist(Playlist playlist);
	void modificarPlaylist(Playlist playlist);
	Playlist recuperarPlaylist(int id);
	List<Playlist> recuperarTodasPlaylist();
}
