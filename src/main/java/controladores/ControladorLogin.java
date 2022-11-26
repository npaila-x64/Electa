package controladores;

import modelos.Votante;
import dao.UsuarioDao;
import vistas.admin.*;

import java.util.List;

public class ControladorLogin {

	private PanelLogin vista;
	private ControladorAplicacion controlador;
	private boolean ingresoAdministrativoEstaActivo = false;

	public ControladorLogin(ControladorAplicacion controlador) {
		this.controlador = controlador;
		vista = new PanelLogin(this);
		this.controlador.agregarLogin(vista);
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

	public void autenticacionFueSolicitada() {
		if (ingresoAdministrativoEstaActivo) {
			autenticarAdministrador();
		} else {
			autenticarVotante();
		}
	}

	private void autenticarAdministrador() {
	}

	private void autenticarVotante() {
		String rutVotante = vista.obtenerRut();
		String claveVotante = vista.obtenerClave();
		Votante votanteIngresado = new Votante();
		votanteIngresado.setRut(rutVotante);
		votanteIngresado.setClave(claveVotante);
		if (esCredencialVotanteValida(votanteIngresado)) {
			vista.autenticacionSeLogro();
			controlador.asignarUsuarioDeSesion(votanteIngresado);
			controlador.abrirVotacionesEnCurso();
		} else {
			vista.autenticacionFallo();
		}
	}

	public void abrir() {
		controlador.asignarUsuarioDeSesion(new Votante());
		controlador.mostrarLogin();
		vista.limpiar();
	}

	public void cambioDeIngresoFueSolicitado() {
		ingresoAdministrativoEstaActivo = !ingresoAdministrativoEstaActivo;
		if (ingresoAdministrativoEstaActivo) {
			vista.cambiarAIngresoAdministrativo();
		} else {
			vista.cambiarAIngresoVotante();
		}
		vista.limpiar();
	}
}