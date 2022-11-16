package vistas.admin;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class VentanaLogin extends JFrame implements ActionListener, LoginVista {

    private Container c;
    private JLabel titulo;
    private JLabel logo;
    private JTextField trut;
    private JTextField tclave;
    private JButton ingreso;
    private LoginVistaControlador controlador;

    public void setLoginVistaControlador(LoginVistaControlador controlador) {
        this.controlador = controlador;
    }

    public VentanaLogin(LoginVistaControlador controlador) {
        setLoginVistaControlador(controlador);
        crearComponentes();
        setVisible(true);
    }

    private void crearComponentes() {
        crearVentana();
        crearPanel();
        crearTitulo();
        crearLogo();
        crearCampoDeTextoRut();
        crearCampoDeTextoClave();
        crearBotonIngreso();
    }

    private void crearVentana() {
        setTitle("Electa");
        setBounds(300, 90, 900, 600);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setResizable(false);
    }

    private void crearPanel() {
        c = getContentPane();
        c.setBackground(Color.WHITE);
        c.setLayout(null);
    }

    private void crearTitulo() {
        titulo = new JLabel("Tu cuenta Electa");
        titulo.setFont(new Font("Arial", Font.BOLD, 36));
        titulo.setSize(900, 300);
        titulo.setHorizontalAlignment(JLabel.CENTER);
        titulo.setVerticalAlignment(JLabel.CENTER);
        c.add(titulo);
    }

    private void crearLogo() {
        ImageIcon imagen = new ImageIcon("src/main/img/logo.png");
        logo = new JLabel();
        logo.setIcon(imagen);

        logo.setSize(250, 96);
        logo.setLocation(325, 0);
        logo.setHorizontalAlignment(JLabel.CENTER);
        logo.setVerticalAlignment(JLabel.CENTER);
        c.add(logo);
    }

    private void crearCampoDeTextoRut() {
        trut = new JTextField();
        trut.setFont(new Font("Arial", Font.PLAIN, 15));
        trut.setSize(300, 45);
        trut.setLocation(300, 230);
        trut.setBorder(BorderFactory.createLineBorder(Color.BLACK));
        trut.setText("Rut");
        c.add(trut);
    }

    private void crearCampoDeTextoClave() {
        tclave = new JTextField();
        tclave.setFont(new Font("Arial", Font.PLAIN, 15));
        tclave.setSize(300, 45);
        tclave.setLocation(300, 315);
        tclave.setBorder(BorderFactory.createLineBorder(Color.BLACK));
        tclave.setText("Contraseña");
        c.add(tclave);
    }

    private void crearBotonIngreso() {
        ingreso = new JButton("Ingresar");
        ingreso.addActionListener(this);
        ingreso.setFont(new Font("Arial", Font.PLAIN, 15));
        ingreso.setSize(200, 50);
        ingreso.setLocation(350, 420);

        ingreso.setBackground(Color.BLACK);
        ingreso.setForeground(Color.WHITE);

        c.add(ingreso);
    }

    @Override
    public String obtenerRut() {
        return trut.getText();
    }

    @Override
    public String obtenerClave() {
        return tclave.getText();
    }

    @Override
    public void autenticacionFallo() {
        System.out.println("Fallo la autenticación");
    }

    @Override
    public void autenticacionSeLogro() {
        System.out.println("La autenticación se logró con exito");
    }

    @Override
    public LoginVistaControlador getVistaLoginControlador() {
        return controlador;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == ingreso) {
            getVistaLoginControlador().autenticacionFueSolicitada(this);
        }
    }
}

class VentanaLoginTest {

    public static void main(String[] args) throws Exception
    {
        VentanaLogin f = new VentanaLogin(null);
    }
}