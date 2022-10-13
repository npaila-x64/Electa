package modelos;

public class Votante extends Usuario {

    private String rut;

    public String getRut() {
        return rut;
    }

    public void setRut(Object rut) {
        this.rut = rut.toString();
    }
}
