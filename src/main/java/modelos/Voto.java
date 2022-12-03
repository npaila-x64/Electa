package modelos;
/**
 * Crea una voto emitido por usuario votante en una votación.
 */
public class Voto {
    /**
     * Indica el identificador numérico único de un voto.
     */
    private Integer id;
    /**
     * Indica la votación en la cual se emitió un voto.
     */
    private Votacion votacion;
    /**
     * Indica el votante que realizó un voto.
     */
    private Usuario votante;
    /**
     * Indica la opción por la cual se emitió un voto.
     */
    private Opcion opcion;

    /**
     * Construye una vota vacío, únicamente con un id de 0.
     */

    public Voto() {
        this.id = 0;
    }

    /**
     * Obtiene el identificador numérico asociado a un voto.
     * @return un Integer que representa un id.
     */
    public Integer getId() {
        return id;
    }

    /**
     * Asigna un identificador numérico único a un voto.
     * @param id un Object cuyo valor entero representa un id.
     */
    public void setId(Object id) {
        this.id = Integer.parseInt(id.toString());
    }

    /**
     * Obtiene una votación asociada a un voto.
     * @return una Votacion asociada.
     */
    public Votacion getVotacion() {
        return votacion;
    }

    /**
     * Asigna una votación a un voto.
     * @param votacion una Votacion a asociar.
     */
    public void setVotacion(Votacion votacion) {
        this.votacion = votacion;
    }

    /**
     * Obtiene en usuario votante asociado a un voto.
     * @return un Usuario votante asociado.
     */
    public Usuario getVotante() {
        return votante;
    }

    /**
     * Asigna un usuario votante a un voto.
     * @param votante un Usuario votante a asociar.
     */

    public void setVotante(Usuario votante) {
        this.votante = votante;
    }

    /**
     * Obtiene una opción asociada a un voto.
     * @return una Opcion asociada.
     */

    public Opcion getOpcion() {
        return opcion;
    }

    /**
     * Asigan una opción a un voto.
     * @param opcion una Opcion a asociar.
     */
    public void setOpcion(Opcion opcion) {
        this.opcion = opcion;
    }
}
