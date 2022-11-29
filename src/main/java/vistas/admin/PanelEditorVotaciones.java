package vistas.admin;

import controladores.admin.ControladorEditorDeVotacion;
import modelos.Votacion;
import vistas.votante.ButtonColumn;

import javax.swing.*;
import javax.swing.table.DefaultTableCellRenderer;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class PanelEditorVotaciones extends JPanel implements ActionListener {

    private ControladorEditorDeVotacion controlador;
    private JLabel textoEncabezado;
    private JLabel textoTitulo;
    private JLabel textoDescripcion;
    private JLabel textoFechaInicio;
    private JLabel textoFechaTermino;
    private JTextField campoTextoFechaInicio;
    private JTextField campoTextoFechaTermino;
    private JTextField campoTextoTitulo;
    private JTextArea campoTextoDescripcion;
    private JButton bAgregarOpcion;
    private JButton bGuardar;
    private JButton bCancelar;
    private JButton bEliminarVotacion;
    private JTable tOpciones;
    private JScrollPane scroll;
    private JPanel panel;
    private JLabel textoHoraInicio;
    private JLabel textoHoraTermino;
    private JLabel textoOpciones;
    private JTextField campoTextoHoraInicio;
    private JTextField campoTextoHoraTermino;

    public PanelEditorVotaciones(ControladorEditorDeVotacion controlador) {
        this.controlador = controlador;
        crearComponentes();
    }

    private void crearComponentes() {
        configurarPanel();
        crearEtiquetaEncabezado();
        crearEtiquetaTitulo();
        crearEtiquetaDescripcion();
        crearEtiquetaFechaInicio();
        crearEtiquetaHoraInicio();
        crearEtiquetaFechaTermino();
        crearEtiquetaHoraTermino();
        crearEtiquetaOpciones();
        crearCampoDeTextoTitulo();
        crearAreaDeTextoDescripcion();
        crearCampoDeTextoFechaInicio();
        crearCampoDeTextoFechaTermino();
        crearCampoDeTextoHoraInicio();
        crearCampoDeTextoHoraTermino();
        crearBotonAgregarOpcion();
        crearBotonGuardar();
        crearBotonCancelar();
        crearBotonEliminarVotacion();
        crearTablaDeVotaciones();
    }

    private void crearCampoDeTextoHoraTermino() {
        campoTextoHoraTermino = new JTextField();
        campoTextoHoraTermino.setFont(new Font("Arial", Font.PLAIN, 15));
        campoTextoHoraTermino.setLocation(501, 434);
        campoTextoHoraTermino.setSize(141, 40);
        campoTextoHoraTermino.setBorder(BorderFactory.createLineBorder(Color.BLACK));
        panel.add(campoTextoHoraTermino);
    }

    private void crearCampoDeTextoHoraInicio() {
        campoTextoHoraInicio = new JTextField();
        campoTextoHoraInicio.setFont(new Font("Arial", Font.PLAIN, 15));
        campoTextoHoraInicio.setLocation(501, 350);
        campoTextoHoraInicio.setSize(141, 40);
        campoTextoHoraInicio.setBorder(BorderFactory.createLineBorder(Color.BLACK));
        panel.add(campoTextoHoraInicio);
    }

    private void crearEtiquetaHoraTermino() {
        textoHoraTermino = new JLabel("Hora de término");
        textoHoraTermino.setFont(new Font("Arial", Font.PLAIN, 16));
        textoHoraTermino.setLocation(501,401);
        textoHoraTermino.setSize(119,36);
        panel.add(textoHoraTermino);
    }

    private void crearEtiquetaOpciones() {
        textoOpciones = new JLabel("Opciones");
        textoOpciones.setFont(new Font("Arial", Font.BOLD, 20));
        textoOpciones.setLocation(188,487);
        textoOpciones.setSize(110,47);
        panel.add(textoOpciones);
    }

    private void crearTablaDeVotaciones() {
        tOpciones = new JTable(controlador.getModeloDeTabla());
        tOpciones.setBounds(30, 40, 200, 300);
        tOpciones.setRowHeight(30);
        tOpciones.setTableHeader(null);
        tOpciones.setCellSelectionEnabled(false);
        tOpciones.getColumnModel().getColumn(0).setPreferredWidth(500);
        tOpciones.getColumnModel().getColumn(1).setPreferredWidth(95);
        tOpciones.setFont(new Font("Arial", Font.PLAIN, 14));
        DefaultTableCellRenderer renderer = new DefaultTableCellRenderer() {
            @Override
            public Component getTableCellRendererComponent(JTable table,
                                                           Object value, boolean isSelected, boolean hasFocus,
                                                           int row, int column) {
                super.getTableCellRendererComponent(table, value, isSelected, hasFocus,
                        row, column);
                setBorder(BorderFactory.createCompoundBorder(getBorder(),
                        BorderFactory.createEmptyBorder(0, 10, 0, 10)));
                return this;
            }
        };
        tOpciones.setDefaultRenderer(String.class, renderer);

        JScrollPane scrollPane = new JScrollPane(tOpciones);
        scrollPane.setSize(512, 172);
        scrollPane.setLocation(188, 534);
        panel.add(scrollPane);

        Action editarVotacion = new AbstractAction() {
            public void actionPerformed(ActionEvent e) {
                int fila = Integer.parseInt(e.getActionCommand());
                controlador.eliminarOpcionFueSolicitado(fila);
            }
        };
        new ButtonColumn(tOpciones, editarVotacion, 1);
    }

    private void crearBotonEliminarVotacion() {
        bEliminarVotacion = new JButton("Eliminar");
        bEliminarVotacion.setFont(new Font("Arial", Font.BOLD, 15));
        bEliminarVotacion.setLocation(24, 31);
        bEliminarVotacion.setSize(163, 37);
        bEliminarVotacion.setBackground(Color.RED);
        bEliminarVotacion.setForeground(Color.WHITE);
        panel.add(bEliminarVotacion);
    }

    private void crearBotonCancelar() {
        bCancelar = new JButton("Cancelar");
        bCancelar.setFont(new Font("Arial", Font.PLAIN, 15));
        bCancelar.setLocation(33, 809);
        bCancelar.setSize(146, 47);
        bCancelar.setBackground(Color.BLACK);
        bCancelar.setForeground(Color.WHITE);
        bCancelar.addActionListener(this);
        panel.add(bCancelar);
    }

    private void crearBotonAgregarOpcion() {
        bAgregarOpcion = new JButton("Agregar opción");
        bAgregarOpcion.setFont(new Font("Arial", Font.PLAIN, 15));
        bAgregarOpcion.setLocation(352, 723);
        bAgregarOpcion.setSize(182, 48);
        bAgregarOpcion.setBackground(Color.BLACK);
        bAgregarOpcion.setForeground(Color.WHITE);
        bAgregarOpcion.addActionListener(this);
        panel.add(bAgregarOpcion);
    }

    private void crearBotonGuardar() {
        bGuardar = new JButton("Guardar");
        bGuardar.setFont(new Font("Arial", Font.PLAIN, 15));
        bGuardar.setSize(200, 50);
        bGuardar.setLocation(343, 809);
        bGuardar.setBackground(Color.BLACK);
        bGuardar.setForeground(Color.WHITE);
        bGuardar.addActionListener(this);
        panel.add(bGuardar);
    }

    private void crearCampoDeTextoTitulo() {
        campoTextoTitulo = new JTextField();
        campoTextoTitulo.setLocation(187, 125);
        campoTextoTitulo.setSize(512, 40);
        campoTextoTitulo.setFont(new Font("Arial", Font.PLAIN, 15));
        campoTextoTitulo.setBorder(BorderFactory.createLineBorder(Color.BLACK));
        campoTextoTitulo.setBorder(BorderFactory.createCompoundBorder(
                campoTextoTitulo.getBorder(),
                BorderFactory.createEmptyBorder(5, 10, 5, 5)));
        panel.add(campoTextoTitulo);
    }

    private void crearAreaDeTextoDescripcion() {
        campoTextoDescripcion = new JTextArea();
        campoTextoDescripcion.setLocation(187, 207);
        campoTextoDescripcion.setSize(512,93);
        campoTextoDescripcion.setFont(new Font("Arial", Font.PLAIN, 15));
        campoTextoDescripcion.setBorder(BorderFactory.createLineBorder(Color.BLACK));
        campoTextoDescripcion.setLineWrap(true);
        campoTextoDescripcion.setWrapStyleWord(true);
        campoTextoDescripcion.setBorder(BorderFactory.createCompoundBorder(
                campoTextoDescripcion.getBorder(),
                BorderFactory.createEmptyBorder(10, 10, 5, 10)));
        panel.add(campoTextoDescripcion);
    }

    private void crearCampoDeTextoFechaInicio() {
        campoTextoFechaInicio = new JTextField();
        campoTextoFechaInicio.setFont(new Font("Arial", Font.PLAIN, 15));
        campoTextoFechaInicio.setLocation(245, 353);
        campoTextoFechaInicio.setSize(140, 40);
        campoTextoFechaInicio.setBorder(BorderFactory.createLineBorder(Color.BLACK));
        panel.add(campoTextoFechaInicio);
    }

    private void crearCampoDeTextoFechaTermino() {
        campoTextoFechaTermino = new JTextField();
        campoTextoFechaTermino.setFont(new Font("Arial", Font.PLAIN, 15));
        campoTextoFechaTermino.setLocation(245, 432);
        campoTextoFechaTermino.setSize(140, 40);
        campoTextoFechaTermino.setBorder(BorderFactory.createLineBorder(Color.BLACK));
        panel.add(campoTextoFechaTermino);
    }

    private void crearEtiquetaFechaTermino() {
        textoFechaTermino = new JLabel("Fecha de termino");
        textoFechaTermino.setFont(new Font("Arial", Font.PLAIN, 16));
        textoFechaTermino.setLocation(245,402);
        textoFechaTermino.setSize(151,23);
        panel.add(textoFechaTermino);
    }

    private void crearEtiquetaHoraInicio() {
        textoHoraInicio = new JLabel("Hora de Inicio");
        textoHoraInicio.setFont(new Font("Arial", Font.PLAIN, 16));
        textoHoraInicio.setLocation(501,321);
        textoHoraInicio.setSize(119,29);
        panel.add(textoHoraInicio);
    }

    private void crearEtiquetaFechaInicio() {
        textoFechaInicio = new JLabel("Fecha de inicio");
        textoFechaInicio.setFont(new Font("Arial", Font.PLAIN, 16));
        textoFechaInicio.setLocation(245,320);
        textoFechaInicio.setSize(119,29);
        panel.add(textoFechaInicio);
    }

    private void crearEtiquetaDescripcion() {
        textoDescripcion = new JLabel("Descripción");
        textoDescripcion.setFont(new Font("Arial", Font.PLAIN, 16));
        textoDescripcion.setLocation(187,167);
        textoDescripcion.setSize(110, 40);
        panel.add(textoDescripcion);
    }

    private void crearEtiquetaTitulo() {
        textoTitulo = new JLabel("Titulo");
        textoTitulo.setFont(new Font("Arial", Font.PLAIN, 16));
        textoTitulo.setLocation(187,85);
        textoTitulo.setSize(110, 40);
        panel.add(textoTitulo);
    }

    private void crearEtiquetaEncabezado() {
        textoEncabezado = new JLabel("Editor de votaciones");
        textoEncabezado.setFont(new Font("Arial", Font.BOLD, 26));
        textoEncabezado.setLocation(307,19);
        textoEncabezado.setSize(275, 60);
        panel.add(textoEncabezado);
    }

    private void configurarPanel() {
        setBackground(Color.WHITE);
        setLayout(null);
        panel = new JPanel();
        panel.setBackground(Color.WHITE);
        scroll = new JScrollPane(panel, JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED, JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
        scroll.setBounds(0, 0, 890, 600);
        scroll.getVerticalScrollBar().setUnitIncrement(20);
        scroll.getVerticalScrollBar().setPreferredSize(new Dimension(18,0));
        panel.setLayout(null);
        panel.setPreferredSize(new Dimension(400,915));
        add(scroll);
    }

    @Override
    public Dimension getPreferredSize() {
        return new Dimension(900, 800);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == bCancelar) {
            controlador.cancelarFueSolicitado();
        }
        if (e.getSource() == bGuardar) {
            controlador.guardarFueSolicitado();
        }
    }

    public void cargarVotacion(Votacion votacion) {
        campoTextoTitulo.setText(votacion.getTitulo());
        campoTextoDescripcion.setText(votacion.getDescripcion());
        campoTextoFechaInicio.setText(votacion.getFechaInicio().toString());
        campoTextoFechaTermino.setText(votacion.getFechaTermino().toString());
    }
}