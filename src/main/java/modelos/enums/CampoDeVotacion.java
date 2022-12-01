package modelos.enums;

import java.util.List;

public enum CampoDeVotacion {

    ID("id"),
    TITULO("titulo"),
    DESCRIPCION("descripcion"),
    FECHA_INICIO("fecha_inicio"),
    HORA_INICIO("hora_inicio"),
    FECHA_TERMINO("fecha_termino"),
    HORA_TERMINO("hora_termino"),
    OPCIONES("opciones"),
    VOTOS_PREFERENCIALES("votos_preferenciales"),
    VOTANTES("votantes"),
    VOTOS_BLANCOS("votos_blancos"),
    ESTADO("estado");

    private final String texto;

    CampoDeVotacion(String texto) {
        this.texto = texto;
    }

    public String getTexto() {
        return texto;
    }

    public static List<CampoDeVotacion> getCamposDeVotacion() {
        return List.of(
                ID, TITULO, DESCRIPCION, ESTADO, OPCIONES,
                VOTANTES, VOTOS_BLANCOS, VOTOS_PREFERENCIALES,
                FECHA_INICIO, HORA_INICIO, FECHA_TERMINO, HORA_TERMINO);
    }

    /*  Conjunto de Enums que representan los campos de una votación
        que son directamente editables por un modelos.Administrador */
    public static List<CampoDeVotacion> getCamposDeVotacionEditablesPorAdministrador() {
        return List.of(
                TITULO, DESCRIPCION, FECHA_INICIO, HORA_INICIO, FECHA_TERMINO, HORA_TERMINO);
    }
}
