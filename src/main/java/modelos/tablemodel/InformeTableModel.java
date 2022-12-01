package modelos.tablemodel;

import modelos.Votacion;

import javax.swing.table.AbstractTableModel;

public class InformeTableModel extends AbstractTableModel {

    private final String[] columnNames = {"Categoría", "Votos", "Porcentaje"};
    private final Class[] columnClass = new Class[] {String.class, Integer.class, Double.class};
    private Votacion votacion;

    public InformeTableModel() {
        votacion = new Votacion();
    }

    public void setVotacion(Votacion votacion) {
        this.votacion = votacion;
        fireTableDataChanged();
    }

    @Override
    public int getRowCount() {
        return 3;
    }

    @Override
    public int getColumnCount() {
        return columnNames.length;
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

    @Override
    public Object getValueAt(int rowIndex, int columnIndex) {
        return switch (columnIndex) {
            case 0 -> switch (rowIndex) {
                case 0 -> "Válidamente emitidos";
                case 1 -> "Votos en blanco";
                case 2 -> "Total de votos";
                default -> "";
            };
            case 1 -> switch (rowIndex) {
                case 0 -> votacion.getVotosPreferenciales();
                case 1 -> votacion.getVotosBlancos();
                case 2 -> votacion.getTotalVotos();
                default -> 0;
            };
            case 2 -> switch (rowIndex) {
                case 0 -> Math.round((votacion.getVotosPreferenciales().doubleValue() / votacion.getTotalVotos()) * 10000) / 100.0;
                case 1 -> Math.round((votacion.getVotosBlancos().doubleValue() / votacion.getTotalVotos()) * 10000) / 100.0;
                case 2 -> 100.0;
                default -> 0.0;
            };
            default -> throw new IllegalStateException();
        };
    }
}
