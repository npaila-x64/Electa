public enum Estados {

    BORRADOR("BORRADOR"),
    PENDIENTE("PENDIENTE"),
    EN_CURSO("EN CURSO"),
    FINALIZADO("FINALIZADO");

    private String nombre;

    Estados(String nombre) {
        this.nombre = nombre;
    }

    public String mostrarNombre() {
        return nombre;
    }

    @Override
    public String toString() {
        return nombre;
    }
}
