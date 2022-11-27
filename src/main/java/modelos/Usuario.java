package modelos;

public class Usuario {

    private Integer id;
    private String clave;
    private String rut;

    public String getRut() {
        return rut;
    }

    public void setRut(Object rut) {
        this.rut = rut.toString();
    }

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
