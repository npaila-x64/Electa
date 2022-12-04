package vistas;

import javax.swing.*;
import java.awt.*;

/**
 * Crea la ventana principal usada durante la totalidad del programa.
 */
public class Marco extends JFrame {
    /**
     * Indica un contenedor para un panel.
     */
    private Container panel;
    /**
     * Indica un administrador de los paneles en un contenedor.
     */
    private final CardLayout cl;

    /**
     * Construye una ventana de tipo Marco. Configura los paneles y las especificaciones de la ventana.
     */
    public Marco() {
        cl = new CardLayout();
        configurarMarco();
        configurarPanel();
    }

    /**
     * Configura el contenedor con el CardLayout.
     */
    private void configurarPanel() {
        panel = getContentPane();
        panel.setLayout(cl);
    }

    /**
     * Configura el nombre, las dimensiones y comportamientos de la ventana.
     */
    private void configurarMarco() {
        setTitle("Electa");
        setBounds(300, 90, 900, 600);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setResizable(false);
    }

    /**
     * Agrega un panel en el contenedor.
     * @param panel el panel a agregar.
     * @param nombre un String cuyo valor representa un nombre identificador del panel.
     */
    public void agregarPanel(Container panel, String nombre) {
        this.panel.add(nombre, panel);
    }

    /**
     * Muestra un panel en pantalla.
     * @param panel un String cuyo valor representa un nombre identificador del panel.
     */
    public void mostrarPanel(String panel) {
        cl.show(this.panel, panel);
    }
}
