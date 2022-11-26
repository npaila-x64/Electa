package vistas;

import vistas.admin.PanelLogin;

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

//    public void mostrarMenu() {
//        cl.show(panel, "menu");
//    }
//
//    public void mostrarEditor() {
//        cl.show(panel, "editor");
//    }
//
//    public void agregarEditor(PanelEditor editor) {
//        panel.add("editor", editor);
//    }
//
//    public void agregarMenu(PanelMenu menu) {
//        panel.add("menu", menu);
//    }
//
//    public void agregarMostrador(PanelMostrador mostrador) {
//        panel.add("mostrador", mostrador);
//    }
//
//    public void mostrarMostrador() {
//        cl.show(panel, "mostrador");
//    }
}
