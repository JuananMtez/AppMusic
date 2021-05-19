package um.tds.appmusic.dominio;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;

public class CancionTest {
	
	private Cancion cancion;
	
	
	@Before
	public void setUp() {
		cancion = new Cancion("titulo", "interprete","estilo", "ruta");

	}

	@Test
	public void testAumentarReproduccion() {
		int resultado = 1;
		cancion.aumentarReproduccion();
		assertEquals(resultado, cancion.getNumReproducciones());
	}
	

}

