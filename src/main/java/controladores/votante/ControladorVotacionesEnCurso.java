package controladores.votante;

import controladores.ControladorAplicacion;
import modelos.Votacion;
import dao.VotacionDao;
import modelos.Usuario;
import vistas.votante.PanelVotacionesEnCurso;
import vistas.VotacionesTableModel;

import javax.swing.table.TableModel;
import java.util.List;

public class ControladorVotacionesEnCurso {

	private final ControladorAplicacion controlador;
	private final PanelVotacionesEnCurso vista;
	private final VotacionesTableModel modeloDeTabla;
	private List<Votacion> votaciones;

	public ControladorVotacionesEnCurso(ControladorAplicacion controlador) {
		this.controlador = controlador;
		modeloDeTabla = new VotacionesTableModel();
		vista = new PanelVotacionesEnCurso(this);
		this.controlador.agregarPanel(vista, "votacionesEnCurso");
	}

	private void cargarVotaciones() {
		Usuario votante = controlador.obtenerUsuario();
		votaciones = VotacionDao
				.obtenerVotacionesEnElQuePuedeVotarElVotante(votante);
		modeloDeTabla.setVotaciones(votaciones);
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
		controlador.abrirEscogerResultado();
	}

	public TableModel getModeloDeTabla() {
		return modeloDeTabla;
	}

	public void abrirVotacionFueSolicitado(int id) {
		controlador.abrirOpcion(votaciones.get(id));
	}
}