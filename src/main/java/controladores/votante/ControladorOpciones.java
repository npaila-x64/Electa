package controladores.votante;

import controladores.ControladorAplicacion;
import modelos.Opcion;
import modelos.Votacion;
import modelos.Usuario;
import modelos.Voto;
import dao.VotacionDao;
import dao.VotoDao;
import vistas.votante.*;
import vistas.votante.tablemodel.OpcionesTableModel;

import javax.swing.table.TableModel;
import java.util.List;

public class ControladorOpciones {

    private final ControladorAplicacion controlador;
    private final PanelOpciones vista;
    private final OpcionesTableModel modelo;
    private Votacion votacion;
    private List<Opcion> opciones;

    public ControladorOpciones(ControladorAplicacion controlador) {
        this.controlador = controlador;
        modelo = new OpcionesTableModel();
        vista = new PanelOpciones(this);
        this.controlador.agregarPanel(vista, "opciones");
    }

    public void escribirVoto(Votacion votacion, Opcion opcionElegida) {
        List<Votacion> votaciones = VotacionDao.obtenerVotaciones();
        for (var votacionSiguiente : votaciones) {
            if (votacionSiguiente.getId().equals(votacion.getId())) {
                List<Opcion> opciones = votacionSiguiente.getOpciones();
                opciones.stream()
                        .filter(opcion -> opcion.getId().equals(opcionElegida.getId()))
                        .forEach(this::incrementarCantidadDeVotosDeOpcionEnUno);
                votacionSiguiente.setOpciones(opciones);
                VotacionDao.escribirVotaciones(votaciones);
                return;
            }
        }
    }

    private void incrementarCantidadDeVotosDeOpcionEnUno(Opcion opcion) {
        int votosOpcion = opcion.getCantidadDeVotos();
        votosOpcion++;
        opcion.setCantidadDeVotos(votosOpcion);
    }

    public void registrarVoto(Opcion opcionElegida) {
        escribirVoto(votacion, opcionElegida);
        escribirVotoEnVotos(votacion, controlador.obtenerUsuario(), opcionElegida);
        escribirVotanteEnVotaciones(votacion, controlador.obtenerUsuario());
    }

    public void escribirVotanteEnVotaciones(Votacion votacion, Usuario votante) {
        List<Votacion> votaciones = VotacionDao.obtenerVotaciones();
        for (var votacionSiguiente : votaciones) {
            if (votacionSiguiente.getId().equals(votacion.getId())) {
                List<Usuario> votantes = votacionSiguiente.getVotantes();
                votantes.add(votante);
                votacionSiguiente.setVotantes(votantes);
                VotacionDao.escribirVotaciones(votaciones);
                return;
            }
        }
    }

    public void escribirVotoEnVotos(Votacion votacion, Usuario votante, Opcion opcion) {
        List<Voto> votos = VotoDao.obtenerVotos();
        Voto voto = new Voto();
        voto.setId(VotoDao.obtenerNuevaIdVoto());
        voto.setVotacion(votacion);
        voto.setVotante(votante);
        voto.setOpcion(opcion);
        votos.add(voto);
        votacion.setVotos(votos);
        VotoDao.escribirVotos(votos);
    }

    public TableModel getModeloDeTabla() {
        return modelo;
    }

    public void votarPorOpcionFueSolicitado(int id) {
        // El id + 1 es necesario para obtener el elemento correcto de la
        // lista opciones pues su primer elemento está oculto en la tabla
        Opcion opcion = opciones.get(id + 1);
        if (pedirConfirmacionAUsuario(opcion)) {
            registrarVoto(opcion);
            controlador.abrirVotacionesEnCurso();
        }
    }

    private boolean pedirConfirmacionAUsuario(Opcion opcion) {
        DialogoDeConfirmacion dialogo =
                new DialogoDeConfirmacion(controlador.getMarco(), true);
        dialogo.setNombreDeOpcion(opcion.getNombre());
        return dialogo.obtenerConfirmacion();
    }

    public void abrir(Votacion votacion, List<Opcion> opciones) {
        this.votacion = votacion;
        this.opciones = opciones;
        cargarOpciones();
        vista.setTituloDeVotacion(votacion.getTitulo());
        vista.setlDescripcionDeVotacion(votacion.getDescripcion());
        controlador.mostrarPanel("opciones");
    }

    private void cargarOpciones() {
        // La sublista es necesaria para ocultar la opción por defecto "Abstenerse"
        modelo.setOpciones(opciones.subList(1, opciones.size()));
    }

    public void volverFueSolicitado() {
        controlador.abrirVotacionesEnCurso();
    }

    public void abstenerseFueSolicitado() {
        var votoBlanco = Opcion.getOpcionConVotoBlanco();
        if (pedirConfirmacionAUsuario(votoBlanco)) {
            registrarVoto(votoBlanco);
            controlador.abrirVotacionesEnCurso();
        }
    }
}
