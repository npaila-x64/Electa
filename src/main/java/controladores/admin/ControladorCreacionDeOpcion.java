package controladores.admin;

import modelos.Votacion;
import modelos.dao.VotacionDao;
import utils.ValidadorDeDatos;
import vistas.admin.MenuCreacionDeOpcion;

public class ControladorCreacionDeOpcion {

    private final MenuCreacionDeOpcion vista;
    private final Integer idVotacion;

    public ControladorCreacionDeOpcion(Integer idVotacion) {
        this.vista = new MenuCreacionDeOpcion(this);
        this.idVotacion = idVotacion;
    }

    public void iniciar() {
        this.vista.mostrar();
    }

    public Votacion obtenerVotacion() {
        return VotacionDao.obtenerVotacionPorID(VotacionDao.obtenerVotaciones(), idVotacion);
    }

    public void agregarOpcionDeVotacion() {
        String opcion = ValidadorDeDatos
                .pedirEntrada("Escriba la opción que desea agregar\n> ", 35);
        if (VotacionDao.opcionYaExiste(obtenerVotacion(), opcion)){
            vista.mostrarOpcionYaExiste(opcion);
            agregarOpcionDeVotacion();
        } else {
            VotacionDao.agregarOpcionAVotacion(obtenerVotacion(), opcion);
        }
    }
}
