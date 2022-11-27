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
        crearCampoDeTextoRut();
        crearCampoDeTextoClave();
        crearBotonIngresar();
        crearBotonIngresoAlternativo();
        crearMensajeDeInvalidez();
    }

    private void crearMensajeDeInvalidez() {
        mensajeDeInvalidez = new JLabel();
        mensajeDeInvalidez.setFont(new Font("Arial", Font.PLAIN, 18));
        mensajeDeInvalidez.setForeground(Color.RED);
        mensajeDeInvalidez.setVisible(false);
        this.add(mensajeDeInvalidez);
    }

    private void configurarPanel() {
        this.setBackground(Color.WHITE);
        this.setLayout(null);
    }

    private void crearTitulo() {
        titulo = new JLabel("Tu cuenta Electa");
        titulo.setFont(new Font("Arial", Font.BOLD, 36));
        titulo.setSize(900, 300);
        titulo.setHorizontalAlignment(JLabel.CENTER);
        titulo.setVerticalAlignment(JLabel.CENTER);
        this.add(titulo);
    }

    private void crearLogo() {
        ImageIcon imagen = new ImageIcon("src/main/img/logo.png");
        logo = new JLabel();
        logo.setIcon(imagen);

        logo.setSize(250, 96);
        logo.setLocation(325, 0);
        logo.setHorizontalAlignment(JLabel.CENTER);
        logo.setVerticalAlignment(JLabel.CENTER);
        this.add(logo);
    }

    private void crearCampoDeTextoRut() {
        trut = new JTextField();
        trut.setFont(new Font("Arial", Font.PLAIN, 15));
        trut.setSize(300, 45);
        trut.setLocation(300, 230);
        trut.setBorder(BorderFactory.createLineBorder(Color.BLACK));
        trut.setBorder(BorderFactory.createCompoundBorder(
                trut.getBorder(),
                BorderFactory.createEmptyBorder(5, 10, 5, 5)));
        trut.setText("Rut");
        this.add(trut);
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
        tclave.setText("Contraseña");
        this.add(tclave);
    }

    private void crearBotonIngresar() {
        ingreso = new JButton("Ingresar");
        ingreso.addActionListener(this);
        ingreso.setFont(new Font("Arial", Font.PLAIN, 15));
        ingreso.setSize(200, 50);
        ingreso.setLocation(350, 420);
        ingreso.setFocusable(false);

        ingreso.setBackground(Color.BLACK);
        ingreso.setForeground(Color.WHITE);

        this.add(ingreso);
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

        this.add(ingresoAlternativo);
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

class VentanaLoginTest {

    public static void main(String[] args) throws Exception
    {
        PanelLogin f = new PanelLogin(null);
    }
}