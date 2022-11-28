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

    public PanelEditorVotaciones(ControladorEditorDeVotacion controlador) {
        this.controlador = controlador;
        crearComponentes();
        setVisible(true);
    }

    private void crearComponentes() {
        configurarPanel();
        crearEtiquetaEncabezado();
        crearEtiquetaTitulo();
        crearEtiquetaDescripcion();
        crearEtiquetaFechaInicio();
        crearEtiquetaFechaTermino();
        crearCampoDeTextoTitulo();
        crearAreaDeTextoDescripcion();
        crearCampoDeTextoFechaInicio();
        crearCampoDeTextoFechaTermino();
        crearBotonAgregarOpcion();
        crearBotonGuardar();
        crearBotonCancelar();
        crearBotonEliminarVotacion();
        crearTablaDeVotaciones();
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
        scrollPane.setSize(595, 200);
        scrollPane.setLocation(40, 245);
        add(scrollPane);

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
        bEliminarVotacion.setSize(200, 50);
        bEliminarVotacion.setLocation(645, 475);
        bEliminarVotacion.setBackground(Color.RED);
        bEliminarVotacion.setForeground(Color.WHITE);
        add(bEliminarVotacion);
    }

    private void crearBotonCancelar() {
        bCancelar = new JButton("Cancelar");
        bCancelar.setFont(new Font("Arial", Font.PLAIN, 15));
        bCancelar.setSize(200, 50);
        bCancelar.setLocation(40, 475);
        bCancelar.setBackground(Color.BLACK);
        bCancelar.setForeground(Color.WHITE);
        bCancelar.addActionListener(this);
        add(bCancelar);
    }

    private void crearBotonAgregarOpcion() {
        bAgregarOpcion = new JButton("Agregar opción");
        bAgregarOpcion.setFont(new Font("Arial", Font.PLAIN, 15));
        bAgregarOpcion.setSize(200, 50);
        bAgregarOpcion.setLocation(645, 245);
        bAgregarOpcion.setBackground(Color.BLACK);
        bAgregarOpcion.setForeground(Color.WHITE);
        bAgregarOpcion.addActionListener(this);
        add(bAgregarOpcion);
    }

    private void crearBotonGuardar() {
        bGuardar = new JButton("Guardar");
        bGuardar.setFont(new Font("Arial", Font.PLAIN, 15));
        bGuardar.setSize(200, 50);
        bGuardar.setLocation(435, 475);
        bGuardar.setBackground(Color.BLACK);
        bGuardar.setForeground(Color.WHITE);
        bGuardar.addActionListener(this);
        add(bGuardar);
    }

    private void crearCampoDeTextoTitulo() {
        campoTextoTitulo = new JTextField();
        campoTextoTitulo.setSize(485, 40);
        campoTextoTitulo.setLocation(150, 48);
        campoTextoTitulo.setFont(new Font("Arial", Font.PLAIN, 15));
        campoTextoTitulo.setBorder(BorderFactory.createLineBorder(Color.BLACK));
        campoTextoTitulo.setBorder(BorderFactory.createCompoundBorder(
                campoTextoTitulo.getBorder(),
                BorderFactory.createEmptyBorder(5, 10, 5, 5)));
        add(campoTextoTitulo);
    }

    private void crearAreaDeTextoDescripcion() {
        campoTextoDescripcion = new JTextArea();
        campoTextoDescripcion.setSize(485,93);
        campoTextoDescripcion.setLocation(150, 98);
        campoTextoDescripcion.setFont(new Font("Arial", Font.PLAIN, 15));
        campoTextoDescripcion.setBorder(BorderFactory.createLineBorder(Color.BLACK));
        campoTextoDescripcion.setLineWrap(true);
        campoTextoDescripcion.setWrapStyleWord(true);
        campoTextoDescripcion.setBorder(BorderFactory.createCompoundBorder(
                campoTextoDescripcion.getBorder(),
                BorderFactory.createEmptyBorder(10, 10, 5, 10)));
        add(campoTextoDescripcion);
    }

    private void crearCampoDeTextoFechaInicio() {
        campoTextoFechaInicio = new JTextField();
        campoTextoFechaInicio.setFont(new Font("Arial", Font.PLAIN, 15));
        campoTextoFechaInicio.setSize(100, 35);
        campoTextoFechaInicio.setLocation(150, 200);
        campoTextoFechaInicio.setBorder(BorderFactory.createLineBorder(Color.BLACK));
        add(campoTextoFechaInicio);
    }

    private void crearCampoDeTextoFechaTermino() {
        campoTextoFechaTermino = new JTextField();
        campoTextoFechaTermino.setFont(new Font("Arial", Font.PLAIN, 15));
        campoTextoFechaTermino.setSize(100, 35);
        campoTextoFechaTermino.setLocation(400, 200);
        campoTextoFechaTermino.setBorder(BorderFactory.createLineBorder(Color.BLACK));
        add(campoTextoFechaTermino);
    }

    private void crearEtiquetaFechaTermino() {
        textoFechaTermino = new JLabel("Fecha de termino:");
        textoFechaTermino.setFont(new Font("Arial", Font.PLAIN, 14));
        textoFechaTermino.setSize(130,40);
        textoFechaTermino.setLocation(275,200);
        add(textoFechaTermino);
    }

    private void crearEtiquetaFechaInicio() {
        textoFechaInicio = new JLabel("Fecha de inicio:");
        textoFechaInicio.setFont(new Font("Arial", Font.PLAIN, 14));
        textoFechaInicio.setSize(130,40);
        textoFechaInicio.setLocation(40,200);
        add(textoFechaInicio);
    }

    private void crearEtiquetaDescripcion() {
        textoDescripcion = new JLabel("Descripción:");
        textoDescripcion.setFont(new Font("Arial", Font.PLAIN, 14));
        textoDescripcion.setSize(120,40);
        textoDescripcion.setLocation(40,98);
        add(textoDescripcion);
    }

    private void crearEtiquetaTitulo() {
        textoTitulo = new JLabel("Titulo:");
        textoTitulo.setFont(new Font("Arial", Font.PLAIN, 14));
        textoTitulo.setSize(100, 40);
        textoTitulo.setLocation(40,48);
        add(textoTitulo);
    }

    private void crearEtiquetaEncabezado() {
        textoEncabezado = new JLabel("Editor de votaciones");
        textoEncabezado.setFont(new Font("Arial", Font.BOLD, 16));
        textoEncabezado.setLocation(0,20);
        textoEncabezado.setSize(900, 18);
        textoEncabezado.setHorizontalAlignment(JLabel.CENTER);
        add(textoEncabezado);
    }

    private void configurarPanel() {
        setBackground(Color.WHITE);
        setLayout(null);
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