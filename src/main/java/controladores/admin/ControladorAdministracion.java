package controladores.admin;

import controladores.votante.ControladorResultados;
import modelos.Votacion;
import vistas.admin.MenuAdministracion;

import java.util.List;

public class ControladorAdministracion {

    private final MenuAdministracion vista;

    public ControladorAdministracion() {
        this.vista = new MenuAdministracion(this);
    }

    public void iniciar() {
        this.vista.mostrar();
    }

    public void mostrarPanelDeControlDeVotaciones() {
        new ControladorAdministracionDeVotaciones().iniciar();
    }

    public void mostrarMenuCreacionDeVotacion() {
        new ControladorCreacionDeVotacion().iniciar();
    }

    public void mostrarMenuResultados() {
        new ControladorResultados().iniciar();
    }
}
