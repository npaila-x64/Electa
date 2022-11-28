package controladores;

import modelos.Usuario;
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

	private boolean esCredencialVotanteValida(Usuario usuario) {
		List<Usuario> votantes = UsuarioDao.obtenerVotantes();
		return esCredencialValida(usuario, votantes);
	}

	private boolean esCredencialAdminValida(Usuario usuario){
		List<Usuario> administradores = UsuarioDao.obtenerAdministradores();
		return esCredencialValida(usuario, administradores);
	}

	private boolean esCredencialValida(Usuario usuario, List<Usuario> usuarios){
		for (Usuario usuarioSiguiente : usuarios) {
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
		return vista.getRut().isEmpty() || vista.getClave().isEmpty();
	}

	private void autenticarAdministrador() {
		autenticarUsuario(true);
	}

	private void autenticarVotante() {
		autenticarUsuario(false);
	}

	private void autenticarUsuario(boolean esAdmin){
		Usuario usuarioIngresado = new Usuario();
		usuarioIngresado.setRut(vista.getRut());
		usuarioIngresado.setClave(vista.getClave());
		if (esCredencialAdminValida(usuarioIngresado)) {
			controlador.asignarUsuarioDeSesion(usuarioIngresado);
			abrirPanelSubsiguiente(esAdmin);
			vista.autenticacionSeLogro();
		} else {
			vista.autenticacionFallo();
		}
	}

	private void abrirPanelSubsiguiente(boolean esAdmin){
		if(esAdmin){
			controlador.abrirAdministracion();
		}else{
			controlador.abrirVotacionesEnCurso();
		}
	}

	public void abrir() {
		controlador.asignarUsuarioDeSesion(new Usuario());
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