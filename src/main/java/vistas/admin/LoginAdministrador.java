package vistas.admin;

import controladores.ControladorLogin;
import utils.ValidadorDeDatos;

public class LoginAdministrador {
	private String campoRut;
	private String campoClave;

	public LoginAdministrador(ControladorLogin controladorLogin) {
		ingresarRut();
		ingresarClave();
		controladorLogin.validarDatosAdministrador(this.campoRut, this.campoClave);
	}

	private void ingresarRut() {
		this.campoRut = ValidadorDeDatos.pedirEntrada("Ingrese su rut\n> ");
	}

	private void ingresarClave() {
		this.campoClave = ValidadorDeDatos.pedirEntrada("Ingrese su clave\n> ");
	}
}