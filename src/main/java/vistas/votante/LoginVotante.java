package vistas.votante;

import controladores.ControladorLogin;
import utils.ValidadorDeDatos;
import vistas.Login;

public class LoginVotante extends Login {
	public LoginVotante(ControladorLogin controladorLogin) {
		ingresarRut();
		ingresarClave();
		controladorLogin.validarDatosVotante(campoRut, campoClave);
	}
}