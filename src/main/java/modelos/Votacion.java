package modelos;

import modelos.enums.CampoDeVotacion;
import modelos.enums.EstadoDeVotacion;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
/**
 * Crea una votación con todos los campos necesarios para ser manejada por el programa.
 */
public class Votacion {
    /**
     * Indica el identificador numérico único de una votación.
     */
    private Integer id;
    /**
     * Indica el título de una votación.
     */
    private String titulo;
    /**
     * Indica la descripción de una votación.
     */
    private String descripcion;
    /**
     * Indica el estado de una votación.
     */
    private EstadoDeVotacion estadoDeVotacion;
    /**
     * Indica la fecha y hora de inicio de una votación.
     */
    private LocalDateTime fechaTiempoInicio;
    /**
     * India la fecha de inicio de una votación.
     */
    private LocalDate fechaInicio;
    /**
     * Indica la hora de inicio de una votación.
     */
    private LocalTime tiempoInicio;
    /**
     * Indica la fecha y hora de término de una votación.
     */
    private LocalDateTime fechaTiempoTermino;
    /**
     * Indica la fecha de término de una votación.
     */
    private LocalDate fechaTermino;
    /**
     * Indica la hora de término de una votación.
     */
    private LocalTime tiempoTermino;
    /**
     * Indica la lista de opciones de una votación.
     */
    private List<Opcion> opciones;
    /**
     * Indica la lista de usuarios votantes de una votación.
     */
    private List<Usuario> votantes;
    /**
     * Indica la cantidad de votos preferenciales de una votación.
     */
    private Integer votosPreferenciales;
    /**
     * Indica la cantidad de votos blancos de una votación.
     */
    private Integer votosBlancos;
    /**
     * Indica la lista de votos de una votación.
     */
    private List<Voto> votos;

    /**
     * Construye una clon de una votación.
     * @param clon la votación a replicar.
     */
    public Votacion(Votacion clon) {
        this.id = clon.getId();
        this.titulo = clon.getTitulo();
        this.descripcion = clon.getDescripcion();
        this.opciones = clon.getOpciones();
        this.votantes = clon.getVotantes();
        this.votos = clon.getVotos();
        this.votosBlancos = clon.getVotosBlancos();
        this.votosPreferenciales = getVotosPreferenciales();
        this.estadoDeVotacion = clon.getEstadoDeVotacion();
        setFechaTiempoInicio(clon.getFechaTiempoInicio());
        setFechaTiempoTermino(clon.getFechaTiempoTermino());
    }

    /**
     * Construye una nueva votación con valores iniciales por defecto. Los campos de id, votos blancos
     * y votos preferenciales inician con un valor de 0. Las listas votantes y votos inician
     * vacías. El estado de votación inicia en borrador. Los campos de fecha y hora inician con los
     * valores de la fecha y hora actuales. La lista de opciones inicia con un voto blanco.
     */

    public Votacion() {
        this.id = 0;
        this.opciones = new ArrayList<>();
        this.votantes = new ArrayList<>();
        this.votos = new ArrayList<>();
        this.votosBlancos = 0;
        this.votosPreferenciales = 0;
        this.estadoDeVotacion = EstadoDeVotacion.BORRADOR;
        setFechaTiempoInicio(LocalDateTime.now());
        setFechaTiempoTermino(LocalDateTime.now());
        agregarOpcionBlanco();
    }

    /**
     * Obtiene el identificador numérico único asociado a una votación.
     * @return un Integer cuyo valor representa un id.
     */

    public Integer getId() {
        return id;
    }

    /**
     * Asigna un identificador numérico único a una votación.
     * @param id un Object cuyo valor entero representa un id.
     */

    public void setId(Object id) {
        this.id = Integer.parseInt(id.toString());
    }

    /**
     * Obtiene un título asociado a una votación.
     * @return un String cuyo valor representa un título.
     */

    public String getTitulo() {
        return titulo;
    }

    /**
     * Asigna un título a una votación.
     * @param titulo un Object cuyo valor a String representa un título.
     */

    public void setTitulo(Object titulo) {
        this.titulo = titulo.toString();
    }

    /**
     * Obtiene una descripción asociada a una votación.
     * @return un String cuyo valor representa una descripción.
     */

    public String getDescripcion() {
        return descripcion;
    }

