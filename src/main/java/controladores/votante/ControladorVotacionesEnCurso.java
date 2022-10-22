package controladores.votante;

import modelos.Votacion;
import modelos.Votante;
import modelos.dao.VotacionDao;
import modelos.enums.Estado;
import vistas.votante.MenuVotacionesEnCurso;

import java.util.List;

public class ControladorVotacionesEnCurso {

	private final MenuVotacionesEnCurso vista;
	private final VotacionDao votacionDao;
	private List<Votacion> votacionesEnCurso;
	private final Votante votante;

	public ControladorVotacionesEnCurso(Votante votanteDeEstaSesion) {
		this.votante = votanteDeEstaSesion;
		this.vista = new MenuVotacionesEnCurso(this);
		this.votacionDao = new VotacionDao();
		refrescarVotaciones();
	}

	public void iniciar() {
		this.vista.mostrar();
	}

	public List<Votacion> obtenerVotacionesEnCurso() {
		refrescarVotaciones();
		return this.votacionesEnCurso;
	}

	public void mostrarMenuParaVotar() {
		new ControladorVotacion(this.votante).iniciar();
	}

	public void mostrarMenuResultados() {
		new ControladorResultados().iniciar();
	}

	private void refrescarVotaciones() {
		this.votacionesEnCurso = this.votacionDao.obtenerVotacionesConEstado(Estado.EN_CURSO);
	}
}