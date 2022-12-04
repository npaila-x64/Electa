package controladores;

import modelos.Votacion;
import dao.VotacionDao;
import modelos.tablemodel.InformeTableModel;
import modelos.tablemodel.OpcionesInformeTableModel;
import vistas.PanelEscogerResultado;
import vistas.PanelResultados;
import modelos.tablemodel.VotacionesTableModel;

import javax.swing.table.TableModel;
import java.time.format.DateTimeFormatter;
import java.util.HashMap;
import java.util.List;
/**
 * Crea el controlador con el cual se administra la lógica de la vista de los resultados.
 */
public class ControladorResultados {
    /**
     * Indica el controlador principal del programa.
     */
    private ControladorAplicacion controlador;
    /**
     * Indica la vista de los resultados.
     */
    private PanelResultados resultados;
    /**
     * Indica la vista del menú para escoger resultados.
     */
    private PanelEscogerResultado escojerResultado;
    /**
     * Indica el modelo de tabla para las votaciones.
     */
    private VotacionesTableModel modeloDeTablaVotaciones;
    /**
     * Indica el modelo de tabla para el informe de las opciones.
     */
    private OpcionesInformeTableModel modeloDeTablaOpciones;
    /**
     * Indica el modelo de tabla para el informe de la votación.
     */
    private InformeTableModel modeloDeTablaInforme;
    /**
     * Indica el panel predecesor.
     */
    private String panelPredecesor;
    /**
     * Indica la lista de votaciones totales que maneja el controlador.
     */
    private List<Votacion> votaciones;

    /**
     * Construye un controlador de tipo ControladorResultados. Instancia el controlador principal, los
     * modelos de tabla y las vistas. Agrega los paneles de las vistas mediante el controlador
     * principal.
     * @param controlador el controlador principal de tipo ControladorAplicacion.
     */
    public ControladorResultados(ControladorAplicacion controlador) {
        this.controlador = controlador;
        modeloDeTablaVotaciones = new VotacionesTableModel();
        modeloDeTablaOpciones = new OpcionesInformeTableModel();
        modeloDeTablaInforme = new InformeTableModel();
        resultados = new PanelResultados(this);
        escojerResultado = new PanelEscogerResultado(this);
        this.controlador.agregarPanel(resultados, "resultados");
        this.controlador.agregarPanel(escojerResultado, "escogerResultado");
    }

    /**
     * Carga las votaciones en estado finalizado dentro de la tabla de la vista escoger resultados.
     */
    public void cargarVotaciones() {
        votaciones = VotacionDao.obtenerVotacionesFinalizadas();
        modeloDeTablaVotaciones.setVotaciones(votaciones);
    }

    /**
     * Carga las votaciones y llama al controlador para mostrar el panel de escoger resultados.
     */
    public void abrirEscogerResultado() {
        cargarVotaciones();
        controlador.mostrarPanel("escogerResultado");
    }

    /**
     * Carga la votación seleccionada y llama al controlador para mostrar el panel de los resultados.
     * @param fila indica la posición de la votación seleccionada en la tabla.
     */
    public void abrirResultados(Integer fila) {
        Votacion votacion = votaciones.get(fila);
        cargarVotacion(votacion);
        controlador.mostrarPanel("resultados");
    }

    /**
     * Carga la información de la votación en las tablas de informe.
     * @param votacion la votación seleccionada.
     */
    private void cargarVotacion(Votacion votacion) {
        resultados.cargarVotacion(votacion);
        modeloDeTablaInforme.setVotacion(votacion);
        modeloDeTablaOpciones.setOpciones(votacion.getOpciones());
    }

    /**
     * Asigna el panel predecesor al panel de resultados.
     * @param panelPredecesor un String cuyo valor indica un panel.
     */
    public void setPanelPredecesor(String panelPredecesor) {
        this.panelPredecesor = panelPredecesor;
    }

    /**
     * Llama al controlador para volver al panel predecesor de ser solicitado.
     */
    public void volverFueSolicitado() {
        controlador.mostrarPanel(panelPredecesor);
    }

    /**
     * Obtiene el modelo de tabla de las votaciones finalizadas.
     * @return un modelo de tabla de tipo TablaModel.
     */
    public TableModel getModeloDeTablaVotaciones() {
        return modeloDeTablaVotaciones;
    }

    /**
     * Obtiene el modelo de tabla del informe de la votación.
     * @return un modelo de tabla de tipo TableModel.
     */
    public TableModel getModeloDeTablaInforme() {
        return modeloDeTablaInforme;
    }
    /**
     * Obtiene el modelo de tabla del informe de las opciones.
     * @return un modelo de tabla de tipo TableModel.
     */
    public TableModel getModeloDeTablaOpciones() {
        return modeloDeTablaOpciones;
    }
    /**
     * Llama al controlador para volver al panel escoger resultados de ser solicitado.
     */
    public void volverAEscogerResultadoFueSolicitado() {
        controlador.mostrarPanel("escogerResultado");
    }
}
