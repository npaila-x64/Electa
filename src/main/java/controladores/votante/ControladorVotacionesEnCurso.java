package controladores.votante;

import controladores.ControladorAplicacion;
import modelos.Votacion;
import dao.VotacionDao;
import modelos.enums.EstadoDeVotacion;
import vistas.admin.PanelLogin;
import vistas.votante.MenuVotacionesEnCurso;
import vistas.votante.PanelVotacionesEnCurso;

import java.awt.*;
import java.util.List;

public class ControladorVotacionesEnCurso {

	private ControladorAplicacion controlador;
	private PanelVotacionesEnCurso vista;

	public ControladorVotacionesEnCurso(ControladorAplicacion controlador) {
		this.controlador = controlador;
		vista = new PanelVotacionesEnCurso(this);
		this.controlador.agregarVotacionesEnCurso(vista);
	}

	public List<Votacion> obtenerVotacionesEnCurso() {
		return VotacionDao.obtenerVotacionesConEstado(EstadoDeVotacion.EN_CURSO);
	}

	public void abrir() {
		controlador.mostrarVotacionesEnCurso();
	}

	public void cerrarSesionFueSolicitada() {
		controlador.abrirLogin();
	}

	public void abirResultadosFueSolicitada() {
		controlador.abrirResultados();
	}
}