    /**
     * Asigna una descripción a una votación.
     * @param descripcion un Object cuyo valor String representa una descripción.
     */
    public void setDescripcion(Object descripcion) {
        this.descripcion = descripcion.toString();
    }

    /**
     * Obtiene un estado asociado a una votación.
     * @return un estado de tipo EstadoDeVotación.
     */

    public EstadoDeVotacion getEstadoDeVotacion() {
        return estadoDeVotacion;
    }

    /**
     * Asigna un estado a una votación.
     * @param estadoDeVotacion un Object del cual se obtiene un EstadoDeVotación.
     */

    public void setEstadoDeVotacion(Object estadoDeVotacion) {
        this.estadoDeVotacion = EstadoDeVotacion.fromObject(estadoDeVotacion);
    }

    /**
     * Obtiene una fecha y hora de inicio asociadas a una votación.
     * @return una fecha y hora de tipo LocalDateTime.
     */

    public LocalDateTime getFechaTiempoInicio() {
        return fechaTiempoInicio;
    }

    /**
     * Asigna una fecha y hora de inicio a una votación.
     * @param fechaTiempoInicio una fecha y hora de tipo LocalDateTime.
     */

    public void setFechaTiempoInicio(LocalDateTime fechaTiempoInicio) {
        this.fechaTiempoInicio = fechaTiempoInicio;
        setFechaInicio(fechaTiempoInicio.toLocalDate());
        setTiempoInicio(fechaTiempoInicio.toLocalTime());
    }

    /**
     * Asigna una fecha de inicio a una votación.
     * @param fechaInicio una fecha de tipo LocalDate.
     */

    private void setFechaInicio(LocalDate fechaInicio) {
        this.fechaInicio = fechaInicio;
    }

    /**
     * Obtiene una fecha de inicio asociada a una votación.
     * @return una fecha de tipo LocalDate.
     */

    public LocalDate getFechaInicio() {
        return this.fechaInicio;
    }

    /**
     * Asigna una hora de inicio a una votación.
     * @param tiempoInicio una hora de tipo LocalTime.
     */

    private void setTiempoInicio(LocalTime tiempoInicio) {
        this.tiempoInicio = tiempoInicio;
    }

    /**
     * Obtiene una hora de inicio asociada a una votación.
     * @return una hora de tipo LocalTime.
     */

    public LocalTime getTiempoInicio() {
        return this.tiempoInicio;
    }

    /**
     * Obtiene una fecha y hora de término asociadas a una votación.
     * @return una fecha y hora de tipo LocalDateTime.
     */

    public LocalDateTime getFechaTiempoTermino() {
        return fechaTiempoTermino;
    }

    /**
     * Asigna una fecha y hora de término a una votación.
     * @param fechaTiempoTermino una fecha y hora de tipo LocalDateTime.
     */

    public void setFechaTiempoTermino(LocalDateTime fechaTiempoTermino) {
        this.fechaTiempoTermino = fechaTiempoTermino;
        setFechaTermino(fechaTiempoTermino.toLocalDate());
        setTiempoTermino(fechaTiempoTermino.toLocalTime());
    }

    /**
     * Asigna una fecha de término a una votación.
     * @param fechaTermino una fecha de tipo LocalDate.
     */

    private void setFechaTermino(LocalDate fechaTermino) {
        this.fechaTermino = fechaTermino;
    }

    /**
     * Obtiene una fecha de término asociada a una votación.
     * @return una fecha de tipo LocalDate.
     */

    public LocalDate getFechaTermino() {
        return this.fechaTermino;
    }

    /**
     * Asigna una hora de término a una votación.
     * @param tiempoTermino una hora de tipo LocalTime.
     */

    private void setTiempoTermino(LocalTime tiempoTermino) {
        this.tiempoTermino = tiempoTermino;
    }

    /**
     * Obtiene una hora de término asociada a una votación.
     * @return una hora de tipo LocalTime.
     */

    public LocalTime getTiempoTermino() {
        return this.tiempoTermino;
    }

    /**
     * Obtiene una lista de opciones asociadas a una votación.
     * @return una lista de tipo List que almacena objetos de tipo Opcion.
     */

    public List<Opcion> getOpciones() {
        return new ArrayList<>(opciones);
    }

    /**
     * Elimina una opción de una lista de opciones asociada a una votación.
     * @param index el indice de la opción en la lista.
     */

    public void removeOpcion(int index) {
        opciones.remove(index);
    }

