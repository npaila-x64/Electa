package vistas.votante;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * Crea una ventana de diálogo para confirmar la emisión de un voto.
 */
public class DialogoDeConfirmacion extends JDialog implements ActionListener {
    /**
     * Indica una etiqueta asociada al mensaje de la ventana.
     */
    private JLabel tEncabezado;
    /**
     * Indica una etiqueta asociada al voto escogido.
     */
    private JLabel tCandidato;
    /**
     * Indica una etiqueta asociada al mensaje de la ventana.
     */
    private JLabel tConfirmar;
    /**
     * Indica un botón para aceptar la emisión de un voto.
     */
    private JButton botonSi;
    /**
     * Indica un botón para negar la emisión de un voto.
     */
    private JButton botonNo;
    /**
     * Indica el estado de la confirmación.
     */
    private boolean confirmacion;

    /**
     * Construye una ventana de diálogo de tipo DialogoDeConfirmacion. Llama al constructor de la
     * clase padre para ingresar los parámetros y configura los componentes y estructura de la ventana.
     * @param owner la ventana original de la cual surge.
     * @param modal un booleano que especifica si la ventana debería bloquear input del usuario en otras.
     */
    public DialogoDeConfirmacion(Frame owner, boolean modal) {
        super(owner, "Confirme su voto", modal);
        crearComponentes();
        setDefaultCloseOperation(JDialog.DO_NOTHING_ON_CLOSE);
        setSize(489, 356);
        setLocationRelativeTo(null);
        setResizable(false);
    }

    /**
     * Asigna el nombre de la opción escogida en el mensaje.
     * @param nombreDeOpcion un String cuyo valor representa un nombre.
     */
    public void setNombreDeOpcion(String nombreDeOpcion) {
        tCandidato.setText(nombreDeOpcion);
    }

    public boolean obtenerConfirmacion() {
        setVisible(true);
        return confirmacion;
    }
    /**
     * Configura el color y layout de la vista.
     */
    private void configurarPanel() {
        getContentPane().setBackground(Color.WHITE);
        getContentPane().setLayout(null);
    }
    /**
     * Crea cada uno de los componentes de la vista.
     */
    private void crearComponentes() {
        configurarPanel();
        crearEtiquetaEncabezado();
        crearEtiquetaCandidato();
        crearEtiquetaConfirmar();
        crearBotonSi();
        crearBotonNo();
    }

    /**
     * Crea una etiqueta que conforma parte del mensaje de confirmación.
     */
    private void crearEtiquetaEncabezado() {
        tEncabezado = new JLabel("Usted votó por");
        tEncabezado.setFont(new Font("Arial", Font.PLAIN, 28));
        tEncabezado.setLocation(145,28);
        tEncabezado.setSize(182, 32);
        this.add(tEncabezado);
    }

    /**
     * Crea una etiqueta contiene la opción escogida para votar.
     */
    private void crearEtiquetaCandidato() {
        tCandidato = new JLabel("Inserte candidato");
        tCandidato.setFont(new Font("Arial", Font.BOLD, 24));
        tCandidato.setLocation(36,72);
        tCandidato.setSize(400, 76);
        tCandidato.setHorizontalAlignment(JLabel.CENTER);
        tCandidato.setVerticalAlignment(JLabel.CENTER);
        this.add(tCandidato);
    }
    /**
     * Crea una etiqueta que conforma la pregunta del mensaje de confirmación.
     */
    private void crearEtiquetaConfirmar() {
        tConfirmar = new JLabel("¿Desea confirmar su voto?");
        tConfirmar.setFont(new Font("Arial", Font.PLAIN, 26));
        tConfirmar.setLocation(65,163);
        tConfirmar.setSize(347, 29);
        tConfirmar.setHorizontalAlignment(JLabel.CENTER);
        this.add(tConfirmar);
    }
    /**
     * Crea un botón para negar la emisión del voto escogido.
     */
    private void crearBotonNo() {
        botonNo = new JButton("No");
        botonNo.setFont(new Font("Arial", Font.PLAIN, 22));
        botonNo.setSize(127, 45);
        botonNo.setLocation(285, 243);
        botonNo.setBackground(Color.BLACK);
        botonNo.setForeground(Color.WHITE);
        botonNo.setFocusable(false);
        botonNo.addActionListener(this);
        this.add(botonNo);
    }

    /**
     * Crea un botón para aceptar la emisión del voto escogido.
     */
    private void crearBotonSi() {
        botonSi = new JButton("Si");
        botonSi.setFont(new Font("Arial", Font.PLAIN, 22));
        botonSi.setSize(127, 45);
        botonSi.setLocation(65, 243);
        botonSi.setBackground(Color.BLACK);
        botonSi.setForeground(Color.WHITE);
        botonSi.setFocusable(false);
        botonSi.addActionListener(this);
        this.add(botonSi);
    }
    /**
     * Capta y procesa algún evento desencadenado por los botones.
     * @param e el evento de tipo ActionEvent a ser procesado.
     */
    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == botonSi) {
            confirmacion = true;
        }
        if (e.getSource() == botonNo) {
            confirmacion = false;
        }
        setVisible(false);
        dispose();
    }
}
