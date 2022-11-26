package vistas.votante;
import javax.swing.*;
import java.awt.*;

class MenuResultadosFrame extends JFrame {
    private Container c;
    private JLabel bordesTabla;
    private JButton bVolver;
    public MenuResultadosFrame()
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

        bVolver = new JButton("Volver");
        bVolver.setFont(new Font("Arial", Font.PLAIN, 15));
        bVolver.setSize(200, 50);
        bVolver.setLocation(350, 475);
        bVolver.setBackground(Color.BLACK);
        bVolver.setForeground(Color.WHITE);

        c.add(bVolver);

        setVisible(true);
    }
}

class VentanaMenuResultados {

    public static void main(String[] args) throws Exception
    {
        MenuResultadosFrame f = new MenuResultadosFrame();
    }
}