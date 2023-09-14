package um.tds.appmusic.dominio;

public class Reproduccion {

	private int codigo;
	private Cancion cancion;
	private int numReproducidas;

	public Reproduccion(Cancion cancion) {
		codigo = 0;
		this.cancion = cancion;
		numReproducidas = 0;
	}

	public int getCodigo() {
		return codigo;
	}

	public void setCodigo(int codigo) {
		this.codigo = codigo;
	}

	public Cancion getCancion() {
		return cancion;
	}

	public void setCancion(Cancion cancion) {
		this.cancion = cancion;
	}

	public int getNumReproducidas() {
		return numReproducidas;
	}

	public void setNumReproducidas(int numReproducidas) {
		this.numReproducidas = numReproducidas;
	}

	public void aumentarReproduccion() {
		numReproducidas++;
		cancion.aumentarReproduccion();
	}

}
