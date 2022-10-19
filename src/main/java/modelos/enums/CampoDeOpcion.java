package modelos.enums;

public enum CampoDeOpcion {
    ID("id"),
    VOTOS("votos"),
    NOMBRE("nombre");

    private String texto;

    CampoDeOpcion(String texto) {
        this.texto = texto;
    }

    public String getTexto() {
        return texto;
    }

}
