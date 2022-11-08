package controladores.admin;

import modelos.Opcion;
import modelos.Votacion;
import dao.VotacionDao;
import modelos.enums.CampoDeVotacion;
import utils.ValidadorDeDatos;
import vistas.admin.MenuEditorDeVotacion;

import java.util.List;

public class ControladorEditorDeVotacion {

    // TODO Terminar de descoplar a clase vista
    private final Integer idVotacion;

    public ControladorEditorDeVotacion(Integer idVotacion) {
        this.idVotacion = idVotacion;
        new MenuEditorDeVotacion(this).mostrar();
    }

    public Votacion obtenerVotacion() {
        return VotacionDao.obtenerVotacionPorID(VotacionDao.obtenerVotaciones(), idVotacion);
    }

    public List<Opcion> obtenerOpcionesDeVotacion() {
        return VotacionDao.obtenerVotacionPorID(VotacionDao.obtenerVotaciones(), idVotacion).getOpciones();
    }

    public void eliminarOpcionDeVotacion(int opcionElegida) {
        VotacionDao.eliminarOpcionDeVotacion(obtenerVotacion(),
                obtenerOpcionesDeVotacion().get(opcionElegida - 1));
    }

    public void mostrarMenuCreacionDeOpcion() {
        new ControladorCreacionDeOpcion(idVotacion).iniciar();
    }

    public void eliminarVotacion() {
        VotacionDao.eliminarVotacion(obtenerVotacion());
    }

    public void editarCampoDeVotacion(CampoDeVotacion campo) {
        Object texto = ValidadorDeDatos.pedirEntrada(campo.getTexto().concat("> "));
        VotacionDao.actualizarCampoDeVotacion(obtenerVotacion(), campo, texto);
    }
}
