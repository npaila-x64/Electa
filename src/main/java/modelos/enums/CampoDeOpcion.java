package modelos.enums;
/**
 * Campos relacionados con un campo de información en particular de una opción.
 *
 */
public enum CampoDeOpcion {
    /**
     * Campo del identificador numérico único de una opción, con un valor String asociado.
     */
    ID("id"),
    /**
     * Campo de la cantidad de votos por una opción, con un valor String asociado.
     */
    VOTOS("votos"),
    /**
     * Campo del nombre de una opción, con un valor String asociado.
     */
    NOMBRE("nombre");

    /**
     * Indica el String asociado a cada constante.
     */
    private String texto;

    /**
     * Construye un enum de tipo CampoDeOpcion.
     * @param texto el String asociado a una constante.
     */

    CampoDeOpcion(String texto) {
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
