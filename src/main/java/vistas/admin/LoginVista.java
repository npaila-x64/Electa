package vistas.admin;

public interface LoginVista {

    public String obtenerRut();
    public String obtenerClave();

    public void autenticacionFallo();
    public void autenticacionSeLogro();

    public LoginVistaControlador getVistaLoginControlador();

}
