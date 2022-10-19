package vistas;

import controladores.ControladorLogin;
import utils.ValidadorDeDatos;

public class LoginVotante {

	private String campoRut;
	private String campoClave;

	private ControladorLogin controladorLogin;

	public LoginVotante(ControladorLogin controladorLogin) {
		this.controladorLogin = controladorLogin;
	}

	public void iniciar() {
		ingresarRut();
		ingresarClave();
		controladorLogin.validarDatos(this.campoRut, this.campoClave);
	}

	private void ingresarRut() {
		this.campoRut = ValidadorDeDatos.pedirEntrada("Ingrese su rut\n> ");
	}

	private void ingresarClave() {
		this.campoClave = ValidadorDeDatos.pedirEntrada("Ingrese su clave\n> ");
	}
}