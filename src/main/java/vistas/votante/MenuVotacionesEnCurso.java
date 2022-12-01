package vistas.votante;

import controladores.votante.ControladorVotacionesEnCurso;
import modelos.Votacion;
import utils.ValidadorDeDatos;

import java.util.List;

public class MenuVotacionesEnCurso {

	private final ControladorVotacionesEnCurso controlador;

	public MenuVotacionesEnCurso(ControladorVotacionesEnCurso controlador) {
		this.controlador = controlador;
	}

	private void mostrarVotacionesEnCurso() {
		System.out.println("Votaciones En Curso");
		mostrarTitulosVotaciones(controlador.obtenerVotacionesEnCurso());
	}

	private void mostrarTitulosVotaciones(List<Votacion> votaciones) {
		for (Votacion votacion : votaciones) {
			System.out.printf("\"%s\"%n", votacion.getTitulo());
		}
	}

	private void mostrarOpcionesMenuVotacionesVotante() {
		System.out.print(("\n" +
						  "OPCIONES\n" +
						  "[1] Votar\n" +
						  "[2] Ver Resultados\n" +
						  "Si desea cerrar su sesión escriba [0]\n").concat("> "));
	}
}