package vistas.admin;
import controladores.admin.ControladorAdministracion;
import modelos.enums.EstadoDeVotacion;
import vistas.votante.ButtonColumn;

import javax.swing.*;
import javax.swing.table.DefaultTableCellRenderer;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * Crea el panel que representa la vista del menú de administración.
 */
public class PanelAdministracion extends JPanel implements ActionListener {
    /**
     * Indica el controlador que administra la lógica de la vista.
     */
    private ControladorAdministracion controlador;
    /**
     * Indica la tabla que contiene las votaciones.
     */
    private JTable tVotaciones;
    /**
     * Indica el botón para cerrar sesión.
     */
    private JButton bCerrarSesion;
    /**
     * Indica el botón para crear una nueva votación.
     */
    private JButton bCrearVotacion;
    /**
     * Indica el botón para ver los resultados de alguna votación.
     */
    private JButton bResultados;
    /**
     * Indica la casilla de verificación para las votaciones en curso.
     */
    private JCheckBox checkEnCurso;
    /**
     * Indica la casilla de verificación para las votaciones pendientes.
     */
    private JCheckBox checkPendientes;
    /**
     * Indica la casilla de verificación para las votaciones finalizadas.
     */
    private JCheckBox checkFinalizadas;
    /**
     * Indica la casilla de verificación para las votaciones en borrador.
     */
    private JCheckBox checkBorradores;

    /**
     * Construye un panel de tipo PanelAdministracion. Instancia el controlador y los componentes
     * que formar parte del panel.
     * @param controlador el controlador que maneja la lógica tras el panel.
     */
    public PanelAdministracion(ControladorAdministracion controlador) {
        this.controlador = controlador;
        crearComponentes();
        setVisible(true);
    }

    /**
     * Crea cada uno de los componentes del panel.
     */
    private void crearComponentes() {
        configurarPanel();
        crearCheckMostrarEnCurso();
        crearCheckPendientes();
        crearCheckFinalizadas();
        crearCheckBorradores();
        crearTablaDeVotaciones();
        crearBotonCrearVotacion();
        crearBotonResultados();
        crearBotonCerrarSesion();
    }

    /**
     * Crea una tabla que muestra cada votación con su estado correspondiente y un botón para ver o
     * modificar dicha votación. Llama al controlador para obtener el modelo de tabla.
     */
    private void crearTablaDeVotaciones() {
        tVotaciones = new JTable(controlador.getModeloDeTabla());
        tVotaciones.setBounds(30, 40, 200, 300);
        tVotaciones.setRowHeight(50);
        tVotaciones.setTableHeader(null);
        tVotaciones.setCellSelectionEnabled(false);
        tVotaciones.getColumnModel().getColumn(0).setPreferredWidth(575);
        tVotaciones.getColumnModel().getColumn(1).setPreferredWidth(130);
        tVotaciones.getColumnModel().getColumn(2).setPreferredWidth(100);
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
        scrollPane.setSize(805, 295);
        scrollPane.setLocation(40, 150);
        add(scrollPane);

        Action editarVotacion = new AbstractAction() {
            public void actionPerformed(ActionEvent e) {
                int fila = Integer.parseInt(e.getActionCommand());
                controlador.editarVotacionFueSolicitado(fila);
            }
        };
        new ButtonColumn(tVotaciones, editarVotacion, 2);
    }

    /**
     * Crea el botón para crear una nueva votación.
     */
    private void crearBotonCrearVotacion() {
        bCrearVotacion = new JButton("Crear nueva votación");
        bCrearVotacion.setFont(new Font("Arial", Font.BOLD, 15));
        bCrearVotacion.setSize(200, 50);
        bCrearVotacion.setLocation(350, 475);
        bCrearVotacion.setBackground(Color.BLACK);
        bCrearVotacion.setForeground(Color.WHITE);
        bCrearVotacion.addActionListener(this);
        add(bCrearVotacion);
    }

    /**
     * Crea el botón para ver los resultados de alguna votación.
     */
    private void crearBotonResultados() {
        bResultados = new JButton("Ver resultados");
        bResultados.setFont(new Font("Arial", Font.BOLD, 15));
        bResultados.setSize(200, 50);
        bResultados.setLocation(645, 475);
        bResultados.setBackground(Color.BLACK);
        bResultados.setForeground(Color.WHITE);
        bResultados.addActionListener(this);
        add(bResultados);
    }

