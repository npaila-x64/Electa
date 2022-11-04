package controladores.votante;

import modelos.Votacion;
import modelos.dao.VotacionDao;
import modelos.enums.EstadoDeVotacion;
import vistas.votante.MenuResultados;

import java.time.format.DateTimeFormatter;
import java.util.HashMap;
import java.util.List;

public class ControladorResultados {

    public ControladorResultados() {
        new MenuResultados(this).mostrar();
    }

    public HashMap<String, Object> obtenerDatosDeVotacion(Votacion votacion) {
        var datos = new HashMap<String, Object>();
        datos.put("titulo", votacion.getTitulo());
        datos.put("votosBlancos", votacion.getVotosBlancos());
        datos.put("votosPreferenciales", votacion.getVotosPreferenciales());
        datos.put("totalVotos", votacion.getTotalVotos());
        datos.put("fechaTiempoInicio", votacion.getFechaTiempoInicio()
                .format(DateTimeFormatter.ofPattern("dd-MM-yyyy hh:mm")));
        datos.put("fechaTiempoTermino", votacion.getFechaTiempoTermino()
                .format(DateTimeFormatter.ofPattern("dd-MM-yyyy hh:mm")));
        return datos;
    }

    public List<Votacion> obtenerVotacionesParaMostrar() {
        return VotacionDao.obtenerVotacionesConEstado(EstadoDeVotacion.FINALIZADO);
    }

    public Votacion obtenerVotacion(int opcionElegida) {
        List<Votacion> votaciones = VotacionDao.obtenerVotacionesFinalizadas();
        return votaciones.get(opcionElegida -1);
    }
}
