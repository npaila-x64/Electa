package vistas.votante;

import controladores.ControladorLogin;
import utils.ValidadorDeDatos;

public class LoginVotante {

	private String campoRut;
	private String campoClave;

	public LoginVotante(ControladorLogin controladorLogin) {
		ingresarRut();
		ingresarClave();
		controladorLogin.validarDatosVotante(this.campoRut, this.campoClave);
	}

	private void ingresarRut() {
		this.campoRut = ValidadorDeDatos.pedirEntrada("Ingrese su rut\n> ");
	}

	private void ingresarClave() {
		this.campoClave = ValidadorDeDatos.pedirEntrada("Ingrese su clave\n> ");
	}
}