package vistas.votante;
import controladores.votante.ControladorVotacionesEnCurso;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class PanelVotacionesEnCurso extends JPanel implements ActionListener {
    private JLabel bordesTabla;
    private JTable tVotacionesEnCurso;
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
        tVotacionesEnCurso = new JTable(controlador.obtenerModeloDeTabla());
        tVotacionesEnCurso.setBounds(30, 40, 200, 300);
        tVotacionesEnCurso.setAutoCreateRowSorter(true);
        tVotacionesEnCurso.setRowHeight(70);
        tVotacionesEnCurso.setTableHeader(null);
        tVotacionesEnCurso.setCellSelectionEnabled(false);
        JScrollPane scrollPane = new JScrollPane(tVotacionesEnCurso);
        scrollPane.setSize(805, 390);
        scrollPane.setLocation(40, 40);
        this.add(scrollPane);
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
        if (e.getSource() == bResultados) {
            controlador.abirResultadosFueSolicitada();
        }
    }
}

//class VentanaMenuVotacion {
//    public static void main(String[] args) throws Exception {
//        PanelVotacionesEnCurso f = new PanelVotacionesEnCurso();
//    }
//}