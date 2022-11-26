package controladores;

import controladores.admin.ControladorAdministracion;
import controladores.votante.ControladorVotacionesEnCurso;
import modelos.Votante;
import dao.UsuarioDao;
import vistas.admin.*;

import java.util.List;

public class ControladorLogin {

	private PanelLogin vista;
	private ControladorAplicacion controlador;

	public ControladorLogin(ControladorAplicacion controlador) {
		this.controlador = controlador;
		vista = new PanelLogin(this);
		this.controlador.agregarLogin(vista);
	}

	public void iniciar() {
		controlador.mostrarLogin();
	}

	private boolean esCredencialVotanteValida(Votante votante) {
		List<Votante> votantes = UsuarioDao.obtenerVotantes();
		for (Votante votanteSiguiente : votantes) {
			// Primero verifica que los rut sean iguales, después se verifica la clave
			if (votanteSiguiente.getRut().equals(votante.getRut())) {
				if (votanteSiguiente.getClave().equals(votante.getClave())) {
					votante.setId(votanteSiguiente.getId());
					return true;
				}
			}
		}
		return false;
	}

	// TODO es casi igual al método esCredencialVotanteValida
	private boolean esCredencialAdministradorValida(Votante admin) {
		List<Votante> administradores = UsuarioDao.obtenerAdministradores();
		for (Votante adminSiguiente : administradores) {
			// Primero verifica que los rut sean iguales, después se verifica la clave
			if (adminSiguiente.getRut().equals(admin.getRut())) {
				if (adminSiguiente.getClave().equals(admin.getClave())) {
					return true;
				}
			}
		}
		return false;
	}

	public void autenticacionFueSolicitada() {
		String rutVotante = vista.obtenerRut();
		String claveVotante = vista.obtenerClave();
		Votante votanteIngresado = new Votante();
		votanteIngresado.setRut(rutVotante);
		votanteIngresado.setClave(claveVotante);
		if (esCredencialVotanteValida(votanteIngresado)) {
			vista.autenticacionSeLogro();
			controlador.mostrarVotacionesEnCurso();
			new ControladorVotacionesEnCurso(votanteIngresado.getId());
		} else {
			vista.autenticacionFallo();
		}
	}
}