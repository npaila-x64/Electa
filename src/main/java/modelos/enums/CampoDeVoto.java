package modelos.enums;
/**
 * Campos relacionados con un campo de información en particular de un voto.
 *
 */
public enum CampoDeVoto {
    /**
     * Campo del identificador numérico único de un voto, con un valor String asociado.
     */
    ID("id"),
    /**
     * Campo del votante emisor de un voto, con un valor String asociado.
     */
    VOTANTE("votante"),
    /**
     * Campo de la votación a la que pertenece un voto, con un valor String asociado.
     */
    VOTACION("votacion"),
    /**
     * Campo de la opción referente a un voto, con un valor String asociado.
     */
    OPCION("opcion");
    /**
     * Indica el String asociado a cada constante.
     */
    private String texto;
    /**
     * Construye un enum de tipo CampoDeVoto.
     * @param texto el String asociado a una constante.
     */
    CampoDeVoto(String texto) {
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
