package vistas;
import controladores.ControladorResultados;
import modelos.Votacion;

import javax.swing.*;
import javax.swing.table.DefaultTableCellRenderer;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.time.format.DateTimeFormatter;

/**
 * Crea el panel que representa la vista de los resultados de una votación.
 */
public class PanelResultados extends JPanel implements ActionListener {
    /**
     * Indica el controlador que administra la lógica de la vista.
     */
    private ControladorResultados controlador;
    /**
     * Indica una tabla asociada a las opciones de una votación.
     */
    private JTable tInformeOpciones;
    /**
     * Indica una tabla asociada a los votos de una votación.
     */
    private JTable tInformeVotos;
    /**
     * Indica un botón para volver.
     */
    private JButton bVolver;
    /**
     * Indica una etiqueta visible en el encabezado de la vista.
     */
    private JLabel lEncabezado;
    /**
     * Indica una etiqueta asociada a la fecha y hora de inicio de una votación.
     */
    private JLabel lFechaHoraInicio;
    /**
     * Indica una etiqueta asociada a la fecha y hora de término de una votación.
     */
    private JLabel lFechaHoraTermino;
    /**
     * Indica un campo de texto asociado a la fecha y hora de inicio de una votación.
     */
    private JTextField tFechaHoraInicio;
    /**
     * Indica un campo de texto asociado a la fecha y hora de término de una votación.
     */
    private JTextField tFechaHoraTermino;

    /**
     * Construye un panel de tipo PanelResultados. Instancia el controlador y los componentes que
     * forman parte del panel.
     * @param controlador el controlador que maneja la lógica tras el panel.
     */

    public PanelResultados(ControladorResultados controlador) {
        this.controlador = controlador;
        crearComponentes();
        setVisible(true);
    }
    /**
     * Crea cada uno de los componentes del panel.
     */
    private void crearComponentes() {
        configurarPanel();
        crearBotonVolver();
        crearEtiquetaFechaHoraInicio();
        crearEtiquetaFechaHoraTermino();
        crearEtiquetaEncabezado();
        crearTablaDeInformeVotacion();
        crearTablaDeInformeOpciones();
        crearCampoDeTextoFechaHoraInicio();
        crearCampoDeTextoFechaHoraTermino();
    }

    /**
     * Crea un campo de texto que contiene información sobre la fecha y hora de inicio de
     * una votación.
     */
    private void crearCampoDeTextoFechaHoraInicio() {
        tFechaHoraInicio = new JTextField();
        tFechaHoraInicio.setFont(new Font("Arial", Font.PLAIN, 15));
        tFechaHoraInicio.setLocation(249, 81);
        tFechaHoraInicio.setSize(179, 30);
        tFechaHoraInicio.setBorder(BorderFactory.createLineBorder(Color.BLACK));
        tFechaHoraInicio.setBorder(BorderFactory.createCompoundBorder(
                tFechaHoraInicio.getBorder(),
                BorderFactory.createEmptyBorder(5, 10, 5, 5)));
        tFechaHoraInicio.setEditable(false);
        tFechaHoraInicio.setBackground(Color.WHITE);
        add(tFechaHoraInicio);
    }
    /**
     * Crea un campo de texto que contiene información sobre la fecha y hora de término de
     * una votación.
     */
    private void crearCampoDeTextoFechaHoraTermino() {
        tFechaHoraTermino = new JTextField();
        tFechaHoraTermino.setFont(new Font("Arial", Font.PLAIN, 15));
        tFechaHoraTermino.setLocation(658, 81);
        tFechaHoraTermino.setSize(179, 30);
        tFechaHoraTermino.setBorder(BorderFactory.createLineBorder(Color.BLACK));
        tFechaHoraTermino.setBorder(BorderFactory.createCompoundBorder(
                tFechaHoraTermino.getBorder(),
                BorderFactory.createEmptyBorder(5, 10, 5, 5)));
        tFechaHoraTermino.setEditable(false);
        tFechaHoraTermino.setBackground(Color.WHITE);
        add(tFechaHoraTermino);
    }

    /**
     * Crea una etiqueta que indica el contenido del campo de texto respectivo.
     */
    private void crearEtiquetaFechaHoraInicio() {
        lFechaHoraInicio = new JLabel("Fecha y hora de inicio");
        lFechaHoraInicio.setFont(new Font("Arial", Font.PLAIN, 16));
        lFechaHoraInicio.setLocation(83, 87);
        lFechaHoraInicio.setSize(189, 18);
        add(lFechaHoraInicio);
    }
    /**
     * Crea una etiqueta que indica el contenido del campo de texto respectivo.
     */
    private void crearEtiquetaFechaHoraTermino() {
        lFechaHoraTermino = new JLabel("Fecha y hora de término");
        lFechaHoraTermino.setFont(new Font("Arial", Font.PLAIN, 16));
        lFechaHoraTermino.setLocation(477, 87);
        lFechaHoraTermino.setSize(196, 18);
        add(lFechaHoraTermino);
    }

