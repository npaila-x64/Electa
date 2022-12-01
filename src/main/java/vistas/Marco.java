package vistas;

import vistas.admin.PanelAdministracion;
import vistas.votante.PanelOpciones;
import vistas.votante.PanelVotacionesEnCurso;

import javax.swing.*;
import java.awt.*;

public class Marco extends JFrame {

    private Container panel;
    private final CardLayout cl;

    public Marco() {
        cl = new CardLayout();
        configurarMarco();
        configurarPanel();
    }

    private void configurarPanel() {
        panel = getContentPane();
        panel.setLayout(cl);
    }

    private void configurarMarco() {
        setTitle("Electa");
        setBounds(300, 90, 900, 600);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setResizable(false);
    }

    public void agregarVotacionesEnCurso(PanelVotacionesEnCurso votacionesEnCurso) {
        panel.add("votacionesEnCurso", votacionesEnCurso);
    }

    public void agregarResultados(PanelResultados resultados) {
        panel.add("resultados", resultados);
    }

    public void agregarOpciones(PanelOpciones opciones) {
        panel.add("opciones", opciones);
    }

    public void agregarAdministracion(PanelAdministracion administracion) {
        panel.add("administracion", administracion);
    }

    public void agregarPanel(Container panel, String nombre) {
        this.panel.add(nombre, panel);
    }

    public void mostrarPanel(String panel) {
        cl.show(this.panel, panel);
    }
}
