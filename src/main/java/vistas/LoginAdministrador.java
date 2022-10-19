package vistas;

import controladores.ControladorLogin;
import utils.ValidadorDeDatos;

public class LoginAdministrador {

	private String campoClave;
	private ControladorLogin controladorLogin;

	public LoginAdministrador(ControladorLogin controladorLogin) {
		this.controladorLogin = controladorLogin;
	}

	public void iniciar() {
		ingresarClave();
		controladorLogin.validarDatosAdministrador(this.campoClave);
	}

	private void ingresarClave() {
		this.campoClave = ValidadorDeDatos
				.pedirEntrada("Ingrese la contraseña del administrador \n> ");

	}
}