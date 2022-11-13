package vistas.votante;
import javax.swing.*;
import java.awt.*;

class MenuVotacionFrame extends JFrame {
    private Container c;
    private JLabel bordesTabla;
    private JButton bCerrarSesion;
    private JButton bResultados;
    public MenuVotacionFrame()
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
        bordesTabla.setSize(805, 390);
        bordesTabla.setLocation(40, 40);
        bordesTabla.setBorder(BorderFactory.createLineBorder(Color.BLACK));
        c.add(bordesTabla);

        bCerrarSesion = new JButton("Cerrar sesión");
        bCerrarSesion.setFont(new Font("Arial", Font.PLAIN, 15));
        bCerrarSesion.setSize(200, 50);
        bCerrarSesion.setLocation(40, 475);
        bCerrarSesion.setBackground(Color.BLACK);
        bCerrarSesion.setForeground(Color.WHITE);
        c.add(bCerrarSesion);

        bResultados = new JButton("Resultados");
        bResultados.setFont(new Font("Arial", Font.PLAIN, 15));
        bResultados.setSize(200, 50);
        bResultados.setLocation(645, 475);
        bResultados.setBackground(Color.BLACK);
        bResultados.setForeground(Color.WHITE);
        c.add(bResultados);

        setVisible(true);
    }
}

class VentanaMenuVotacion {

    public static void main(String[] args) throws Exception
    {
        MenuVotacionFrame f = new MenuVotacionFrame();
    }
}