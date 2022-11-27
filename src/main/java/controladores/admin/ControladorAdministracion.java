package controladores.admin;

import controladores.ControladorAplicacion;
import vistas.admin.PanelAdministracion;

public class ControladorAdministracion {

    private final PanelAdministracion vista;
    private final ControladorAplicacion controlador;

    public ControladorAdministracion(ControladorAplicacion controlador) {
        this.controlador = controlador;
        vista = new PanelAdministracion(this);
        this.controlador.agregarPanel(vista, "administracion");
    }

    public void mostrarPanelDeControlDeVotaciones() {
        new ControladorAdministracionDeVotaciones();
    }

    public void mostrarMenuCreacionDeVotacion() {
        new ControladorCreacionDeVotacion().iniciar();
    }

    public void cerrarSesionFueSolicitado() {
        controlador.abrirLogin();
    }

    public void verResultadosFueSolicitado() {
        controlador.abrirResultados();
    }

    public void abrir() {
        controlador.asignarPanelPredecesorDeResultados("administracion");
        controlador.mostrarPanel("administracion");
    }
}
