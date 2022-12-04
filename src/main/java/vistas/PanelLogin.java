package vistas;
import controladores.ControladorLogin;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * Crea el panel que representa la vista del login.
 */
public class PanelLogin extends JPanel implements ActionListener {
    /**
     * Indica una etiqueta visible en el encabezado de la vista.
     */
    private JLabel titulo;
    /**
     * Indica una etiqueta que contiene el logo del programa.
     */
    private JLabel logo;
    /**
     * Indica una etiqueta que despliega un mensaje de invalidez.
     */
    private JLabel mensajeDeInvalidez;
    /**
     * Indica una etiqueta asociada al campo del rut.
     */
    private JLabel erut;
    /**
     * Indica una etiqueta asociada al campo de la clave.
     */
    private JLabel eclave;
    /**
     * Indica un campo de texto asociado a un rut.
     */
    private JTextField trut;
    /**
     * Indica un campo de texto asociado a una clave.
     */
    private JTextField tclave;
    /**
     * Indica un botón de ingreso.
     */
    private JButton ingreso;
    /**
     * Indica un botón de ingreso alternativo o cambio de ingreso.
     */
    private JButton ingresoAlternativo;
    /**
     * Indica el controlador que administra la lógica de la vista.
     */
    private ControladorLogin controlador;

    /**
     * Construye un panel de tipo PanelLogin. Instancia el controlador y los componentes que forman
     * parte del panel.
     * @param controlador el controlador que maneja la lógica tras el panel.
     */

    public PanelLogin(ControladorLogin controlador) {
        this.controlador = controlador;
        crearComponentes();
        setVisible(true);
    }
    /**
     * Crea cada uno de los componentes del panel.
     */
    private void crearComponentes() {
        configurarPanel();
        crearTitulo();
        crearLogo();
        crearEtiquetaRut();
        crearCampoDeTextoRut();
        crearEtiquetaClave();
        crearCampoDeTextoClave();
        crearBotonIngresar();
        crearBotonIngresoAlternativo();
        crearMensajeDeInvalidez();
    }

    /**
     * Crea la etiqueta asociada al campo de ingreso de un rut. La etiqueta únicamente
     * contiene un texto que indica el contenido del campo de texto respectivo.
     */
    private void crearEtiquetaRut() {
        erut = new JLabel("Rut");
        erut.setFont(new Font("Arial", Font.BOLD, 18));
        erut.setSize(42, 29);
        erut.setLocation(300, 188);
        add(erut);
    }
    /**
     * Crea la etiqueta asociada al campo de ingreso de una clave. La etiqueta únicamente
     * contiene un texto que indica el contenido del campo de texto respectivo.
     */
    private void crearEtiquetaClave() {
        eclave = new JLabel("Contraseña");
        eclave.setFont(new Font("Arial", Font.BOLD, 18));
        eclave.setSize(169, 29);
        eclave.setLocation(300, 286);
        add(eclave);
    }

    /**
     * Crea un mensaje de invalidez en caso de que alguno de los datos ingresados no
     * sea admisible.
     */
    private void crearMensajeDeInvalidez() {
        mensajeDeInvalidez = new JLabel();
        mensajeDeInvalidez.setFont(new Font("Arial", Font.PLAIN, 18));
        mensajeDeInvalidez.setForeground(Color.RED);
        mensajeDeInvalidez.setVisible(false);
        add(mensajeDeInvalidez);
    }
    /**
     * Configura el color y layout del panel.
     */
    private void configurarPanel() {
        setBackground(Color.WHITE);
        setLayout(null);
    }

    /**
     * Crea la etiqueta informativa de la vista.
     */
    private void crearTitulo() {
        titulo = new JLabel("Tu cuenta Electa");
        titulo.setFont(new Font("Arial", Font.BOLD, 36));
        titulo.setSize(900, 270);
        titulo.setHorizontalAlignment(JLabel.CENTER);
        titulo.setVerticalAlignment(JLabel.CENTER);
        add(titulo);
    }

    /**
     * Crea un icono visible del logo de la aplicación.
     */
    private void crearLogo() {
        ImageIcon imagen = new ImageIcon("src/main/img/logo.png");
        logo = new JLabel();
        logo.setIcon(imagen);

        logo.setSize(250, 96);
        logo.setLocation(325, 0);
        logo.setHorizontalAlignment(JLabel.CENTER);
        logo.setVerticalAlignment(JLabel.CENTER);
        add(logo);
    }

