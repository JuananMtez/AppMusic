package um.tds.appmusic.dominio;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;

public class UsuarioTest {

	private Usuario usuario;
	private Playlist playlist;
	private Cancion cancion1;

	@Before
	public void setUp() {

		usuario = new Usuario("nombre", "apellidos", "email", "login", "password", "fecha", false);
		cancion1 = new Cancion("titulo", "interprete", "estilo", "ruta");
		playlist = new Playlist("Playlist");

		usuario.addPlaylist(playlist);
		usuario.addReproduccion(cancion1);
	}

	@Test
	public void testExisteReproduccion() {
		assertTrue(usuario.existeReproduccion(cancion1));
	}

	@Test
	public void testGetCancionesPlaylist() {
		assertNotNull(usuario.getCancionesPlaylist(playlist));
	}

	@Test
	public void test2GetCancionesPlaylist() {
		assertEquals(playlist.getCanciones(), usuario.getCancionesPlaylist(playlist));
	}
}


