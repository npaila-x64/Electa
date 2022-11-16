package vistas.admin;

public interface LoginVista {

    String obtenerRut();
    String obtenerClave();

    void autenticacionFallo();
    void autenticacionSeLogro();

    LoginVistaControlador getVistaLoginControlador();
}
