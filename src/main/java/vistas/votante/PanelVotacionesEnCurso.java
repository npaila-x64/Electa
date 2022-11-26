package vistas.votante;
import controladores.votante.ControladorVotacionesEnCurso;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class PanelVotacionesEnCurso extends JPanel implements ActionListener {
    private JLabel bordesTabla;
    private JButton bCerrarSesion;
    private JButton bResultados;
    private ControladorVotacionesEnCurso controlador;

    public PanelVotacionesEnCurso(ControladorVotacionesEnCurso controlador) {
        this.controlador = controlador;
        crearComponentes();
        setVisible(true);
    }

    private void crearComponentes() {
        configurarPanel();
        crearTablaDeVotaciones();
        crearBotonCerrarSesion();
        crearBotonResultados();
    }

    private void crearBotonResultados() {
        bResultados = new JButton("Resultados");
        bResultados.addActionListener(this);
        bResultados.setFont(new Font("Arial", Font.PLAIN, 15));
        bResultados.setSize(200, 50);
        bResultados.setLocation(645, 475);
        bResultados.setBackground(Color.BLACK);
        bResultados.setForeground(Color.WHITE);
        this.add(bResultados);
    }

    private void crearTablaDeVotaciones() {
        bordesTabla = new JLabel();
        bordesTabla.setSize(805, 390);
        bordesTabla.setLocation(40, 40);
        bordesTabla.setBorder(BorderFactory.createLineBorder(Color.BLACK));
        this.add(bordesTabla);
    }

    private void crearBotonCerrarSesion() {
        bCerrarSesion = new JButton("Cerrar sesión");
        bCerrarSesion.addActionListener(this);
        bCerrarSesion.setFont(new Font("Arial", Font.PLAIN, 15));
        bCerrarSesion.setSize(200, 50);
        bCerrarSesion.setLocation(40, 475);
        bCerrarSesion.setBackground(Color.BLACK);
        bCerrarSesion.setForeground(Color.WHITE);
        this.add(bCerrarSesion);
    }

    private void configurarPanel() {
        this.setBackground(Color.WHITE);
        this.setLayout(null);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == bCerrarSesion) {
            controlador.cerrarSesionFueSolicitada();
        }
    }
}

//class VentanaMenuVotacion {
//    public static void main(String[] args) throws Exception {
//        PanelVotacionesEnCurso f = new PanelVotacionesEnCurso();
//    }
//}