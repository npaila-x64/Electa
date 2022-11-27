package controladores;

import modelos.Votacion;
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
		this.controlador.agregarLogin(vista);
	}

	private boolean esCredencialVotanteValida(Votante votante) {
		List<Votante> votantes = UsuarioDao.obtenerVotantes();
		return esCredencialValida(votante, votantes);
	}

	private boolean esCredencialAdminValida(Votante admin){
		List<Votante> administradores = UsuarioDao.obtenerAdministradores();
		return esCredencialValida(admin, administradores);
	}

	private boolean esCredencialValida(Votante usuario, List<Votante> usuarios){
		for (Votante usuarioSiguiente : usuarios) {
			// Primero verifica que los rut sean iguales, después se verifica la clave
			if (usuarioSiguiente.getRut().equals(usuario.getRut())) {
				if (usuarioSiguiente.getClave().equals(usuario.getClave())) {
					usuario.setId(usuarioSiguiente.getId());
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
		Votante votanteIngresado = new Votante();
		votanteIngresado.setRut(vista.getRut());
		votanteIngresado.setClave(vista.getClave());
		if (esCredencialAdminValida(votanteIngresado)) {
			controlador.asignarUsuarioDeSesion(votanteIngresado);
			controlador.abrirVotacionesEnCurso();
			vista.autenticacionSeLogro();
		} else {
			vista.autenticacionFallo();
		}
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