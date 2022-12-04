package vistas.votante;
import controladores.votante.ControladorVotacionesEnCurso;

import javax.swing.*;
import javax.swing.table.DefaultTableCellRenderer;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
/**
 * Crea el panel que representa la vista de votaciones en curso.
 */
public class PanelVotacionesEnCurso extends JPanel implements ActionListener {
    /**
     * Indica una etiqueta visible en el encabezado de la vista.
     */
    private JLabel lVotaciones;
    /**
     * Indica una tabla de votaciones en curso.
     */
    private JTable tVotacionesEnCurso;
    /**
     * Indica un botón para cerrar sesión.
     */
    private JButton bCerrarSesion;
    /**
     * Indica un botón para ver los resultados de una votación.
     */
    private JButton bResultados;
    /**
     * Indica el controlador que administra la lógica de la vista.
     */
    private final ControladorVotacionesEnCurso controlador;

    /**
     * Construye un panel de tipo PanelVotacionesEnCurso. Instancia el controlador y los componentes que
     * forman parte del panel.
     * @param controlador el controlador que maneja la lógica tras el panel.
     */
    public PanelVotacionesEnCurso(ControladorVotacionesEnCurso controlador) {
        this.controlador = controlador;
        crearComponentes();
        setVisible(true);
    }
    /**
     * Crea cada uno de los componentes del panel.
     */
    private void crearComponentes() {
        configurarPanel();
        crearEtiquetaVotaciones();
        crearTablaDeVotaciones();
        crearBotonCerrarSesion();
        crearBotonResultados();
    }
    /**
     * Crea una etiqueta que indica el nombre de la vista en el encabezado de la ventana.
     */
    private void crearEtiquetaVotaciones() {
        lVotaciones = new JLabel("Votaciones en curso", SwingConstants.CENTER);
        lVotaciones.setFont(new Font("Arial", Font.BOLD, 28));
        lVotaciones.setSize(900, 32);
        lVotaciones.setLocation(0,32);
        add(lVotaciones);
    }
    /**
     * Crea un botón para ir a la vista escoger resultados.
     */
    private void crearBotonResultados() {
        bResultados = new JButton("Resultados");
        bResultados.addActionListener(this);
        bResultados.setFont(new Font("Arial", Font.PLAIN, 15));
        bResultados.setSize(200, 50);
        bResultados.setLocation(645, 475);
        bResultados.setBackground(Color.BLACK);
        bResultados.setForeground(Color.WHITE);
        add(bResultados);
    }
    /**
     * Crea una tabla que muestra cada una de las votaciones en curso,
     * seguido de un botón para votar por votar en dicha votación. Llama al controlador
     * para obtener el modelo de tabla.
     */
    private void crearTablaDeVotaciones() {
        tVotacionesEnCurso = new JTable(controlador.getModeloDeTabla());
        tVotacionesEnCurso.setBounds(30, 40, 200, 300);
        tVotacionesEnCurso.setRowHeight(60);
        tVotacionesEnCurso.setTableHeader(null);
        tVotacionesEnCurso.setCellSelectionEnabled(false);
        tVotacionesEnCurso.getColumnModel().getColumn(0).setPreferredWidth(600);
        tVotacionesEnCurso.getColumnModel().getColumn(1).setPreferredWidth(205);
        tVotacionesEnCurso.setFont(new Font("Arial", Font.BOLD, 16));
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
        tVotacionesEnCurso.setDefaultRenderer(String.class, renderer);

        JScrollPane scrollPane = new JScrollPane(tVotacionesEnCurso);
        scrollPane.setSize(805, 345);
        scrollPane.setLocation(40, 85);
        add(scrollPane);

        Action abrirVotacion = new AbstractAction() {
            public void actionPerformed(ActionEvent e) {
                int fila = Integer.parseInt(e.getActionCommand());
                controlador.abrirVotacionFueSolicitado(fila);
            }
        };
        new ButtonColumn(tVotacionesEnCurso, abrirVotacion, 1);
    }
    /**
     * Crea el botón para cerrar sesión.
     */
    private void crearBotonCerrarSesion() {
        bCerrarSesion = new JButton("Cerrar sesión");
        bCerrarSesion.addActionListener(this);
        bCerrarSesion.setFont(new Font("Arial", Font.PLAIN, 15));
        bCerrarSesion.setSize(200, 50);
        bCerrarSesion.setLocation(40, 475);
        bCerrarSesion.setBackground(Color.BLACK);
        bCerrarSesion.setForeground(Color.WHITE);
        add(bCerrarSesion);
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
        if (e.getSource() == bCerrarSesion) {
            controlador.cerrarSesionFueSolicitada();
        }
        if (e.getSource() == bResultados) {
            controlador.abirResultadosFueSolicitada();
        }
    }
}