package vistas.votante;

import controladores.votante.ControladorOpciones;
import javax.swing.*;
import javax.swing.table.DefaultTableCellRenderer;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
/**
 * Crea el panel que representa la vista de opciones.
 */
public class PanelOpciones extends JPanel implements ActionListener {
    /**
     * Indica el controlador que administra la lógica de la vista.
     */
    private ControladorOpciones controlador;
    /**
     * Indica una etiqueta asociada al título de una votación.
     */
    private JLabel lTitulo;
    /**
     * Indica una etiqueta asociada a la descripción de una votación.
     */
    private JLabel lDescripcion;
    /**
     * Indica un área de texto asociada a la descripción de una votación.
     */
    private JTextArea campoTextoDescripcion;
    /**
     * Indica un botón para volver.
     */
    private JButton bVolver;
    /**
     * Indica un botón para emitir un voto blanco.
     */
    private JButton bAbstencion;
    /**
     * Indica una tabla de opciones de una votación.
     */
    private JTable tOpciones;
    /**
     * Construye un panel de tipo PanelOpciones. Instancia el controlador y los componentes que
     * forman parte del panel.
     * @param controlador el controlador que maneja la lógica tras el panel.
     */
    public PanelOpciones(ControladorOpciones controlador) {
        this.controlador = controlador;
        crearComponentes();
        setVisible(true);
    }
    /**
     * Crea cada uno de los componentes del panel.
     */
    private void crearComponentes() {
        configurarPanel();
        crearEtiquetaTitulo();
        crearEtiquetaDescripcion();
        crearTablaDeOpciones();
        crearBotonVolver();
        crearBotonAbstencion();
        crearCampoDeTextoDescripcion();
    }

    /**
     * Crea el área de texto de la descripción asociada a una votación.
     */
    private void crearCampoDeTextoDescripcion() {
        campoTextoDescripcion = new JTextArea();
        campoTextoDescripcion.setLocation(254, 78);
        campoTextoDescripcion.setSize(476,93);
        campoTextoDescripcion.setFont(new Font("Arial", Font.PLAIN, 15));
        campoTextoDescripcion.setBorder(BorderFactory.createLineBorder(Color.BLACK, 1));
        campoTextoDescripcion.setLineWrap(true);
        campoTextoDescripcion.setWrapStyleWord(true);
        campoTextoDescripcion.setBorder(BorderFactory.createCompoundBorder(
                campoTextoDescripcion.getBorder(),
                BorderFactory.createEmptyBorder(10, 10, 5, 10)));
        campoTextoDescripcion.setEditable(false);
        add(campoTextoDescripcion);
    }
    /**
     * Crea la etiqueta asociada al título de una votación. La etiqueta únicamente
     * contiene un texto que indica la votación por la que se está votando.
     */
    private void crearEtiquetaTitulo() {
        lTitulo = new JLabel();
        lTitulo.setFont(new Font("Arial", Font.BOLD, 26));
        lTitulo.setSize(900, 70);
        lTitulo.setHorizontalAlignment(JLabel.CENTER);
        lTitulo.setVerticalAlignment(JLabel.CENTER);
        add(lTitulo);
    }
    /**
     * Crea la etiqueta asociada a la descripción de una votación. La etiqueta únicamente
     * contiene un texto que indica el contenido del área de texto respectiva.
     */
    private void crearEtiquetaDescripcion() {
        lDescripcion = new JLabel("Descripción");
        lDescripcion.setFont(new Font("Arial", Font.PLAIN, 16));
        lDescripcion.setLocation(148, 112);
        lDescripcion.setSize(119, 25);
        add(lDescripcion);
    }

    /**
     * Crea un botón para emitir un voto blanco o de abstención.
     */
    private void crearBotonAbstencion() {
        bAbstencion = new JButton("Abstención");
        bAbstencion.setFont(new Font("Arial", Font.PLAIN, 15));
        bAbstencion.setSize(200, 50);
        bAbstencion.setLocation(645, 475);
        bAbstencion.setBackground(Color.WHITE);
        bAbstencion.setForeground(Color.BLACK);
        bAbstencion.addActionListener(this);
        add(bAbstencion);
    }
    /**
     * Crea un botón para volver a las votaciones en curso.
     */
    private void crearBotonVolver() {
        bVolver = new JButton("Volver");
        bVolver.setFont(new Font("Arial", Font.PLAIN, 15));
        bVolver.setSize(200, 50);
        bVolver.setLocation(350, 475);
        bVolver.setBackground(Color.BLACK);
        bVolver.setForeground(Color.WHITE);
        bVolver.addActionListener(this);
        add(bVolver);
    }
    /**
     * Crea una tabla que muestra cada opción de una votación (a excepción del voto blanco),
     * seguido de un botón para votar por dicha opción. Llama al controlador para obtener el modelo
     * de tabla.
     */
    private void crearTablaDeOpciones() {
        tOpciones = new JTable(controlador.getModeloDeTabla());
        tOpciones.setBounds(30, 40, 200, 300);
        tOpciones.setRowHeight(60);
        tOpciones.setTableHeader(null);
        tOpciones.setCellSelectionEnabled(false);
        tOpciones.getColumnModel().getColumn(0).setPreferredWidth(600);
        tOpciones.getColumnModel().getColumn(1).setPreferredWidth(205);
        tOpciones.setFont(new Font("Arial", Font.BOLD, 16));
        DefaultTableCellRenderer renderer = new DefaultTableCellRenderer() {
            @Override
            public Component getTableCellRendererComponent(JTable table,
                                                           Object value, boolean isSelected, boolean hasFocus,
                                                           int row, int column) {
                super.getTableCellRendererComponent(table, value, isSelected, hasFocus,
                        row, column);
                setBorder(BorderFactory.createCompoundBorder(getBorder(),
                        BorderFactory.createEmptyBorder(0, 20, 0, 10)));
                return this;
            }
        };
        tOpciones.setDefaultRenderer(String.class, renderer);

        JScrollPane scrollPane = new JScrollPane(tOpciones);
        scrollPane.setLocation(148, 190);
        scrollPane.setSize(582, 249);
        add(scrollPane);

        Action abrirVotacion = new AbstractAction() {
            public void actionPerformed(ActionEvent e) {
                int fila = Integer.parseInt(e.getActionCommand());
                controlador.votarPorOpcionFueSolicitado(fila);
            }
        };
        new ButtonColumn(tOpciones, abrirVotacion, 1);
    }
    /**
     * Configura el color y layout del panel.
     */
    private void configurarPanel() {
        this.setBackground(Color.WHITE);
        this.setLayout(null);
    }
    /**
     * Asigna el título de una votación en su etiqueta respectiva.
     * @param titulo un String cuyo valor representa un título.
     */
    public void setTituloDeVotacion(String titulo) {
        lTitulo.setText(titulo);
    }
    /**
     * Asigna la descripción de una votación en su etiqueta respectiva.
     * @param descripcion un String cuyo valor representa una descripción.
     */
    public void setlDescripcionDeVotacion(String descripcion) {
        campoTextoDescripcion.setText(descripcion);
    }
    /**
     * Capta y procesa algún evento desencadenado por los botones.
     * @param e el evento de tipo ActionEvent a ser procesado.
     */
    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == bVolver) {
            controlador.volverFueSolicitado();
        }
        if (e.getSource() == bAbstencion) {
            controlador.abstenerseFueSolicitado();
        }
    }
}