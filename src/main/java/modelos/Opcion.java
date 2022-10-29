package modelos;

public class Opcion {

    private Integer id;
    private String nombre;
    private Votacion votacion;
    private Integer cantidadDeVotos;

    public Opcion() {
        this.id = -1;
        this.nombre = "";
        this.votacion = new Votacion();
        this.cantidadDeVotos = 0;
    }

    public static Opcion getOpcionConVotoBlanco() {
        var opcion = new Opcion();
        opcion.setId(1);
        opcion.setNombre("Abstenerse");
        opcion.setCantidadDeVotos(0);
        return opcion;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Object id) {
        this.id = Integer.parseInt(id.toString());
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(Object nombre) {
        this.nombre = nombre.toString();
    }

    public Votacion getVotacion() {
        return new Votacion(votacion);
    }

    public void setVotacion(Votacion votacion) {
        this.votacion = new Votacion(votacion);
    }

    public Integer getCantidadDeVotos() {
        return cantidadDeVotos;
    }

    public void setCantidadDeVotos(Object cantidadDeVotos) {
        this.cantidadDeVotos = Integer.parseInt(cantidadDeVotos.toString());
    }
}
