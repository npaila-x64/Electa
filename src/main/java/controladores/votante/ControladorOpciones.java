package controladores.votante;

import controladores.ControladorAplicacion;
import modelos.Opcion;
import modelos.Votacion;
import modelos.Usuario;
import modelos.Voto;
import dao.VotacionDao;
import dao.VotoDao;
import modelos.tablemodel.OpcionesTableModel;
import vistas.votante.*;

import javax.swing.table.TableModel;
import java.util.List;
/**
 * Crea el controlador con el cual se administra la lógica de la vista de opciones.
 */
public class ControladorOpciones {
    /**
     * Indica el controlador principal del programa.
     */
    private final ControladorAplicacion controlador;
    /**
     * Indica la vista de opciones.
     */
    private final PanelOpciones vista;
    /**
     * Indica el modelo de tabla utilizado por la vista.
     */
    private final OpcionesTableModel modeloDeTabla;
    /**
     * Indica la votación en la que se votará que administra el controlador.
     */
    private Votacion votacion;
    /**
     * Indica la lista de opciones de la votación.
     */
    private List<Opcion> opciones;

    /**
     * Construye un controlador de tipo ControladorOpciones. Instancia el controlador principal, el modelo
     * de tabla y la vista. Agrega el panel de la vista mediante el controlador principal y configura
     * el texto de los botones de la tabla.
     * @param controlador el controlador principal de tipo ControladorAplicacion.
     */
    public ControladorOpciones(ControladorAplicacion controlador) {
        this.controlador = controlador;
        modeloDeTabla = new OpcionesTableModel();
        modeloDeTabla.setTextoBoton("Votar");
        vista = new PanelOpciones(this);
        this.controlador.agregarPanel(vista, "opciones");
    }

    /**
     * Escribe un voto emitido en su votación correspondiente.
     * @param votacion la votación en la cual se vota.
     * @param opcionElegida la opción elegida por el votante.
     */
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

    /**
     * Incrementa la cantidad de votos emitidos en una opción.
     * @param opcion la opción que recibe el incremento.
     */
    private void incrementarCantidadDeVotosDeOpcionEnUno(Opcion opcion) {
        int votosOpcion = opcion.getCantidadDeVotos();
        votosOpcion++;
        opcion.setCantidadDeVotos(votosOpcion);
    }

    /**
     * Registra un voto en los datos de los votos y en la votación junto al usuario emisor.
     * @param opcionElegida la opción elegida por el usuario votante.
     */
    public void registrarVoto(Opcion opcionElegida) {
        escribirVoto(votacion, opcionElegida);
        escribirVotoEnVotos(votacion, controlador.obtenerUsuario(), opcionElegida);
        escribirVotanteEnVotaciones(votacion, controlador.obtenerUsuario());
    }

    /**
     * Registra al usuario votante emisor de un voto en una votación.
     * @param votacion la votación en la cual se votó.
     * @param votante el usuario votante que emitió el voto.
     */
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

    /**
     * Registra el voto en los datos que almacenan los votos.
     * @param votacion la votación en la cual se votó.
     * @param votante el usuario votante que emitió el voto.
     * @param opcion la opción por la cual se votó.
     */
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

    /**
     * Obtiene el modelo de tabla.
     * @return una tabla de tipo TableModel.
     */
    public TableModel getModeloDeTabla() {
        return modeloDeTabla;
    }

    /**
     * Pide una confirmación al usuario cuando se emite un voto y luego llama al controlador para
     * volver a la vista de votaciones en curso.
     * @param id
     */
    public void votarPorOpcionFueSolicitado(int id) {
        // El id + 1 es necesario para obtener el elemento correcto de la
        // lista opciones pues su primer elemento está oculto en la tabla
        Opcion opcion = opciones.get(id + 1);
        if (pedirConfirmacionAUsuario(opcion)) {
            registrarVoto(opcion);
            controlador.abrirVotacionesEnCurso();
        }
    }

    /**
     * Recibe el input de confirmación por parte del usuario al emitir un voto mediante un JOptionPane.
     * @param opcion la opción por la cual se votó.
     * @return un booleano que indica la respuesta del usuario.
     */
    private boolean pedirConfirmacionAUsuario(Opcion opcion) {
        DialogoDeConfirmacion dialogo =
                new DialogoDeConfirmacion(controlador.getMarco(), true);
        dialogo.setNombreDeOpcion(opcion.getNombre());
        return dialogo.obtenerConfirmacion();
    }

    /**
     * Carga las opciones de la votación en la vista y en la tabla. Asigna información de la votación en
     * la vista. Llama al controlador principal para mostrar el panel de opciones.
     * @param votacion
     * @param opciones
     */
    public void abrir(Votacion votacion, List<Opcion> opciones) {
        this.votacion = votacion;
        this.opciones = opciones;
        cargarOpciones();
        vista.setTituloDeVotacion(votacion.getTitulo());
        vista.setlDescripcionDeVotacion(votacion.getDescripcion());
        controlador.mostrarPanel("opciones");
    }

    /**
     * Carga las opciones de la votación en la tabla.
     */
    private void cargarOpciones() {
        // La sublista es necesaria para ocultar la opción por defecto "Abstenerse"
        modeloDeTabla.setOpciones(opciones.subList(1, opciones.size()));
    }

    /**
     * Llama al controlador para volver a las votaciones en curso de ser solicitado.
     */
    public void volverFueSolicitado() {
        controlador.abrirVotacionesEnCurso();
    }

    /**
     * Procesa la emisión de un voto blanco por parte del usuario y regresa a las votaciones en curso
     * si el usuario confirma su voto.
     */
    public void abstenerseFueSolicitado() {
        var votoBlanco = Opcion.getOpcionConVotoBlanco();
        if (pedirConfirmacionAUsuario(votoBlanco)) {
            registrarVoto(votoBlanco);
            controlador.abrirVotacionesEnCurso();
        }
    }
}
