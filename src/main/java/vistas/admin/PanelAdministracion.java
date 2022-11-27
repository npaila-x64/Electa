package vistas.admin;
import controladores.admin.ControladorAdministracion;
import vistas.votante.ButtonColumn;

import javax.swing.*;
import javax.swing.table.DefaultTableCellRenderer;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class PanelAdministracion extends JPanel implements ActionListener {

    private ControladorAdministracion controlador;
    private JTable tVotaciones;
    private JLabel textoDesde;
    private JLabel textoHasta;
    private JTextField campoTextoDesde;
    private JTextField campoTextoHasta;
    private JButton bCerrarSesion;
    private JButton bCrearVotacion;
    private JButton bResultados;
    private JCheckBox checkEnCurso;
    private JCheckBox checkPendientes;
    private JCheckBox checkFinalizadas;
    private JCheckBox checkBorradores;

    public PanelAdministracion(ControladorAdministracion controlador) {
        this.controlador = controlador;
        crearComponentes();
        setVisible(true);
    }

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
        crearEtiquetaFiltroDesde();
        crearEtiquetaFiltroHasta();
        crearCampoDeTextoFiltroDesde();
        crearCampoDeTextoFiltroHasta();
    }

    private void crearCampoDeTextoFiltroHasta() {
        campoTextoHasta = new JTextField();
        campoTextoHasta.setFont(new Font("Arial", Font.PLAIN, 15));
        campoTextoHasta.setSize(100, 40);
        campoTextoHasta.setLocation(745, 40);
        campoTextoHasta.setBorder(BorderFactory.createLineBorder(Color.BLACK));
        add(campoTextoHasta);
    }

    private void crearCampoDeTextoFiltroDesde() {
        campoTextoDesde = new JTextField();
        campoTextoDesde.setFont(new Font("Arial", Font.PLAIN, 15));
        campoTextoDesde.setSize(100, 40);
        campoTextoDesde.setLocation(560, 40);
        campoTextoDesde.setBorder(BorderFactory.createLineBorder(Color.BLACK));
        add(campoTextoDesde);
    }

    private void crearEtiquetaFiltroHasta() {
        textoHasta = new JLabel("hasta");
        textoHasta.setFont(new Font("Arial", Font.PLAIN, 15));
        textoHasta.setSize(100,40);
        textoHasta.setLocation(685, 40);
        add(textoHasta);
    }

    private void crearEtiquetaFiltroDesde() {
        textoDesde = new JLabel("Filtrar desde");
        textoDesde.setFont(new Font("Arial", Font.PLAIN, 15));
        textoDesde.setSize(120,40);
        textoDesde.setLocation(460, 40);
        add(textoDesde);
    }

    private void crearTablaDeVotaciones() {
        tVotaciones = new JTable(controlador.getModeloDeTabla());
        tVotaciones.setBounds(30, 40, 200, 300);
        tVotaciones.setRowHeight(60);
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

    private void crearBotonCrearVotacion() {
        bCrearVotacion = new JButton("Crear nueva votación");
        bCrearVotacion.setFont(new Font("Arial", Font.PLAIN, 15));
        bCrearVotacion.setSize(200, 50);
        bCrearVotacion.setLocation(350, 475);
        bCrearVotacion.setBackground(Color.BLACK);
        bCrearVotacion.setForeground(Color.WHITE);
        bCrearVotacion.addActionListener(this);
        add(bCrearVotacion);
    }

    private void crearBotonResultados() {
        bResultados = new JButton("Resultados");
        bResultados.setFont(new Font("Arial", Font.PLAIN, 15));
        bResultados.setSize(200, 50);
        bResultados.setLocation(645, 475);
        bResultados.setBackground(Color.BLACK);
        bResultados.setForeground(Color.WHITE);
        bResultados.addActionListener(this);
        add(bResultados);
    }

    private void crearBotonCerrarSesion() {
        bCerrarSesion = new JButton("Cerrar sesión");
        bCerrarSesion.setFont(new Font("Arial", Font.PLAIN, 15));
        bCerrarSesion.setSize(200, 50);
        bCerrarSesion.setLocation(40, 40);
        bCerrarSesion.setBackground(Color.BLACK);
        bCerrarSesion.setForeground(Color.WHITE);
        bCerrarSesion.addActionListener(this);
        add(bCerrarSesion);
    }

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

    private void configurarPanel() {
        setBackground(Color.WHITE);
        setLayout(null);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == bCerrarSesion) {
            controlador.cerrarSesionFueSolicitado();
        }
        if (e.getSource() == bResultados) {
            controlador.verResultadosFueSolicitado();
        }
        if (e.getSource() == checkEnCurso) {
            controlador.checkEnCursoFueEjecutado();
        }
        if (e.getSource() == checkFinalizadas) {
            controlador.checkFinalizadasFueEjecutado();
        }
        if (e.getSource() == checkPendientes) {
            controlador.checkPendientesFueEjecutado();
        }
        if (e.getSource() == checkBorradores) {
            controlador.checkBorradoresFueEjecutado();
        }
    }
}