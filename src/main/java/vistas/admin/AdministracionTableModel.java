package vistas.admin;

import modelos.Votacion;

import javax.swing.table.AbstractTableModel;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class AdministracionTableModel extends AbstractTableModel {

    private final String[] columnNames = {"Votación", "Estado", "Botón"};
    private final Class[] columnClass = new Class[] {String.class, String.class, String.class};

    private List<Votacion> votaciones;

    public AdministracionTableModel() {
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
        switch (col) {
            case 0 -> {
                return votaciones.get(row).getTitulo();
            }
            case 1 -> {
                return votaciones.get(row).getEstadoDeVotacion().getTexto();
            }
            case 2 -> {
                return switch (votaciones.get(row).getEstadoDeVotacion()) {
                    case FINALIZADO -> "Ver";
                    default -> "Modificar";
                };
            }
            default -> throw new IllegalStateException();
        }
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
        return col != 0 && col != 1;
    }
}
