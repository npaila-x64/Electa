package controladores;

import modelos.Votacion;
import dao.VotacionDao;
import modelos.enums.EstadoDeVotacion;
import vistas.votante.PanelResultados;

import java.time.format.DateTimeFormatter;
import java.util.HashMap;
import java.util.List;

public class ControladorResultados {

    private ControladorAplicacion controlador;
    private PanelResultados vista;

    public ControladorResultados(ControladorAplicacion controlador) {
        this.controlador = controlador;
        vista = new PanelResultados(this);
        this.controlador.agregarPanel(vista, "resultados");
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

    public void abrir() {
        controlador.mostrarPanel("resultados");
    }

    public void volverFueSolitado(String panel) {
        controlador.mostrarPanel(panel);
    }
}
