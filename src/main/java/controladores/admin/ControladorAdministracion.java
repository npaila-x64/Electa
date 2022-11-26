package controladores.admin;

import controladores.votante.ControladorResultados;
import vistas.admin.MenuAdministracion;

public class ControladorAdministracion {

    public ControladorAdministracion() {
        new MenuAdministracion(this).mostrar();
    }

    public void mostrarPanelDeControlDeVotaciones() {
        new ControladorAdministracionDeVotaciones();
    }

    public void mostrarMenuCreacionDeVotacion() {
        new ControladorCreacionDeVotacion().iniciar();
    }
}
