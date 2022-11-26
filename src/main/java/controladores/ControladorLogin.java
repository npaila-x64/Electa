package controladores;

import controladores.admin.ControladorAdministracion;
import controladores.votante.ControladorVotacionesEnCurso;
import excepciones.AccesoADatosInterrumpidoException;
import modelos.Votante;
import dao.UsuarioDao;
import utils.ValidadorDeDatos;
import vistas.admin.*;
import vistas.votante.LoginVotante;

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

    // TODO casi igual al método validarDatosVotante
	public void validarDatosAdministrador(String rutAdmin, String claveAdmin) {
		Votante adminIngresado = new Votante();
		adminIngresado.setRut(rutAdmin);
		adminIngresado.setClave(claveAdmin);
		if (esCredencialAdministradorValida(adminIngresado)) {
			new ControladorAdministracion();
		} else {
			System.err.println("RUT y/o contraseña incorrectos");
		}
	}

	public void validarDatosVotante(String rutVotante, String claveVotante) {
		Votante votanteIngresado = new Votante();
		votanteIngresado.setRut(rutVotante);
		votanteIngresado.setClave(claveVotante);
		if (esCredencialVotanteValida(votanteIngresado)) {
			new ControladorVotacionesEnCurso(votanteIngresado.getId());
		} else {
			System.err.println("RUT y/o contraseña incorrectos");
		}
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

			new ControladorVotacionesEnCurso(votanteIngresado.getId());
		} else {
			vista.autenticacionFallo();
		}
	}
}