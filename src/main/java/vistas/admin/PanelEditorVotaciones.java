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
import java.util.Properties;
/**
 * Crea el panel que representa la vista del editor de votaciones.
 */
public class PanelEditorVotaciones extends JPanel implements ActionListener {
    /**
     * Indica el controlador que administra la lógica de la vista.
     */
    private ControladorEditorDeVotacion controlador;
    /**
     * Indica una etiqueta visible en el encabezado de la vista.
     */
    private JLabel textoEncabezado;
    /**
     * Indica una etiqueta asociada al título de una votación.
     */
    private JLabel textoTitulo;
    /**
     * Indica una etiqueta asociada a la descripción de una votación.
     */
    private JLabel textoDescripcion;
    /**
     * Indica una etiqueta asociada a la fecha y hora de inicio de una votación.
     */
    private JLabel textoFechaInicio;
    /**
     * Indica una etiqueta asociada a la fecha y hora de término de una votación.
     */
    private JLabel textoFechaTermino;
    /**
     * Indica una etiqueta asociada al estado de una votación.
     */
    private JLabel textoEstado;
    /**
     * Indica un campo de texto asociado al título de una votación.
     */
    private JTextField campoTextoTitulo;
    /**
     * Indica un campo de texto asociado al estado de una votación.
     */
    private JTextField campoTextoEstado;
    /**
     * Indica un campo de texto asociado a la descripción de una votación.
     */
    private JTextArea campoTextoDescripcion;
    /**
     * Indica un botón para agregar una opción.
     */
    private JButton bAgregarOpcion;
    /**
     * Indica un botón para guardar.
     */
    private JButton bGuardar;
    /**
     * Indica un botón para volver.
     */
    private JButton bVolver;
    /**
     * Indica un botón para poner en marcha.
     */
    private JButton bPonerEnMarcha;
    /**
     * Indica una tabla de opciones de una votación.
     */
    private JTable tOpciones;
    /**
     * Indica un contenedor con scroll para la tabla.
     */
    private JScrollPane scroll;
    /**
     * Indica el panel actual.
     */
    private JPanel panel;
    /**
     * Indica una etiqueta asociada a la hora de inicio de una votación.
     */
    private JLabel textoHoraInicio;
    /**
     * Indica una etiqueta asociada a la hora de término de una votación.
     */
    private JLabel textoHoraTermino;
    /**
     * Indica una etiqueta asociada a las opciones de una votación.
     */
    private JLabel textoOpciones;
    /**
     * Indica un calendario desplegable asociado a la fecha de inicio de una votación.
     */
    private JDatePickerImpl datePickerFechaInicio;
    /**
     * Indica un calendario desplegable asociado a la fecha de término de una votación.
     */
    private JDatePickerImpl datePickerFechaTermino;
    /**
     * Indica un spinner asociado a la hora de inicio de una votación.
     */
    private JSpinner spinnerHoraInicio;
    /**
     * Indica un spinner asociado a el minuto de inicio de la votación.
     */
    private JSpinner spinnerMinutosInicio;
    /**
     * Indica un spinner asociado a el minuto de término de la votación.
     */
    private JSpinner spinnerMinutosTermino;
    /**
     * Indica un spinner asociado a la hora de término de la votación.
     */
    private JSpinner spinnerHoraTermino;

    /**
     * Construye un panel de tipo PanelEditorVotaciones. Instancia el controlador y los componentes que
     * forman parte del panel.
     * @param controlador el controlador que maneja la lógica tras el panel.
     */
    public PanelEditorVotaciones(ControladorEditorDeVotacion controlador) {
        this.controlador = controlador;
        crearComponentes();
    }
    /**
     * Crea cada uno de los componentes del panel.
     */
    private void crearComponentes() {
        configurarPanel();
        crearEtiquetas();
        crearCampoDeTexto();
        crearCamposTemporales();
        crearBotones();
        crearTablaDeOpciones();
    }

