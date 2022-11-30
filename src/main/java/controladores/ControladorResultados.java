package controladores;

import modelos.Votacion;
import dao.VotacionDao;
import modelos.enums.EstadoDeVotacion;
import vistas.PanelEscogerResultado;
import vistas.PanelResultados;
import vistas.VotacionesTableModel;

import javax.swing.table.TableModel;
import java.time.format.DateTimeFormatter;
import java.util.HashMap;
import java.util.List;

public class ControladorResultados {

    private ControladorAplicacion controlador;
    private PanelResultados resultados;
    private PanelEscogerResultado escojerResultado;
    private VotacionesTableModel modeloDeTabla;
    private String panelPredecesor;
    private List<Votacion> votaciones;

    public ControladorResultados(ControladorAplicacion controlador) {
        this.controlador = controlador;
        modeloDeTabla = new VotacionesTableModel();
        resultados = new PanelResultados(this);
        escojerResultado = new PanelEscogerResultado(this);
        this.controlador.agregarPanel(resultados, "resultados");
        this.controlador.agregarPanel(escojerResultado, "escojerResultado");
    }

    public HashMap<String, Object> obtenerDatosDeVotacion(Votacion votacion) {
        var datos = new HashMap<String, Object>();
        datos.put("titulo", votacion.getTitulo());
        datos.put("votosBlancos", votacion.getVotosBlancos());
        datos.put("votosPreferenciales", votacion.getVotosPreferenciales());
        datos.put("totalVotos", votacion.getTotalVotos());
        datos.put("fechaTiempoInicio", votacion.getFechaTiempoInicio()
                .format(DateTimeFormatter.ofPattern("dd-MM-yyyy hh:mm")));
        datos.put("fechaTiempoTermino", votacion.getFechaTiempoTermino()
                .format(DateTimeFormatter.ofPattern("dd-MM-yyyy hh:mm")));
        return datos;
    }

    public List<Votacion> obtenerVotacionesParaMostrar() {
        return VotacionDao.obtenerVotacionesConEstado(EstadoDeVotacion.FINALIZADO);
    }

    public Votacion obtenerVotacion(int opcionElegida) {
        List<Votacion> votaciones = VotacionDao.obtenerVotacionesFinalizadas();
        return votaciones.get(opcionElegida - 1);
    }

    public void cargarVotaciones() {
        votaciones = VotacionDao.obtenerVotacionesFinalizadas();
        modeloDeTabla.setVotaciones(votaciones);
    }

    public void abrirEscogerResultado() {
        cargarVotaciones();
        controlador.mostrarPanel("escojerResultado");
    }

    public void abrirResultados(Integer fila) {
        cargarVotacion(votaciones.get(fila));
        controlador.mostrarPanel("resultados");
    }

    private void cargarVotacion(Votacion votacion) {

        //
    }

    public void setPanelPredecesor(String panelPredecesor) {
        this.panelPredecesor = panelPredecesor;
    }

    public void volverFueSolitado() {
        controlador.mostrarPanel(panelPredecesor);
    }

    public TableModel getModeloDeTablaVotaciones() {
        return modeloDeTabla;
    }

    public void abrirResultadosDeVotacionFueSolicitado(int fila) {
        abrirResultados(fila);
    }
}
