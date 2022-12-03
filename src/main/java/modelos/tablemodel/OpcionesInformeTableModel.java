package modelos.tablemodel;

import modelos.Opcion;

import javax.swing.table.AbstractTableModel;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
/**
 * Crea una tabla personalizada para su despliegue en la vista de los resultados de una votación.
 */
public class OpcionesInformeTableModel extends AbstractTableModel {
    /**
     * Indica los nombres de las columnas de la tabla.
     */
    private final String[] columnNames = {"Opción", "Votos", "Porcentaje"};
    /**
     * Indica las clases de las columnas de la tabla.
     */
    private final Class[] columnClass = new Class[] {String.class, Integer.class, Double.class};
    /**
     * Indica una lista de opciones de la cual se obtienen los resultados.
     */
    private List<Opcion> opciones;
    /**
     * Indica el total de votos de una votación.
     */
    private Integer totalVotos;

    /**
     * Construye una tabla de tipo OpcionesInformeTableModel.
     */
    public OpcionesInformeTableModel() {
        this.opciones = new ArrayList<>();
    }

    /**
     * Asigna una lista de opciones para su uso en la tabla.
     * @param opciones una lista de tipo List que almacena objetos de tipo Opcion.
     */
    public void setOpciones(List<Opcion> opciones) {
        this.opciones = opciones;
        calcularTotalVotos();
        fireTableDataChanged();
    }

    /**
     * Calcula la cantidad total de votos, sumando los de cada opción.
     */
    private void calcularTotalVotos() {
        totalVotos = 0;
        for (Opcion o : opciones) {
            totalVotos += o.getCantidadDeVotos();
        }
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
     * obtienen los nombres de las opciones, en la segunda columna se obtienen las cantidades
     * de cada tipo de voto de las opciones y en la tercera se obtienen los porcentajes de dichas
     * cantidades.
     *
     * @param row la posición de la fila.
     * @param col la posición de la columna.
     * @return un Object que representa un valor visible en la tabla.
     */
    @Override
    public Object getValueAt(int row, int col) {
        return switch (col) {
            case 0 -> opciones.get(row).getNombre();
            case 1 -> opciones.get(row).getCantidadDeVotos();
            case 2 -> Math.round((opciones.get(row).getCantidadDeVotos().doubleValue()/totalVotos)*10000)/100.0;
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
     * Verifica si una celda de la tabla es editable, en este caso ninguna es editable.
     * @param row la fila indicada.
     * @param col la columna indicada.
     * @return un booleano que indica si es editable o no.
     */
    @Override
    public boolean isCellEditable(int row, int col) {
        return false;
    }
}
