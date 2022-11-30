package vistas.votante;

import controladores.votante.ControladorOpciones;
import javax.swing.*;
import javax.swing.table.DefaultTableCellRenderer;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class PanelOpciones extends JPanel implements ActionListener {

    private ControladorOpciones controlador;
    private JLabel lTitulo;
    private JLabel lDescripcion;
    private JTextArea campoTextoDescripcion;
    private JButton bVolver;
    private JButton bAbstencion;
    private JTable tOpciones;

    public PanelOpciones(ControladorOpciones controlador) {
        this.controlador = controlador;
        crearComponentes();
        setVisible(true);
    }

    private void crearComponentes() {
        configurarPanel();
        crearEtiquetaTitulo();
        crearEtiquetaDescripcion();
        crearTablaDeOpciones();
        crearBotonVolver();
        crearBotonAbstencion();
        crearCampoDeTextoDescripcion();
    }

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

    private void crearEtiquetaTitulo() {
        lTitulo = new JLabel();
        lTitulo.setFont(new Font("Arial", Font.BOLD, 26));
        lTitulo.setSize(900, 70);
        lTitulo.setHorizontalAlignment(JLabel.CENTER);
        lTitulo.setVerticalAlignment(JLabel.CENTER);
        add(lTitulo);
    }

    private void crearEtiquetaDescripcion() {
        lDescripcion = new JLabel("Descripción");
        lDescripcion.setFont(new Font("Arial", Font.PLAIN, 16));
        lDescripcion.setLocation(148, 112);
        lDescripcion.setSize(119, 25);
        add(lDescripcion);
    }

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

    private void configurarPanel() {
        this.setBackground(Color.WHITE);
        this.setLayout(null);
    }

    public void setTituloDeVotacion(String titulo) {
        lTitulo.setText(titulo);
    }

    public void setlDescripcionDeVotacion(String descripcion) {
        campoTextoDescripcion.setText(descripcion);
    }

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