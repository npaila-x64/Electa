package vistas.admin;
import controladores.ControladorLogin;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class PanelLogin extends JPanel implements ActionListener {

    private JLabel titulo;
    private JLabel logo;
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
        trut.setText("Rut");
        this.add(trut);
    }

    private void crearCampoDeTextoClave() {
        tclave = new JTextField();
        tclave.setFont(new Font("Arial", Font.PLAIN, 15));
        tclave.setSize(300, 45);
        tclave.setLocation(300, 315);
        tclave.setBorder(BorderFactory.createLineBorder(Color.BLACK));
        tclave.setText("Contraseña");
        this.add(tclave);
    }

    private void crearBotonIngresar() {
        ingreso = new JButton("Ingresar");
        ingreso.addActionListener(this);
        ingreso.setFont(new Font("Arial", Font.PLAIN, 15));
        ingreso.setSize(200, 50);
        ingreso.setLocation(350, 420);

        ingreso.setBackground(Color.BLACK);
        ingreso.setForeground(Color.WHITE);

        this.add(ingreso);
    }

    public String obtenerRut() {
        return trut.getText();
    }

    public String obtenerClave() {
        return tclave.getText();
    }

    public void limpiar() {
        trut.setText("");
        tclave.setText("");
    }

    public void cambiarAIngresoAdministrativo() {
        titulo.setText("Ingreso administrativo");
    }

    public void cambiarAIngresoVotante() {
        titulo.setText("Tu cuenta Electa");
    }

    public void autenticacionFallo() {
        System.out.println("Fallo la autenticación");
    }

    public void autenticacionSeLogro() {
        System.out.println("La autenticación se logró con exito");
    }

    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == ingreso) {
            controlador.autenticacionFueSolicitada();
        }
        if (e.getSource() == ingresoAlternativo) {
            controlador.cambioDeIngresoFueSolicitado();
        }
    }
}

class VentanaLoginTest {

    public static void main(String[] args) throws Exception
    {
        PanelLogin f = new PanelLogin(null);
    }
}