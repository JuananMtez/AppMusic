package um.tds.appmusic.gui;

import javax.swing.table.AbstractTableModel;

import um.tds.appmusic.dominio.Cancion;

import java.util.*;

public class CancionesTableModel extends AbstractTableModel {

	private static final long serialVersionUID = 1L;
	private String[] columnNames = { "Titulo", "Interprete" };
	private List<Cancion> canciones;

	public CancionesTableModel(List<Cancion> canciones) {
		this.canciones = canciones;
	}

	@Override
	public int getColumnCount() {
		return columnNames.length;
	}

	@Override
	public int getRowCount() {
		return canciones.size();
	}

	public String getColumnName(int col) {
		return columnNames[col];
	}

	@Override
	public Object getValueAt(int row, int col) {
		String valor = "";

		Cancion song = canciones.get(row);
		switch (col) {
		case 0:
			valor = song.getTitulo();
			break;
		case 1:
			valor = song.getInterprete();
			break;
		}

		return valor;
	}

	public void removeRow(int row) {
		fireTableRowsDeleted(row, row);
	}

	public void setValueAt(Object value, int row, int col) {
	};
}
