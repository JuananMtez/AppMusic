package um.tds.appmusic.dominio;

public class Cancion {

	private int codigo;
	private String titulo;
	private String interprete;
	private String estilo;
	private String rutaDisco;
	private int numReproducciones;

	public Cancion(String titulo, String interprete, String estilo, String rutaDisco) {
		this.codigo = 0;
		this.interprete = interprete;
		this.titulo = titulo;
		this.estilo = estilo;
		this.rutaDisco = rutaDisco;
		this.numReproducciones = 0;
	}

	public int getCodigo() {
		return codigo;
	}

	public void setCodigo(int codigo) {
		this.codigo = codigo;
	}

	public String getTitulo() {
		return titulo;
	}

	public String getInterprete() {
		return interprete;
	}

	public String getEstilo() {
		return estilo;
	}

	public String getRutaDisco() {
		return rutaDisco;
	}

	public int getNumReproducciones() {
		return numReproducciones;
	}

	public void setNumReproducciones(int numReproducciones) {
		this.numReproducciones = numReproducciones;
	}

	public void aumentarReproduccion() {
		numReproducciones++;
	}

}
