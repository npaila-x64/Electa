package controladores.admin;

import controladores.ControladorAplicacion;
import modelos.Opcion;
import modelos.Votacion;
import dao.VotacionDao;
import modelos.enums.EstadoDeVotacion;
import modelos.tablemodel.OpcionesTableModel;
import vistas.admin.PanelEditorVotaciones;

import javax.swing.*;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class ControladorEditorDeVotacion {

    private final PanelEditorVotaciones vista;
    private final ControladorAplicacion controlador;
    private final OpcionesTableModel modeloDeTabla;
    private Votacion votacion = new Votacion();
    private Boolean estaVotacionEnProcesoDeCreacion;

    public ControladorEditorDeVotacion(ControladorAplicacion controlador) {
        this.controlador = controlador;
        modeloDeTabla = new OpcionesTableModel();
        modeloDeTabla.setTextoBoton("Eliminar");
        vista = new PanelEditorVotaciones(this);
        this.controlador.agregarPanel(vista, "editor");
    }

    public void abrir(Votacion votacion, boolean esNuevaVotacion, boolean modoLectura) {
        this.votacion = votacion;
        this.estaVotacionEnProcesoDeCreacion = esNuevaVotacion;
        cargarVotacion();
        cargarOpciones();
        controlador.mostrarPanel("editor");
        vista.setModoLectura(modoLectura);
        if (esNuevaVotacion || !votacion.getEstadoDeVotacion().equals(EstadoDeVotacion.BORRADOR)) {
            vista.setHabilitarBotonPonerEnMarcha(false);
        }
        vista.moverScrollAlInicio();
    }

    private void cargarOpciones() {
        // La sublista es necesaria para ocultar la opción por defecto "Abstenerse"
        modeloDeTabla.setOpciones(votacion.getOpciones().subList(1, votacion.getOpciones().size()));
    }

    private void cargarVotacion() {
        vista.cargarVotacion(votacion);
    }

    public void volverFueSolicitado() {
        controlador.abrirAdministracion();
    }

    public OpcionesTableModel getModeloDeTabla() {
        return modeloDeTabla;
    }

    public void eliminarOpcionFueSolicitado(int fila) {
        // La fila + 1 es necesaria para eliminar el elemento correcto de la
        // lista opciones pues su primer elemento está oculto en la tabla
        votacion.removerOpcion(fila + 1);
        cargarOpciones();
    }

    private void mostrarNoSeCumplenRequisitosDeEntrada() {
        JOptionPane.showMessageDialog(controlador.getMarco(),
                "Faltan datos por completar",
                "Advertencia",
                JOptionPane.WARNING_MESSAGE);
    }

    private void procesarVotacion() {
        if (estaVotacionEnProcesoDeCreacion) {
            VotacionDao.crearVotacion(votacion);
            estaVotacionEnProcesoDeCreacion = false;
            vista.setHabilitarBotonPonerEnMarcha(true);
        } else {
            VotacionDao.actualizarVotacion(votacion);
        }
    }

    private void mostrarDialogoDeExito(String mensaje) {
        JOptionPane.showMessageDialog(controlador.getMarco(),
                mensaje,
                "Éxito",
                JOptionPane.INFORMATION_MESSAGE);
    }

    private void obtenerDatosDeVista() {
        votacion.setTitulo(vista.getTitulo());
        votacion.setDescripcion(vista.getDescripcion());
        obtenerDatosTemporalesDeVista();
    }

    private void obtenerDatosTemporalesDeVista() {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-uuuu");
        LocalDateTime fechaInicio = LocalDate.parse(vista.getFechaInicio(), formatter).atStartOfDay()
                .withHour(vista.getHoraInicio()).withMinute(vista.getMinutoInicio());
        votacion.setFechaTiempoInicio(fechaInicio);
        LocalDateTime fechaTermino = LocalDate.parse(vista.getFechaTermino(), formatter).atStartOfDay()
                .withHour(vista.getHoraTermino()).withMinute(vista.getMinutoTermino());
        votacion.setFechaTiempoTermino(fechaTermino);
    }

    private boolean seCumplenRequisitosDeEntrada() {
        if (vista.getTitulo().isBlank()) {
            return false;
        }
        if (votacion.getOpciones().size() < 2) {
            return false;
        }
        if (votacion.getFechaTiempoInicio().isAfter(votacion.getFechaTiempoTermino())) {
            return false;
        }
        return true;
    }

    private String pedirNombreDeOpcionPorDialogo() {
        UIManager.put("OptionPane.okButtonText", "OK");
        UIManager.put("OptionPane.cancelButtonText", "Cancelar");
        return JOptionPane.showInputDialog(
                controlador.getMarco(),
                "Escriba el nombre de la opción.",
                "Agregar opción",
                JOptionPane.PLAIN_MESSAGE);
    }

    private boolean esNombreDeOpcionValido(String nombreDeOpcion) {
        if (nombreDeOpcion == null) return false;
        if (nombreDeOpcion.isBlank()) return false;
        if (esNombreDeOpcionDuplicado(nombreDeOpcion)) return false;
        return true;
    }

    private boolean esNombreDeOpcionDuplicado(String nombreDeOpcion) {
        for (Opcion opcion : votacion.getOpciones()) {
            if (opcion.getNombre().equals(nombreDeOpcion)) {
                return true;
            }
        }
        return false;
    }

    private void crearNuevaOpcion(String nombreDeOpcion) {
        String id = String.valueOf(votacion.getOpciones().size());
        Opcion opcion = new Opcion();
        opcion.setId(id);
        opcion.setNombre(nombreDeOpcion);
        opcion.setVotacion(votacion);
        opcion.setCantidadDeVotos(0);
        votacion.addOpcion(opcion);
        cargarOpciones();
    }

    public void agregarOpcionFueSolicitado() {
        String nombreDeOpcion = pedirNombreDeOpcionPorDialogo();
        if (esNombreDeOpcionValido(nombreDeOpcion)) {
            crearNuevaOpcion(nombreDeOpcion);
        }
    }

    public void guardarFueSolicitado() {
        obtenerDatosDeVista();
        if (seCumplenRequisitosDeEntrada()) {
            procesarVotacion();
            mostrarDialogoDeExito("¡La votación se guardó en forma exitosa!");
        } else {
            mostrarNoSeCumplenRequisitosDeEntrada();
        }
    }

    public void ponerVotacionEnMarchaFueSolicitado() {
        obtenerDatosDeVista();
        if (seCumplenRequisitosDeEntrada()) {
            if (pedirConfirmacionAUsuarioParaPonerEnMarcha()) {
                votacion.setEstadoDeVotacion(EstadoDeVotacion.PENDIENTE);
                procesarVotacion();
                vista.cargarVotacion(votacion);
                vista.setModoLectura(true);
                mostrarDialogoDeExito("¡La votación se puso en marcha en forma exitosa!");
            }
        } else {
            mostrarNoSeCumplenRequisitosDeEntrada();
        }
    }

    private boolean pedirConfirmacionAUsuarioParaPonerEnMarcha() {
        UIManager.put("OptionPane.yesButtonText", "Sí");
        UIManager.put("OptionPane.noButtonText", "No");
        UIManager.put("OptionPane.cancelButtonText", "Cancelar");
        int respuesta = JOptionPane.showConfirmDialog(
                controlador.getMarco(),
                "La siguiente operación pondrá en marcha la votación \n" +
                        "y por tanto esta ya no va a poder ser modificable. \n" +
                        "                      ¿Desea confirmar?",
                "Confirmación",
                JOptionPane.YES_NO_CANCEL_OPTION);
        return respuesta == JOptionPane.YES_OPTION;
    }
}
