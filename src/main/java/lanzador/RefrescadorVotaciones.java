package lanzador;

import modelos.enums.EstadoDeVotacion;
import modelos.Votacion;

import java.time.LocalDateTime;
import java.util.List;

import dao.VotacionDao;


/*
    Clase con métodos dedicados a refrescar
    contínuamente los estados de las votaciones,
    requiere ser ejecutado en un hilo aparte
 */

public class RefrescadorVotaciones {

    public static void main(String[] args) {
        try {
            refrescarEstadosDeVotaciones();
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }

    public static void refrescarEstadosDeVotaciones() throws InterruptedException {
        while (true) {
            List<Votacion> votaciones = VotacionDao.obtenerVotaciones();
            for (Votacion votacionSiguiente : votaciones) {
                asignarEstadoAVotacion(obtenerFechaTiempoAhora(), votacionSiguiente);
            }
            VotacionDao.escribirVotaciones(votaciones);
            System.out.println("Estados actualizados: " + obtenerFechaTiempoAhora());
            Thread.sleep(1000);
        }
    }

    public static void asignarEstadoAVotacion(LocalDateTime fechaTiempoAhora, Votacion votacion) {
        EstadoDeVotacion estado = votacion.getEstadoDeVotacion();
        if (estado.equals(EstadoDeVotacion.BORRADOR)) return;
        var fechaTiempoInicio = votacion.getFechaTiempoInicio();
        var fechaTiempoTermino = votacion.getFechaTiempoTermino();
        estado = obtenerEstadoPorFecha(fechaTiempoAhora, fechaTiempoInicio, fechaTiempoTermino);
        votacion.setEstadoDeVotacion(estado);
    }

    private static EstadoDeVotacion obtenerEstadoPorFecha(LocalDateTime fechaAhora, LocalDateTime fechaInicio, LocalDateTime fechaTermino) {
        if (fechaAhora.isAfter(fechaTermino)) {
            return EstadoDeVotacion.FINALIZADO;
        } else if (fechaAhora.isBefore(fechaInicio)) {
            return EstadoDeVotacion.PENDIENTE;
        } else {
            return EstadoDeVotacion.EN_CURSO;
        }
    }

    public static LocalDateTime obtenerFechaTiempoAhora() {
        return LocalDateTime.now();
    }
}