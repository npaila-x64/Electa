package vistas.votante;
import javax.swing.*;
import java.awt.*;

class VentanaOpcionesFrame extends JFrame {
    private Container c;
    private JLabel bordesTabla;
    private JButton bVolver;
    private JButton bAbstencion;
    public VentanaOpcionesFrame()
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

        bAbstencion = new JButton("Abstención");
        bAbstencion.setFont(new Font("Arial", Font.PLAIN, 15));
        bAbstencion.setSize(200, 50);
        bAbstencion.setLocation(645, 475);
        bAbstencion.setBackground(Color.WHITE);
        bAbstencion.setForeground(Color.BLACK);
        c.add(bAbstencion);

        setVisible(true);
    }
}

class VentanaOpciones {

    public static void main(String[] args) throws Exception
    {
        VentanaOpcionesFrame f = new VentanaOpcionesFrame();
    }
}