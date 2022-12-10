package modelos;

public class Voto {

    private Integer id;
    private Votacion votacion;
    private Usuario votante;
    private Opcion opcion;

    public Voto() {
        this.id = 0;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Object id) {
        this.id = Integer.parseInt(id.toString());
    }

    public Votacion getVotacion() {
        return votacion;
    }

    public void setVotacion(Votacion votacion) {
        this.votacion = votacion;
    }

    public Usuario getVotante() {
        return votante;
    }

    public void setVotante(Usuario votante) {
        this.votante = votante;
    }

    public Opcion getOpcion() {
        return opcion;
    }

    public void setOpcion(Opcion opcion) {
        this.opcion = opcion;
    }

    public boolean esVotoBlanco() {
        return opcion.esOpcionBlanco();
    }

    public Integer getIdVotacion() {
        return votacion.getId();
    }
}