    /**
     * Crea los calendarios desplegables y los spinners de hora y minutos.
     */
    private void crearCamposTemporales() {
        crearDatePickerFechaInicio();
        crearDatePickerFechaTermino();
        crearSpinnerHoraInicio();
        crearSpinnerMinutosInicio();
        crearSpinnerHoraTermino();
        crearSpinnerMinutosTermino();
    }

    /**
     * Crea cada uno de los botones del panel.
     */
    private void crearBotones() {
        crearBotonAgregarOpcion();
        crearBotonGuardar();
        crearBotonCancelar();
        crearBotonPonerEnMarcha();
    }
    /**
     * Crea cada uno de los campos de texto del panel.
     */
    private void crearCampoDeTexto() {
        crearAreaDeTextoDescripcion();
        crearCampoDeTextoTitulo();
        crearCampoDeTextoEstado();
    }

    /**
     * Crea cada una de las etiquetas del panel.
     */
    private void crearEtiquetas() {
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
        crearEtiquetaEstado();
    }

    /**
     * Crea el campo de texto asociado al estado actual de la votación en el editor.
     */
    private void crearCampoDeTextoEstado() {
        campoTextoEstado = new JTextField();
        campoTextoEstado.setHorizontalAlignment(SwingConstants.CENTER);
        campoTextoEstado.setLocation(716, 44);
        campoTextoEstado.setSize(128, 40);
        campoTextoEstado.setFont(new Font("Arial", Font.BOLD, 15));
        campoTextoEstado.setBorder(BorderFactory.createLineBorder(Color.BLACK, 2));
        campoTextoEstado.setBorder(BorderFactory.createCompoundBorder(
                campoTextoEstado.getBorder(),
                BorderFactory.createEmptyBorder(5, 10, 5, 5)));
        campoTextoEstado.setEditable(false);
        campoTextoEstado.setFocusable(false);
        panel.add(campoTextoEstado);
    }

    /**
     * Crea la etiqueta asociada al estado actual de la votación en el editor. La etiqueta únicamente
     * contiene un texto referente al estado.
     */
    private void crearEtiquetaEstado() {
        textoEstado = new JLabel("Estado");
        textoEstado.setFont(new Font("Arial", Font.PLAIN, 16));
        textoEstado.setLocation(716,15);
        textoEstado.setSize(67, 29);
        panel.add(textoEstado);
    }

    /**
     * Crea una etiqueta para separar mediante dos puntos ":" los campos de hora y minutos de término de
     * una votación.
     */
    private void crearEtiquetaSeparadorHoraTermino() {
        JLabel separador = new JLabel(":");
        separador.setFont(new Font("Arial", Font.BOLD, 24));
        separador.setLocation(566,438);
        separador.setSize(29,29);
        panel.add(separador);
    }
    /**
     * Crea una etiqueta para separar mediante dos puntos ":" los campos de hora y minutos de inicio de
     * una votación.
     */
    private void crearEtiquetaSeparadorHoraInicio() {
        JLabel separador = new JLabel(":");
        separador.setFont(new Font("Arial", Font.BOLD, 24));
        separador.setLocation(566,354);
        separador.setSize(29,29);
        panel.add(separador);
    }

