package modelos.enums;

import java.util.List;
/**
 * Campos relacionados con un campo de información en particular de una votación.
 *
 */
public enum CampoDeVotacion {
    /**
     * Campo del identificador numérico único de una votación, con un valor String asociado.
     */
    ID("id"),
    /**
     * Campo del título de una votación, con un valor String asociado.
     */
    TITULO("titulo"),
    /**
     * Campo de la descripción de una votación, con un valor String asociado.
     */
    DESCRIPCION("descripcion"),
    /**
     * Campo de la fecha de inicio de una votación, con un valor String asociado.
     */
    FECHA_INICIO("fecha_inicio"),
    /**
     * Campo de la hora de inicio de una votación, con un valor String asociado.
     */
    HORA_INICIO("hora_inicio"),
    /**
     * Campo de la fecha de término de una votación, con un valor String asociado.
     */
    FECHA_TERMINO("fecha_termino"),
    /**
     * Campo de la hora de término de una votación, con un valor String asociado.
     */
    HORA_TERMINO("hora_termino"),
    /**
     * Campo de las opciones de una votación, con un valor String asociado.
     */
    OPCIONES("opciones"),
    /**
     * Campo de los votos preferenciales emitidos en una votación, con un valor String asociado.
     */
    VOTOS_PREFERENCIALES("votos_preferenciales"),
    /**
     * Campo de los votantes que votaron en una votación, con un valor String asociado.
     */
    VOTANTES("votantes"),
    /**
     * Campo de los votos blancos emitidos en una votación, con un valor String asociado.
     */
    VOTOS_BLANCOS("votos_blancos"),
    /**
     * Campo del estado de una votación, con un valor String asociado.
     */
    ESTADO("estado");
    /**
     * Indica el String asociado a cada constante.
     */
    private final String texto;
    /**
     * Construye un enum de tipo CampoDeVotacion.
     * @param texto el String asociado a una constante.
     */
    CampoDeVotacion(String texto) {
        this.texto = texto;
    }
    /**
     * Obtiene el String asociado a una constante.
     * @return el String del texto asociado.
     */
    public String getTexto() {
        return texto;
    }

    /**
     * Obtiene una lista de cada constante del enum CampoDeVotacion.
     * @return una lista de tipo List que almacena objetos de tipo CampoDeVotacion.
     */
    public static List<CampoDeVotacion> getCamposDeVotacion() {
        return List.of(
                ID, TITULO, DESCRIPCION, ESTADO, OPCIONES,
                VOTANTES, VOTOS_BLANCOS, VOTOS_PREFERENCIALES,
                FECHA_INICIO, HORA_INICIO, FECHA_TERMINO, HORA_TERMINO);
    }

    /**
     * Obtiene una lista de cada constante del enum CampoDeVotacion que referencia un campo
     * editable por un usuario administrador en una votación.
     * @returnuna lista de tipo List que almacena objetos de tipo CampoDeVotacion.
     */
    public static List<CampoDeVotacion> getCamposDeVotacionEditablesPorAdministrador() {
        return List.of(
                TITULO, DESCRIPCION, FECHA_INICIO, HORA_INICIO, FECHA_TERMINO, HORA_TERMINO);
    }
}
