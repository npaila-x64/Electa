package vistas.admin;
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

class MyFrame extends JFrame implements ActionListener {

    private Container c;
    private JLabel titulo;
    private JLabel logo;
    private JTextField trut;
    private JTextField tclave;
    private JButton ingreso;

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

        titulo = new JLabel("Tu cuenta Electa");
        titulo.setFont(new Font("Arial", Font.BOLD, 36));
        titulo.setSize(900, 300);
        titulo.setHorizontalAlignment(JLabel.CENTER);
        titulo.setVerticalAlignment(JLabel.CENTER);
//        titulo.setBorder(BorderFactory.createLineBorder(Color.BLACK));
        c.add(titulo);

        ImageIcon imagen = new ImageIcon("src/main/img/logo.png");
        logo = new JLabel();
        logo.setIcon(imagen);

        logo.setSize(205, 78);
//        logo.setLocation(300, 230);
        logo.setHorizontalAlignment(JLabel.CENTER);
        logo.setVerticalAlignment(JLabel.CENTER);
//        titulo.setBorder(BorderFactory.createLineBorder(Color.BLACK));
        c.add(logo);



        trut = new JTextField();
        trut.setFont(new Font("Arial", Font.PLAIN, 15));
        trut.setSize(300, 45);
        trut.setLocation(300, 230);
        trut.setBorder(BorderFactory.createLineBorder(Color.BLACK));
        trut.setText("Rut");
        c.add(trut);


        tclave = new JTextField();
        tclave.setFont(new Font("Arial", Font.PLAIN, 15));
        tclave.setSize(300, 45);
        tclave.setLocation(300, 315);
        tclave.setBorder(BorderFactory.createLineBorder(Color.BLACK));
        tclave.setText("Contraseña");
        c.add(tclave);

        ingreso = new JButton("Ingresar");
        ingreso.setFont(new Font("Arial", Font.PLAIN, 15));
        ingreso.setSize(200, 50);
        ingreso.setLocation(350, 420);
        ingreso.addActionListener(this);

        ingreso.setBackground(Color.BLACK);
        ingreso.setForeground(Color.WHITE);

        c.add(ingreso);

        setVisible(true);
    }

    public void actionPerformed(ActionEvent e)
    {
        if (e.getSource() == ingreso) {
        }
    }
}

class VentanaLogin {

    public static void main(String[] args) throws Exception
    {
        MyFrame f = new MyFrame();
    }
}