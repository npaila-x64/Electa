package controladores.votante;

import modelos.Votacion;
import modelos.Votante;
import modelos.dao.VotacionDao;
import modelos.enums.Estado;
import vistas.votante.MenuVotacionesEnCurso;

import java.util.List;

public class ControladorVotacionesEnCurso {

	private final MenuVotacionesEnCurso vista;
	private final Integer idVotante;

	public ControladorVotacionesEnCurso(Integer idVotanteDeEstaSesion) {
		this.idVotante = idVotanteDeEstaSesion;
		this.vista = new MenuVotacionesEnCurso(this);
	}

	public void iniciar() {
		this.vista.mostrar();
	}

	public List<Votacion> obtenerVotacionesEnCurso() {
		return VotacionDao.obtenerVotacionesConEstado(Estado.EN_CURSO);
	}

	public void mostrarMenuParaVotar() {
		new ControladorVotacion(this.idVotante).iniciar();
	}

	public void mostrarMenuResultados() {
		new ControladorResultados().iniciar();
	}

}