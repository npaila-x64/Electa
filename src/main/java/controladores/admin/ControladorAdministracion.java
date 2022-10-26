package controladores.admin;

import controladores.votante.ControladorResultados;
import modelos.Votacion;
import vistas.admin.MenuAdministracion;

import java.util.List;

public class ControladorAdministracion {

    private final MenuAdministracion vista;

    public ControladorAdministracion() {
        new MenuAdministracion(this).mostrar();
    }

    public void mostrarPanelDeControlDeVotaciones() {
        new ControladorAdministracionDeVotaciones();
    }

    public void mostrarMenuCreacionDeVotacion() {
        new ControladorCreacionDeVotacion().iniciar();
    }

    public void mostrarMenuResultados() {
        new ControladorResultados().iniciar();
    }
}
