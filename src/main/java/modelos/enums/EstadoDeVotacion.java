package modelos.enums;

import java.util.List;

public enum EstadoDeVotacion {

    BORRADOR("BORRADOR"),
    PENDIENTE("PENDIENTE"),
    EN_CURSO("EN CURSO"),
    FINALIZADO("FINALIZADO");

    private String texto;

    EstadoDeVotacion(String texto) {
        this.texto = texto;
    }

    public static List<EstadoDeVotacion> getEstados() {
        return List.of(BORRADOR, PENDIENTE, EN_CURSO, FINALIZADO);
    }

    public String getTexto() {
        return texto;
    }

    public static EstadoDeVotacion fromObject(Object texto) {
        for (EstadoDeVotacion estado : EstadoDeVotacion.values()) {
            if (estado.getTexto().equalsIgnoreCase(texto.toString())) {
                return estado;
            }
        }
        return null;
    }

    public int length() {
        return this.texto.length();
    }

    @Override
    public String toString() {
        return texto;
    }
}
