package controladores.admin;

import modelos.Votacion;
import modelos.dao.VotacionDao;
import vistas.admin.MenuAdministracionDeVotaciones;

import java.util.List;

public class ControladorAdministracionDeVotaciones {

    public ControladorAdministracionDeVotaciones() {
        var vista = new MenuAdministracionDeVotaciones(this);
        vista.mostrar();
    }

    public void mostrarEditorDeVotacion(Integer opcionElegida) {
        Votacion votacion = obtenerVotacion(opcionElegida);
        new ControladorEditorDeVotacion(votacion.getId()).iniciar();
    }

    private Votacion obtenerVotacion(Integer opcionElegida) {
        List<Votacion> votaciones = VotacionDao.obtenerVotaciones();
        return votaciones.get(opcionElegida -1);
    }
}
