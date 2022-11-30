package vistas;

import modelos.Votacion;

import javax.swing.table.AbstractTableModel;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class VotacionesTableModel extends AbstractTableModel {

    private final String[] columnNames = {"Votación", "Botón"};
    private final Class[] columnClass = new Class[] {String.class, String.class};

    private List<Votacion> votaciones;

    public VotacionesTableModel() {
        this.votaciones = new ArrayList<>();
    }

    public void setVotaciones(List<Votacion> votaciones) {
        this.votaciones = votaciones;
        fireTableDataChanged();
    }

    @Override
    public int getRowCount() {
        return Optional.of(votaciones).orElse(new ArrayList<>()).size();
    }

    @Override
    public int getColumnCount() {
        return columnNames.length;
    }

    @Override
    public Object getValueAt(int row, int col) {
        return switch (col) {
            case 0 -> votaciones.get(row).getTitulo();
            case 1 -> "Ir a votación";
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
