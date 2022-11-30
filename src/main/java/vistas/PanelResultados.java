package vistas;
import controladores.ControladorResultados;
import modelos.Votacion;

import javax.swing.*;
import javax.swing.table.DefaultTableCellRenderer;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.time.format.DateTimeFormatter;

public class PanelResultados extends JPanel implements ActionListener {

    private ControladorResultados controlador;
    private JTable tVotacionesEnCurso;
    private JTable tInforme;
    private JButton bVolver;
    private JLabel lEncabezado;
    private JLabel lFechaHoraInicio;
    private JLabel lFechaHoraTermino;
    private JTextField tFechaHoraInicio;
    private JTextField tFechaHoraTermino;

    public PanelResultados(ControladorResultados controlador) {
        this.controlador = controlador;
        crearComponentes();
        setVisible(true);
    }

    private void crearComponentes() {
        configurarPanel();
        crearBotonVolver();
        crearEtiquetaFechaHoraInicio();
        crearEtiquetaFechaHoraTermino();
        crearEtiquetaEncabezado();
        crearTablaDeVotaciones();
        crearTablaDeInforme();
        crearCampoDeTextoFechaHoraInicio();
        crearCampoDeTextoFechaHoraTermino();
    }

    private void crearCampoDeTextoFechaHoraInicio() {
        tFechaHoraInicio = new JTextField();
        tFechaHoraInicio.setFont(new Font("Arial", Font.PLAIN, 15));
        tFechaHoraInicio.setLocation(249, 81);
        tFechaHoraInicio.setSize(179, 30);
        tFechaHoraInicio.setBorder(BorderFactory.createLineBorder(Color.BLACK));
        tFechaHoraInicio.setBorder(BorderFactory.createCompoundBorder(
                tFechaHoraInicio.getBorder(),
                BorderFactory.createEmptyBorder(5, 10, 5, 5)));
        tFechaHoraInicio.setEditable(false);
        tFechaHoraInicio.setBackground(Color.WHITE);
        add(tFechaHoraInicio);
    }

    private void crearCampoDeTextoFechaHoraTermino() {
        tFechaHoraTermino = new JTextField();
        tFechaHoraTermino.setFont(new Font("Arial", Font.PLAIN, 15));
        tFechaHoraTermino.setLocation(658, 81);
        tFechaHoraTermino.setSize(179, 30);
        tFechaHoraTermino.setBorder(BorderFactory.createLineBorder(Color.BLACK));
        tFechaHoraTermino.setBorder(BorderFactory.createCompoundBorder(
                tFechaHoraTermino.getBorder(),
                BorderFactory.createEmptyBorder(5, 10, 5, 5)));
        tFechaHoraTermino.setEditable(false);
        tFechaHoraTermino.setBackground(Color.WHITE);
        add(tFechaHoraTermino);
    }

    private void crearEtiquetaFechaHoraInicio() {
        lFechaHoraInicio = new JLabel("Fecha y hora de inicio");
        lFechaHoraInicio.setFont(new Font("Arial", Font.PLAIN, 16));
        lFechaHoraInicio.setLocation(83, 87);
        lFechaHoraInicio.setSize(189, 18);
        add(lFechaHoraInicio);
    }

    private void crearEtiquetaFechaHoraTermino() {
        lFechaHoraTermino = new JLabel("Fecha y hora de término");
        lFechaHoraTermino.setFont(new Font("Arial", Font.PLAIN, 16));
        lFechaHoraTermino.setLocation(477, 87);
        lFechaHoraTermino.setSize(196, 18);
        add(lFechaHoraTermino);
    }

