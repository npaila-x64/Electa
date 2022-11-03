package controladores.admin;

import modelos.Votacion;
import modelos.dao.VotacionDao;
import modelos.enums.EstadoDeVotacion;
import vistas.admin.MenuAdministracionDeVotaciones;

import java.util.List;

public class ControladorAdministracionDeVotaciones {

    public ControladorAdministracionDeVotaciones() {
        new MenuAdministracionDeVotaciones(this).mostrar();
    }

    public void mostrarEditorDeVotacion(Integer opcionElegida) {
        Votacion votacion = obtenerVotacion(opcionElegida);
        new ControladorEditorDeVotacion(votacion.getId());
    }

    private Votacion obtenerVotacion(Integer opcionElegida) {
        List<Votacion> votaciones = VotacionDao.obtenerVotaciones();
        return votaciones.get(opcionElegida -1);
    }

    public boolean seEncuentraEnCurso(Integer opcionElegida){
        Votacion votacion = obtenerVotacion(opcionElegida);
        return votacion.getEstadoDeVotacion().equals(EstadoDeVotacion.EN_CURSO);
    }

    public boolean seEncuentraFinalizada(Integer opcionElegida){
        Votacion votacion = obtenerVotacion(opcionElegida);
        return votacion.getEstadoDeVotacion().equals(EstadoDeVotacion.FINALIZADO);
    }

    public boolean noEsEditable(Integer opcionElegida){
        if(seEncuentraEnCurso(opcionElegida) || seEncuentraFinalizada(opcionElegida)){
            mostrarMensajeVotacionNoEditable();
            return true;
        }
        return false;
    }

    private void mostrarMensajeVotacionNoEditable(){
        System.out.println("\nNo es posible editar la votación seleccionada, por favor elija otra\n");
    }
}
