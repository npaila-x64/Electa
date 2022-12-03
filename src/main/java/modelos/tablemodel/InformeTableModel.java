package modelos.tablemodel;

import modelos.Votacion;

import javax.swing.table.AbstractTableModel;
/**
 * Crea una tabla personalizada para su despliegue en la vista de los resultados de una votación.
 */
public class InformeTableModel extends AbstractTableModel {
    /**
     * Indica los nombres de las columnas de la tabla.
     */
    private final String[] columnNames = {"Categoría", "Votos", "Porcentaje"};
    /**
     * Indica las clases de las columnas de la tabla.
     */
    private final Class[] columnClass = new Class[] {String.class, Integer.class, Double.class};
    /**
     * Indica una votación de la cual se obtienen los resultados.
     */
    private Votacion votacion;

    /**
     * Construye una tabla de tipo InformeTableModel.
     */
    public InformeTableModel() {
        votacion = new Votacion();
    }

    /**
     * Asigna una votación para su uso en la tabla.
     * @param votacion una votación indicada.
     */
    public void setVotacion(Votacion votacion) {
        this.votacion = votacion;
        fireTableDataChanged();
    }

    /**
     * Obtiene la cantidad de filas de la tabla.
     * @return un entero de valor constante 3.
     */
    @Override
    public int getRowCount() {
        return 3;
    }

    /**
     * Obtiene la cantidad de columnas de la tabla.
     * @return un entero cuyo valor representa el largo de la lista nombres de las columnas.
     */
    @Override
    public int getColumnCount() {
        return columnNames.length;
    }

    /**
     * Obtiene el nombre de una columna indicada.
     * @param col la columna indicada.
     * @return un String cuyo valor representa el nombre de la columna.
     */
    @Override
    public String getColumnName(int col) {
        return columnNames[col];
    }

    /**
     * Obtiene la clase de una columna.
     * @param columnIndex la columna indicada.
     * @return una Class que representa la clase de una columna.
     */
    @Override
    public Class<?> getColumnClass(int columnIndex) {
        return columnClass[columnIndex];
    }

    /**
     * Verifica si una celda de la tabla es editable, en este caso ninguna es editable.
     * @param row la fila indicada.
     * @param col la columna indicada.
     * @return un booleano que indica si es editable o no.
     */
    @Override
    public boolean isCellEditable(int row, int col) {
        return false;
    }
    /**
     * Obtiene un objeto en una posición en específico de la tabla. En el caso de la primera columna se
     * indica el tipo de los votos en función de la fila, en la segunda columna se obtienen las cantidades
     * de cada tipo de voto en función de la fila y en la tercera se obtienen los porcentajes de dichas
     * cantidades en función de la fila.
     *
     * @param rowIndex la posición de la fila.
     * @param columnIndex la posición de la columna.
     * @return un Object que representa un valor visible en la tabla.
     */
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
