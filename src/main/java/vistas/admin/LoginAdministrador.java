package vistas.admin;

import controladores.ControladorLogin;
import utils.ValidadorDeDatos;

public class LoginAdministrador {

	private String campoClave;

	public LoginAdministrador(ControladorLogin controladorLogin) {
		ingresarClave();
		controladorLogin.validarDatosAdministrador(this.campoClave);
	}

	private void ingresarClave() {
		this.campoClave = ValidadorDeDatos
				.pedirEntrada("Ingrese la contraseña del administrador \n> ");
	}
}