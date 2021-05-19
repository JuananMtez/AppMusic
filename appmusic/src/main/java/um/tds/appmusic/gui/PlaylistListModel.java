package um.tds.appmusic.gui;

import javax.swing.AbstractListModel;

import um.tds.appmusic.dominio.Playlist;

import java.util.*;

public class PlaylistListModel extends AbstractListModel {
	private List<Playlist> playlists;
	
	public PlaylistListModel(List<Playlist> playlists) {
		this.playlists = playlists;
	}

	@Override
	public Object getElementAt(int arg0) {
		// TODO Auto-generated method stub
		Playlist plist = playlists.get(arg0);
		return plist.getNombre();
	}
	
	@Override
	public int getSize() {
		// TODO Auto-generated method stub
		return playlists.size();
	}

}
