package vistas;

import vistas.admin.PanelLogin;
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

    public void agregarLogin(PanelLogin login) {
        panel.add("login", login);
    }

    public void mostrarLogin() {
        cl.show(panel, "login");
    }

    public void agregarVotacionesEnCurso(PanelVotacionesEnCurso votacionesEnCurso) {
        panel.add("votacionesEnCurso", votacionesEnCurso);
    }

    public void mostrarVotacionesEnCurso() {
        cl.show(panel, "votacionesEnCurso");
    }
}
