package controladores.admin;

import modelos.Votacion;
import dao.VotacionDao;
import modelos.enums.EstadoDeVotacion;
import vistas.admin.MenuAdministracionDeVotaciones;

import java.util.List;

public class ControladorAdministracionDeVotaciones {

    public ControladorAdministracionDeVotaciones() {
        new MenuAdministracionDeVotaciones(this).mostrar();
    }

    public void mostrarEditorDeVotacion(Integer opcionElegida) {
        Votacion votacion = obtenerVotacion(opcionElegida);
//        new ControladorEditorDeVotacion(votacion.getId());
    }

    private Votacion obtenerVotacion(Integer opcionElegida) {
        List<Votacion> votaciones = VotacionDao.obtenerVotaciones();
        return votaciones.get(opcionElegida -1);
    }

    public boolean votacionNoEsEditable(Integer opcionElegida){
        Votacion votacion = obtenerVotacion(opcionElegida);
        if(votacion.estaEnEstado(EstadoDeVotacion.EN_CURSO) ||
                votacion.estaEnEstado(EstadoDeVotacion.FINALIZADO)){
            mostrarMensajeVotacionNoEditable();
            return true;
        }
        return false;
    }

    private void mostrarMensajeVotacionNoEditable(){
        System.out.println("\nNo es posible editar la votación seleccionada, por favor elija otra\n");
    }
}
