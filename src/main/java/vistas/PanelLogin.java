package vistas;
import controladores.ControladorLogin;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class PanelLogin extends JPanel implements ActionListener {

    private JLabel titulo;
    private JLabel logo;
    private JLabel mensajeDeInvalidez;
    private JLabel erut;
    private JLabel eclave;
    private JTextField trut;
    private JTextField tclave;
    private JButton ingreso;
    private JButton ingresoAlternativo;
    private ControladorLogin controlador;

    public PanelLogin(ControladorLogin controlador) {
        this.controlador = controlador;
        crearComponentes();
        setVisible(true);
    }

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

    private void crearEtiquetaRut() {
        erut = new JLabel("Rut");
        erut.setFont(new Font("Arial", Font.BOLD, 18));
        erut.setSize(42, 29);
        erut.setLocation(300, 188);
        add(erut);
    }

    private void crearEtiquetaClave() {
        eclave = new JLabel("Contraseña");
        eclave.setFont(new Font("Arial", Font.BOLD, 18));
        eclave.setSize(169, 29);
        eclave.setLocation(300, 286);
        add(eclave);
    }

    private void crearMensajeDeInvalidez() {
        mensajeDeInvalidez = new JLabel();
        mensajeDeInvalidez.setFont(new Font("Arial", Font.PLAIN, 18));
        mensajeDeInvalidez.setForeground(Color.RED);
        mensajeDeInvalidez.setVisible(false);
        add(mensajeDeInvalidez);
    }

    private void configurarPanel() {
        setBackground(Color.WHITE);
        setLayout(null);
    }

    private void crearTitulo() {
        titulo = new JLabel("Tu cuenta Electa");
        titulo.setFont(new Font("Arial", Font.BOLD, 36));
        titulo.setSize(900, 270);
        titulo.setHorizontalAlignment(JLabel.CENTER);
        titulo.setVerticalAlignment(JLabel.CENTER);
        add(titulo);
    }

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

    public String getRut() {
        return trut.getText();
    }

    public String getClave() {
        return tclave.getText();
    }

    public void limpiar() {
        trut.setText("");
        tclave.setText("");
        mensajeDeInvalidez.setVisible(false);
    }

    public void cambiarAIngresoAdministrativo() {
        titulo.setText("Ingreso administrativo");
        ingresoAlternativo.setText("Ingreso como votante");
    }

    public void cambiarAIngresoVotante() {
        titulo.setText("Tu cuenta Electa");
        ingresoAlternativo.setText("Ingreso administrativo");
    }

    public void autenticacionFallo() {
        System.out.println("Fallo la autenticación");
        mensajeDeInvalidez.setSize(350, 20);
        mensajeDeInvalidez.setLocation(282, 380);
        mensajeDeInvalidez.setText("El rut y/o la constraseña están incorrectos");
        mensajeDeInvalidez.setVisible(true);
    }

    public void autenticacionSeLogro() {
        System.out.println("La autenticación se logró con exito");
        mensajeDeInvalidez.setVisible(false);
    }

    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == ingreso) {
            controlador.autenticacionFueSolicitada();
        }
        if (e.getSource() == ingresoAlternativo) {
            controlador.cambioDeIngresoFueSolicitado();
        }
    }

    public void mostrarCredencialesEstanVacias() {
        System.out.println("Fallo la autenticación");
        mensajeDeInvalidez.setSize(320, 20);
        mensajeDeInvalidez.setLocation(300, 380);
        mensajeDeInvalidez.setText("El rut y/o la constraseña están vacíos");
        mensajeDeInvalidez.setVisible(true);
    }
}