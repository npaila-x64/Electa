import java.util.List;

public class Opcion {

    private Integer id;
    private String nombre;
    private Votacion votacion;
    private Integer cantidadDeVotos;
    private final String textoVotoBlanco = "Abstenerse";

    public Opcion() {}

    public Opcion(TipoDeVoto tipoDeVoto) {
        if (tipoDeVoto.equals(TipoDeVoto.VOTO_BLANCO)) {
            this.id = 1;
            this.nombre = textoVotoBlanco;
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