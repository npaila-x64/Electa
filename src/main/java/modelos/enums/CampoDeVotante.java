package modelos.enums;
/**
 * Campos relacionados con un campo de información en particular de un usuario votante.
 *
 */
public enum CampoDeVotante {
    /**
     * Campo del identificador numérico único de un votante, con un valor String asociado.
     */
    ID("id"),
    /**
     * Campo del rut de un votante, con un valor String asociado.
     */
    RUT("rut"),
    /**
     * Campo de la clave o contraseña de un votante, con un valor String asociado.
     */
    CLAVE("clave");
    /**
     * Indica el String asociado a cada constante.
     */
    private String texto;
    /**
     * Construye un enum de tipo CampoDeVotante.
     * @param texto el String asociado a una constante.
     */

    CampoDeVotante(String texto) {
        this.texto = texto;
    }
    /**
     * Obtiene el String asociado a una constante.
     * @return el String del texto asociado.
     */
    public String getTexto() {
        return texto;
    }
}
