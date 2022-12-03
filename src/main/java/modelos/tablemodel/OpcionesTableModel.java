package modelos.tablemodel;

import modelos.Opcion;

import javax.swing.table.AbstractTableModel;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
/**
 * Crea una tabla personalizada para su despliegue en la vista de las opciones de una votación.
 */
public class OpcionesTableModel extends AbstractTableModel {
    /**
     * Indica los nombres de las columnas de la tabla.
     */
    private final String[] columnNames = {"Opción", "Botón"};
    /**
     * Indica las clases de las columnas de la tabla.
     */
    private final Class[] columnClass = new Class[] {String.class, String.class};
    /**
     * Indica una lista de opciones de la cual se obtienen los resultados.
     */
    private List<Opcion> opciones;
    /**
     * Indica el texto visible en un botón.
     */
    private String texto;

    /**
     * Construye una tabla de tipo OpcionesTableModel.
     */
    public OpcionesTableModel() {
        this.opciones = new ArrayList<>();
    }
    /**
     * Asigna una lista de opciones para su uso en la tabla.
     * @param opciones una lista de tipo List que almacena objetos de tipo Opcion.
     */
    public void setOpciones(List<Opcion> opciones) {
        this.opciones = opciones;
        fireTableDataChanged();
    }

    /**
     * Asigna el texto correspondiente a un botón.
     * @param texto un String cuyo valor representa un texto para el botón.
     */
    public void setTextoBoton(String texto) {
        this.texto = texto;
    }
    /**
     * Obtiene la cantidad de filas de la tabla.
     * @return un entero que indica el tamaño de la lista de opciones de la tabla.
     */
    @Override
    public int getRowCount() {
        return Optional.of(opciones).orElse(new ArrayList<>()).size();
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
     * Obtiene un objeto en una posición en específico de la tabla. En el caso de la primera columna se
     * obtienen los nombres de las opciones y en la segunda columna se obtiene el texto correspondiente
     * a los botones.
     *
     * @param row la posición de la fila.
     * @param col la posición de la columna.
     * @return un Object que representa un valor visible en la tabla.
     */
    @Override
    public Object getValueAt(int row, int col) {
        return switch (col) {
            case 0 -> opciones.get(row).getNombre();
            case 1 -> texto;
            default -> throw new IllegalStateException();
        };
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
     * Verifica si una celda de la tabla es editable, en este caso solo las celdas de
     * la primera columna no son editables.
     * @param row la fila indicada.
     * @param col la columna indicada.
     * @return un booleano que indica si es editable o no.
     */
    @Override
    public boolean isCellEditable(int row, int col) {
        return col != 0;
    }
}
