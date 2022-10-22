package controladores.votante;

import modelos.Votacion;
import modelos.dao.VotacionDao;
import modelos.enums.Estado;
import vistas.votante.MenuResultados;

import java.time.format.DateTimeFormatter;
import java.util.HashMap;
import java.util.List;

public class ControladorResultados {

    private final MenuResultados vista;
    private final VotacionDao votacionDao;
    private List<Votacion> votaciones;

    public ControladorResultados() {
        this.vista = new MenuResultados(this);
        this.votacionDao = new VotacionDao();
        refrescarVotaciones();
    }

    public void iniciar() {
        this.vista.mostrar();
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
        refrescarVotaciones();
        return this.votaciones;
    }

    public Votacion obtenerVotacion(int opcionElegida) {
        return this.votaciones.get(opcionElegida - 1);
    }

    private void refrescarVotaciones() {
        this.votaciones = this.votacionDao.obtenerVotacionesConEstado(Estado.FINALIZADO);
    }
}
