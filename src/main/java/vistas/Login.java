package vistas;

import utils.ValidadorDeDatos;

public abstract class Login {
    protected String campoRut;
    protected String campoClave;
    public void ingresarRut() {
        this.campoRut = ValidadorDeDatos.pedirEntrada("Ingrese su rut\n> ");
    }
    public void ingresarClave() {
        this.campoClave = ValidadorDeDatos.pedirEntrada("Ingrese su clave\n> ");
    }
}
