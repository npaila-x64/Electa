package vistas.votante;

import modelos.Votacion;

import javax.swing.table.AbstractTableModel;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class VotacionesEnCursoTableModel extends AbstractTableModel {

    private final String[] columnNames = {"Votación"};
    private List<Votacion> votaciones;

    public VotacionesEnCursoTableModel() {
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
        if (col == 0) {
            return votaciones.get(row).getTitulo();
        }
        throw new IllegalStateException();
    }

    public String getColumnName(int col) {
        return columnNames[col];
    }

    public Class getColumnClass(int col) {
        return String.class;
    }
}
