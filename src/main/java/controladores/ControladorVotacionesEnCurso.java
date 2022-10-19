package controladores;

import modelos.dao.VotacionDao;
import vistas.MenuVotacionesEnCurso;

public class ControladorVotacionesEnCurso {
	private MenuVotacionesEnCurso vista;
	private VotacionDao votacionDao;

	public ControladorVotacionesEnCurso() {
		MenuVotacionesEnCurso vista = new MenuVotacionesEnCurso(this);
		VotacionDao votacionDao = new VotacionDao();
	}
}