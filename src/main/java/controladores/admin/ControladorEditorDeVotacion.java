package controladores.admin;

import controladores.ControladorAplicacion;
import modelos.Opcion;
import modelos.Votacion;
import dao.VotacionDao;
import modelos.enums.CampoDeVotacion;
import utils.ValidadorDeDatos;
import vistas.admin.OpcionesTableModel;
import vistas.admin.PanelEditorVotaciones;

import javax.swing.*;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class ControladorEditorDeVotacion {

    private final PanelEditorVotaciones vista;
    private final ControladorAplicacion controlador;
    private final OpcionesTableModel modelo;
    private Votacion votacion = new Votacion();
    private Boolean estaVotacionEnProcesoDeCreacion;

    public ControladorEditorDeVotacion(ControladorAplicacion controlador) {
        this.controlador = controlador;
        modelo = new OpcionesTableModel();
        vista = new PanelEditorVotaciones(this);
        this.controlador.agregarPanel(vista, "editor");
    }

    private void eliminarVotacion() {
        VotacionDao.eliminarVotacion(votacion);
    }

    public void abrir(Votacion votacion, boolean esNuevaVotacion, boolean modoLectura) {
        this.votacion = votacion;
        this.estaVotacionEnProcesoDeCreacion = esNuevaVotacion;
        modelo.setOpciones(votacion.getOpciones());
        cargarVotacion();
        controlador.mostrarPanel("editor");
//        vista.setModoLector(modoLectura);
        vista.moverScrollAInicio();
    }

    private void cargarVotacion() {
        vista.cargarVotacion(votacion);
    }

    public void cancelarFueSolicitado() {
        controlador.abrirAdministracion();
    }

    public OpcionesTableModel getModeloDeTabla() {
        return modelo;
    }

    public void eliminarOpcionFueSolicitado(int fila) {
        votacion.removeOpcion(fila);
        modelo.setOpciones(votacion.getOpciones());
    }

    public void guardarFueSolicitado() {
        if (seCumplenRequisitosObligatorios()) {
            obtenerDatosDeVotacionDeVista();
            if (estaVotacionEnProcesoDeCreacion) {
                VotacionDao.crearVotacion(votacion);
                estaVotacionEnProcesoDeCreacion = false;
            } else {
                VotacionDao.actualizarVotacion(votacion);
            }
            vista.mostrarVotacionGuardadaConExito();
        } else {
            vista.mostrarNoSeCumplenRequisitosObligatorios();
        }
    }

    private void obtenerDatosDeVotacionDeVista() {
        votacion.setTitulo(vista.getTitulo());
        votacion.setDescripcion(vista.getDescripcion());

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-uuuu");

        LocalDateTime fechaInicio = LocalDate.parse(vista.getFechaInicio(), formatter).atStartOfDay()
                .withHour(vista.getHoraInicio()).withMinute(vista.getMinutoInicio());
        votacion.setFechaTiempoInicio(fechaInicio);

        LocalDateTime fechaTermino = LocalDate.parse(vista.getFechaTermino(), formatter).atStartOfDay()
                .withHour(vista.getHoraTermino()).withMinute(vista.getMinutoTermino());
        votacion.setFechaTiempoTermino(fechaTermino);
    }

    private boolean seCumplenRequisitosObligatorios() {
        if (vista.getTitulo().isBlank()) {
            return false;
        }
        if (votacion.getOpciones().size() < 2) {
            return false;
        }
        return true;
    }

    public void agregarOpcionFueSolicitado() {
        UIManager.put("OptionPane.okButtonText", "OK");
        UIManager.put("OptionPane.cancelButtonText", "Cancelar");
        String nombre = JOptionPane.showInputDialog(
                controlador.getMarco(),
                "Escriba el nombre de la opción.",
                "Agregar opción",
                JOptionPane.PLAIN_MESSAGE);
        if (!(nombre.isBlank() || nombre == null)) {

        }
    }
}
