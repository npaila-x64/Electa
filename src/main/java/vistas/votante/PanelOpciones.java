package vistas.votante;

import controladores.votante.ControladorVotacion;
import javax.swing.*;
import javax.swing.table.DefaultTableCellRenderer;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class PanelOpciones extends JPanel implements ActionListener {

    private ControladorVotacion controlador;
    private JLabel lOpciones;
    private JButton bVolver;
    private JButton bAbstencion;
    private JTable tOpciones;

    public PanelOpciones(ControladorVotacion controlador) {
        this.controlador = controlador;
        crearComponentes();
        setVisible(true);
    }

    private void crearComponentes() {
        configurarPanel();
        crearEtiquetaOpciones();
        crearTablaDeOpciones();
        crearBotonVolver();
        crearBotonAbstencion();
    }

    private void crearEtiquetaOpciones() {
        lOpciones = new JLabel("Opciones");
        lOpciones.setFont(new Font("Arial", Font.BOLD, 28));
        lOpciones.setSize(274, 32);
        lOpciones.setLocation(379, 32);
        this.add(lOpciones);
    }

    private void crearBotonAbstencion() {
        bAbstencion = new JButton("Abstención");
        bAbstencion.setFont(new Font("Arial", Font.PLAIN, 15));
        bAbstencion.setSize(200, 50);
        bAbstencion.setLocation(645, 475);
        bAbstencion.setBackground(Color.WHITE);
        bAbstencion.setForeground(Color.BLACK);
        bAbstencion.addActionListener(this);
        this.add(bAbstencion);
    }

    private void crearBotonVolver() {
        bVolver = new JButton("Volver");
        bVolver.setFont(new Font("Arial", Font.PLAIN, 15));
        bVolver.setSize(200, 50);
        bVolver.setLocation(350, 475);
        bVolver.setBackground(Color.BLACK);
        bVolver.setForeground(Color.WHITE);
        bVolver.addActionListener(this);
        this.add(bVolver);
    }

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
        scrollPane.setSize(805, 345);
        scrollPane.setLocation(40, 85);
        this.add(scrollPane);

        Action abrirVotacion = new AbstractAction() {
            public void actionPerformed(ActionEvent e) {
                int fila = Integer.parseInt(e.getActionCommand());
                controlador.votarPorOpcionFueSolicitado(fila);
            }
        };
        new ButtonColumn(tOpciones, abrirVotacion, 1);
    }

    private void configurarPanel() {
        this.setBackground(Color.WHITE);
        this.setLayout(null);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == bVolver) {
            controlador.volverFueSolicitado();
        }
    }
}