package controladores.admin;

import controladores.ControladorAplicacion;
import modelos.Opcion;
import modelos.Votacion;
import dao.VotacionDao;
import modelos.enums.CampoDeVotacion;
import utils.ValidadorDeDatos;
import vistas.admin.OpcionesTableModel;
import vistas.admin.PanelEditorVotaciones;

import javax.swing.table.TableModel;
import java.util.List;

public class ControladorEditorDeVotacion {

    private final PanelEditorVotaciones vista;
    private final ControladorAplicacion controlador;
    private Votacion votacion = new Votacion();
    private OpcionesTableModel modelo;

    public ControladorEditorDeVotacion(ControladorAplicacion controlador) {
        this.controlador = controlador;
        modelo = new OpcionesTableModel();
        vista = new PanelEditorVotaciones(this);
        this.controlador.agregarPanel(vista, "editor");
    }

    private void eliminarVotacion() {
        VotacionDao.eliminarVotacion(votacion);
    }

    private void editarCampoDeVotacion(CampoDeVotacion campo) {
        Object texto = ValidadorDeDatos.pedirEntrada(campo.getTexto().concat("> "));
        VotacionDao.actualizarCampoDeVotacion(votacion, campo, texto);
    }

    public void abrir(Votacion votacion) {
        this.votacion = votacion;
        modelo.setOpciones(votacion.getOpciones());
        cargarVotacion();
        controlador.mostrarPanel("editor");
        vista.moverScrollAInicio();
    }

    private void cargarVotacion() {
        vista.cargarVotacion(votacion);
    }

    public void cancelarFueSolicitado() {
        controlador.abrirAdministracion();
    }

    public OpcionesTableModel getModeloDeTabla() {
        return modelo;
    }

    public void eliminarOpcionFueSolicitado(int fila) {
        VotacionDao.eliminarOpcionDeVotacion(votacion, votacion.getOpciones().get(fila));
        votacion.getOpciones().remove(fila - 1);
        modelo.setOpciones(votacion.getOpciones());
    }

    public void guardarFueSolicitado() {

    }
}
