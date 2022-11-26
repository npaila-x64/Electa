package vistas.votante;
import javax.swing.*;
import java.awt.*;

class MyFrame extends JFrame {

    private Container c;
    private JLabel tEncabezado;
    private JLabel tCandidato;
    private JLabel tConfirmar;
    private JButton botonSi;
    private JButton botonNo;

    public MyFrame()
    {
        setTitle("Electa");
        setBounds(300, 90, 900, 600);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setResizable(false);

        c = getContentPane();
        c.setBackground(Color.WHITE);
        c.setLayout(null);

        tEncabezado = new JLabel("Usted votó por");
        tEncabezado.setFont(new Font("Arial", Font.PLAIN, 36));
        tEncabezado.setLocation(0,75);
        tEncabezado.setSize(900, 100);
        tEncabezado.setHorizontalAlignment(JLabel.CENTER);
        tEncabezado.setVerticalAlignment(JLabel.CENTER);
        c.add(tEncabezado);

        String candidato = "Insertar Candidato";
        tCandidato = new JLabel(candidato);
        tCandidato.setFont(new Font("Arial", Font.BOLD, 36));
        tCandidato.setLocation(0,175);
        tCandidato.setSize(900, 100);
        tCandidato.setHorizontalAlignment(JLabel.CENTER);
        tCandidato.setVerticalAlignment(JLabel.CENTER);
        //candidato.setBorder(BorderFactory.createLineBorder(Color.BLACK));
        c.add(tCandidato);

        tConfirmar = new JLabel("¿Desea confirmar su voto?");
        tConfirmar.setFont(new Font("Arial", Font.PLAIN, 36));
        tConfirmar.setLocation(0,275);
        tConfirmar.setSize(900, 100);
        tConfirmar.setHorizontalAlignment(JLabel.CENTER);
        tConfirmar.setVerticalAlignment(JLabel.CENTER);
        c.add(tConfirmar);


        botonSi = new JButton("Si");
        botonSi.setFont(new Font("Arial", Font.PLAIN, 15));
        botonSi.setSize(200, 50);
        botonSi.setLocation(175, 420);
        botonSi.setBackground(Color.BLACK);
        botonSi.setForeground(Color.WHITE);
        c.add(botonSi);

        botonNo = new JButton("No");
        botonNo.setFont(new Font("Arial", Font.PLAIN, 15));
        botonNo.setSize(200, 50);
        botonNo.setLocation(525, 420);
        botonNo.setBackground(Color.BLACK);
        botonNo.setForeground(Color.WHITE);

        c.add(botonNo);

        setVisible(true);
    }

}

class ConfirmarVoto {

    public static void main(String[] args) throws Exception
    {
        MyFrame f = new MyFrame();
    }
}