    /**
     * Crea el campo de texto mediante el cual se ingresa el rut de un usuario.
     */
    private void crearCampoDeTextoRut() {
        trut = new JTextField();
        trut.setFont(new Font("Arial", Font.PLAIN, 15));
        trut.setSize(300, 45);
        trut.setLocation(300, 217);
        trut.setBorder(BorderFactory.createLineBorder(Color.BLACK));
        trut.setBorder(BorderFactory.createCompoundBorder(
                trut.getBorder(),
                BorderFactory.createEmptyBorder(5, 10, 5, 5)));
        add(trut);
    }
    /**
     * Crea el campo de texto mediante el cual se ingresa la clave de un usuario.
     */
    private void crearCampoDeTextoClave() {
        tclave = new JPasswordField();
        tclave.setFont(new Font("Arial", Font.PLAIN, 15));
        tclave.setSize(300, 45);
        tclave.setLocation(300, 315);
        tclave.setBorder(BorderFactory.createLineBorder(Color.BLACK));
        tclave.setBorder(BorderFactory.createCompoundBorder(
                tclave.getBorder(),
                BorderFactory.createEmptyBorder(5, 10, 5, 5)));
        add(tclave);
    }

    /**
     * Crea un botón para ingresar a la aplicación con las credenciales ingresadas.
     */
    private void crearBotonIngresar() {
        ingreso = new JButton("Ingresar");
        ingreso.addActionListener(this);
        ingreso.setFont(new Font("Arial", Font.BOLD, 17));
        ingreso.setSize(200, 50);
        ingreso.setLocation(350, 420);
        ingreso.setFocusable(false);

        ingreso.setBackground(Color.BLACK);
        ingreso.setForeground(Color.WHITE);

        add(ingreso);
    }

    /**
     * Crea un botón para cambiar el tipo de login de usuario, alternando entre el ingreso como
     * votante y el ingreso como administrador.
     */
    private void crearBotonIngresoAlternativo() {
        ingresoAlternativo = new JButton();
        ingresoAlternativo.setText("Ingreso administrativo");
        ingresoAlternativo.addActionListener(this);
        ingresoAlternativo.setFont(new Font("Arial", Font.PLAIN, 15));
        ingresoAlternativo.setSize(181, 35);
        ingresoAlternativo.setLocation(360, 493);
        ingresoAlternativo.setFocusable(false);

        ingresoAlternativo.setBackground(Color.WHITE);
        ingresoAlternativo.setForeground(Color.BLACK);

        add(ingresoAlternativo);
    }

    /**
     * Obtiene el rut ingresado en el campo de texto correspondiente.
     * @return un String cuyo valor representa un rut ingresado.
     */
    public String getRut() {
        return trut.getText();
    }
    /**
     * Obtiene la clave ingresada en el campo de texto correspondiente.
     * @return un String cuyo valor representa una clave ingresada.
     */

    public String getClave() {
        return tclave.getText();
    }

    /**
     * Deja en blanco los campos de texto y oculta el mensaje de invalidez.
     */
    public void limpiar() {
        trut.setText("");
        tclave.setText("");
        mensajeDeInvalidez.setVisible(false);
    }

    /**
     * Cambia el texto del encabezado de la página al solicitar ingresar como administrador.
     */
    public void cambiarAIngresoAdministrativo() {
        titulo.setText("Ingreso administrativo");
        ingresoAlternativo.setText("Ingreso como votante");
    }
    /**
     * Cambia el texto del encabezado de la página al solicitar ingresar como votante.
     */
    public void cambiarAIngresoVotante() {
        titulo.setText("Tu cuenta Electa");
        ingresoAlternativo.setText("Ingreso administrativo");
    }

    /**
     * Muestra un mensaje de invalidez cuando el rut y/o la clave son incorrectos.
     */
    public void autenticacionFallo() {
        System.out.println("Fallo la autenticación");
        mensajeDeInvalidez.setSize(350, 20);
        mensajeDeInvalidez.setLocation(282, 380);
        mensajeDeInvalidez.setText("El rut y/o la constraseña están incorrectos");
        mensajeDeInvalidez.setVisible(true);
    }

    /**
     * Muestra un mensaje de éxito en consola cuando la autenticación se logró.
     */
    public void autenticacionSeLogro() {
        System.out.println("La autenticación se logró con exito");
        mensajeDeInvalidez.setVisible(false);
    }
    /**
     * Capta y procesa algún evento desencadenado por los botones.
     * @param e el evento de tipo ActionEvent a ser procesado.
     */
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == ingreso) {
            controlador.autenticacionFueSolicitada();
        }
        if (e.getSource() == ingresoAlternativo) {
            controlador.cambioDeIngresoFueSolicitado();
        }
    }
    /**
     * Muestra un mensaje de invalidez cuando se intenta ingresar y los campos de rut y clave
     * se encuentran vacíos.
     */
    public void mostrarCredencialesEstanVacias() {
        System.out.println("Fallo la autenticación");
        mensajeDeInvalidez.setSize(320, 20);
        mensajeDeInvalidez.setLocation(300, 380);
        mensajeDeInvalidez.setText("El rut y/o la constraseña están vacíos");
        mensajeDeInvalidez.setVisible(true);
    }
}