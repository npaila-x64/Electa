package controladores.votante;

import controladores.ControladorAplicacion;
import modelos.Opcion;
import modelos.Votacion;
import modelos.Votante;
import modelos.Voto;
import dao.VotacionDao;
import dao.VotoDao;
import utils.ValidadorDeDatos;
import vistas.votante.*;

import javax.swing.table.TableModel;
import java.util.ArrayList;
import java.util.List;

public class ControladorVotacion {

    private final ControladorAplicacion controlador;
    private final PanelOpciones vista;
    private final OpcionesTableModel modelo;
    private Votacion votacion;
    private List<Opcion> opciones;

    public ControladorVotacion(ControladorAplicacion controlador) {
        this.controlador = controlador;
        modelo = new OpcionesTableModel();
        vista = new PanelOpciones(this);
        this.controlador.agregarPanel(vista, "opciones");
    }

    public List<Votacion> obtenerVotacionesEnElQuePuedeVotarElVotante() {
        return VotacionDao
                .obtenerVotacionesEnElQuePuedeVotarElVotante(controlador.obtenerUsuario());
    }

    public void mostrarMenuOpcionesParaVotar(Integer opcionElegida) {
        int nuevaOpcion = ValidadorDeDatos.pedirOpcionHasta(opciones.size());
        switch (nuevaOpcion) {
            case 0 -> {return;}
            default -> registrarVoto(votacion,
                    controlador.obtenerUsuario(), opciones.get(nuevaOpcion - 1));
        }
        mostrarVotoRealizadoConExito();
    }

    private void mostrarVotoRealizadoConExito() {
        System.out.println("¡Voto realizado con exito!\n");
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

    public void registrarVoto(Votacion votacion, Votante votante, Opcion opcionElegida) {
        escribirVoto(votacion, opcionElegida);
        escribirVotoEnVotos(votacion, votante, opcionElegida);
        escribirVotanteEnVotaciones(votacion, votante);
    }

    public void escribirVotanteEnVotaciones(Votacion votacion, Votante votante) {
        List<Votacion> votaciones = VotacionDao.obtenerVotaciones();
        for (var votacionSiguiente : votaciones) {
            if (votacionSiguiente.getId().equals(votacion.getId())) {
                List<Votante> votantes = votacionSiguiente.getVotantes();
                votantes.add(votante);
                votacionSiguiente.setVotantes(votantes);
                VotacionDao.escribirVotaciones(votaciones);
                return;
            }
        }
    }

    public void escribirVotoEnVotos(Votacion votacion, Votante votante, Opcion opcion) {
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
        Opcion opcion = opciones.get(id);
        mostrarDialogoDeConfirmacion(opcion);
    }

    private void mostrarDialogoDeConfirmacion(Opcion opcion) {
        DialogoDeConfirmacion dialogo =
                new DialogoDeConfirmacion(controlador.getMarco(), "Confirme su voto", true);
        dialogo.setNombreDeOpcion(opcion.getNombre());
        if (dialogo.obtenerConfirmacion()) {
            registrarVoto(votacion, controlador.obtenerUsuario(), opcion);
            controlador.abrirVotacionesEnCurso();
        }
    }

    public void abrir(Votacion votacion, List<Opcion> opciones) {
        this.votacion = votacion;
        this.opciones = opciones;
        cargarOpciones();
        controlador.mostrarPanel("opciones");
    }

    private void cargarOpciones() {
        modelo.setOpciones(opciones);
    }

    public void volverFueSolicitado() {
        controlador.abrirVotacionesEnCurso();
    }

    public void abstenerseFueSolicitado() {
        mostrarDialogoDeConfirmacion(Opcion.getOpcionConVotoBlanco());
    }
}