    /**
     * Crea un spinner para manipular la hora de inicio de una votación. Consiste en dos botones
     * ubicados de forma seguida verticalmente, el botón superior permite incrementar la hora y el
     * botón superior permite decrementar la hora.
     */
    private void crearSpinnerHoraInicio() {
        spinnerHoraInicio = new JSpinner(new SpinnerNumberModel(0, 0, 23, 1));
        spinnerHoraInicio.setLocation(501, 350);
        spinnerHoraInicio.setSize(55, 40);
        spinnerHoraInicio.setEditor(
                new JSpinner.NumberEditor(spinnerHoraInicio, "00"));
        JTextField textField = ((JSpinner.DefaultEditor) spinnerHoraInicio.getEditor()).getTextField();
        textField.setHorizontalAlignment(SwingConstants.RIGHT);
        textField.setFocusable(false);
        textField.setEditable(false);
        textField.setBackground(Color.WHITE);
        textField.setFont(new Font("Arial", Font.PLAIN, 15));
        textField.setBorder(BorderFactory.createLineBorder(Color.BLACK, 2));
        panel.add(spinnerHoraInicio);
    }
    /**
     * Crea un spinner para manipular el minuto de inicio de una votación. Consiste en dos botones
     * ubicados de forma seguida verticalmente, el botón superior permite incrementar el minuto y el
     * botón superior permite decrementar el minuto.
     */
    private void crearSpinnerMinutosInicio() {
        spinnerMinutosInicio = new JSpinner(new SpinnerNumberModel(0, 0, 59, 1));
        spinnerMinutosInicio.setLocation(587, 350);
        spinnerMinutosInicio.setSize(55, 40);
        spinnerMinutosInicio.setEditor(
                new JSpinner.NumberEditor(spinnerMinutosInicio, "00"));
        JTextField textField = ((JSpinner.DefaultEditor) spinnerMinutosInicio.getEditor()).getTextField();
        textField.setHorizontalAlignment(SwingConstants.RIGHT);
        textField.setFocusable(false);
        textField.setEditable(false);
        textField.setBackground(Color.WHITE);
        textField.setFont(new Font("Arial", Font.PLAIN, 15));
        textField.setBorder(BorderFactory.createLineBorder(Color.BLACK, 2));
        panel.add(spinnerMinutosInicio);
    }
    /**
     * Crea un spinner para manipular la hora de término de una votación. Consiste en dos botones
     * ubicados de forma seguida verticalmente, el botón superior permite incrementar la hora y el
     * botón superior permite decrementar la hora.
     */
    private void crearSpinnerHoraTermino() {
        spinnerHoraTermino = new JSpinner(new SpinnerNumberModel(0, 0, 23, 1));
        spinnerHoraTermino.setLocation(501, 434);
        spinnerHoraTermino.setSize(55, 40);
        spinnerHoraTermino.setEditor(
                new JSpinner.NumberEditor(spinnerHoraTermino, "00"));
        JTextField textField = ((JSpinner.DefaultEditor) spinnerHoraTermino.getEditor()).getTextField();
        textField.setHorizontalAlignment(SwingConstants.RIGHT);
        textField.setFocusable(false);
        textField.setEditable(false);
        textField.setBackground(Color.WHITE);
        textField.setFont(new Font("Arial", Font.PLAIN, 15));
        textField.setBorder(BorderFactory.createLineBorder(Color.BLACK, 2));
        panel.add(spinnerHoraTermino);
    }
    /**
     * Crea un spinner para manipular el minuto de término de una votación. Consiste en dos botones
     * ubicados de forma seguida verticalmente, el botón superior permite incrementar el minuto y el
     * botón superior permite decrementar el minuto.
     */
    private void crearSpinnerMinutosTermino() {
        spinnerMinutosTermino = new JSpinner(new SpinnerNumberModel(0, 0, 59, 1));
        spinnerMinutosTermino.setLocation(587, 434);
        spinnerMinutosTermino.setSize(55, 40);
        spinnerMinutosTermino.setEditor(
                new JSpinner.NumberEditor(spinnerMinutosTermino, "00"));
        JTextField textField = ((JSpinner.DefaultEditor) spinnerMinutosTermino.getEditor()).getTextField();
        textField.setHorizontalAlignment(SwingConstants.RIGHT);
        textField.setFocusable(false);
        textField.setEditable(false);
        textField.setBackground(Color.WHITE);
        textField.setFont(new Font("Arial", Font.PLAIN, 15));
        textField.setBorder(BorderFactory.createLineBorder(Color.BLACK, 2));
        panel.add(spinnerMinutosTermino);
    }

    /**
     * Crea un calendario desplegable con el cual se puede indicar el día, mes y año de inicio de
     * una votación.
     */
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
    /**
     * Crea un calendario desplegable de tipo JDatePickerImpl con el cual se puede indicar el día,
     * mes y año de término de una votación.
     */
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
    /**
     * Crea la etiqueta asociada a la hora de término de una votación. La etiqueta únicamente
     * contiene un texto que indica el contenido del campo de texto respectivo.
     */
    private void crearEtiquetaHoraTermino() {
        textoHoraTermino = new JLabel("Hora de término");
        textoHoraTermino.setFont(new Font("Arial", Font.PLAIN, 16));
        textoHoraTermino.setLocation(501,401);
        textoHoraTermino.setSize(119,36);
        panel.add(textoHoraTermino);
    }