    /**
     * Asigna una lista de opciones a una votación.
     * @param opciones una lista de tipo List que almacena objetos de tipo Opcion.
     */

    public void setOpciones(List<Opcion> opciones) {
        this.opciones = new ArrayList<>(opciones);
    }

    /**
     * Obtiene una lista de usuarios votantes asociada a una votación.
     * @return una lista de tipo List que almacena objetos de tipo Usuario.
     */

    public List<Usuario> getVotantes() {
        return new ArrayList<>(votantes);
    }

    /**
     * Asigna una lista de usuarios votantes a una votación.
     * @param votantes una lista de tipo List que almacena objetos de tipo Usuario.
     */

    public void setVotantes(List<Usuario> votantes) {
        this.votantes = new ArrayList<>(votantes);
    }

    /**
     * Obtiene la cantidad de votos preferenciales asociada a una votación.
     * @return un Integer cuyo valor representa la cantidad de votos preferenciales.
     */

    public Integer getVotosPreferenciales() {
        return votosPreferenciales;
    }

    /**
     * Asigna una cantidad de votos preferenciales a una votación.
     * @param votosPreferenciales un Object cuyo valor entero representa una cantidad de votos preferenciales.
     */

    private void setVotosPreferenciales(Object votosPreferenciales) {
        this.votosPreferenciales = Integer.parseInt(votosPreferenciales.toString());
    }

    /**
     * Actualiza la cantidad de votos blancos y preferenciales en una votación.
     */

    private void updateVotos() {
        this.votosPreferenciales = 0;
        this.votosBlancos = 0;
        for (Voto voto : this.votos) {
            if (voto.getVotacion().getId().equals(this.getId())) {
                if (voto.getOpcion().getId() == 1) {
                    this.votosBlancos++;
                } else {
                    this.votosPreferenciales++;
                }
            }
        }
    }

    /**
     * Obtiene una cantidad de votos blancos asociados a una votación.
     * @return un Integer que representa una cantidad de votos blancos.
     */

    public Integer getVotosBlancos() {
        return votosBlancos;
    }

    /**
     * Agrega una opción de voto blanco a la lista de opciones asociada a una votación.
     */

    private void agregarOpcionBlanco() {
        this.opciones.add(0, Opcion.getOpcionConVotoBlanco());
    }

    /**
     * Asigna una cantidad de votos blancos a una votación.
     * @param votosBlancos un Object cuyo valor entero representa una cantidad de votos blancos.
     */
    private void setVotosBlancos(Object votosBlancos) {
        this.votosBlancos = Integer.parseInt(votosBlancos.toString());
    }

    /**
     * Obtiene una cantidad de votos blancos asociada a una votación.
     * @return un Integer que representa una cantidad de votos blancos.
     */

    public Integer getTotalVotos(){
        return votosPreferenciales+votosBlancos;
    }

    /**
     * Obtiene una lista de votos asociada a una votación.
     * @return una lista de tipo List que almacena objetos de tipo Voto.
     */
    public List<Voto> getVotos() {
        return new ArrayList<>(votos);
    }

    /**
     * Asigna una lista de votos a una votación.
     * @param votos una lista de tipo List que almacena objetos de tipo Voto.
     */
    public void setVotos(List<Voto> votos) {
        this.votos = new ArrayList<>(votos);
        updateVotos();
    }

    /**
     * Agrega una opción a la lista de opciones asociada a una votación.
     */
    public void addOpcion(Opcion opcion) {
        opciones.add(opcion);
    }

    @Override
    public String toString() {
        return new String()
            .concat(String.format("%s: %s\n", CampoDeVotacion.ID, id))
            .concat(String.format("%s: %s\n", CampoDeVotacion.TITULO, titulo))
            .concat(String.format("%s: %s\n", CampoDeVotacion.DESCRIPCION, descripcion))
            .concat(String.format("%s: %s\n", CampoDeVotacion.ESTADO, estadoDeVotacion))
            .concat(String.format("%s: %s\n", CampoDeVotacion.FECHA_INICIO, fechaTiempoInicio))
            .concat(String.format("%s: %s\n", CampoDeVotacion.FECHA_TERMINO, fechaTiempoTermino))
            .concat(String.format("%s: %s\n", CampoDeVotacion.VOTOS_PREFERENCIALES, votosPreferenciales))
            .concat(String.format("%s: %s\n", CampoDeVotacion.VOTOS_BLANCOS, votosBlancos));
    }
}
