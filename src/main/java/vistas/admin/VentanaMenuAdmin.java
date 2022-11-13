package vistas.admin;
import javax.swing.*;
import java.awt.*;

class MenuAdminFrame extends JFrame {
    private Container c;
    private JLabel bordesTabla;
    private JLabel textoDesde;
    private JLabel textoHasta;
    private JTextField campoTextoDesde;
    private JTextField campoTextoHasta;
    private JButton bCerrarSesion;
    private JButton bCrearVotacion;
    private JButton bResultados;
    private JCheckBox checkEnCurso;
    private JCheckBox checkPendiente;
    private JCheckBox checkFinalizadas;
    public MenuAdminFrame()
    {
        setTitle("Electa");
        setBounds(300, 90, 900, 600);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setResizable(false);

        c = getContentPane();
        c.setBackground(Color.WHITE);
        c.setLayout(null);

        bordesTabla = new JLabel();
        bordesTabla.setSize(805, 275);
        bordesTabla.setLocation(40, 150);
        bordesTabla.setBorder(BorderFactory.createLineBorder(Color.BLACK));
        c.add(bordesTabla);

        textoDesde = new JLabel("Filtrar desde");
        textoDesde.setFont(new Font("Arial", Font.PLAIN, 15));
        textoDesde.setSize(120,40);
        textoDesde.setLocation(460, 40);
        c.add(textoDesde);

        textoHasta = new JLabel("hasta");
        textoHasta.setFont(new Font("Arial", Font.PLAIN, 15));
        textoHasta.setSize(100,40);
        textoHasta.setLocation(685, 40);
        c.add(textoHasta);

        campoTextoDesde = new JTextField();
        campoTextoDesde.setFont(new Font("Arial", Font.PLAIN, 15));
        campoTextoDesde.setSize(100, 40);
        campoTextoDesde.setLocation(560, 40);
        campoTextoDesde.setBorder(BorderFactory.createLineBorder(Color.BLACK));
        c.add(campoTextoDesde);

        campoTextoHasta = new JTextField();
        campoTextoHasta.setFont(new Font("Arial", Font.PLAIN, 15));
        campoTextoHasta.setSize(100, 40);
        campoTextoHasta.setLocation(745, 40);
        campoTextoHasta.setBorder(BorderFactory.createLineBorder(Color.BLACK));
        c.add(campoTextoHasta);

        bCerrarSesion = new JButton("Cerrar sesión");
        bCerrarSesion.setFont(new Font("Arial", Font.PLAIN, 15));
        bCerrarSesion.setSize(200, 50);
        bCerrarSesion.setLocation(40, 40);
        bCerrarSesion.setBackground(Color.BLACK);
        bCerrarSesion.setForeground(Color.WHITE);
        c.add(bCerrarSesion);

        bCrearVotacion = new JButton("Crear nueva votación");
        bCrearVotacion.setFont(new Font("Arial", Font.PLAIN, 15));
        bCrearVotacion.setSize(200, 50);
        bCrearVotacion.setLocation(350, 475);
        bCrearVotacion.setBackground(Color.BLACK);
        bCrearVotacion.setForeground(Color.WHITE);
        c.add(bCrearVotacion);

        bResultados = new JButton("Resultados");
        bResultados.setFont(new Font("Arial", Font.PLAIN, 15));
        bResultados.setSize(200, 50);
        bResultados.setLocation(645, 475);
        bResultados.setBackground(Color.BLACK);
        bResultados.setForeground(Color.WHITE);
        c.add(bResultados);

        checkEnCurso = new JCheckBox("Mostrar En Curso");
        checkEnCurso.setFont(new Font("Arial", Font.PLAIN, 17));
        checkEnCurso.setSize(160, 20);
        checkEnCurso.setLocation(50, 110);
        checkEnCurso.setBackground(Color.WHITE);
        c.add(checkEnCurso);

        checkPendiente = new JCheckBox("Mostrar Pendientes");
        checkPendiente.setFont(new Font("Arial", Font.PLAIN, 17));
        checkPendiente.setSize(170, 20);
        checkPendiente.setLocation(230, 110);
        checkPendiente.setBackground(Color.WHITE);
        c.add(checkPendiente);

        checkFinalizadas = new JCheckBox("Mostrar Finalizadas");
        checkFinalizadas.setFont(new Font("Arial", Font.PLAIN, 17));
        checkFinalizadas.setSize(170, 20);
        checkFinalizadas.setLocation(420, 110);
        checkFinalizadas.setBackground(Color.WHITE);
        c.add(checkFinalizadas);

        setVisible(true);
    }
}

class VentanaMenuAdmin {

    public static void main(String[] args) throws Exception
    {
        MenuAdminFrame f = new MenuAdminFrame();
    }
}