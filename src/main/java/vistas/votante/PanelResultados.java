package vistas.votante;
import controladores.ControladorResultados;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class PanelResultados extends JPanel implements ActionListener {

    private ControladorResultados controlador;
    private JLabel tEncabezado;
    private JLabel bordesTabla1;
    private JLabel bordesTabla2;
    private JButton bVolver;

    public PanelResultados(ControladorResultados controlador) {
        this.controlador = controlador;
        crearComponentes();
        setVisible(true);
    }

    private void crearComponentes() {
        configurarPanel();
        crearBotonVolver();

        tEncabezado = new JLabel("Resultados Elecciones Rectoriales");
        tEncabezado.setFont(new Font("Arial", Font.PLAIN, 36));
        tEncabezado.setLocation(0,40);
        tEncabezado.setSize(900, 36);
        tEncabezado.setHorizontalAlignment(JLabel.CENTER);
        this.add(tEncabezado);

        bordesTabla1 = new JLabel();
        bordesTabla1.setSize(805, 170);
        bordesTabla1.setLocation(40, 96);
        bordesTabla1.setBorder(BorderFactory.createLineBorder(Color.BLACK));
        this.add(bordesTabla1);

        bordesTabla2 = new JLabel();
        bordesTabla2.setSize(805, 170);
        bordesTabla2.setLocation(40, 286);
        bordesTabla2.setBorder(BorderFactory.createLineBorder(Color.BLACK));
        this.add(bordesTabla2);
    }

    private void crearBotonVolver() {
        bVolver = new JButton("Volver");
        bVolver.addActionListener(this);
        bVolver.setFont(new Font("Arial", Font.PLAIN, 15));
        bVolver.setSize(200, 50);
        bVolver.setLocation(350, 475);
        bVolver.setBackground(Color.BLACK);
        bVolver.setForeground(Color.WHITE);
        this.add(bVolver);
    }

    private void configurarPanel() {
        this.setBackground(Color.WHITE);
        this.setLayout(null);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == bVolver) {
            controlador.volverFueSolitado();
        }
    }
}