    /**
     * Crea una tabla que contiene información sobre la cantidad de votos y el porcentaje de cada
     * opción en una votación. Llama al controlador para obtener el modelo de tabla.
     */
    private void crearTablaDeInformeVotacion() {
        tInformeOpciones = new JTable(controlador.getModeloDeTablaOpciones());
        tInformeOpciones.setBounds(30, 40, 200, 300);
        tInformeOpciones.setRowHeight(30);
        tInformeOpciones.setCellSelectionEnabled(false);
        tInformeOpciones.getColumnModel().getColumn(0).setPreferredWidth(340);
        tInformeOpciones.getColumnModel().getColumn(1).setPreferredWidth(205);
        tInformeOpciones.getColumnModel().getColumn(2).setPreferredWidth(205);
        tInformeOpciones.setFont(new Font("Arial", Font.PLAIN, 14));
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
        tInformeOpciones.setDefaultRenderer(String.class, renderer);

        JScrollPane scrollPane = new JScrollPane(tInformeOpciones);
        scrollPane.setLocation(85, 130);
        scrollPane.setSize(750, 150);
        scrollPane.getViewport().setBackground(Color.WHITE);
        add(scrollPane);
    }
    /**
     * Crea una tabla que contiene información sobre la cantidad de votos y el porcentaje de los votos
     * emitidos, los votos blancos y el total en una votación. Llama al controlador para obtener el
     * modelo de tabla.
     */
    private void crearTablaDeInformeOpciones() {
        tInformeVotos = new JTable(controlador.getModeloDeTablaInforme());
        tInformeVotos.setBounds(30, 40, 200, 300);
        tInformeVotos.setRowHeight(30);
        tInformeVotos.setCellSelectionEnabled(false);
        tInformeVotos.getColumnModel().getColumn(0).setPreferredWidth(340);
        tInformeVotos.getColumnModel().getColumn(1).setPreferredWidth(205);
        tInformeVotos.getColumnModel().getColumn(2).setPreferredWidth(205);
        tInformeVotos.setFont(new Font("Arial", Font.PLAIN, 14));
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
        tInformeVotos.setDefaultRenderer(String.class, renderer);

        JScrollPane scrollPane = new JScrollPane(tInformeVotos);
        scrollPane.setLocation(83, 293);
        scrollPane.setSize(753, 150);
        scrollPane.getViewport().setBackground(Color.WHITE);
        add(scrollPane);
    }

    /**
     * Crea una etiqueta que indica el nombre de la vista en el encabezado de la ventana.
     */
    private void crearEtiquetaEncabezado() {
        lEncabezado = new JLabel("Resultados");
        lEncabezado.setFont(new Font("Arial", Font.PLAIN, 22));
        lEncabezado.setLocation(0,25);
        lEncabezado.setSize(900, 36);
        lEncabezado.setHorizontalAlignment(JLabel.CENTER);
        add(lEncabezado);
    }

    /**
     * Crea un botón para volver a la vista anterior.
     */
    private void crearBotonVolver() {
        bVolver = new JButton("Volver");
        bVolver.addActionListener(this);
        bVolver.setFont(new Font("Arial", Font.PLAIN, 15));
        bVolver.setSize(200, 50);
        bVolver.setLocation(350, 475);
        bVolver.setBackground(Color.BLACK);
        bVolver.setForeground(Color.WHITE);
        add(bVolver);
    }
    /**
     * Configura el color y layout del panel.
     */
    private void configurarPanel() {
        setBackground(Color.WHITE);
        setLayout(null);
    }

    /**
     * Carga la información de una votación escogida en el encabezado y los campos de fecha y hora.
     * @param votacion la votación elegida para ver los resultados.
     */
    public void cargarVotacion(Votacion votacion) {
        lEncabezado.setText("Resultados de ".concat(votacion.getTitulo()));
        String fechaHoraInicio = String.format("%s a las %s",
                votacion.getFechaInicio().format(DateTimeFormatter.ofPattern("dd-MM-uuuu")),
                votacion.getTiempoInicio());
        String fechaHoraTermino = String.format("%s a las %s",
                votacion.getFechaTermino().format(DateTimeFormatter.ofPattern("dd-MM-uuuu")),
                votacion.getTiempoTermino());
        tFechaHoraInicio.setText(fechaHoraInicio);
        tFechaHoraTermino.setText(fechaHoraTermino);
    }
    /**
     * Capta y procesa algún evento desencadenado por los botones.
     * @param e el evento de tipo ActionEvent a ser procesado.
     */
    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == bVolver) {
            controlador.volverAEscogerResultadoFueSolicitado();
        }
    }
}