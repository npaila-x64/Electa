package controladores;

import modelos.Usuario;
import dao.UsuarioDao;
import vistas.PanelLogin;

import java.util.List;
/**
 * Crea el controlador con el cual se administra la lógica de la vista del login.
 */
public class ControladorLogin {
	/**
	 * Indica la vista del login.
	 */
	private PanelLogin vista;
	/**
	 * Indica el controlador principal del programa.
	 */
	private ControladorAplicacion controlador;
	/**
	 * Indica si el ingreso como admin se encuentra activo.
	 */
	private boolean ingresoAdministrativoEstaActivo = false;

	/**
	 * Construye un controlador de tipo ControladorLogin. Instancia el controlador principal y la vista.
	 * Agrega el panel de la vista mediante el controlador principal.
	 * @param controlador el controlador principal de tipo ControladorAplicacion.
	 */
	public ControladorLogin(ControladorAplicacion controlador) {
		this.controlador = controlador;
		vista = new PanelLogin(this);
		this.controlador.agregarPanel(vista, "login");
	}

	/**
	 * Obtiene la lista de usuarios votantes registrados y la compara con el usuario ingresado.
	 *
	 * @param usuario el usuario ingresado.
	 * @return un booleano que indica si las credenciales son válidas.
	 */
	private boolean esCredencialVotanteValida(Usuario usuario) {
		List<Usuario> votantes = UsuarioDao.obtenerVotantes();
		return esCredencialValida(usuario, votantes);
	}

	/**
	 * Obtiene la lista de usuarios votantes registrados y la compara con el usuario ingresado.
	 *
	 * @param usuario el usuario ingresado.
	 * @return un booleano que indica si las credenciales son válidas.
	 */
	private boolean esCredencialAdminValida(Usuario usuario){
		List<Usuario> administradores = UsuarioDao.obtenerAdministradores();
		return esCredencialValida(usuario, administradores);
	}

	/**
	 * Verifica que las credenciales ingresadas correspondan a algún usuario en la lista.
	 * @param usuario el usuario ingresado.
	 * @param usuarios la lista de usuarios registrados.
	 * @return un booleano que comprueba la existencia del usuario ingresado.
	 */
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

	/**
	 * Autentica el usuario ingresado si las credenciales no están vacías.
	 */
	public void autenticacionFueSolicitada() {
		if (!estanLasCredencialesVacias()) {
			autenticarUsuario(ingresoAdministrativoEstaActivo);
		} else {
			vista.mostrarCredencialesEstanVacias();
		}
	}

	/**
	 * Verifica si las entradas en los campos de texto se encuentran vacías.
	 * @return un booleano que comprueba ingreso de credenciales vacío.
	 */
	private boolean estanLasCredencialesVacias() {
		return vista.getRut().isEmpty() || vista.getClave().isEmpty();
	}

	/**
	 * Autentica al usuario para los casos de usuario votante y administradir.
	 * @param esAdmin
	 */
	private void autenticarUsuario(boolean esAdmin){
		Usuario usuarioIngresado = obtenerUsuarioIngresado();
		if (esAdmin){
			autenticarAdministrador(usuarioIngresado);
		} else {
			autenticarVotante(usuarioIngresado);
		}
	}

	/**
	 * Obtiene el usuario que se intenta ingresar mediante los campos de rut y contraseña.
	 * @return el usuario ingresado.
	 */
	private Usuario obtenerUsuarioIngresado(){
		Usuario usuarioIngresado = new Usuario();
		usuarioIngresado.setRut(vista.getRut());
		usuarioIngresado.setClave(vista.getClave());
		return usuarioIngresado;
	}
	/**
	 * Valida que las credenciales del usuario administrador sean válidas y llama al controlador principal
	 * para asignar al usuario de la sesión y ir a la vista del menu administración.
	 * @param usuarioIngresado el usuario administrador ingresado.
	 */
	private void autenticarAdministrador(Usuario usuarioIngresado) {
		if (esCredencialAdminValida(usuarioIngresado)){
			controlador.asignarUsuarioDeSesion(usuarioIngresado);
			controlador.abrirAdministracion();
			vista.autenticacionSeLogro();
		} else {
			vista.autenticacionFallo();
		}
	}

	/**
	 * Valida que las credenciales del usuario votante sean válidas y llama al controlador principal
	 * para asignar al usuario de la sesión y ir a la vista de votaciones en curso.
	 * @param usuarioIngresado el usuario votante ingresado.
	 */

	private void autenticarVotante(Usuario usuarioIngresado) {
		if (esCredencialVotanteValida(usuarioIngresado)){
			controlador.asignarUsuarioDeSesion(usuarioIngresado);
			controlador.abrirVotacionesEnCurso();
			vista.autenticacionSeLogro();
		} else {
			vista.autenticacionFallo();
		}
	}
	/**
	 * Llama al controlador principal para asignar al usuario de la sesión y mostrar el panel
	 * del login.
	 */
	public void abrir() {
		controlador.asignarUsuarioDeSesion(new Usuario());
		controlador.mostrarPanel("login");
		vista.limpiar();
	}

	/**
	 * Altera la vista cuando se solicita ingresar como administrador o como votante.
	 */
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