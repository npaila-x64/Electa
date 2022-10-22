package controladores.admin;

import modelos.Votacion;
import modelos.dao.VotacionDao;
import utils.ValidadorDeDatos;
import vistas.admin.MenuCreacionDeOpcion;

public class ControladorCreacionDeOpcion {

    private final MenuCreacionDeOpcion vista;
    private final VotacionDao votacionDao;
    private final Votacion votacion;

    public ControladorCreacionDeOpcion(Votacion votacion) {
        this.vista = new MenuCreacionDeOpcion(this);
        this.votacionDao = new VotacionDao();
        this.votacion = votacion;
    }

    public void iniciar() {
        this.vista.mostrar();
    }

    public Votacion obtenerVotacion() {
        return this.votacion;
    }

    public void agregarOpcionDeVotacion() {
        // TODO Validar ingreso de opción vacía
        String opcion = ValidadorDeDatos
                .pedirEntrada("Escriba la opción que desea agregar\n> ", 35);
        if (votacionDao.opcionYaExiste(votacion, opcion)){
            vista.mostrarOpcionYaExiste(opcion);
            agregarOpcionDeVotacion();
        } else {
            votacionDao.agregarOpcionAVotacion(votacion, opcion);
        }
    }
}
