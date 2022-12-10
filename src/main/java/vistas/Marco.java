package vistas;

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
        setIconImage(new ImageIcon("src/main/img/logo_min.png").getImage());
        setBounds(300, 90, 900, 600);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setResizable(false);
    }

    public void agregarPanel(Container panel, String nombre) {
        this.panel.add(nombre, panel);
    }

    public void mostrarPanel(String panel) {
        cl.show(this.panel, panel);
    }
}
