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

public class Votacion {

    private Integer id;
    private String titulo;
    private String descripcion;
    private EstadoDeVotacion estadoDeVotacion;
    private LocalDateTime fechaTiempoInicio;
    private LocalDate fechaInicio;
    private LocalTime tiempoInicio;
    private LocalDateTime fechaTiempoTermino;
    private LocalDate fechaTermino;
    private LocalTime tiempoTermino;
    private List<Opcion> opciones;
    private List<Usuario> votantes;
    private Integer votosPreferenciales;
    private Integer votosBlancos;
    private List<Voto> votos;

    // TODO el clon puede ser creado por un método especial llamado clon() en vez de usar el constructor
    // Método constructor usado para clonar una Votacion
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

    public Votacion() {
        this.opciones = new ArrayList<>();
        this.votantes = new ArrayList<>();
        this.votos = new ArrayList<>();
        this.votosBlancos = 0;
        this.votosPreferenciales = 0;
        this.estadoDeVotacion = EstadoDeVotacion.BORRADOR;
//      TODO Ver un modo estandarizar fechas de inicio y termino por defecto
        setFechaTiempoInicio(LocalDateTime.now());
        setFechaTiempoTermino(LocalDateTime.now().plusYears(50));
        agregarOpcionBlanco();
    }

    public Integer getId() {
        return id;
    }

    public void setId(Object id) {
        this.id = Integer.parseInt(id.toString());
    }

    public String getTitulo() {
        return titulo;
    }

    public void setTitulo(Object titulo) {
        this.titulo = titulo.toString();
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(Object descripcion) {
        this.descripcion = descripcion.toString();
    }

    public EstadoDeVotacion getEstadoDeVotacion() {
        return estadoDeVotacion;
    }

    public void setEstadoDeVotacion(Object estadoDeVotacion) {
        this.estadoDeVotacion = EstadoDeVotacion.fromObject(estadoDeVotacion);
    }

    public LocalDateTime getFechaTiempoInicio() {
        return fechaTiempoInicio;
    }

    public void setFechaTiempoInicio(LocalDateTime fechaTiempoInicio) {
        this.fechaTiempoInicio = fechaTiempoInicio;
        setFechaInicio(fechaTiempoInicio.toLocalDate());
        setTiempoInicio(fechaTiempoInicio.toLocalTime());
    }

    private void setFechaInicio(LocalDate fechaInicio) {
        this.fechaInicio = fechaInicio;
    }

    public LocalDate getFechaInicio() {
        return this.fechaInicio;
    }

    private void setTiempoInicio(LocalTime tiempoInicio) {
        this.tiempoInicio = tiempoInicio;
    }

    public LocalTime getTiempoInicio() {
        return this.tiempoInicio;
    }

    public LocalDateTime getFechaTiempoTermino() {
        return fechaTiempoTermino;
    }

    public void setFechaTiempoTermino(LocalDateTime fechaTiempoTermino) {
        this.fechaTiempoTermino = fechaTiempoTermino;
        setFechaTermino(fechaTiempoTermino.toLocalDate());
        setTiempoTermino(fechaTiempoTermino.toLocalTime());
    }

    private void setFechaTermino(LocalDate fechaTermino) {
        this.fechaTermino = fechaTermino;
    }

    public LocalDate getFechaTermino() {
        return this.fechaTermino;
    }

    private void setTiempoTermino(LocalTime tiempoTermino) {
        this.tiempoTermino = tiempoTermino;
    }

    public LocalTime getTiempoTermino() {
        return this.tiempoTermino;
    }

    public List<Opcion> getOpciones() {
        return new ArrayList<>(opciones);
    }

    public void setOpciones(List<Opcion> opciones) {
        this.opciones = new ArrayList<>(opciones);
    }

    public List<Usuario> getVotantes() {
        return new ArrayList<>(votantes);
    }

    public void setVotantes(List<Usuario> votantes) {
        this.votantes = new ArrayList<>(votantes);
    }

    public Integer getVotosPreferenciales() {
        return votosPreferenciales;
    }

    private void setVotosPreferenciales(Object votosPreferenciales) {
        this.votosPreferenciales = Integer.parseInt(votosPreferenciales.toString());
    }

    private void updateVotos() {
        this.votosPreferenciales = 0;
        this.votosBlancos = 0;
        for (Voto voto : this.votos) {
            if (voto.getVotacion().getId().equals(this.getId())) {
                if (voto.getOpcion().getId() != 1) {
                    this.votosPreferenciales++;
                } else {
                    this.votosBlancos++;
                }
            }
        }
    }

    public Integer getVotosBlancos() {
        return votosBlancos;
    }

    private void agregarOpcionBlanco() {
        this.opciones.add(0, Opcion.getOpcionConVotoBlanco());
    }

    private void setVotosBlancos(Object votosBlancos) {
        this.votosBlancos = Integer.parseInt(votosBlancos.toString());
    }

    public Integer getTotalVotos(){
        return votosPreferenciales+votosBlancos;
    }

    public List<Voto> getVotos() {
        return new ArrayList<>(votos);
    }

    public void setVotos(List<Voto> votos) {
        this.votos = new ArrayList<>(votos);
        updateVotos();
    }

    public void setAttributo(CampoDeVotacion campo, Object valor) {
        // TODO Averiguar si esto es buena práctica
        Map<CampoDeVotacion, Runnable> mapaDeSetters = new HashMap<>();
        mapaDeSetters.put(CampoDeVotacion.ID, () -> setId(valor));
        mapaDeSetters.put(CampoDeVotacion.TITULO, () -> setTitulo(valor));
        mapaDeSetters.put(CampoDeVotacion.DESCRIPCION, () -> setDescripcion(valor));
        mapaDeSetters.put(CampoDeVotacion.ESTADO, () -> setEstadoDeVotacion(valor));
        mapaDeSetters.put(CampoDeVotacion.FECHA_INICIO, () -> setFechaInicio((LocalDate) valor));
        mapaDeSetters.put(CampoDeVotacion.HORA_INICIO, () -> setTiempoInicio((LocalTime) valor));
        mapaDeSetters.put(CampoDeVotacion.FECHA_TERMINO, () -> setFechaTermino((LocalDate) valor));
        mapaDeSetters.put(CampoDeVotacion.HORA_TERMINO, () -> setTiempoTermino((LocalTime) valor));
        mapaDeSetters.put(CampoDeVotacion.OPCIONES, () -> setOpciones((List<Opcion>) valor));
        mapaDeSetters.put(CampoDeVotacion.VOTANTES, () -> setVotantes((List<Usuario>) valor));
        mapaDeSetters.put(CampoDeVotacion.VOTOS_BLANCOS, () -> setVotosBlancos(valor));
        mapaDeSetters.put(CampoDeVotacion.VOTOS_PREFERENCIALES, () -> setVotosPreferenciales(valor));
        mapaDeSetters.get(campo).run();
    }

    public boolean estaEnEstado(EstadoDeVotacion estadoConsultado){
        return estadoDeVotacion.equals(estadoConsultado);
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
