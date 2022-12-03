package modelos;

/**
 * Crea una opción que un votante puede escoger para emitir su voto en una votación.
 */
public class Opcion {
    /**
     * Indica el identificador numérico único de una opción.
     */
    private Integer id;
    /**
     * Indica el nombre de una opción.
     */
    private String nombre;
    /**
     * Indica la votación a la cual una opción pertenece.
     */
    private Votacion votacion;
    /**
     * Indica la cantidad de votos emitidos por una opción.
     */
    private Integer cantidadDeVotos;

    /**
     * Construye una opción vacía, con un id de -1, un nombre vacío y una cantidad de votos de 0.
     */
    public Opcion() {
        this.id = -1;
        this.nombre = "";
        this.cantidadDeVotos = 0;
    }

    /**
     * Crea una opción de abstinencia o de voto blanco, con un id fijo de 1, "Abstenerse" como nombre y
     * una cantidad de votos de 0.
     * @return una nueva opción de voto blanco.
     */

    public static Opcion getOpcionConVotoBlanco() {
        var opcion = new Opcion();
        opcion.setId(1);
        opcion.setNombre("Abstenerse");
        opcion.setCantidadDeVotos(0);
        return opcion;
    }

    /**
     * Obtiene el identificador numérico único asociado a una opción.
     * @return un Integer cuyo valor representa un id.
     */

    public Integer getId() {
        return id;
    }

    /**
     * Asigna un identificador numérico único a una opción.
     * @param id un Object cuyo valor entero representa un id.
     */
    public void setId(Object id) {
        this.id = Integer.parseInt(id.toString());
    }

    /**
     * Obtiene un nombre asociado a una opción.
     * @return un String cuyo valor representa un nombre.
     */
    public String getNombre() {
        return nombre;
    }

    /**
     * Asigna un nombre a una opción.
     * @param nombre un Object cuyo valor String representa un nombre.
     */

    public void setNombre(Object nombre) {
        this.nombre = nombre.toString();
    }

    /**
     * Obtiene una votación asociada a una opción.
     * @return una nueva Votacion creada a partir de una votación asociada.
     */
    public Votacion getVotacion() {
        return new Votacion(votacion);
    }

    /**
     * Asigna una votación a una opción.
     * @param votacion la Votacion asociada.
     */
    public void setVotacion(Votacion votacion) {
        this.votacion = new Votacion(votacion);
    }

    /**
     * Obtiene una cantidad de votos emitidos asociados a una opción.
     * @return un Integer que representa una cantidad de votos.
     */
    public Integer getCantidadDeVotos() {
        return cantidadDeVotos;
    }

    /**
     * Asigna una cantidad de votos emitidos a una opción.
     * @param cantidadDeVotos un Object cuyo valor entero representa una cantidad de votos.
     */
    public void setCantidadDeVotos(Object cantidadDeVotos) {
        this.cantidadDeVotos = Integer.parseInt(cantidadDeVotos.toString());
    }
}
