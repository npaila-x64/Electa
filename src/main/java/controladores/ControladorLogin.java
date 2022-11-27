package controladores;

import modelos.Votante;
import dao.UsuarioDao;
import vistas.PanelLogin;

import java.util.List;

public class ControladorLogin {

	private PanelLogin vista;
	private ControladorAplicacion controlador;
	private boolean ingresoAdministrativoEstaActivo = false;

	public ControladorLogin(ControladorAplicacion controlador) {
		this.controlador = controlador;
		vista = new PanelLogin(this);
		this.controlador.agregarPanel(vista, "login");
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
		if (!estanLasCredencialesVacias()) {
			if (ingresoAdministrativoEstaActivo) {
				autenticarAdministrador();
			} else {
				autenticarVotante();
			}
		} else {
			vista.mostrarCredencialesEstanVacias();
		}
	}

	private boolean estanLasCredencialesVacias() {
		return vista.getRut().isEmpty() && vista.getClave().isEmpty();
	}

	private void autenticarAdministrador() {
		controlador.abrirAdministracion();
	}

	private void autenticarVotante() {
		Votante votanteIngresado = new Votante();
		votanteIngresado.setRut(vista.getRut());
		votanteIngresado.setClave(vista.getClave());
		if (esCredencialVotanteValida(votanteIngresado)) {
			controlador.asignarUsuarioDeSesion(votanteIngresado);
			controlador.abrirVotacionesEnCurso();
			vista.autenticacionSeLogro();
		} else {
			vista.autenticacionFallo();
		}
	}

	public void abrir() {
		controlador.asignarUsuarioDeSesion(new Votante());
		controlador.mostrarPanel("login");
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