    /**
     * Crea el botón para cerrar sesión.
     */
    private void crearBotonCerrarSesion() {
        bCerrarSesion = new JButton("Cerrar sesión");
        bCerrarSesion.setFont(new Font("Arial", Font.BOLD, 15));
        bCerrarSesion.setSize(200, 50);
        bCerrarSesion.setLocation(40, 40);
        bCerrarSesion.setBackground(Color.BLACK);
        bCerrarSesion.setForeground(Color.WHITE);
        bCerrarSesion.addActionListener(this);
        add(bCerrarSesion);
    }

    /**
     * Crea la casilla de verificación para mostrar las votaciones en curso.
     */
    private void crearCheckMostrarEnCurso() {
        checkEnCurso = new JCheckBox("Mostrar En Curso");
        checkEnCurso.setFont(new Font("Arial", Font.PLAIN, 14));
        checkEnCurso.setSize(160, 20);
        checkEnCurso.setLocation(50, 110);
        checkEnCurso.setBackground(Color.WHITE);
        checkEnCurso.setFocusable(false);
        checkEnCurso.addActionListener(this);
        checkEnCurso.setSelected(true);
        add(checkEnCurso);
    }
    /**
     * Crea la casilla de verificación para mostrar las votaciones pendientes.
     */
    private void crearCheckPendientes() {
        checkPendientes = new JCheckBox("Mostrar Pendientes");
        checkPendientes.setFont(new Font("Arial", Font.PLAIN, 14));
        checkPendientes.setSize(170, 20);
        checkPendientes.setLocation(220, 110);
        checkPendientes.setBackground(Color.WHITE);
        checkPendientes.setFocusable(false);
        checkPendientes.addActionListener(this);
        checkPendientes.setSelected(true);
        add(checkPendientes);
    }
    /**
     * Crea la casilla de verificación para mostrar las votaciones finalizadas.
     */
    private void crearCheckFinalizadas() {
        checkFinalizadas = new JCheckBox("Mostrar Finalizadas");
        checkFinalizadas.setFont(new Font("Arial", Font.PLAIN, 14));
        checkFinalizadas.setSize(170, 20);
        checkFinalizadas.setLocation(410, 110);
        checkFinalizadas.setBackground(Color.WHITE);
        checkFinalizadas.setFocusable(false);
        checkFinalizadas.addActionListener(this);
        checkFinalizadas.setSelected(true);
        add(checkFinalizadas);
    }
    /**
     * Crea la casilla de verificación para mostrar las votaciones en borrador.
     */
    private void crearCheckBorradores() {
        checkBorradores = new JCheckBox("Mostrar Borradores");
        checkBorradores.setFont(new Font("Arial", Font.PLAIN, 14));
        checkBorradores.setSize(170, 20);
        checkBorradores.setLocation(600, 110);
        checkBorradores.setBackground(Color.WHITE);
        checkBorradores.setFocusable(false);
        checkBorradores.addActionListener(this);
        checkBorradores.setSelected(true);
        add(checkBorradores);
    }

    /**
     * Configura el color y layout del panel.
     */
    private void configurarPanel() {
        setBackground(Color.WHITE);
        setLayout(null);
    }

    /**
     * Capta y procesa algún evento desencadenado por los botones o casillas de verificación.
     * @param e el evento de tipo ActionEvent a ser procesado.
     */
    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == bCrearVotacion) {
            controlador.crearVotacionFueSolicitado();
        }
        if (e.getSource() == bCerrarSesion) {
            controlador.cerrarSesionFueSolicitado();
        }
        if (e.getSource() == bResultados) {
            controlador.verResultadosFueSolicitado();
        }
        if (e.getSource() == checkEnCurso) {
            controlador.checkEstadoFueEjecutado(EstadoDeVotacion.EN_CURSO);
        }
        if (e.getSource() == checkFinalizadas) {
            controlador.checkEstadoFueEjecutado(EstadoDeVotacion.FINALIZADO);
        }
        if (e.getSource() == checkPendientes) {
            controlador.checkEstadoFueEjecutado(EstadoDeVotacion.PENDIENTE);
        }
        if (e.getSource() == checkBorradores) {
            controlador.checkEstadoFueEjecutado(EstadoDeVotacion.BORRADOR);
        }
    }
}