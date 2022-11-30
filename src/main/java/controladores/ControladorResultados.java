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

public class ControladorResultados {

    private ControladorAplicacion controlador;
    private PanelResultados resultados;
    private PanelEscogerResultado escojerResultado;
    private VotacionesTableModel modeloDeTablaVotaciones;
    private OpcionesInformeTableModel modeloDeTablaOpciones;
    private InformeTableModel modeloDeTablaInforme;
    private String panelPredecesor;
    private List<Votacion> votaciones;

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

    public void cargarVotaciones() {
        votaciones = VotacionDao.obtenerVotacionesFinalizadas();
        modeloDeTablaVotaciones.setVotaciones(votaciones);
    }

    public void abrirEscogerResultado() {
        cargarVotaciones();
        controlador.mostrarPanel("escogerResultado");
    }

    public void abrirResultados(Integer fila) {
        Votacion votacion = votaciones.get(fila);
        cargarVotacion(votacion);
        controlador.mostrarPanel("resultados");
    }

    private void cargarVotacion(Votacion votacion) {
        resultados.cargarVotacion(votacion);
        modeloDeTablaInforme.setVotacion(votacion);
        modeloDeTablaOpciones.setOpciones(votacion.getOpciones());
    }

    public void setPanelPredecesor(String panelPredecesor) {
        this.panelPredecesor = panelPredecesor;
    }

    public void volverFueSolicitado() {
        controlador.mostrarPanel(panelPredecesor);
    }

    public TableModel getModeloDeTablaVotaciones() {
        return modeloDeTablaVotaciones;
    }

    public TableModel getModeloDeTablaInforme() {
        return modeloDeTablaInforme;
    }

    public TableModel getModeloDeTablaOpciones() {
        return modeloDeTablaOpciones;
    }

    public void volverAEscogerResultadoFueSolicitado() {
        controlador.mostrarPanel("escogerResultado");
    }
}
