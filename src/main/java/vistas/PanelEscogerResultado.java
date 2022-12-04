package vistas;
import controladores.ControladorResultados;
import vistas.votante.ButtonColumn;

import javax.swing.*;
import javax.swing.table.DefaultTableCellRenderer;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
/**
 * Crea el panel que representa la vista de escoger resultados.
 */
public class PanelEscogerResultado extends JPanel implements ActionListener {
    /**
     * Indica el controlador que administra la lógica de la vista.
     */
    private ControladorResultados controlador;
    /**
     * Indica una etiqueta visible en el encabezado de la vista.
     */
    private JLabel tEncabezado;
    /**
     * Indica un botón para volver.
     */
    private JButton bVolver;
    /**
     * Indica la tabla que contiene las votaciones terminadas.
     */
    private JTable tVotaciones;

    /**
     * Crea un panel de tipo PanelEscogerResultado. Instancia el controlador y los componentes que
     * forman parte del panel.
     * @param controlador el controlador que maneja la lógica tras el panel.
     */
    public PanelEscogerResultado(ControladorResultados controlador) {
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
        crearEtiquetaEncabezado();
        crearTablaVotacionesFinalizadas();
    }
    /**
     * Crea una tabla que muestra cada votación que se encuentra en estado finalizado y un botón para
     * ver los resultados de dicha votación. Llama al controlador para obtener el modelo de tabla.
     */
    private void crearTablaVotacionesFinalizadas() {
        tVotaciones = new JTable(controlador.getModeloDeTablaVotaciones());
        tVotaciones.setBounds(0, 0, 200, 300);
        tVotaciones.setRowHeight(40);
        tVotaciones.setTableHeader(null);
        tVotaciones.setCellSelectionEnabled(false);
        tVotaciones.getColumnModel().getColumn(0).setPreferredWidth(600);
        tVotaciones.getColumnModel().getColumn(1).setPreferredWidth(205);
        tVotaciones.setFont(new Font("Arial", Font.PLAIN, 14));
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
        tVotaciones.setDefaultRenderer(String.class, renderer);

        JScrollPane scrollPane = new JScrollPane(tVotaciones);
        scrollPane.setSize(805, 345);
        scrollPane.setLocation(40, 100);
        add(scrollPane);

        Action abrirVotacion = new AbstractAction() {
            public void actionPerformed(ActionEvent e) {
                int fila = Integer.parseInt(e.getActionCommand());
                controlador.abrirResultados(fila);
            }
        };
        new ButtonColumn(tVotaciones, abrirVotacion, 1);
    }

    /**
     * Crea una etiqueta que indica el nombre de la vista en el encabezado de la ventana.
     */
    private void crearEtiquetaEncabezado() {
        tEncabezado = new JLabel("Resultados de votaciones anteriores");
        tEncabezado.setFont(new Font("Arial", Font.PLAIN, 36));
        tEncabezado.setLocation(0,40);
        tEncabezado.setSize(900, 34);
        tEncabezado.setHorizontalAlignment(JLabel.CENTER);
        add(tEncabezado);
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
     * Capta y procesa algún evento desencadenado por los botones.
     * @param e el evento de tipo ActionEvent a ser procesado.
     */
    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == bVolver) {
            controlador.volverFueSolicitado();
        }
    }
}