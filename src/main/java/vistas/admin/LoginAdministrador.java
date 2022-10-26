package vistas.admin;

import controladores.ControladorLogin;
import utils.ValidadorDeDatos;

public class LoginAdministrador {

	private String campoClave;
	private final ControladorLogin controladorLogin;

	public LoginAdministrador(ControladorLogin controladorLogin) {
		this.controladorLogin = controladorLogin;
	}

	public void mostrar() {
		ingresarClave();
		controladorLogin.validarDatosAdministrador(this.campoClave);
	}

	private void ingresarClave() {
		this.campoClave = ValidadorDeDatos
				.pedirEntrada("Ingrese la contraseña del administrador \n> ");
	}
}