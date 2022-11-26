package vistas.admin;
import javax.swing.*;
import java.awt.*;

class MenuEditorVotacionesFrame extends JFrame {
    private Container c;
    private JLabel textoEncabezado;
    private JLabel textoTitulo;
    private JLabel textoDescripcion;
    private JLabel textoFechaInicio;
    private JLabel textoFechaTermino;
    private JTextField campoTextoFechaInicio;
    private JTextField campoTextoFechaTermino;
    private JTextField campoTextoTitulo;
    private JTextField campoTextoDescripcion;
    private JLabel bordesTabla;
    private JButton bAgregarOpcion;
    private JButton bEliminarOpcion;
    private JButton bGuardar;
    private JButton bCancelar;
    private JButton bEliminar;
    public MenuEditorVotacionesFrame()
    {
        setTitle("Electa");
        setBounds(300, 90, 900, 600);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setResizable(false);

        c = getContentPane();
        c.setBackground(Color.WHITE);
        c.setLayout(null);

        textoEncabezado = new JLabel("Editor de votaciones");
        textoEncabezado.setFont(new Font("Arial", Font.BOLD, 16));
        textoEncabezado.setLocation(0,20);
        textoEncabezado.setSize(900, 18);
        textoEncabezado.setHorizontalAlignment(JLabel.CENTER);
        c.add(textoEncabezado);

        textoTitulo = new JLabel("Titulo:");
        textoTitulo.setFont(new Font("Arial", Font.PLAIN, 14));
        textoTitulo.setSize(100, 40);
        textoTitulo.setLocation(40,48);
        c.add(textoTitulo);

        textoDescripcion = new JLabel("Descripción:");
        textoDescripcion.setFont(new Font("Arial", Font.PLAIN, 14));
        textoDescripcion.setSize(120,40);
        textoDescripcion.setLocation(40,98);
        c.add(textoDescripcion);

        textoFechaInicio = new JLabel("Fecha de inicio:");
        textoFechaInicio.setFont(new Font("Arial", Font.PLAIN, 14));
        textoFechaInicio.setSize(130,40);
        textoFechaInicio.setLocation(40,200);
        c.add(textoFechaInicio);

        textoFechaTermino = new JLabel("Fecha de termino:");
        textoFechaTermino.setFont(new Font("Arial", Font.PLAIN, 14));
        textoFechaTermino.setSize(130,40);
        textoFechaTermino.setLocation(275,200);
        c.add(textoFechaTermino);

        campoTextoTitulo = new JTextField();
        campoTextoTitulo.setSize(485, 40);
        campoTextoTitulo.setLocation(150, 48);
        campoTextoTitulo.setBorder(BorderFactory.createLineBorder(Color.BLACK));
        c.add(campoTextoTitulo);

        campoTextoDescripcion = new JTextField();
        campoTextoDescripcion.setSize(485,93);
        campoTextoDescripcion.setLocation(150, 98);
        campoTextoDescripcion.setBorder(BorderFactory.createLineBorder(Color.BLACK));
        c.add(campoTextoDescripcion);

        campoTextoFechaInicio = new JTextField();
        campoTextoFechaInicio.setFont(new Font("Arial", Font.PLAIN, 15));
        campoTextoFechaInicio.setSize(100, 35);
        campoTextoFechaInicio.setLocation(150, 200);
        campoTextoFechaInicio.setBorder(BorderFactory.createLineBorder(Color.BLACK));
        c.add(campoTextoFechaInicio);

        campoTextoFechaTermino = new JTextField();
        campoTextoFechaTermino.setFont(new Font("Arial", Font.PLAIN, 15));
        campoTextoFechaTermino.setSize(100, 35);
        campoTextoFechaTermino.setLocation(400, 200);
        campoTextoFechaTermino.setBorder(BorderFactory.createLineBorder(Color.BLACK));
        c.add(campoTextoFechaTermino);

        bordesTabla = new JLabel();
        bordesTabla.setSize(595, 200);
        bordesTabla.setLocation(40, 245);
        bordesTabla.setBorder(BorderFactory.createLineBorder(Color.BLACK));
        c.add(bordesTabla);

        bAgregarOpcion = new JButton("Agregar opción");
        bAgregarOpcion.setFont(new Font("Arial", Font.PLAIN, 15));
        bAgregarOpcion.setSize(200, 50);
        bAgregarOpcion.setLocation(645, 245);
        bAgregarOpcion.setBackground(Color.BLACK);
        bAgregarOpcion.setForeground(Color.WHITE);
        c.add(bAgregarOpcion);

        bEliminarOpcion = new JButton("Eliminar opción");
        bEliminarOpcion.setFont(new Font("Arial", Font.PLAIN, 15));
        bEliminarOpcion.setSize(200, 50);
        bEliminarOpcion.setLocation(645, 305);
        bEliminarOpcion.setBackground(Color.BLACK);
        bEliminarOpcion.setForeground(Color.WHITE);
        c.add(bEliminarOpcion);

        bGuardar = new JButton("Guardar");
        bGuardar.setFont(new Font("Arial", Font.PLAIN, 15));
        bGuardar.setSize(200, 50);
        bGuardar.setLocation(435, 475);
        bGuardar.setBackground(Color.BLACK);
        bGuardar.setForeground(Color.WHITE);
        c.add(bGuardar);

        bCancelar = new JButton("Cancelar");
        bCancelar.setFont(new Font("Arial", Font.PLAIN, 15));
        bCancelar.setSize(200, 50);
        bCancelar.setLocation(645, 475);
        bCancelar.setBackground(Color.BLACK);
        bCancelar.setForeground(Color.WHITE);
        c.add(bCancelar);

        bEliminar = new JButton("Eliminar");
        bEliminar.setFont(new Font("Arial", Font.PLAIN, 15));
        bEliminar.setSize(200, 50);
        //bEliminar.setLocation(645, 475);
        bEliminar.setLocation(40, 475);
        bEliminar.setBackground(Color.BLACK);
        bEliminar.setForeground(Color.WHITE);
        c.add(bEliminar);

        setVisible(true);
    }
}

class VentanaMenuEditorVotaciones {

    public static void main(String[] args) throws Exception
    {
        MenuEditorVotacionesFrame f = new MenuEditorVotacionesFrame();
    }
}