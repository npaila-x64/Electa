package controladores.votante;

import controladores.ControladorAplicacion;
import modelos.Votacion;
import dao.VotacionDao;
import modelos.Usuario;
import vistas.votante.PanelVotacionesEnCurso;
import modelos.tablemodel.VotacionesTableModel;

import javax.swing.table.TableModel;
import java.util.List;
/**
 * Crea el controlador con el cual se administra la lógica de la vista de votaciones en curso.
 */
public class ControladorVotacionesEnCurso {
	/**
	 * Indica el controlador principal del programa.
	 */
	private final ControladorAplicacion controlador;
	/**
	 * Indica la vista de votaciones en curso.
	 */
	private final PanelVotacionesEnCurso vista;
	/**
	 * Indica el modelo de tabla utilizado por la vista.
	 */
	private final VotacionesTableModel modeloDeTabla;
	/**
	 * Indica la lista de votaciones totales que administra el controlador.
	 */
	private List<Votacion> votaciones;

	/**
	 * Construye un controlador de tipo ControladorVotacionesEnCurso. Instancia el controlador principal,
	 * el modelo de tabla y la vista. Agrega el panel de la vista mediante el controlador principal.
	 * @param controlador el controlador principal de tipo ControladorAplicacion.
	 */
	public ControladorVotacionesEnCurso(ControladorAplicacion controlador) {
		this.controlador = controlador;
		modeloDeTabla = new VotacionesTableModel();
		vista = new PanelVotacionesEnCurso(this);
		this.controlador.agregarPanel(vista, "votacionesEnCurso");
	}

	/**
	 * Carga las votaciones en las que el usuario puede votar dentro de la tabla.
	 */
	private void cargarVotaciones() {
		Usuario votante = controlador.obtenerUsuario();
		votaciones = VotacionDao
				.obtenerVotacionesEnElQuePuedeVotarElVotante(votante);
		modeloDeTabla.setVotaciones(votaciones);
	}

	/**
	 * Carga las votaciones y llama al controlador principal para mostrar la vista de votaciones en
	 * curso y asignar dicha vista como panel predecesor al panel de los resultados.
	 */
	public void abrir() {
		cargarVotaciones();
		controlador.asignarPanelPredecesorDeResultados("votacionesEnCurso");
		controlador.mostrarPanel("votacionesEnCurso");
	}

	/**
	 * Llama al controlador principal para volver a la vista del login cuando es solicitado.
	 */
	public void cerrarSesionFueSolicitada() {
		controlador.abrirLogin();
	}
	/**
	 * Llama al controlador principal para ir a la vista de resultados cuando es solicitado.
	 */
	public void abirResultadosFueSolicitada() {
		controlador.abrirEscogerResultado();
	}
	/**
	 * Obtiene el modelo de tabla.
	 * @return una tabla de tipo TableModel.
	 */
	public TableModel getModeloDeTabla() {
		return modeloDeTabla;
	}
	/**
	 * Llama al controlador principal para abrir la vista de opciones cuando es solicitado e indica
	 * la votación seleccionada.
	 * @param id el identificador numérico único de la votación.
	 */
	public void abrirVotacionFueSolicitado(int id) {
		controlador.abrirOpcion(votaciones.get(id));
	}
}