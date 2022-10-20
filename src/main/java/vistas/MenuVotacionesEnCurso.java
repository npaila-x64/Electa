package vistas;

import controladores.ControladorVotacionesEnCurso;
import modelos.Votacion;
import modelos.Votante;
import modelos.enums.Estado;
import utils.AccesoADatos;
import utils.ValidadorDeDatos;

import java.util.List;

public class MenuVotacionesEnCurso {
	private final ControladorVotacionesEnCurso controlador;
	public MenuVotacionesEnCurso(ControladorVotacionesEnCurso controlador) {
		this.controlador = controlador;
	}

	public void mostrar() {
		while (true) {
			mostrarVotacionesEnCurso();
			mostrarOpcionesMenuVotacionesVotante();
			switch (ValidadorDeDatos.pedirOpcionHasta(2)) {
				case 0 -> {return;}
				case 1 -> controlador.mostrarMenuParaVotar();
				case 2 -> controlador.mostrarMenuResultados();
			}
		}
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
		System.out.print("""
                
                OPCIONES
                [1] Votar
                [2] Ver Resultados
                Si desea cerrar su sesión escriba [0]
                """.concat("> "));
	}
}