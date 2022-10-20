package modelos;

import modelos.enums.TipoDeVoto;

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

    public Opcion(TipoDeVoto tipoDeVoto) {
        if (tipoDeVoto.equals(TipoDeVoto.VOTO_BLANCO)) {
            this.id = 1;
            this.nombre = "Abstenerse";
            this.cantidadDeVotos = 0;
        }
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
