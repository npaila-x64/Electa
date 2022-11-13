package vistas.votante;
import javax.swing.*;
import java.awt.*;

class ResultadosFrame extends JFrame {
    private Container c;
    private JLabel tEncabezado;
    private JLabel bordesTabla1;
    private JLabel bordesTabla2;
    private JButton bVolver;
    public ResultadosFrame()
    {
        setTitle("Electa");
        setBounds(300, 90, 900, 600);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setResizable(false);

        c = getContentPane();
        c.setBackground(Color.WHITE);
        c.setLayout(null);

        String año = "2022";
        tEncabezado = new JLabel("Resultados Elecciones Rectoriales " + año);
        tEncabezado.setFont(new Font("Arial", Font.PLAIN, 36));
        tEncabezado.setLocation(0,40);
        tEncabezado.setSize(900, 36);
        tEncabezado.setHorizontalAlignment(JLabel.CENTER);
        c.add(tEncabezado);

        bordesTabla1 = new JLabel();
        bordesTabla1.setSize(805, 170);
        bordesTabla1.setLocation(40, 96);
        bordesTabla1.setBorder(BorderFactory.createLineBorder(Color.BLACK));
        c.add(bordesTabla1);

        bordesTabla2 = new JLabel();
        bordesTabla2.setSize(805, 170);
        bordesTabla2.setLocation(40, 286);
        bordesTabla2.setBorder(BorderFactory.createLineBorder(Color.BLACK));
        c.add(bordesTabla2);

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

class VentanaResultados {

    public static void main(String[] args) throws Exception
    {
        ResultadosFrame f = new ResultadosFrame();
    }
}