package vistas.votante;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class DialogoDeConfirmacion extends JDialog implements ActionListener {

    private JLabel tEncabezado;
    private JLabel tCandidato;
    private JLabel tConfirmar;
    private JButton botonSi;
    private JButton botonNo;
    private boolean confirmacion;

    public DialogoDeConfirmacion(Frame owner, boolean modal) {
        super(owner, "Confirme su voto", modal);
        crearComponentes();
        setDefaultCloseOperation(JDialog.DO_NOTHING_ON_CLOSE);
        setSize(489, 356);
        setLocationRelativeTo(null);
        setResizable(false);
    }

    public void setNombreDeOpcion(String nombreDeOpcion) {
        tCandidato.setText(nombreDeOpcion);
    }

    public boolean obtenerConfirmacion() {
        setVisible(true);
        return confirmacion;
    }

    private void configurarPanel() {
        getContentPane().setBackground(Color.WHITE);
        getContentPane().setLayout(null);
    }

    private void crearComponentes() {
        configurarPanel();
        crearEtiquetaEncabezado();
        crearEtiquetaCandidato();
        crearEtiquetaConfirmar();
        crearBotonSi();
        crearBotonNo();
    }

    private void crearEtiquetaEncabezado() {
        tEncabezado = new JLabel("Usted votó por");
        tEncabezado.setFont(new Font("Arial", Font.PLAIN, 28));
        tEncabezado.setLocation(145,28);
        tEncabezado.setSize(182, 32);
        this.add(tEncabezado);
    }

    private void crearEtiquetaCandidato() {
        tCandidato = new JLabel("Inserte candidato");
        tCandidato.setFont(new Font("Arial", Font.BOLD, 24));
        tCandidato.setLocation(36,72);
        tCandidato.setSize(400, 76);
        tCandidato.setHorizontalAlignment(JLabel.CENTER);
        tCandidato.setVerticalAlignment(JLabel.CENTER);
        this.add(tCandidato);
    }

    private void crearEtiquetaConfirmar() {
        tConfirmar = new JLabel("¿Desea confirmar su voto?");
        tConfirmar.setFont(new Font("Arial", Font.PLAIN, 26));
        tConfirmar.setLocation(65,163);
        tConfirmar.setSize(347, 29);
        tConfirmar.setHorizontalAlignment(JLabel.CENTER);
        this.add(tConfirmar);
    }

    private void crearBotonNo() {
        botonNo = new JButton("No");
        botonNo.setFont(new Font("Arial", Font.PLAIN, 22));
        botonNo.setSize(127, 45);
        botonNo.setLocation(285, 243);
        botonNo.setBackground(Color.BLACK);
        botonNo.setForeground(Color.WHITE);
        botonNo.setFocusable(false);
        botonNo.addActionListener(this);
        this.add(botonNo);
    }

    private void crearBotonSi() {
        botonSi = new JButton("Si");
        botonSi.setFont(new Font("Arial", Font.PLAIN, 22));
        botonSi.setSize(127, 45);
        botonSi.setLocation(65, 243);
        botonSi.setBackground(Color.BLACK);
        botonSi.setForeground(Color.WHITE);
        botonSi.setFocusable(false);
        botonSi.addActionListener(this);
        this.add(botonSi);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == botonSi) {
            confirmacion = true;
        }
        if (e.getSource() == botonNo) {
            confirmacion = false;
        }
        setVisible(false);
        dispose();
    }
}
