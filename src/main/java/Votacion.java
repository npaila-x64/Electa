import java.time.LocalDateTime;
import java.util.List;

public class Votacion {

    private Integer id;
    private String titulo;
    private String descripcion;
    private Estado estado;
    private LocalDateTime fechaTiempoInicio;
    private LocalDateTime fechaTiempoTermino;
    private List<Opcion> opciones;
    private List<Votante> votantes;
    private Integer votosPreferenciales;
    private Integer votosBlancos;

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
    }

    public LocalDateTime getFechaTiempoTermino() {
        return fechaTiempoTermino;
    }

    public void setFechaTiempoTermino(LocalDateTime fechaTiempoTermino) {
        this.fechaTiempoTermino = fechaTiempoTermino;
    }

    public List<Opcion> getOpciones() {
        return opciones;
    }

    public void setOpciones(List<Opcion> opciones) {
        this.opciones = opciones;
    }

    public List<Votante> getVotantes() {
        return votantes;
    }

    public void setVotantes(List<Votante> votantes) {
        this.votantes = votantes;
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

    public void setVotosBlancos(Object votosBlancos) {
        this.votosBlancos = Integer.parseInt(votosBlancos.toString());
    }
}
