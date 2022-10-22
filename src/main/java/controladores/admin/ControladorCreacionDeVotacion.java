package controladores.admin;

import modelos.Votacion;
import modelos.dao.VotacionDao;
import vistas.admin.MenuCreacionDeVotacion;

import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;

public class ControladorCreacionDeVotacion {

    private final MenuCreacionDeVotacion vista;
    private final Votacion votacion;
    private final VotacionDao votacionDao;

    public ControladorCreacionDeVotacion() {
        this.vista = new MenuCreacionDeVotacion(this);
        this.votacion = new Votacion();
        this.votacionDao = new VotacionDao();
    }

    public void iniciar() {
        vista.ingresarDatosCampo();
        situarDatosCampo();
        votacionDao.crearVotacion(votacion);
        vista.mostrarVotacionCreadaConExito();
        new ControladorCreacionDeOpcion(votacion).iniciar();
    }

    private void situarDatosCampo() {
        votacion.setTitulo(vista.getCampoTitulo());
        votacion.setDescripcion(vista.getCampoDescripcion());
        LocalDate fechaInicioDate = LocalDate.parse(vista.getCampoFechaInicio(), DateTimeFormatter.ofPattern("dd-MM-yyyy"));
        LocalTime horaInicioTime = LocalTime.parse(vista.getCampoHoraInicio(), DateTimeFormatter.ofPattern("HH:mm"));
        votacion.setFechaTiempoInicio(fechaInicioDate.atTime(horaInicioTime));
        LocalDate fechaTerminoDate = LocalDate.parse(vista.getCampoFechaTermino(), DateTimeFormatter.ofPattern("dd-MM-yyyy"));
        LocalTime horaTerminoTime = LocalTime.parse(vista.getCampoHoraTermino(), DateTimeFormatter.ofPattern("HH:mm"));
        votacion.setFechaTiempoTermino(fechaTerminoDate.atTime(horaTerminoTime));
    }
}
