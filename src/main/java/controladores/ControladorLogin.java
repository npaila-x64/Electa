package controladores;

import excepciones.AccesoADatosInterrumpidoException;
import modelos.dao.UsuarioDao;
import utils.AccesoADatos;
import utils.ValidadorDeDatos;
import vistas.LoginAdministrador;
import vistas.LoginVotante;

public class ControladorLogin {
	private LoginAdministrador loginAdministrador;
	private LoginVotante loginVotante;
	private UsuarioDao usuarioDao;

	public ControladorLogin() {
		this.loginVotante = new LoginVotante(this);
		this.loginAdministrador = new LoginAdministrador(this);
		this.usuarioDao = new UsuarioDao();
	}

	public void iniciar() {
		mostrarMenuDeIngreso();
	}

	private void mostrarOpcionesDeIngreso() {
		System.out.print("""
                
                ¡Bienvenido/a al sistema Electa!
                [1] Ingresar como Votante
                [2] Ingresar como Administrador
                Si desea salir escriba [0]
                """.concat("> "));
	}

	private void mostrarMenuDeIngreso() {
		while (true) {
			mostrarOpcionesDeIngreso();
			try {
				switch (ValidadorDeDatos.pedirOpcionHasta(2)) {
					case 0 -> {return;}
					case 1 -> loginVotante.iniciar();
					case 2 -> loginAdministrador.iniciar();
				}
			} catch (AccesoADatosInterrumpidoException e) {
				mostrarSistemaNoDisponible(e.getMessage());
			}
		}
	}

	public void validarDatos(String campoClave) {

	}

	public void validarDatos(String rutVotante, String claveVotante) {
		if (ValidadorDeDatos.esCredencialVotanteValida(rutVotante, claveVotante)) {
//			mostrarMenuVotacionesVotante(AccesoADatos.obtenerVotantePorRut(rutVotante));
		} else {
			System.err.println("RUT y/o contraseña incorrectos");
		}
	}

	private void mostrarSistemaNoDisponible(String mensaje) {
		System.err.println("El sistema no se encuentra disponible por ahora, disculpe las molestias\n" +
				"Mensaje de error: " + mensaje);
	}
}