package modelos.enums;

import java.util.List;

/**
 * Posibles estados que puede tener una votación en un momento dado.
 */

public enum EstadoDeVotacion {
    /**
     * Estado de una votación en borrador, con un valor String asociado.
     */
    BORRADOR("BORRADOR"),
    /**
     * Estado de una votación pendiente, con un valor String asociado.
     */
    PENDIENTE("PENDIENTE"),
    /**
     * Estado de una votación en curso, con un valor String asociado.
     */
    EN_CURSO("EN CURSO"),
    /**
     * Estado de una votación finalizada, con un valor String asociado.
     */
    FINALIZADO("FINALIZADO");
    /**
     * Indica el String asociado a cada constante.
     */
    private String texto;
    /**
     * Construye un enum de tipo EstadoDeVotacion.
     * @param texto el String asociado a una constante.
     */
    EstadoDeVotacion(String texto) {
        this.texto = texto;
    }
    /**
     * Obtiene una lista de cada constante del enum EstadoDeVotacion.
     * @return una lista de tipo List que almacena objetos de tipo EstadoDeVotacion.
     */
    public static List<EstadoDeVotacion> getEstados() {
        return List.of(BORRADOR, PENDIENTE, EN_CURSO, FINALIZADO);
    }
    /**
     * Obtiene el String asociado a una constante.
     * @return el String del texto asociado.
     */
    public String getTexto() {
        return texto;
    }

    /**
     * Obtiene un estado si el String de texto asociado a dicho estado es coincidente con
     * el valor String de un Object dado.
     * @param texto el objeto Object que representa un texto.
     * @return un EstadoDeVotacion coincidente con el texto.
     */
    public static EstadoDeVotacion fromObject(Object texto) {
        for (EstadoDeVotacion estado : EstadoDeVotacion.values()) {
            if (estado.getTexto().equalsIgnoreCase(texto.toString())) {
                return estado;
            }
        }
        return null;
    }
}
