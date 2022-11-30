package vistas.votante.tablemodel;

import modelos.Opcion;
import modelos.Votacion;

import javax.swing.table.AbstractTableModel;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class OpcionesTableModel extends AbstractTableModel {

    private final String[] columnNames = {"Opción", "Botón"};
    private final Class[] columnClass = new Class[] {String.class, String.class};

    private List<Opcion> opciones;

    public OpcionesTableModel() {
        this.opciones = new ArrayList<>();
    }

    public void setOpciones(List<Opcion> opciones) {
        this.opciones = opciones;
        fireTableDataChanged();
    }

    @Override
    public int getRowCount() {
        return Optional.of(opciones).orElse(new ArrayList<>()).size();
    }

    @Override
    public int getColumnCount() {
        return columnNames.length;
    }

    @Override
    public Object getValueAt(int row, int col) {
        return switch (col) {
            case 0 -> opciones.get(row).getNombre();
            case 1 -> "Votar";
            default -> throw new IllegalStateException();
        };
    }

    @Override
    public String getColumnName(int col) {
        return columnNames[col];
    }

    @Override
    public Class<?> getColumnClass(int columnIndex) {
        return columnClass[columnIndex];
    }

    @Override
    public boolean isCellEditable(int row, int col) {
        return col != 0;
    }
}
