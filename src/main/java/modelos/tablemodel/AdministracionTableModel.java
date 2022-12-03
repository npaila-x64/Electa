package modelos.tablemodel;

import modelos.Votacion;

import javax.swing.table.AbstractTableModel;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

/**
 * Crea una tabla personalizada para su despliegue en la vista principal del usuario administrador.
 */

public class AdministracionTableModel extends AbstractTableModel {
    /**
     * Indica los nombres de las columnas de la tabla.
     */
    private final String[] columnNames = {"Votación", "Estado", "Botón"};
    /**
     * Indica las clases de las columnas de la tabla.
     */
    private final Class[] columnClass = new Class[] {String.class, String.class, String.class};
    /**
     * Indica la lista de votaciones que visualizará la tabla.
     */
    private List<Votacion> votaciones;

    /**
     * Construye una tabla de tipo AdministracionTableModel.
     */
    public AdministracionTableModel() {
        this.votaciones = new ArrayList<>();
    }

    /**
     * Asigna una lista de votaciones para su uso en la tabla.
     * @param votaciones una lista de tipo List que almacena objetos de tipo Votacion.
     */
    public void setVotaciones(List<Votacion> votaciones) {
        this.votaciones = votaciones;
        fireTableDataChanged();
    }

    /**
     * Obtiene la cantidad de filas de la tabla.
     * @return un entero que indica el tamaño de la lista de votaciones de la tabla.
     */
    @Override
    public int getRowCount() {
        return Optional.of(votaciones).orElse(new ArrayList<>()).size();
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
     * Obtiene un objeto en una posición en específico de la tabla. En el caso de la primera columna
     * se obtienen los títulos de las votaciones, en la segunda columna se obtienen los
     * estados de las votaciones y en la tercera se obtiene el texto del botón en función de el
     * estado de la votación.
     * @param row la posición de la fila.
     * @param col la posición de la columna.
     * @return un Object que representa un valor visible en la tabla.
     */
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
     * Verifica si una celda de la tabla es editable, en este caso si se trata de la primera o segunda
     * columna entonces la celda no es editable.
     *
     * @param row la fila indicada.
     * @param col la columna indicada.
     * @return un booleano que indica si es editable o no.
     */
    @Override
    public boolean isCellEditable(int row, int col) {
        return col != 0 && col != 1;
    }
}
