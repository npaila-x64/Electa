package controladores;

import modelos.dao.UsuarioDao;
import vistas.LoginAdministrador;
import vistas.LoginVotante;

public class ControladorLogin {
	private LoginAdministrador loginAdministrador;
	private LoginVotante loginVotante;
	private UsuarioDao usuarioDao;

	public ControladorLogin() {
		LoginVotante loginVotante = new LoginVotante(this);
		LoginAdministrador loginAdministrador = new LoginAdministrador(this);
		UsuarioDao usuarioDao = new UsuarioDao();
	}

	public void iniciar() {

	}
}