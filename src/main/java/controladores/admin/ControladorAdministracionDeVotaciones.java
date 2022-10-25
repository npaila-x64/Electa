package controladores.admin;

import modelos.Votacion;
import modelos.dao.VotacionDao;
import vistas.admin.MenuAdministracionDeVotaciones;

import java.util.List;

public class ControladorAdministracionDeVotaciones {

    private final MenuAdministracionDeVotaciones vista;
    private final VotacionDao votacionDao;
    private List<Votacion> votaciones;

    public ControladorAdministracionDeVotaciones() {
        this.vista = new MenuAdministracionDeVotaciones(this);
        this.votacionDao = new VotacionDao();
        refrescarVotaciones();
    }

    public void iniciar() {
        this.vista.mostrar();
    }

    public void mostrarEditorDeVotacion(Integer opcionElegida) {
        Votacion votacion = obtenerVotacion(opcionElegida);
        new ControladorEditorDeVotacion(votacion).iniciar();
    }

    public List<Votacion> obtenerVotaciones() {
        return this.votaciones;
    }

    private Votacion obtenerVotacion(Integer opcionElegida) {
        return votaciones.get(opcionElegida - 1);
    }

    public void refrescarVotaciones() {
        this.votaciones = votacionDao.obtenerVotaciones();
    }
}
