public class Usuario {

    private Integer id;
    private String clave;

    public Integer getId() {
        return id;
    }

    public void setId(Object id) {
        this.id = Integer.parseInt(id.toString());
    }

    public String getClave() {
        return clave;
    }

    public void setClave(Object clave) {
        this.clave = clave.toString();
    }
}
