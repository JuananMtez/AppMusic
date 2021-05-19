package um.tds.appmusic.dominio;

public class DescuentoGeneral implements Descuento {

	@Override
	public String calcDescuento() {
		return new String("20%");
	}

}
