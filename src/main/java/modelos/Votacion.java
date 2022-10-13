package modelos;

import modelos.enums.CampoDeVotacion;
import modelos.enums.Estado;
import modelos.enums.TipoDeVoto;

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
    private Estado estado;
    private LocalDateTime fechaTiempoInicio;
    private LocalDate fechaInicio;
    private LocalTime tiempoInicio;
    private LocalDateTime fechaTiempoTermino;
    private LocalDate fechaTermino;
    private LocalTime tiempoTermino;
    private List<Opcion> opciones;
    private List<Votante> votantes;
    private Integer votosPreferenciales;
    private Integer votosBlancos;
    private List<Voto> listaDeVotos;

    // Método constructor usado para clonar una modelos.Votacion
    public Votacion(Votacion clon) {
        this.id = clon.getId();
        this.titulo = clon.getTitulo();
        this.descripcion = clon.getDescripcion();
        this.opciones = clon.getOpciones();
        this.votantes = clon.getVotantes();
        this.listaDeVotos = clon.getListaDeVotos();
        this.votosBlancos = clon.getVotosBlancos();
        this.votosPreferenciales = getVotosPreferenciales();
        this.estado = clon.getEstado();
        setFechaTiempoInicio(clon.getFechaTiempoInicio());
        setFechaTiempoTermino(clon.getFechaTiempoTermino());
    }

    public Votacion() {
        this.opciones = new ArrayList<>();
        this.votantes = new ArrayList<>();
        this.listaDeVotos = new ArrayList<>();
        this.votosBlancos = 0;
        this.votosPreferenciales = 0;
        this.estado = Estado.BORRADOR;
//      TODO estandarizar fecha de inicio y termino por defecto
        setFechaTiempoInicio(LocalDateTime.now());
        setFechaTiempoTermino(LocalDateTime.now().plusYears(50));
        agregarVotoBlanco();
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

    public Estado getEstado() {
        return estado;
    }

    public void setEstado(Object estado) {
        this.estado = Estado.fromObject(estado);
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

    public List<Votante> getVotantes() {
        return new ArrayList<>(votantes);
    }

    public void setVotantes(List<Votante> votantes) {
        this.votantes = new ArrayList<>(votantes);
    }

    public Integer getVotosPreferenciales() {
        return votosPreferenciales;
    }

    public void setVotosPreferenciales(Object votosPreferenciales) {
        this.votosPreferenciales = Integer.parseInt(votosPreferenciales.toString());
    }

    public Integer getVotosBlancos() {
        return votosBlancos;
    }

    private void agregarVotoBlanco() {
        this.opciones.add(0, new Opcion(TipoDeVoto.VOTO_BLANCO));
    }

    public void setVotosBlancos(Object votosBlancos) {
        this.votosBlancos = Integer.parseInt(votosBlancos.toString());
    }

    public Integer getTotalVotos(){
        return votosPreferenciales+votosBlancos;
    }

    public List<Voto> getListaDeVotos() {
        return new ArrayList<>(listaDeVotos);
    }

    public void setListaDeVotos(List<Voto> listaDeVotos) {
        this.listaDeVotos = new ArrayList<>(listaDeVotos);
    }

    public void setAttributo(CampoDeVotacion campo, Object valor) {
        Map<CampoDeVotacion, Runnable> mapaDeSetters = new HashMap<>();
        mapaDeSetters.put(CampoDeVotacion.ID, () -> setId(valor));
        mapaDeSetters.put(CampoDeVotacion.TITULO, () -> setTitulo(valor));
        mapaDeSetters.put(CampoDeVotacion.DESCRIPCION, () -> setDescripcion(valor));
        mapaDeSetters.put(CampoDeVotacion.ESTADO, () -> setEstado(valor));
        mapaDeSetters.put(CampoDeVotacion.FECHA_INICIO, () -> setFechaInicio((LocalDate) valor));
        mapaDeSetters.put(CampoDeVotacion.HORA_INICIO, () -> setTiempoInicio((LocalTime) valor));
        mapaDeSetters.put(CampoDeVotacion.FECHA_TERMINO, () -> setFechaTermino((LocalDate) valor));
        mapaDeSetters.put(CampoDeVotacion.HORA_TERMINO, () -> setTiempoTermino((LocalTime) valor));
        mapaDeSetters.put(CampoDeVotacion.OPCIONES, () -> setOpciones((List<Opcion>) valor));
        mapaDeSetters.put(CampoDeVotacion.VOTANTES, () -> setVotantes((List<Votante>) valor));
        mapaDeSetters.put(CampoDeVotacion.VOTOS_BLANCOS, () -> setVotosBlancos(valor));
        mapaDeSetters.put(CampoDeVotacion.VOTOS_PREFERENCIALES, () -> setVotosPreferenciales(valor));
        mapaDeSetters.get(campo).run();
    }
}
