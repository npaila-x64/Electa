package controladores.votante;

import controladores.ControladorAplicacion;
import modelos.Votacion;
import dao.VotacionDao;
import modelos.Votante;
import modelos.enums.EstadoDeVotacion;
import vistas.votante.PanelVotacionesEnCurso;
import vistas.votante.VotacionesEnCursoTableModel;

import javax.swing.table.TableModel;
import java.util.List;

public class ControladorVotacionesEnCurso {

	private final ControladorAplicacion controlador;
	private final PanelVotacionesEnCurso vista;
	private final VotacionesEnCursoTableModel modelo;

	public ControladorVotacionesEnCurso(ControladorAplicacion controlador) {
		this.controlador = controlador;
		modelo = new VotacionesEnCursoTableModel();
		vista = new PanelVotacionesEnCurso(this);
		this.controlador.agregarVotacionesEnCurso(vista);
	}

	public void cargarVotaciones() {
		Votante votante = controlador.obtenerUsuario();
		List<Votacion> votaciones = VotacionDao
				.obtenerVotacionesEnElQuePuedeVotarElVotante(votante.getId());
		modelo.setVotaciones(votaciones);
	}

	public List<Votacion> obtenerVotacionesEnCurso() {
		return VotacionDao.obtenerVotacionesConEstado(EstadoDeVotacion.EN_CURSO);
	}

	public void abrir() {
		cargarVotaciones();
		controlador.mostrarVotacionesEnCurso();
	}

	public void cerrarSesionFueSolicitada() {
		controlador.abrirLogin();
	}

	public void abirResultadosFueSolicitada() {
		controlador.abrirResultados();
	}

	public TableModel obtenerModeloDeTabla() {
		return modelo;
	}

	public void abrirVotacionFueSolicitado(int id) {

	}
}