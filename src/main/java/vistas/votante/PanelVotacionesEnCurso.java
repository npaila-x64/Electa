package vistas.votante;
import controladores.votante.ControladorVotacionesEnCurso;
import vistas.ButtonColumn;

import javax.swing.*;
import javax.swing.table.DefaultTableCellRenderer;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class PanelVotacionesEnCurso extends JPanel implements ActionListener {
    private JLabel lVotaciones;
    private JTable tVotacionesEnCurso;
    private JButton bCerrarSesion;
    private JButton bResultados;
    private final ControladorVotacionesEnCurso controlador;

    public PanelVotacionesEnCurso(ControladorVotacionesEnCurso controlador) {
        this.controlador = controlador;
        crearComponentes();
        setVisible(true);
    }

    private void crearComponentes() {
        configurarPanel();
        crearEtiquetaVotaciones();
        crearTablaDeVotaciones();
        crearBotonCerrarSesion();
        crearBotonResultados();
    }

    private void crearEtiquetaVotaciones() {
        lVotaciones = new JLabel("Votaciones en curso", SwingConstants.CENTER);
        lVotaciones.setFont(new Font("Arial", Font.BOLD, 28));
        lVotaciones.setSize(900, 32);
        lVotaciones.setLocation(0,32);
        add(lVotaciones);
    }

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

    private void configurarPanel() {
        setBackground(Color.WHITE);
        setLayout(null);
    }

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