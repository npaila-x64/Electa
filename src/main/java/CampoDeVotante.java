public enum CampoDeVotante {

    ID("id"),
    RUT("rut"),
    CLAVE("clave");

    private String nombre;

    CampoDeVotante(String nombre) {
        this.nombre = nombre;
    }

    public String getNombre() {
        return nombre;
    }
}
