package modelos;

public class Voto {

    private Integer id;
    private Votacion votacion;
    private Votante votante;
    private Opcion opcion;

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

    public Votante getVotante() {
        return votante;
    }

    public void setVotante(Votante votante) {
        this.votante = votante;
    }

    public Opcion getOpcion() {
        return opcion;
    }

    public void setOpcion(Opcion opcion) {
        this.opcion = opcion;
    }
}
