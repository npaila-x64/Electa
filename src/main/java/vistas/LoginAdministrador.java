package vistas;

import controladores.ControladorLogin;

public class LoginAdministrador {

	private String campoClave;
	private ControladorLogin controladorLogin;

	public LoginAdministrador(ControladorLogin controladorLogin) {
		this.controladorLogin = controladorLogin;
	}

	public void iniciar() {
		ingresarClave();
		controladorLogin.validarDatos(this.campoClave);
	}

	private void ingresarClave() {

	}
}