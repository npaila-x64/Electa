import java.util.List;

public class Opcion {

    private String nombre;
    private Votacion votacion;
    private List<Voto> listaDeVotos;
    private Integer cantidadDeVotos;

    public String getNombre() {
        return nombre;
    }

    public void setNombre(Object nombre) {
        this.nombre = nombre.toString();
    }

    public Votacion getVotacion() {
        return votacion;
    }

    public void setVotacion(Votacion votacion) {
        this.votacion = votacion;
    }

    public List<Voto> getListaDeVotos() {
        return listaDeVotos;
    }

    public void setListaDeVotos(List<Voto> listaDeVotos) {
        this.listaDeVotos = listaDeVotos;
    }

    public Integer getCantidadDeVotos() {
        return cantidadDeVotos;
    }

    public void setCantidadDeVotos(Object cantidadDeVotos) {
        this.cantidadDeVotos = Integer.parseInt(cantidadDeVotos.toString());
    }
}
