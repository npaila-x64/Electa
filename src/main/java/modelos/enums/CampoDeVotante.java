package modelos.enums;

public enum CampoDeVotante {

    ID("id"),
    RUT("rut"),
    CLAVE("clave");

    private String texto;

    CampoDeVotante(String texto) {
        this.texto = texto;
    }

    public String getTexto() {
        return texto;
    }
}
