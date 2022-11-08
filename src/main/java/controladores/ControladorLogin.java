package controladores;

import controladores.admin.ControladorAdministracion;
import controladores.votante.ControladorVotacionesEnCurso;
import excepciones.AccesoADatosInterrumpidoException;
import modelos.Votante;
import dao.UsuarioDao;
import utils.ValidadorDeDatos;
import vistas.admin.LoginAdministrador;
import vistas.votante.LoginVotante;

import java.util.List;

public class ControladorLogin {

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
					case 1 -> new LoginVotante(this);
					case 2 -> new LoginAdministrador(this);
				}
			} catch (AccesoADatosInterrumpidoException e) {
				mostrarSistemaNoDisponible(e.getMessage());
			}
		}
	}

    // TODO casi igual al método validarDatosVotante
	public void validarDatosAdministrador(String rutAdmin, String claveAdmin) {
		Votante admininIngresado = new Votante();
		admininIngresado.setRut(rutAdmin);
		admininIngresado.setClave(claveAdmin);
		if (esCredencialAdministradorValida(admininIngresado)) {
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

	private void mostrarSistemaNoDisponible(String mensaje) {
		System.err.println("El sistema no se encuentra disponible por ahora, disculpe las molestias\n" +
				"Mensaje de error: " + mensaje);
	}
}