package controladores.votante;

import controladores.ControladorAplicacion;
import modelos.Votacion;
import dao.VotacionDao;
import modelos.Usuario;
import modelos.enums.EstadoDeVotacion;
import vistas.votante.PanelVotacionesEnCurso;
import vistas.votante.VotacionesEnCursoTableModel;

import javax.swing.table.TableModel;
import java.util.List;

public class ControladorVotacionesEnCurso {

	private final ControladorAplicacion controlador;
	private final PanelVotacionesEnCurso vista;
	private final VotacionesEnCursoTableModel modelo;
	private List<Votacion> votaciones;

	public ControladorVotacionesEnCurso(ControladorAplicacion controlador) {
		this.controlador = controlador;
		modelo = new VotacionesEnCursoTableModel();
		vista = new PanelVotacionesEnCurso(this);
		this.controlador.agregarPanel(vista, "votacionesEnCurso");
	}

	private void cargarVotaciones() {
		Usuario votante = controlador.obtenerUsuario();
		votaciones = VotacionDao
				.obtenerVotacionesEnElQuePuedeVotarElVotante(votante);
		modelo.setVotaciones(votaciones);
	}

	public List<Votacion> obtenerVotacionesEnCurso() {
		return VotacionDao.obtenerVotacionesConEstado(EstadoDeVotacion.EN_CURSO);
	}

	public void abrir() {
		cargarVotaciones();
		controlador.asignarPanelPredecesorDeResultados("votacionesEnCurso");
		controlador.mostrarPanel("votacionesEnCurso");
	}

	public void cerrarSesionFueSolicitada() {
		controlador.abrirLogin();
	}

	public void abirResultadosFueSolicitada() {
		controlador.abrirResultados();
	}

	public TableModel getModeloDeTabla() {
		return modelo;
	}

	public void abrirVotacionFueSolicitado(int id) {
		controlador.abrirOpcion(votaciones.get(id));
	}
}