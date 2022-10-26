package controladores.admin;

import modelos.Votacion;
import modelos.dao.VotacionDao;
import vistas.admin.MenuCreacionDeVotacion;

import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;

public class ControladorCreacionDeVotacion {

    private final MenuCreacionDeVotacion vista;

    public ControladorCreacionDeVotacion() {
        this.vista = new MenuCreacionDeVotacion(this);
    }

    public void iniciar() {
        Votacion votacion = new Votacion();
        vista.ingresarDatosCampo();
        situarDatosCampo(votacion);
        VotacionDao.crearVotacion(votacion);
        vista.mostrarVotacionCreadaConExito();
        new ControladorCreacionDeOpcion(votacion.getId()).iniciar();
    }

    private void situarDatosCampo(Votacion votacion) {
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
