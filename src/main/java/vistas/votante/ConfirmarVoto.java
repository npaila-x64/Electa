package vistas.votante;
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

class MyFrame extends JFrame implements ActionListener {

    private Container c;
    private JLabel titulo;
    private JLabel candidato;
    private JLabel confirmar;
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

        titulo = new JLabel("Usted votó por");
        titulo.setFont(new Font("Arial", Font.PLAIN, 36));
        titulo.setLocation(0,75);
        titulo.setSize(900, 100);
        titulo.setHorizontalAlignment(JLabel.CENTER);
        titulo.setVerticalAlignment(JLabel.CENTER);
//        titulo.setBorder(BorderFactory.createLineBorder(Color.BLACK));
        c.add(titulo);

        candidato = new JLabel("insertar candidato");
        candidato.setFont(new Font("Arial", Font.BOLD, 36));
        candidato.setLocation(0,175);
        candidato.setSize(900, 100);
        candidato.setHorizontalAlignment(JLabel.CENTER);
        candidato.setVerticalAlignment(JLabel.CENTER);
        //candidato.setBorder(BorderFactory.createLineBorder(Color.BLACK));
        c.add(candidato);

        confirmar = new JLabel("¿Desea confirmar su voto?");
        confirmar.setFont(new Font("Arial", Font.PLAIN, 36));
        confirmar.setLocation(0,275);
        confirmar.setSize(900, 100);
        confirmar.setHorizontalAlignment(JLabel.CENTER);
        confirmar.setVerticalAlignment(JLabel.CENTER);
        //confirmar.setBorder(BorderFactory.createLineBorder(Color.BLACK));
        c.add(confirmar);


        botonSi = new JButton("Si");
        botonSi.setFont(new Font("Arial", Font.PLAIN, 15));
        botonSi.setSize(200, 50);
        botonSi.setLocation(175, 420);
        botonSi.addActionListener(this);

        botonSi.setBackground(Color.BLACK);
        botonSi.setForeground(Color.WHITE);

        c.add(botonSi);

        botonNo = new JButton("No");
        botonNo.setFont(new Font("Arial", Font.PLAIN, 15));
        botonNo.setSize(200, 50);
        botonNo.setLocation(525, 420);
        botonNo.addActionListener(this);

        botonNo.setBackground(Color.BLACK);
        botonNo.setForeground(Color.WHITE);

        c.add(botonNo);

        setVisible(true);
    }

    public void actionPerformed(ActionEvent e)
    {
        if (e.getSource() == botonSi) {
        }
    }
}

class ConfirmarVoto {

    public static void main(String[] args) throws Exception
    {
        MyFrame f = new MyFrame();
    }
}