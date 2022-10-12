import java.util.List;

public enum Estado {

    BORRADOR("BORRADOR"),
    PENDIENTE("PENDIENTE"),
    EN_CURSO("EN CURSO"),
    FINALIZADO("FINALIZADO");

    private String texto;

    Estado(String texto) {
        this.texto = texto;
    }

    public static List<Estado> getEstados() {
        return List.of(BORRADOR, PENDIENTE, EN_CURSO, FINALIZADO);
    }

    public String getTexto() {
        return texto;
    }

    public static Estado fromObject(Object texto) {
        for (Estado estado : Estado.values()) {
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
