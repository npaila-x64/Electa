package lanzador;

import modelos.enums.EstadoDeVotacion;
import modelos.Votacion;

import java.time.LocalDateTime;
import java.util.List;

import dao.VotacionDao;


/**
 * Dedicada a refrescar continuamente los estados de las votaciones, se ejecuta en un hilo
 * aparte de la aplicación principal, de esta forma funciona en tiempo real para multiples
 * usuarios.
 */

public class RefrescadorVotaciones {
    /**
     * Se encarga de llamar al método que refresca los estados de las votaciones y manejar
     * las excepciones de tipo InterruptedException que pueda lanzar.
     * @param args
     */

    public static void main(String[] args) {
        try {
            refrescarEstadosDeVotaciones();
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * Se encarga de asignar estados actualizados en cada votación, para luego
     * sobreescribirlas en el archivo. Esta acción se repite cada 1000 milisegundos.
     *
     * @throws InterruptedException Cuando ocurre una interrupción inesperada en el proceso
     * de refrescar los estados de las votaciones.
     */

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

    /**
     * Se encarga de asignar el estado que le corresponde a una votación dada, guiándose
     * por márgenes de tiempo.
     *
     * @param fechaTiempoAhora Indica la fecha y hora actuales.
     * @param votacion Indica la votación a la cual se le busca asignar un estado.
     */

    public static void asignarEstadoAVotacion(LocalDateTime fechaTiempoAhora, Votacion votacion) {
        EstadoDeVotacion estado = votacion.getEstadoDeVotacion();
        if (estado.equals(EstadoDeVotacion.BORRADOR)) return;
        var fechaTiempoInicio = votacion.getFechaTiempoInicio();
        var fechaTiempoTermino = votacion.getFechaTiempoTermino();
        estado = obtenerEstadoPorFecha(fechaTiempoAhora, fechaTiempoInicio, fechaTiempoTermino);
        votacion.setEstadoDeVotacion(estado);
    }

    /**
     * Se encarga de identificar que estado le corresponde a una votación en un momento
     * dado, según su fecha de inicio y termino.
     *
     * @param fechaAhora Indica la fecha y hora actuales.
     * @param fechaInicio Indica la fecha y hora de inicio de la votación.
     * @param fechaTermino Indica la fecha de y hora de termino de la votación.
     * @return Devuelve un estado de tipo EstadoDeVotacion.
     */

    private static EstadoDeVotacion obtenerEstadoPorFecha(LocalDateTime fechaAhora, LocalDateTime fechaInicio, LocalDateTime fechaTermino) {
        if (fechaAhora.isAfter(fechaTermino)) {
            return EstadoDeVotacion.FINALIZADO;
        } else if (fechaAhora.isBefore(fechaInicio)) {
            return EstadoDeVotacion.PENDIENTE;
        } else {
            return EstadoDeVotacion.EN_CURSO;
        }
    }

    /**
     * Se encarga retornar la fecha y hora actuales.
     * @return Devuelve una fecha y hora de tipo LocalDateTime.
     */

    public static LocalDateTime obtenerFechaTiempoAhora() {
        return LocalDateTime.now();
    }
}