    private void crearTablaDeVotaciones() {
        tVotacionesEnCurso = new JTable(controlador.getModeloDeTablaOpciones());
        tVotacionesEnCurso.setBounds(30, 40, 200, 300);
        tVotacionesEnCurso.setRowHeight(30);
        tVotacionesEnCurso.setCellSelectionEnabled(false);
        tVotacionesEnCurso.getColumnModel().getColumn(0).setPreferredWidth(340);
        tVotacionesEnCurso.getColumnModel().getColumn(1).setPreferredWidth(205);
        tVotacionesEnCurso.getColumnModel().getColumn(2).setPreferredWidth(205);
        tVotacionesEnCurso.setFont(new Font("Arial", Font.PLAIN, 14));
        DefaultTableCellRenderer renderer = new DefaultTableCellRenderer() {
            @Override
            public Component getTableCellRendererComponent(JTable table,
                                                           Object value, boolean isSelected, boolean hasFocus,
                                                           int row, int column) {
                super.getTableCellRendererComponent(table, value, isSelected, hasFocus,
                        row, column);
                setBorder(BorderFactory.createCompoundBorder(getBorder(),
                        BorderFactory.createEmptyBorder(0, 20, 0, 10)));
                return this;
            }
        };
        tVotacionesEnCurso.setDefaultRenderer(String.class, renderer);

        JScrollPane scrollPane = new JScrollPane(tVotacionesEnCurso);
        scrollPane.setLocation(85, 130);
        scrollPane.setSize(750, 150);
        scrollPane.getViewport().setBackground(Color.WHITE);
        add(scrollPane);
    }

    private void crearTablaDeInforme() {
        tInforme = new JTable(controlador.getModeloDeTablaInforme());
        tInforme.setBounds(30, 40, 200, 300);
        tInforme.setRowHeight(30);
        tInforme.setCellSelectionEnabled(false);
        tInforme.getColumnModel().getColumn(0).setPreferredWidth(340);
        tInforme.getColumnModel().getColumn(1).setPreferredWidth(205);
        tInforme.getColumnModel().getColumn(2).setPreferredWidth(205);
        tInforme.setFont(new Font("Arial", Font.PLAIN, 14));
        DefaultTableCellRenderer renderer = new DefaultTableCellRenderer() {
            @Override
            public Component getTableCellRendererComponent(JTable table,
                                                           Object value, boolean isSelected, boolean hasFocus,
                                                           int row, int column) {
                super.getTableCellRendererComponent(table, value, isSelected, hasFocus,
                        row, column);
                setBorder(BorderFactory.createCompoundBorder(getBorder(),
                        BorderFactory.createEmptyBorder(0, 20, 0, 10)));
                return this;
            }
        };
        tInforme.setDefaultRenderer(String.class, renderer);

        JScrollPane scrollPane = new JScrollPane(tInforme);
        scrollPane.setLocation(83, 293);
        scrollPane.setSize(753, 150);
        scrollPane.getViewport().setBackground(Color.WHITE);
        add(scrollPane);
    }

    private void crearEtiquetaEncabezado() {
        lEncabezado = new JLabel("Resultados Elecciones Rectoriales");
        lEncabezado.setFont(new Font("Arial", Font.PLAIN, 22));
        lEncabezado.setLocation(0,25);
        lEncabezado.setSize(900, 36);
        lEncabezado.setHorizontalAlignment(JLabel.CENTER);
        add(lEncabezado);
    }

    private void crearBotonVolver() {
        bVolver = new JButton("Volver");
        bVolver.addActionListener(this);
        bVolver.setFont(new Font("Arial", Font.PLAIN, 15));
        bVolver.setSize(200, 50);
        bVolver.setLocation(350, 475);
        bVolver.setBackground(Color.BLACK);
        bVolver.setForeground(Color.WHITE);
        add(bVolver);
    }

    private void configurarPanel() {
        setBackground(Color.WHITE);
        setLayout(null);
    }

    public void cargarVotacion(Votacion votacion) {
        String fechaHoraInicio = String.format("%s a las %s",
                votacion.getFechaInicio().format(DateTimeFormatter.ofPattern("dd-MM-uuuu")),
                votacion.getTiempoInicio());
        String fechaHoraTermino = String.format("%s a las %s",
                votacion.getFechaTermino().format(DateTimeFormatter.ofPattern("dd-MM-uuuu")),
                votacion.getTiempoTermino());
        tFechaHoraInicio.setText(fechaHoraInicio);
        tFechaHoraTermino.setText(fechaHoraTermino);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == bVolver) {
            controlador.volverAEscogerResultadoFueSolicitado();
        }
    }
}