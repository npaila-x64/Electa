package vistas.admin;

import controladores.ControladorLogin;
import utils.ValidadorDeDatos;
import vistas.Login;

public class LoginAdministrador extends Login {
	public LoginAdministrador(ControladorLogin controladorLogin) {
		ingresarRut();
		ingresarClave();
		controladorLogin.validarDatosAdministrador(campoRut, campoClave);
	}
}