package vistas.admin;
import controladores.admin.ControladorAdministracion;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class PanelAdministracion extends JPanel implements ActionListener {

    private ControladorAdministracion controlador;
    private JLabel bordesTabla;
    private JLabel textoDesde;
    private JLabel textoHasta;
    private JTextField campoTextoDesde;
    private JTextField campoTextoHasta;
    private JButton bCerrarSesion;
    private JButton bCrearVotacion;
    private JButton bResultados;
    private JCheckBox checkEnCurso;
    private JCheckBox checkPendientes;
    private JCheckBox checkFinalizadas;

    public PanelAdministracion(ControladorAdministracion controlador) {
        this.controlador = controlador;
        crearComponentes();
        setVisible(true);
    }

    private void crearComponentes() {
        configurarPanel();
        crearCheckMostrarEnCurso();
        crearCheckPendientes();
        crearCheckFinalizadas();
        crearTablaDeVotaciones();
        crearBotonCrearVotacion();
        crearBotonResultados();
        crearBotonCerrarSesion();
        crearEtiquetaFiltroDesde();
        crearEtiquetaFiltroHasta();
        crearCampoDeTextoFiltroDesde();
        crearCampoDeTextoFiltroHasta();
    }

    private void crearCampoDeTextoFiltroHasta() {
        campoTextoHasta = new JTextField();
        campoTextoHasta.setFont(new Font("Arial", Font.PLAIN, 15));
        campoTextoHasta.setSize(100, 40);
        campoTextoHasta.setLocation(745, 40);
        campoTextoHasta.setBorder(BorderFactory.createLineBorder(Color.BLACK));
        add(campoTextoHasta);
    }

    private void crearCampoDeTextoFiltroDesde() {
        campoTextoDesde = new JTextField();
        campoTextoDesde.setFont(new Font("Arial", Font.PLAIN, 15));
        campoTextoDesde.setSize(100, 40);
        campoTextoDesde.setLocation(560, 40);
        campoTextoDesde.setBorder(BorderFactory.createLineBorder(Color.BLACK));
        add(campoTextoDesde);
    }

    private void crearEtiquetaFiltroHasta() {
        textoHasta = new JLabel("hasta");
        textoHasta.setFont(new Font("Arial", Font.PLAIN, 15));
        textoHasta.setSize(100,40);
        textoHasta.setLocation(685, 40);
        add(textoHasta);
    }

    private void crearEtiquetaFiltroDesde() {
        textoDesde = new JLabel("Filtrar desde");
        textoDesde.setFont(new Font("Arial", Font.PLAIN, 15));
        textoDesde.setSize(120,40);
        textoDesde.setLocation(460, 40);
        add(textoDesde);
    }

    private void crearTablaDeVotaciones() {
        bordesTabla = new JLabel();
        bordesTabla.setSize(805, 275);
        bordesTabla.setLocation(40, 150);
        bordesTabla.setBorder(BorderFactory.createLineBorder(Color.BLACK));
        add(bordesTabla);
    }

    private void crearBotonCrearVotacion() {
        bCrearVotacion = new JButton("Crear nueva votación");
        bCrearVotacion.setFont(new Font("Arial", Font.PLAIN, 15));
        bCrearVotacion.setSize(200, 50);
        bCrearVotacion.setLocation(350, 475);
        bCrearVotacion.setBackground(Color.BLACK);
        bCrearVotacion.setForeground(Color.WHITE);
        bCrearVotacion.addActionListener(this);
        add(bCrearVotacion);
    }

    private void crearBotonResultados() {
        bResultados = new JButton("Resultados");
        bResultados.setFont(new Font("Arial", Font.PLAIN, 15));
        bResultados.setSize(200, 50);
        bResultados.setLocation(645, 475);
        bResultados.setBackground(Color.BLACK);
        bResultados.setForeground(Color.WHITE);
        bResultados.addActionListener(this);
        add(bResultados);
    }

    private void crearBotonCerrarSesion() {
        bCerrarSesion = new JButton("Cerrar sesión");
        bCerrarSesion.setFont(new Font("Arial", Font.PLAIN, 15));
        bCerrarSesion.setSize(200, 50);
        bCerrarSesion.setLocation(40, 40);
        bCerrarSesion.setBackground(Color.BLACK);
        bCerrarSesion.setForeground(Color.WHITE);
        bCerrarSesion.addActionListener(this);
        add(bCerrarSesion);
    }

    private void crearCheckMostrarEnCurso() {
        checkEnCurso = new JCheckBox("Mostrar En Curso");
        checkEnCurso.setFont(new Font("Arial", Font.PLAIN, 17));
        checkEnCurso.setSize(160, 20);
        checkEnCurso.setLocation(50, 110);
        checkEnCurso.setBackground(Color.WHITE);
        add(checkEnCurso);
    }

    private void crearCheckPendientes() {
        checkPendientes = new JCheckBox("Mostrar Pendientes");
        checkPendientes.setFont(new Font("Arial", Font.PLAIN, 17));
        checkPendientes.setSize(170, 20);
        checkPendientes.setLocation(230, 110);
        checkPendientes.setBackground(Color.WHITE);
        add(checkPendientes);
    }

    private void crearCheckFinalizadas() {
        checkFinalizadas = new JCheckBox("Mostrar Finalizadas");
        checkFinalizadas.setFont(new Font("Arial", Font.PLAIN, 17));
        checkFinalizadas.setSize(170, 20);
        checkFinalizadas.setLocation(420, 110);
        checkFinalizadas.setBackground(Color.WHITE);
        add(checkFinalizadas);
    }

    private void configurarPanel() {
        setBackground(Color.WHITE);
        setLayout(null);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == bCerrarSesion) {
            controlador.cerrarSesionFueSolicitado();
        }
        if (e.getSource() == bResultados) {
            controlador.verResultadosFueSolicitado();
        }
    }
}