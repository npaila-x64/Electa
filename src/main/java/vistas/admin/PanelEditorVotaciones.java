package vistas.admin;

import controladores.admin.ControladorEditorDeVotacion;
import modelos.Votacion;
import org.jdatepicker.impl.JDatePanelImpl;
import org.jdatepicker.impl.JDatePickerImpl;
import org.jdatepicker.impl.UtilDateModel;
import vistas.votante.ButtonColumn;

import javax.swing.*;
import javax.swing.table.DefaultTableCellRenderer;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.text.SimpleDateFormat;
import java.time.LocalTime;
import java.util.Calendar;
import java.util.Date;
import java.util.Properties;

public class PanelEditorVotaciones extends JPanel implements ActionListener {

    private ControladorEditorDeVotacion controlador;
    private JLabel textoEncabezado;
    private JLabel textoTitulo;
    private JLabel textoDescripcion;
    private JLabel textoFechaInicio;
    private JLabel textoFechaTermino;
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
    private JDatePickerImpl datePickerFechaInicio;
    private JDatePickerImpl datePickerFechaTermino;
    private JSpinner spinnerHoraInicio;
    private JSpinner spinnerMinutosInicio;
    private JSpinner spinnerMinutosTermino;
    private JSpinner spinnerHoraTermino;

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
        crearEtiquetaSeparadorHoraTermino();
        crearEtiquetaSeparadorHoraInicio();
        crearAreaDeTextoDescripcion();
        crearCampoDeTextoTitulo();
        crearDatePickerFechaInicio();
        crearDatePickerFechaTermino();
        crearSpinnerHoraInicio();
        crearSpinnerMinutosInicio();
        crearSpinnerHoraTermino();
        crearSpinnerMinutosTermino();
        crearBotonAgregarOpcion();
        crearBotonGuardar();
        crearBotonCancelar();
        crearBotonEliminarVotacion();
        crearTablaDeVotaciones();
    }

    private void crearEtiquetaSeparadorHoraTermino() {
        JLabel separador = new JLabel(":");
        separador.setFont(new Font("Arial", Font.BOLD, 24));
        separador.setLocation(566,438);
        separador.setSize(29,29);
        panel.add(separador);
    }

    private void crearEtiquetaSeparadorHoraInicio() {
        JLabel separador = new JLabel(":");
        separador.setFont(new Font("Arial", Font.BOLD, 24));
        separador.setLocation(566,354);
        separador.setSize(29,29);
        panel.add(separador);
    }

    private void crearSpinnerHoraInicio() {
        SpinnerDateModel model = new SpinnerDateModel();
        model.setCalendarField(Calendar.HOUR_OF_DAY);
        spinnerHoraInicio = new JSpinner(model);
        spinnerHoraInicio.setEditor(new JSpinner.DateEditor(spinnerHoraInicio, "HH"));
        spinnerHoraInicio.setLocation(501, 350);
        spinnerHoraInicio.setSize(55, 40);
        JTextField textField = ((JSpinner.DefaultEditor) spinnerHoraInicio.getEditor()).getTextField();
        textField.setHorizontalAlignment(SwingConstants.RIGHT);
        textField.setFocusable(false);
        textField.setEditable(false);
        textField.setBackground(Color.WHITE);
        textField.setFont(new Font("Arial", Font.PLAIN, 15));
        textField.setBorder(BorderFactory.createLineBorder(Color.BLACK, 2));
        panel.add(spinnerHoraInicio);
    }

    private void crearSpinnerMinutosInicio() {
        SpinnerDateModel model = new SpinnerDateModel();
        model.setCalendarField(Calendar.MINUTE);
        spinnerMinutosInicio = new JSpinner(model);
        spinnerMinutosInicio.setEditor(new JSpinner.DateEditor(spinnerMinutosInicio, "mm"));
        spinnerMinutosInicio.setLocation(587, 350);
        spinnerMinutosInicio.setSize(55, 40);
        JTextField textField = ((JSpinner.DefaultEditor) spinnerMinutosInicio.getEditor()).getTextField();
        textField.setHorizontalAlignment(SwingConstants.RIGHT);
        textField.setFocusable(false);
        textField.setEditable(false);
        textField.setBackground(Color.WHITE);
        textField.setFont(new Font("Arial", Font.PLAIN, 15));
        textField.setBorder(BorderFactory.createLineBorder(Color.BLACK, 2));
        panel.add(spinnerMinutosInicio);
    }

    private void crearSpinnerHoraTermino() {
        SpinnerDateModel model = new SpinnerDateModel();
        model.setCalendarField(Calendar.HOUR_OF_DAY);
        spinnerHoraTermino = new JSpinner(model);
        spinnerHoraTermino.setEditor(new JSpinner.DateEditor(spinnerHoraTermino, "HH"));
        spinnerHoraTermino.setLocation(501, 434);
        spinnerHoraTermino.setSize(55, 40);
        JTextField textField = ((JSpinner.DefaultEditor) spinnerHoraTermino.getEditor()).getTextField();
        textField.setHorizontalAlignment(SwingConstants.RIGHT);
        textField.setFocusable(false);
        textField.setEditable(false);
        textField.setBackground(Color.WHITE);
        textField.setFont(new Font("Arial", Font.PLAIN, 15));
        textField.setBorder(BorderFactory.createLineBorder(Color.BLACK, 2));
        panel.add(spinnerHoraTermino);
    }

    private void crearSpinnerMinutosTermino() {
        SpinnerDateModel model = new SpinnerDateModel();
        model.setCalendarField(Calendar.MINUTE);
        spinnerMinutosTermino = new JSpinner(model);
        spinnerMinutosTermino.setEditor(new JSpinner.DateEditor(spinnerMinutosTermino, "mm"));
        spinnerMinutosTermino.setLocation(587, 434);
        spinnerMinutosTermino.setSize(55, 40);
        JTextField textField = ((JSpinner.DefaultEditor) spinnerMinutosTermino.getEditor()).getTextField();
        textField.setHorizontalAlignment(SwingConstants.RIGHT);
        textField.setFocusable(false);
        textField.setEditable(false);
        textField.setBackground(Color.WHITE);
        textField.setFont(new Font("Arial", Font.PLAIN, 15));
        textField.setBorder(BorderFactory.createLineBorder(Color.BLACK, 2));
        panel.add(spinnerMinutosTermino);
    }

    private void crearDatePickerFechaInicio() {
        UtilDateModel model = new UtilDateModel();
        Properties p = new Properties();
        p.put("text.today", "Hoy");
        p.put("text.month", "Mes");
        p.put("text.year", "Año");
        JDatePanelImpl datePanel = new JDatePanelImpl(model, p);
        datePickerFechaInicio = new JDatePickerImpl(datePanel, new DateLabelFormatter());
        datePickerFechaInicio.setLocation(245, 353);
        datePickerFechaInicio.setSize(141, 40);
        datePickerFechaInicio.setBackground(Color.WHITE);
        datePickerFechaInicio.setButtonFocusable(false);
        JFormattedTextField ftf = datePickerFechaInicio.getJFormattedTextField();
        ftf.setFont(new Font("Arial", Font.PLAIN, 15));
        ftf.setPreferredSize(new Dimension(141, 40));
        ftf.setBackground(Color.WHITE);
        ftf.setBorder(BorderFactory.createLineBorder(Color.BLACK, 2));
        ftf.setBorder(BorderFactory.createCompoundBorder(
                ftf.getBorder(),
                BorderFactory.createEmptyBorder(10, 12, 10, 10)));
        panel.add(datePickerFechaInicio);
    }
    private void crearDatePickerFechaTermino() {
        UtilDateModel model = new UtilDateModel();
        Properties propiedadesDatePicker = new Properties();
        propiedadesDatePicker.put("text.today", "Hoy");
        propiedadesDatePicker.put("text.month", "Mes");
        propiedadesDatePicker.put("text.year", "Año");
        JDatePanelImpl datePanel = new JDatePanelImpl(model, propiedadesDatePicker);
        datePickerFechaTermino = new JDatePickerImpl(datePanel, new DateLabelFormatter());
        datePickerFechaTermino.setLocation(245, 432);
        datePickerFechaTermino.setSize(141, 40);
        datePickerFechaTermino.setBackground(Color.WHITE);
        datePickerFechaTermino.setButtonFocusable(false);
        JFormattedTextField ftf = datePickerFechaTermino.getJFormattedTextField();
        ftf.setFont(new Font("Arial", Font.PLAIN, 15));
        ftf.setPreferredSize(new Dimension(141, 40));
        ftf.setBackground(Color.WHITE);
        ftf.setBorder(BorderFactory.createLineBorder(Color.BLACK, 2));
        ftf.setBorder(BorderFactory.createCompoundBorder(
                ftf.getBorder(),
                BorderFactory.createEmptyBorder(10, 12, 10, 10)));
        panel.add(datePickerFechaTermino);
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
        scrollPane.setBorder(BorderFactory.createLineBorder(Color.BLACK, 2));
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
        bEliminarVotacion = new JButton("Eliminar votación");
        bEliminarVotacion.setFont(new Font("Arial", Font.BOLD, 15));
        bEliminarVotacion.setLocation(683, 809);
        bEliminarVotacion.setSize(174, 46);
        bEliminarVotacion.setBackground(Color.RED);
        bEliminarVotacion.setForeground(Color.WHITE);
        panel.add(bEliminarVotacion);
    }

    private void crearBotonCancelar() {
        bCancelar = new JButton("Cancelar");
        bCancelar.setFont(new Font("Arial", Font.BOLD, 15));
        bCancelar.setLocation(33, 809);
        bCancelar.setSize(146, 47);
        bCancelar.setBackground(Color.BLACK);
        bCancelar.setForeground(Color.WHITE);
        bCancelar.addActionListener(this);
        panel.add(bCancelar);
    }

    private void crearBotonAgregarOpcion() {
        bAgregarOpcion = new JButton("Agregar nueva opción");
        bAgregarOpcion.setFont(new Font("Arial", Font.BOLD, 15));
        bAgregarOpcion.setLocation(340, 723);
        bAgregarOpcion.setSize(206, 40);
        bAgregarOpcion.setBackground(Color.BLACK);
        bAgregarOpcion.setForeground(Color.WHITE);
        bAgregarOpcion.addActionListener(this);
        panel.add(bAgregarOpcion);
    }

    private void crearBotonGuardar() {
        bGuardar = new JButton("Guardar");
        bGuardar.setFont(new Font("Arial", Font.BOLD, 15));
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
        campoTextoTitulo.setBorder(BorderFactory.createLineBorder(Color.BLACK, 2));
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
        campoTextoDescripcion.setBorder(BorderFactory.createLineBorder(Color.BLACK, 2));
        campoTextoDescripcion.setLineWrap(true);
        campoTextoDescripcion.setWrapStyleWord(true);
        campoTextoDescripcion.setBorder(BorderFactory.createCompoundBorder(
                campoTextoDescripcion.getBorder(),
                BorderFactory.createEmptyBorder(10, 10, 5, 10)));
        panel.add(campoTextoDescripcion);
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
        scroll.getVerticalScrollBar().setUnitIncrement(25);
        scroll.getVerticalScrollBar().setPreferredSize(new Dimension(18,0));
        panel.setLayout(null);
        panel.setPreferredSize(new Dimension(400,915));
        add(scroll);
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
        var fechaInicio = votacion.getFechaInicio();
        datePickerFechaInicio.getModel().setDate(
                fechaInicio.getYear(),
                fechaInicio.getDayOfMonth(),
                fechaInicio.getDayOfMonth());
        datePickerFechaInicio.getModel().setSelected(true);
        var fechaTermino = votacion.getFechaTermino();
        datePickerFechaTermino.getModel().setDate(
                fechaTermino.getYear(),
                fechaTermino.getDayOfMonth(),
                fechaTermino.getDayOfMonth());
        datePickerFechaTermino.getModel().setSelected(true);
//        var horaInicio = votacion.getTiempoInicio();
//        spinnerHoraInicio.getModel().setValue(horaInicio.getHour());
//        spinnerMinutosInicio.getModel().setValue(horaInicio.getMinute());
//        var horaTermino = votacion.getTiempoTermino();
//        spinnerHoraTermino.getModel().setValue(horaTermino.getHour());
//        spinnerMinutosTermino.getModel().setValue(horaTermino.getMinute());
    }

    public void moverScrollAInicio() {
        scroll.getViewport().setViewPosition(new Point(0,0));
    }
}