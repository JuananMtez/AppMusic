package um.tds.appmusic.dominio;

public class DescuentoAncianos implements Descuento {

	@Override
	public String calcDescuento() {
		return new String("50%");
	}
}
