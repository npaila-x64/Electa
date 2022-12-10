package vistas.votante;

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import javax.swing.border.*;
import javax.swing.table.*;

/**
 * Provee un renderizador y editor que luce como un JButton. El renderizador/editor
 * será usado por una columna específica en la tabla, de manera que al agregar un texto
 * o un icono, este es renderizado en un botón.
 *
 * El botón puede ser invocado al ser presionado por el cursor en pantalla o
 * presionando la barra espaciadora. Cuando el botón es invocado también lo hace
 * la Acción provista. La fuente de la acción será la tabla. El comando de la
 * acción contendrá el número de fila del modelo del botón que fue presionado.
 */
public class ButtonColumn extends AbstractCellEditor
        implements TableCellRenderer, TableCellEditor, ActionListener, MouseListener {
    /**
     * Indica la tabla que será usada.
     */
    private JTable table;
    /**
     * Indica la acción que será invocada mediante los botones.
     */
    private Action action;
    /**
     * Indica el borde del botón cuando está enfocado.
     */
    private Border originalBorder;
    /**
     * Indica el borde del botón cuando no está enfocado.
     */
    private Border focusBorder;
    private JButton renderButton;
    private JButton editButton;
    /**
     * el valor de la celda a ser editada
     */
    private Object editorValue;
    /**
     *
     */
    private boolean isButtonColumnEditor;

    /**
     * Construye el ButtonColumn para ser usado como renderizador y editor. El renderizador/editor
     * será automáticamente instalado en la columna especificada de la tabla.
     *
     * @param table la tabla que contendrá el renderizador/editor de botones
     * @param action la acción a ser invocada cuando el botón es invocado.
     * @param column la columna en la cual el renderizador/editor de botones será añadido.
     */
    public ButtonColumn(JTable table, Action action, int column) {
        this.table = table;
        this.action = action;

        renderButton = new JButton();
        editButton = new JButton();
        editButton.setFocusPainted( false );
        editButton.addActionListener( this );
        originalBorder = editButton.getBorder();
        setFocusBorder( new LineBorder(Color.BLUE) );

        TableColumnModel columnModel = table.getColumnModel();
        columnModel.getColumn(column).setCellRenderer( this );
        columnModel.getColumn(column).setCellEditor( this );
        table.addMouseListener( this);
        renderButton.setBackground(Color.WHITE);
        renderButton.setForeground(Color.BLACK);
    }


    /**
     *  Obtiene el borde del botón cuando se enfoca en la celda.
     *
     *  @return el borde del botón.
     */
    public Border getFocusBorder()
    {
        return focusBorder;
    }

    /**
     * Asigna el borde del botón cuando se enfoca en la celda.
     * @param focusBorder el borde del botón.
     */
    public void setFocusBorder(Border focusBorder)
    {
        this.focusBorder = focusBorder;
        editButton.setBorder( focusBorder );
    }

    /**
     *
     * @param table           the <code>JTable</code> that is asking the
     *                          editor to edit; can be <code>null</code>
     * @param value           el valor de la celda a ser editada, se espera un texto,
     *                        un icono o que esté vacía.
     * @param isSelected      true if the cell is to be rendered with
     *                          highlighting
     * @param row             la fila de la celda que está siendo editada.
     * @param column          la columna de la celda que está siendo editada.
     * @return el botón de edición.
     */
    @Override
    public Component getTableCellEditorComponent(
            JTable table, Object value, boolean isSelected, int row, int column)
    {
        if (value == null)
        {
            editButton.setText( "" );
            editButton.setIcon( null );
        }
        else if (value instanceof Icon)
        {
            editButton.setText( "" );
            editButton.setIcon( (Icon)value );
        }
        else
        {
            editButton.setText( value.toString() );
            editButton.setIcon( null );
        }

        this.editorValue = value;
        return editButton;
    }

    /**
     * Obtiene el valor de la celda
     * @return un objeto que representa el valor de la celda.
     */
    @Override
    public Object getCellEditorValue() {
        return editorValue;
    }



    /**
     *
     * @param table           the <code>JTable</code> that is asking the
     *                          renderer to draw; can be <code>null</code>
     * @param value           the value of the cell to be rendered.  It is
     *                          up to the specific renderer to interpret
     *                          and draw the value.  For example, if
     *                          <code>value</code>
     *                          is the string "true", it could be rendered as a
     *                          string or it could be rendered as a check
     *                          box that is checked.  <code>null</code> is a
     *                          valid value
     * @param isSelected      true if the cell is to be rendered with the
     *                          selection highlighted; otherwise false
     * @param hasFocus        if true, render cell appropriately.  For
     *                          example, put a special border on the cell, if
     *                          the cell can be edited, render in the color used
     *                          to indicate editing
     * @param row             the row index of the cell being drawn.  When
     *                          drawing the header, the value of
     *                          <code>row</code> is -1
     * @param column          the column index of the cell being drawn
     *
     * @return
     */
    public Component getTableCellRendererComponent(
            JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int column) {
        if (isSelected) {
            renderButton.setBackground(Color.WHITE);
            renderButton.setForeground(Color.BLACK);
        }
        else
        {
            renderButton.setForeground(table.getForeground());
            renderButton.setBackground(UIManager.getColor("Button.background"));
        }

        if (hasFocus)
        {
            renderButton.setBorder( focusBorder );
        }
        else
        {
            renderButton.setBorder( originalBorder );
        }

//		renderButton.setText( (value == null) ? "" : value.toString() );
        if (value == null)
        {
            renderButton.setText( "" );
            renderButton.setIcon( null );
        }
        else if (value instanceof Icon)
        {
            renderButton.setText( "" );
            renderButton.setIcon( (Icon)value );
        }
        else
        {
            renderButton.setText( value.toString() );
            renderButton.setIcon( null );
        }

        return renderButton;
    }


    /**
     * Cuando el botón es presionado se detiene la edición y se invoca la acción indicada.
     * @param e el evento a ser procesado.
     */
    public void actionPerformed(ActionEvent e) {
        int row = table.convertRowIndexToModel( table.getEditingRow() );
        fireEditingStopped();

        //  Invoke the Action

        ActionEvent event = new ActionEvent(
                table,
                ActionEvent.ACTION_PERFORMED,
                "" + row);

        action.actionPerformed(event);
    }

    /**
     * Cuando el mouse es presionado el editor es invocado. Si se arrastra
     * el mouse a otra celda sin antes soltarlo, el editor seguirá activo.
     *
     *
     * @param e el evento a ser procesado.
     */
    public void mousePressed(MouseEvent e) {
        if (table.isEditing() &&  table.getCellEditor() == this)
            isButtonColumnEditor = true;
    }

    /**
     * Cuando el mouse es soltado se asegura de desactivar el editor.
     *
     * @param e el evento a ser procesado.
     */
    public void mouseReleased(MouseEvent e) {
        if (isButtonColumnEditor &&  table.isEditing())
            table.getCellEditor().stopCellEditing();

        isButtonColumnEditor = false;
    }

    /**
     * Sin implementación.
     * @param e un evento a ser procesado.
     */
    public void mouseClicked(MouseEvent e) {}
    /**
     * Sin implementación.
     * @param e un evento a ser procesado.
     */
    public void mouseEntered(MouseEvent e) {}
    /**
     * Sin implementación.
     * @param e un evento a ser procesado.
     */
    public void mouseExited(MouseEvent e) {}
}