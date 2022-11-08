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
        // TODO Arreglar este desorden
        EstadoDeVotacion estado = votacion.getEstadoDeVotacion();
        if (estado.equals(EstadoDeVotacion.BORRADOR)) return;
        var fechaTiempoInicio = votacion.getFechaTiempoInicio();
        var fechaTiempoTermino = votacion.getFechaTiempoTermino();
        if (fechaTiempoAhora.isAfter(fechaTiempoTermino)) {
            estado = EstadoDeVotacion.FINALIZADO;
        } else if (fechaTiempoAhora.isBefore(fechaTiempoInicio)) {
            estado = EstadoDeVotacion.PENDIENTE;
        } else {
            estado = EstadoDeVotacion.EN_CURSO;
        }
        votacion.setEstadoDeVotacion(estado);
    }

    public static LocalDateTime obtenerFechaTiempoAhora() {
        return LocalDateTime.now();
    }
}