    /**
     * Crea la etiqueta asociada a las opciones de una votación. La etiqueta únicamente
     * contiene un texto que indica el contenido de la tabla de opciones.
     */
    private void crearEtiquetaOpciones() {
        textoOpciones = new JLabel("Opciones");
        textoOpciones.setFont(new Font("Arial", Font.BOLD, 20));
        textoOpciones.setLocation(188,487);
        textoOpciones.setSize(110,47);
        panel.add(textoOpciones);
    }

    /**
     * Crea una tabla que muestra cada opción de una votación (a excepción del voto blanco),
     * seguido de un botón para eliminar dicha opción. Llama al controlador para obtener el modelo
     * de tabla.
     */
    private void crearTablaDeOpciones() {
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

    /**
     * Crea un botón para cambiar el estado de una votación en borrador a pendiente.
     */
    private void crearBotonPonerEnMarcha() {
        bPonerEnMarcha = new JButton("Poner en marcha");
        bPonerEnMarcha.setFont(new Font("Arial", Font.BOLD, 15));
        bPonerEnMarcha.setLocation(683, 809);
        bPonerEnMarcha.setSize(174, 46);
        bPonerEnMarcha.setBackground(new Color(253, 151, 39));
        bPonerEnMarcha.setForeground(Color.WHITE);
        bPonerEnMarcha.addActionListener(this);
        panel.add(bPonerEnMarcha);
    }

    /**
     * Crea un botón para volver al menú de administración sin guardar los cambios.
     */
    private void crearBotonCancelar() {
        bVolver = new JButton("Volver");
        bVolver.setFont(new Font("Arial", Font.BOLD, 15));
        bVolver.setLocation(33, 809);
        bVolver.setSize(146, 47);
        bVolver.setBackground(Color.BLACK);
        bVolver.setForeground(Color.WHITE);
        bVolver.addActionListener(this);
        panel.add(bVolver);
    }

    /**
     * Crea un botón para agregar una nueva opción a la votación.
     */
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

    /**
     * Crea un botón para guardar los cambios realizados en el editor de votación.
     */
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

    /**
     * Crea un campo de texto para ingresar o modificar el título de una votación.
     */
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
    /**
     * Crea un campo de texto para ingresar o modificar la descripción de una votación.
     */
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
    /**
     * Crea la etiqueta asociada a la fecha de término de una votación. La etiqueta únicamente
     * contiene un texto que indica el contenido del campo de texto respectivo.
     */
    private void crearEtiquetaFechaTermino() {
        textoFechaTermino = new JLabel("Fecha de termino");
        textoFechaTermino.setFont(new Font("Arial", Font.PLAIN, 16));
        textoFechaTermino.setLocation(245,402);
        textoFechaTermino.setSize(151,23);
        panel.add(textoFechaTermino);
    }
    /**
     * Crea la etiqueta asociada a la hora de inicio de una votación. La etiqueta únicamente
     * contiene un texto que indica el contenido del campo de texto respectivo.
     */
    private void crearEtiquetaHoraInicio() {
        textoHoraInicio = new JLabel("Hora de Inicio");
        textoHoraInicio.setFont(new Font("Arial", Font.PLAIN, 16));
        textoHoraInicio.setLocation(501,321);
        textoHoraInicio.setSize(119,29);
        panel.add(textoHoraInicio);
    }
    /**
     * Crea la etiqueta asociada a la fecha de inicio de una votación. La etiqueta únicamente
     * contiene un texto que indica el contenido del campo de texto respectivo.
     */
    private void crearEtiquetaFechaInicio() {
        textoFechaInicio = new JLabel("Fecha de inicio");
        textoFechaInicio.setFont(new Font("Arial", Font.PLAIN, 16));
        textoFechaInicio.setLocation(245,320);
        textoFechaInicio.setSize(119,29);
        panel.add(textoFechaInicio);
    }
    /**
     * Crea la etiqueta asociada a la descripción de una votación. La etiqueta únicamente
     * contiene un texto que indica el contenido del campo de texto respectivo.
     */
    private void crearEtiquetaDescripcion() {
        textoDescripcion = new JLabel("Descripción");
        textoDescripcion.setFont(new Font("Arial", Font.PLAIN, 16));
        textoDescripcion.setLocation(187,167);
        textoDescripcion.setSize(110, 40);
        panel.add(textoDescripcion);
    }
    /**
     * Crea la etiqueta asociada al título de una votación. La etiqueta únicamente
     * contiene un texto que indica el contenido del campo de texto respectivo.
     */
    private void crearEtiquetaTitulo() {
        textoTitulo = new JLabel("Titulo");
        textoTitulo.setFont(new Font("Arial", Font.PLAIN, 16));
        textoTitulo.setLocation(187,85);
        textoTitulo.setSize(110, 40);
        panel.add(textoTitulo);
    }
    /**
     * Crea una etiqueta que indica el nombre de la vista en el encabezado de la ventana.
     */
    private void crearEtiquetaEncabezado() {
        textoEncabezado = new JLabel("Editor de votaciones");
        textoEncabezado.setFont(new Font("Arial", Font.BOLD, 26));
        textoEncabezado.setLocation(307,19);
        textoEncabezado.setSize(275, 60);
        panel.add(textoEncabezado);
    }
    /**
     * Instancia el panel y configura el color, layout, scrollpane y las dimensiones.
     */
    private void configurarPanel() {
        setLayout(null);
        panel = new JPanel();
        panel.setBackground(Color.WHITE);
        panel.setLayout(null);
        panel.setPreferredSize(new Dimension(400,915));
        configurarScrollPane();
    }

    /**
     * Configura el contenedor con scroll de la tabla de opciones.
     */
    private void configurarScrollPane() {
        scroll = new JScrollPane(panel);
        scroll.setBounds(0, 0, 890, 600);
        scroll.getVerticalScrollBar().setUnitIncrement(25);
        scroll.getVerticalScrollBar().setPreferredSize(new Dimension(18,0));
        add(scroll);
    }

    /**
     * Obtiene el título ingresado en el campo de texto respectivo.
     * @return un String cuyo valor indica el input del usuario.
     */
    public String getTitulo() {
        return campoTextoTitulo.getText();
    }
    /**
     * Obtiene la descripción ingresada en el campo de texto respectivo.
     * @return un String cuyo valor indica el input del usuario.
     */
    public String getDescripcion() {
        return campoTextoDescripcion.getText();
    }
    /**
     * Obtiene la hora de inicio ingresada mediante el spinner respectivo.
     * @return un entero cuyo valor indica el input del usuario.
     */
    public int getHoraInicio() {
        return Integer.parseInt(spinnerHoraInicio.getValue().toString());
    }
    /**
     * Obtiene la hora de término ingresada mediante el spinner respectivo.
     * @return un entero cuyo valor indica el input del usuario.
     */
    public int getHoraTermino() {
        return Integer.parseInt(spinnerHoraTermino.getValue().toString());
    }
    /**
     * Obtiene el minuto de inicio ingresada mediante el spinner respectivo.
     * @return un entero cuyo valor indica el input del usuario.
     */
    public int getMinutoInicio() {
        return Integer.parseInt(spinnerMinutosInicio.getValue().toString());
    }
    /**
     * Obtiene el minuto de término ingresada mediante el spinner respectivo.
     * @return un entero cuyo valor indica el input del usuario.
     */
    public int getMinutoTermino() {
        return Integer.parseInt(spinnerMinutosTermino.getValue().toString());
    }
    /**
     * Obtiene la fecha de inicio ingresada mediante el calendario respectivo.
     * @return un entero cuyo valor indica el input del usuario.
     */
    public String getFechaInicio() {
        return datePickerFechaInicio.getJFormattedTextField().getText();
    }
    /**
     * Obtiene la fecha de término ingresada mediante el calendario respectivo.
     * @return un entero cuyo valor indica el input del usuario.
     */
    public String getFechaTermino() {
        return datePickerFechaTermino.getJFormattedTextField().getText();
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
        if (e.getSource() == bGuardar) {
            controlador.guardarFueSolicitado();
        }
        if (e.getSource() == bAgregarOpcion) {
            controlador.agregarOpcionFueSolicitado();
        }
        if (e.getSource() == bPonerEnMarcha) {
            controlador.ponerVotacionEnMarchaFueSolicitado();
        }
    }

    /**
     * Carga la información de una votación seleccionada para su edición en los respectivos campos de
     * texto.
     * @param votacion la votación a editar.
     */
    public void cargarVotacion(Votacion votacion) {
        campoTextoEstado.setText(votacion.getEstadoDeVotacion().getTexto());
        campoTextoTitulo.setText(votacion.getTitulo());
        campoTextoDescripcion.setText(votacion.getDescripcion());
        cargarFechaInicio(votacion);
        cargarFechaTermino(votacion);
        cargarHorasYMinutos(votacion);
    }

    /**
     * Carga las horas y minutos de una votación a editar en los spinners.
     * @param votacion la votación a editar.
     */
    private void cargarHorasYMinutos(Votacion votacion) {
        spinnerHoraInicio.getModel().setValue(votacion.getTiempoInicio().getHour());
        spinnerMinutosInicio.getModel().setValue(votacion.getTiempoInicio().getMinute());
        spinnerHoraTermino.getModel().setValue(votacion.getTiempoTermino().getHour());
        spinnerMinutosTermino.getModel().setValue(votacion.getFechaTiempoTermino().getMinute());
    }

    /**
     * Carga la fecha de inicio de una votación a editar en el calendario.
     * @param votacion la votación a editar.
     */
    private void cargarFechaInicio(Votacion votacion) {
        var fechaInicio = votacion.getFechaInicio();
        datePickerFechaInicio.getModel().setDate(
                fechaInicio.getYear(),
                fechaInicio.getMonthValue() - 1,
                fechaInicio.getDayOfMonth());
        datePickerFechaInicio.getModel().setSelected(true);
    }

    /**
     * Carga la fecha de término de una votación a editar en el calendario.
     * @param votacion la votación a editar.
     */
    private void cargarFechaTermino(Votacion votacion) {
        var fechaTermino = votacion.getFechaTermino();
        datePickerFechaTermino.getModel().setDate(
                fechaTermino.getYear(),
                fechaTermino.getMonthValue() - 1,
                fechaTermino.getDayOfMonth());
        datePickerFechaTermino.getModel().setSelected(true);
    }

    /**
     * Mueve el scroll de la vista al inicio.
     */
    public void moverScrollAlInicio() {
        scroll.getViewport().setViewPosition(new Point(0,0));
    }

    /**
     * Habilita el modo lectura de una votación, restringiendo la edición de sus campos.
     * @param modoLectura un booleano que indica si se habilita el modo lectura.
     */
    public void setModoLectura(boolean modoLectura) {
        setHabilitarComponentes(!modoLectura);
    }

    /**
     * Habilita la edición de los campos de una votación.
     * @param b un booleano que indica la habilitación de los campos.
     */
    private void setHabilitarComponentes(boolean b) {
        campoTextoTitulo.setEditable(b);
        campoTextoDescripcion.setEditable(b);
        datePickerFechaInicio.getComponent(1).setEnabled(b);
        datePickerFechaTermino.getComponent(1).setEnabled(b);
        spinnerHoraInicio.setEnabled(b);
        spinnerHoraTermino.setEnabled(b);
        spinnerMinutosInicio.setEnabled(b);
        spinnerMinutosTermino.setEnabled(b);
        bAgregarOpcion.setEnabled(b);
        bPonerEnMarcha.setEnabled(b);
        bGuardar.setEnabled(b);
        tOpciones.setEnabled(b);
    }

    /**
     * Habilita el botón para poner en marcha una votación.
     * @param b un booleano que indica la disponibilidad del botón.
     */
    public void setHabilitarBotonPonerEnMarcha(boolean b) {
        bPonerEnMarcha.setEnabled(b);
    }
}