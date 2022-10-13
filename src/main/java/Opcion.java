import java.util.List;

public class Opcion {

    private String nombre;
    private Votacion votacion;
    private Integer cantidadDeVotos;
    private final String textoVotoBlanco = "Abstenerse";

    public Opcion() {}

    public Opcion(TipoDeVoto tipoDeVoto) {
        if (tipoDeVoto.equals(TipoDeVoto.VOTO_BLANCO)) {
            this.nombre = textoVotoBlanco;
        }
    }

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

    public Integer getCantidadDeVotos() {
        return cantidadDeVotos;
    }

    public void setCantidadDeVotos(Object cantidadDeVotos) {
        this.cantidadDeVotos = Integer.parseInt(cantidadDeVotos.toString());
    }
}
enum TipoDeVoto {
    VOTO_BLANCO, VOTO_PREFERENCIAL
}