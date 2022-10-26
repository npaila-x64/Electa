package controladores.votante;

import modelos.Votacion;
import modelos.Votante;
import modelos.dao.VotacionDao;
import modelos.enums.Estado;
import vistas.votante.MenuVotacionesEnCurso;

import java.util.List;

public class ControladorVotacionesEnCurso {

	private final Integer idVotante;

	public ControladorVotacionesEnCurso(Integer idVotanteDeEstaSesion) {
		this.idVotante = idVotanteDeEstaSesion;
		new MenuVotacionesEnCurso(this).mostrar();
	}

	public List<Votacion> obtenerVotacionesEnCurso() {
		return VotacionDao.obtenerVotacionesConEstado(Estado.EN_CURSO);
	}

	public void mostrarMenuParaVotar() {
		new ControladorVotacion(this.idVotante);
	}

	public void mostrarMenuResultados() {
		new ControladorResultados();
	}

}