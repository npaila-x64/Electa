package modelos.enums;

public enum CampoDeVoto {

    ID("id"),
    VOTANTE("votante"),
    VOTACION("votacion"),
    OPCION("opcion");

    private String texto;

    CampoDeVoto(String texto) {
        this.texto = texto;
    }

    public String getTexto() {
        return texto;
    }
}
