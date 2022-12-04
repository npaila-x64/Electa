package controladores.admin;

import controladores.ControladorAplicacion;
import dao.VotacionDao;
import modelos.Votacion;
import modelos.enums.EstadoDeVotacion;
import modelos.tablemodel.AdministracionTableModel;
import vistas.admin.PanelAdministracion;

import javax.swing.table.TableModel;
import java.util.HashMap;
import java.util.List;
/**
 * Crea el controlador con el cual se administra la lógica de la vista del menú de administración.
 */
public class ControladorAdministracion {
    /**
     * Indica la vista del menú de administración.
     */
    private final PanelAdministracion vista;
    /**
     * Indica el controlador principal del programa.
     */
    private final ControladorAplicacion controlador;
    /**
     * Indica el modelo de tabla utilizado por el menú de administración.
     */
    private final AdministracionTableModel modelo;
    /**
     * Indica la lista de votaciones totales que administra el controlador.
     */
    private List<Votacion> votaciones;
    /**
     * Indica un mapa que contiene como llave un EstadoDeVotacion y como valor un Boolean asociado.
     */
    private HashMap<EstadoDeVotacion, Boolean> estados;

    /**
     * Construye un controlador de tipo ControladorAdministración. Instancia el controlador principal, el
     * modelo de tabla y la vista. Agrega el panel de la vista mediante el controlador principal y
     * carga los estados de las votaciones almacenados en el mapa como verdadero.
     *
     * @param controlador el controlador principal de tipo ControladorAplicacion.
     */
    public ControladorAdministracion(ControladorAplicacion controlador) {
        this.controlador = controlador;
        modelo = new AdministracionTableModel();
        vista = new PanelAdministracion(this);
        this.controlador.agregarPanel(vista, "administracion");
        cargarEstadosDeVotacionIniciales();
    }

    /**
     * Instancia el mapa de estados, por cada estado asigna un booleano verdadero. Esto es para
     * que todos los estados verdaderos sean visibles en la vista del menú administración.
     */
    private void cargarEstadosDeVotacionIniciales() {
        estados = new HashMap<>();
        for (EstadoDeVotacion estado : EstadoDeVotacion.getEstados()) {
            estados.put(estado, true);
        }
    }

    /**
     * Carga las votaciones con estado verdadero en la tabla.
     */
    private void cargarVotaciones() {
        votaciones = VotacionDao.obtenerVotacionesConEstados(estados);
        modelo.setVotaciones(votaciones);
    }

    /**
     * Llama al controlador principal para volver a la vista del login cuando es solicitado.
     */
    public void cerrarSesionFueSolicitado() {
        controlador.abrirLogin();
    }

    /**
     * Llama al controlador principal para ir a la vista de resultados cuando es solicitado.
     */
    public void verResultadosFueSolicitado() {
        controlador.abrirEscogerResultado();
    }

    /**
     * Carga las votaciones y llama al controlador principal para mostrar la vista del menú
     * administración y asignar dicha vista como panel predecesor al panel de los resultados.
     */
    public void abrir() {
        cargarVotaciones();
        controlador.asignarPanelPredecesorDeResultados("administracion");
        controlador.mostrarPanel("administracion");
    }

    /**
     * Obtiene el modelo de tabla.
     * @return una tabla de tipo TableModel.
     */
    public TableModel getModeloDeTabla() {
        return modelo;
    }

    /**
     * Llama al controlador principal para abrir la vista del editor de votación cuando es solicitado.
     * Indica la votación seleccionada y si esta está en modo lectura o no.
     * @param fila la posición de la votación indicada.
     */
    public void editarVotacionFueSolicitado(int fila) {
        Votacion votacion = votaciones.get(fila);
        if (votacion.getEstadoDeVotacion().equals(EstadoDeVotacion.BORRADOR)) {
            controlador.abrirEditor(votacion, false, false);
        } else {
            controlador.abrirEditor(votacion, false, true);
        }
    }

    /**
     * Vuelve a cargar las votaciones al indicar el ocultamiento o visualización de las votaciones
     * en un cierto estado. Actualiza el mapa de estados con un valor contrario al booleano que tenía
     * en el momento el estado indicado.
     * @param estado el estado de tipo EstadoDeVotación a ser ocultado o visualizado.
     */
    public void checkEstadoFueEjecutado(EstadoDeVotacion estado) {
        estados.put(estado, !estados.get(estado));
        cargarVotaciones();
    }

    /**
     * Llama al controlador principal para crear una nueva votación cuando es solicitado.
     */
    public void crearVotacionFueSolicitado() {
        controlador.abrirEditor(new Votacion(), true, false);
    }
}
