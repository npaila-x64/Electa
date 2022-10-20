package controladores;

import modelos.Votacion;
import modelos.Votante;
import modelos.dao.VotacionDao;
import modelos.enums.Estado;
import utils.AccesoADatos;
import utils.ValidadorDeDatos;
import vistas.MenuVotacionesEnCurso;

import java.util.List;

public class ControladorVotacionesEnCurso {

	private final MenuVotacionesEnCurso vista;
	private final VotacionDao votacionDao;
	private final List<Votacion> votacionesEnCurso;
	private final Votante votante;

	public ControladorVotacionesEnCurso(Votante votanteDeEstaSesion) {
		this.votante = votanteDeEstaSesion;
		this.vista = new MenuVotacionesEnCurso(this);
		this.votacionDao = new VotacionDao();
		this.votacionesEnCurso = this.votacionDao.obtenerVotacionesConEstado(Estado.EN_CURSO);
	}

	public void iniciar() {
		this.vista.mostrar();
	}

	public List<Votacion> obtenerVotacionesEnCurso() {
		return this.votacionesEnCurso;
	}

	public void mostrarMenuParaVotar() {
		while (true) {
			List<Votacion> votacionesDisponibles = this.votacionDao
					.obtenerVotacionesEnElQuePuedeVotarElVotante(votante);
			mostrarVotacionesDisponiblesParaVotacion(votacionesDisponibles);
			int opcionElegida = ValidadorDeDatos.pedirOpcionHasta(votacionesDisponibles.size());
			if (opcionElegida == 0) break;
			mostrarMenuOpcionesParaVotar(
					votacionesDisponibles.get(opcionElegida - 1), votante);
		}
	}

	public void mostrarMenuResultados() {
		while (true) {
			List<Votacion> votacionesFinalizadas = this.votacionDao
					.obtenerVotacionesConEstado(Estado.FINALIZADO);
			mostrarOpcionesMenuResultados(votacionesFinalizadas);
			int opcionElegida = ValidadorDeDatos.pedirOpcionHasta(votacionesFinalizadas.size());
			if (opcionElegida == 0) break;
			mostrarResultadosVotacion(votacionesFinalizadas.get(opcionElegida - 1));
		}
	}
}