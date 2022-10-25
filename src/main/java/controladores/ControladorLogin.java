package controladores;

import controladores.admin.ControladorAdministracion;
import controladores.votante.ControladorVotacionesEnCurso;
import excepciones.AccesoADatosInterrumpidoException;
import modelos.Votante;
import modelos.dao.UsuarioDao;
import utils.ValidadorDeDatos;
import vistas.admin.LoginAdministrador;
import vistas.votante.LoginVotante;

import java.util.List;

public class ControladorLogin {
	private final LoginAdministrador loginAdministrador;
	private final LoginVotante loginVotante;
	private final UsuarioDao usuarioDao;

	public ControladorLogin() {
		this.loginVotante = new LoginVotante(this);
		this.loginAdministrador = new LoginAdministrador(this);
		this.usuarioDao = new UsuarioDao();
	}

	public void iniciar() {
		mostrarMenuDeIngreso();
	}

	private void mostrarOpcionesDeIngreso() {
		System.out.print(("\n" +
						  "¡Bienvenido/a al sistema Electa!\n" +
						  "[1] Ingresar como Votante\n" +
						  "[2] Ingresar como Administrador\n" +
						  "Si desea salir escriba [0]\n").concat("> "));
	}

	private void mostrarMenuDeIngreso() {
		while (true) {
			mostrarOpcionesDeIngreso();
			try {
				switch (ValidadorDeDatos.pedirOpcionHasta(2)) {
					case 0 -> {return;}
					case 1 -> loginVotante.mostrar();
					case 2 -> loginAdministrador.mostrar();
				}
			} catch (AccesoADatosInterrumpidoException e) {
				mostrarSistemaNoDisponible(e.getMessage());
			}
		}
	}

	public void validarDatosAdministrador(String campoClave) {
		if (esCredencialAdministradorValida(campoClave)) {
			new ControladorAdministracion().iniciar();
		} else {
			System.err.println("Contraseña incorrecta");
		}
	}

	public void validarDatosVotante(String rutVotante, String claveVotante) {
		Votante votanteIngresado = new Votante();
		votanteIngresado.setRut(rutVotante);
		votanteIngresado.setClave(claveVotante);
		if (esCredencialVotanteValida(votanteIngresado)) {
			new ControladorVotacionesEnCurso(votanteIngresado).iniciar();
		} else {
			System.err.println("RUT y/o contraseña incorrectos");
		}
	}

	private boolean esCredencialVotanteValida(Votante votante) {
		List<Votante> votantes = this.usuarioDao.obtenerVotantes();
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

	private boolean esCredencialAdministradorValida(String clave) {
		return clave.equals(this.usuarioDao.obtenerCredencialAdmin());
	}

	private void mostrarSistemaNoDisponible(String mensaje) {
		System.err.println("El sistema no se encuentra disponible por ahora, disculpe las molestias\n" +
				"Mensaje de error: " + mensaje);
	}
}