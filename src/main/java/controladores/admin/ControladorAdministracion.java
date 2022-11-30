package controladores.admin;

import controladores.ControladorAplicacion;
import dao.VotacionDao;
import modelos.Votacion;
import modelos.enums.EstadoDeVotacion;
import vistas.admin.tablemodel.AdministracionTableModel;
import vistas.admin.PanelAdministracion;

import javax.swing.table.TableModel;
import java.util.HashMap;
import java.util.List;

public class ControladorAdministracion {

    private final PanelAdministracion vista;
    private final ControladorAplicacion controlador;
    private final AdministracionTableModel modelo;
    private List<Votacion> votaciones;
    private HashMap<EstadoDeVotacion, Boolean> estados;

    public ControladorAdministracion(ControladorAplicacion controlador) {
        this.controlador = controlador;
        modelo = new AdministracionTableModel();
        vista = new PanelAdministracion(this);
        this.controlador.agregarPanel(vista, "administracion");
        cargarEstadosDeVotacionIniciales();
    }

    private void cargarEstadosDeVotacionIniciales() {
        estados = new HashMap<>();
        for (EstadoDeVotacion estado : EstadoDeVotacion.getEstados()) {
            estados.put(estado, true);
        }
    }

    private void cargarVotaciones() {
        votaciones = VotacionDao.obtenerVotacionesConEstados(estados);
        modelo.setVotaciones(votaciones);
    }

    public void cerrarSesionFueSolicitado() {
        controlador.abrirLogin();
    }

    public void verResultadosFueSolicitado() {
        controlador.abrirResultados();
    }

    public void abrir() {
        cargarVotaciones();
        controlador.asignarPanelPredecesorDeResultados("administracion");
        controlador.mostrarPanel("administracion");
    }

    public TableModel getModeloDeTabla() {
        return modelo;
    }

    public void editarVotacionFueSolicitado(int fila) {
        Votacion votacion = votaciones.get(fila);
        if (votacion.getEstadoDeVotacion().equals(EstadoDeVotacion.BORRADOR)) {
            controlador.abrirEditor(votacion, false, false);
        } else {
            controlador.abrirEditor(votacion, false, true);
        }
    }

    public void checkBorradoresFueEjecutado() {
        estados.put(EstadoDeVotacion.BORRADOR, !estados.get(EstadoDeVotacion.BORRADOR));
        cargarVotaciones();
    }

    public void checkPendientesFueEjecutado() {
        estados.put(EstadoDeVotacion.PENDIENTE, !estados.get(EstadoDeVotacion.PENDIENTE));
        cargarVotaciones();
    }

    public void checkFinalizadasFueEjecutado() {
        estados.put(EstadoDeVotacion.FINALIZADO, !estados.get(EstadoDeVotacion.FINALIZADO));
        cargarVotaciones();
    }

    public void checkEnCursoFueEjecutado() {
        estados.put(EstadoDeVotacion.EN_CURSO, !estados.get(EstadoDeVotacion.EN_CURSO));
        cargarVotaciones();
    }

    public void crearVotacionFueSolicitado() {
        controlador.abrirEditor(new Votacion(), true, false);
    }
}
