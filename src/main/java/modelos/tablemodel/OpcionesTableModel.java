package modelos.tablemodel;

import modelos.Opcion;

import javax.swing.table.AbstractTableModel;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class OpcionesTableModel extends AbstractTableModel {

    private final String[] columnNames = {"Opción", "Votos", "Porcentaje"};
    private final Class[] columnClass = new Class[] {String.class, Integer.class, Double.class};
    private List<Opcion> opciones;
    private Integer totalVotos;

    public OpcionesTableModel() {
        this.opciones = new ArrayList<>();
    }

    public void setOpciones(List<Opcion> opciones) {
        this.opciones = opciones;
        calcularTotalVotos();
        fireTableDataChanged();
    }

    private void calcularTotalVotos() {
        totalVotos = 0;
        for (Opcion o : opciones) {
            totalVotos += o.getCantidadDeVotos();
        }
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
            case 1 -> opciones.get(row).getCantidadDeVotos();
            case 2 -> Math.round((opciones.get(row).getCantidadDeVotos().doubleValue()/totalVotos)*10000)/100.0;
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
        return false;
    }
}
