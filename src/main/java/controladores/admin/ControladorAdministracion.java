package controladores.admin;

import controladores.votante.ControladorResultados;
import vistas.admin.MenuAdministracion;

public class ControladorAdministracion {

    private final MenuAdministracion vista;

    public ControladorAdministracion() {
        this.vista = new MenuAdministracion(this);
    }

    public void iniciar() {
        this.vista.mostrar();
    }

    public void mostrarPanelDeControlDeVotaciones() {
        new ControladorControlDeVotaciones().iniciar();
    }

    public void mostrarMenuCreacionDeVotacion() {
        new ControladorCreacionDeVotacion().iniciar();
    }

    public void mostrarMenuResultados() {
        new ControladorResultados().iniciar();
    }
}
